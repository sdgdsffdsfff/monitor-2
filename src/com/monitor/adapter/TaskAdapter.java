package com.monitor.adapter;

import java.util.ArrayList;

import com.monitor.R;
import com.monitor.TaskInfoActivity;
import com.monitor.dal.TaskDAL;
import com.monitor.model.TaskInfo;
import com.monitor.utils.DBDate;
import com.monitor.utils.Redirect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TaskAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<TaskInfo> taskList;
	
	class ItemView{
		TextView taskname;
		TextView repeatDay;
		TextView refreshtime;
		TextView status;
		TextView info;
		ImageView edit;
		ImageView delete;
	}
	
	public TaskAdapter(Context context, ArrayList<TaskInfo> list){
		this.context = context;
		this.taskList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int index = position;
		ItemView item = null;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.task_main_item, null);
			item = new ItemView();
			item.taskname = (TextView)convertView.findViewById(R.id.task_main_item_taskname);
			item.repeatDay = (TextView)convertView.findViewById(R.id.task_main_item_repeatDay);
			item.refreshtime = (TextView)convertView.findViewById(R.id.task_main_item_refreshtime);
			item.status = (TextView)convertView.findViewById(R.id.task_main_item_status);
			item.info = (TextView)convertView.findViewById(R.id.task_main_item_info);
			item.edit = (ImageView)convertView.findViewById(R.id.task_main_item_edit);
			item.delete = (ImageView)convertView.findViewById(R.id.task_main_item_delete);
			
			item.edit.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Redirect.toAddTask(context, taskList.get(index));
				}
			});			
			item.delete.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context).setTitle("是否确认删除？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确认", new DialogInterface.OnClickListener() {					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							int ret = new TaskDAL(context).delete(taskList.get(index).taskId);
							if(ret > 0){
								taskList.remove(index);							
								notifyDataSetChanged();
								Toast.makeText(context, "任务删除成功！", Toast.LENGTH_LONG).show();
							}else{
								Toast.makeText(context, "任务删除失败！", Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("取消", null).show();
				}
			});
			
			convertView.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Redirect.toTaskLog(context, taskList.get(index));
				}
			});
			
			convertView.setTag(item);
			//itemList.set(index, item);
		}else{
			item = (ItemView)convertView.getTag();
		}
		TaskInfo task = taskList.get(index);
		item.taskname.setText(task.taskName);
		item.refreshtime.setText(getRepeatDay(task.repeatDay));
		if(task.switchStatus == 0){
			item.status.setText("关闭");
			item.status.setTextColor(Color.rgb(255, 100, 0));
		}else{
			item.status.setText("开启");
			item.status.setTextColor(Color.rgb(0, 160, 0));
		}
		if(task.timeType == 0){
			item.repeatDay.setText("全天");
		}else{
			item.repeatDay.setText(DBDate.getTime(task.startTime, task.endTime));
		}
		String tmp = "间隔：" + task.interval + "分钟　提醒：";
		if(task.warningBeep == 1) tmp += "警告 ";
		if(task.errorBeep == 1){
			if(task.warningBeep == 1) tmp += ", ";
			tmp += "异常  ";
		}
		tmp += "　网站：" + task.siteId.split(",").length + " 个";
		
		item.info.setText(tmp);
		
		return convertView;
	}
	
	
	
	private String getRepeatDay(String repeatDay){
		String day = "";
		String[] array = repeatDay.split(",");
		for(int i = 0; i < array.length; i++){
			if(i > 0) day += ",　";
			day += TaskInfoActivity.repeatDay[Integer.parseInt(array[i])];
		}
		return day;
	}
	

}
