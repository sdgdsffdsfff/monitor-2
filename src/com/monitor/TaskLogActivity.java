package com.monitor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.monitor.dal.TaskLogDAL;
import com.monitor.model.TaskInfo;
import com.monitor.model.TaskLogInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class TaskLogActivity extends BaseActivity {

	private WebView web = null;
	private TaskInfo task = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_log);
		
		task = (TaskInfo)getIntent().getSerializableExtra("task");
		
		initView();
		initBackButton("查看任务运行记录");
	}
	
	protected void initView(){
		web = (WebView)findViewById(R.id.task_log_web);
		WebSettings setting = web.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		web.setScrollBarStyle(0);
		web.loadUrl("file:///android_asset/tasklog.html");
		web.addJavascriptInterface(new GetTaskAllLog(task, this), "JavaScriptInterface");
	}

}

class GetTaskAllLog{
	private TaskInfo taskInfo;
	private Context context;
	private int pageSize = 15;
	
	public GetTaskAllLog(TaskInfo taskInfo, Context context){
		this.taskInfo = taskInfo;
		this.context = context;
	}
	
	public String getData(int page){
		JSONObject map;
		JSONArray array = new JSONArray();
		
		try {
			map = new JSONObject();
			map.put("taskName", taskInfo.taskName);
			map.put("interval", taskInfo.interval);
			map.put("pageSize", pageSize);
			array.put(map);
			
			TaskLogDAL dal = new TaskLogDAL(context);
			ArrayList<TaskLogInfo> list = dal.queryTaskAllLog(taskInfo.taskId, pageSize, page);
			dal.close();
			
			for(int i = 0; i < list.size(); i++){
				map = new JSONObject();
				map.put("logId", list.get(i).logId);
				map.put("addTime", list.get(i).addTime);
				map.put("endTime", list.get(i).endTime);
				map.put("runTimes", list.get(i).runTimes);
				map.put("streamLength", list.get(i).streamLength);
				map.put("status", list.get(i).status);
				array.put(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}			
		
		return array.toString();
	}
}
