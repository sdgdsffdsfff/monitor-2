package com.monitor;

import java.util.ArrayList;

import com.monitor.bll.TaskBLL;
import com.monitor.dal.SiteDAL;
import com.monitor.model.SiteInfo;
import com.monitor.model.TaskInfo;
import com.monitor.utils.DBDate;
import com.monitor.view.MyTimePicker;
import com.monitor.view.SwitchButton;
import com.monitor.view.TaskView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class TaskInfoActivity extends BaseActivity {

	public static String[] repeatDay = {"日", "一", "二", "三", "四", "五", "六"};
	private TaskInfo task = null;
	private int timeType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_add);
		
		task = (TaskInfo)getIntent().getSerializableExtra("task");
		if(task == null){
			task = new TaskInfo(0, "", "", 0, 0, 0, "", 5, 1, 1, 1, "", 0);
		}
		timeType = task.timeType;
		
		initView();
		
		initBackButton("设置定时任务");
	}
	
	protected void initView(){
		final LinearLayout timeLayout = (LinearLayout)findViewById(R.id.task_info_time_layout);
		final LinearLayout repeatLayout = (LinearLayout)findViewById(R.id.task_info_repeat_layout);
		final LinearLayout siteLayout = (LinearLayout)findViewById(R.id.task_info_site_layout);
		final MyTimePicker start = (MyTimePicker)findViewById(R.id.task_info_start_time);
		final MyTimePicker end = (MyTimePicker)findViewById(R.id.task_info_end_time);
		final RadioGroup group = (RadioGroup)findViewById(R.id.task_info_time_group);
		final EditText taskname = (EditText)findViewById(R.id.task_info_taskname);
		final SwitchButton switchButton = (SwitchButton)findViewById(R.id.task_info_status);
		final SwitchButton warningBeep = (SwitchButton)findViewById(R.id.task_info_warning);
		final SwitchButton errorBeep = (SwitchButton)findViewById(R.id.task_info_error);
		final Spinner interval = (Spinner)findViewById(R.id.task_info_interval);
		Button save = (Button)findViewById(R.id.task_info_save);
		
		String rptd = "," + task.repeatDay + ",";
		for(int i = 0; i < repeatDay.length; i++){
			TextView text = new TextView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(7, 0, 8, 0);
			text.setPadding(5, 2, 5, 2);
			text.setClickable(true);
			text.setText(repeatDay[i]);
			text.setLayoutParams(params);
			text.setBackgroundResource(R.drawable.task_info_repeat_button);
			text.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {				
					boolean isSelected = !v.isSelected();
					v.setSelected(isSelected);
					((TextView)v).setTextColor( isSelected ? Color.WHITE : Color.BLACK);
				}
			});
			if(rptd.indexOf("," + i + ",") >= 0){
				text.setSelected(true);
				text.setTextColor(Color.WHITE);
			}
			
			repeatLayout.addView(text);
		}
		
		SiteDAL dal = new SiteDAL(this);
		ArrayList<SiteInfo> siteList = dal.queryAll();
		dal.close();
		String sites = "," + task.siteId + ",";
		for(int i = 0; i < siteList.size(); i++){
			CheckBox chk = new CheckBox(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 6, 0);
			chk.setLayoutParams(params);
			chk.setTag(siteList.get(i).siteId);
			chk.setText(siteList.get(i).siteName);
			
			if(sites.indexOf("," + siteList.get(i).siteId + ",") >= 0){
				chk.setChecked(true);
			}
			
			siteLayout.addView(chk);
		}
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.task_info_time_custom){
					timeLayout.setVisibility(View.VISIBLE);
					timeType = 1;
				}else{
					timeLayout.setVisibility(View.GONE);
					timeType = 0;
				}
			}
		});
		
		start.setIs24HourView(true);
		end.setIs24HourView(true);
		
		save.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task.taskName = taskname.getText().toString().trim();
				if(task.taskName.length() == 0){
					showMessage("请填写任务名称！");
					return;
				}
				task.siteId = "";
				for(int i = 0; i < siteLayout.getChildCount(); i++){
					if(((CheckBox)siteLayout.getChildAt(i)).isChecked()){
						if(task.siteId.length() > 0){
							task.siteId += ",";
						}
						task.siteId += siteLayout.getChildAt(i).getTag().toString();
					}
				}
				if(task.siteId.length() == 0){
					showMessage("请选择要检测的网站！");
					return;
				}
				task.repeatDay = "";
				for(int i = 0; i < repeatDay.length; i++){
					if(repeatLayout.getChildAt(i).isSelected()){
						if(task.repeatDay.length() > 0){
							task.repeatDay += ",";
						}
						task.repeatDay += i;
					}
				}
				task.timeType = timeType;
				if(task.timeType == 1){
					task.startTime = start.getCurrentHour() * 60 + start.getCurrentMinute();
					task.endTime = end.getCurrentHour() * 60 + end.getCurrentMinute();
				}
				task.switchStatus = switchButton.getFlage() ? 1 : 0;
				task.warningBeep = warningBeep.getFlage() ? 1 : 0;
				task.errorBeep = errorBeep.getFlage() ? 1 : 0;
				task.addTime = DBDate.getString();
				task.interval = Integer.parseInt(interval.getSelectedItem().toString().replace("分钟", ""));
				
				int ret = TaskBLL.add(TaskInfoActivity.this, task);
				if(ret > 0){
					showMessage("任务设置成功！");
					setResult(TaskView.flag);
					
					exit();
				}else{
					showMessage("任务设置失败！");
				}
			}
		});
		
		//init value
		taskname.setText(task.taskName);
		((RadioButton)group.getChildAt(timeType)).setChecked(true);
		if(task.timeType == 1){
			start.setCurrentHour(task.startTime / 60);
			start.setCurrentMinute(task.startTime % 60);
			end.setCurrentHour(task.endTime / 60);
			end.setCurrentHour(task.endTime % 60);
		}
		if(task.switchStatus == 0) switchButton.setFlage(false);
		if(task.warningBeep == 0) warningBeep.setFlage(false);
		if(task.errorBeep == 0) errorBeep.setFlage(false);
		String[] array = this.getResources().getStringArray(R.array.interval);
		for(int i = 0; i < array.length; i++){
			if(Integer.parseInt(array[i].replace("分钟", "")) == task.interval){
				interval.setSelection(i);
				break;
			}
		}
	}

}
