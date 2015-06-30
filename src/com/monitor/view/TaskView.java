package com.monitor.view;

import java.util.ArrayList;

import com.monitor.MainActivity;
import com.monitor.adapter.TaskAdapter;
import com.monitor.dal.TaskDAL;
import com.monitor.model.TaskInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class TaskView extends BaseView {

	public static final int flag = 1001;
	private ArrayList<TaskInfo> list = null;
	private TaskAdapter adapter = null;
	
	public TaskView(Activity activity, ListView mainView) {
		super(activity, mainView);
	}
	
	public void init(){
		new GetDataTask().execute();
	}

	class GetDataTask extends AsyncTask<Void, Integer, ArrayList<TaskInfo>>{
		@Override
		protected ArrayList<TaskInfo> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			TaskDAL dal = new TaskDAL(activity);
			ArrayList<TaskInfo> list = dal.queryAll();
			dal.close();
			
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<TaskInfo> result) {
			// TODO Auto-generated method stub
			list = result;
			initView();
			
			super.onPostExecute(result);
		}
	}
	
	protected void initView(){
		adapter = new TaskAdapter(activity, list);
		mainView.setAdapter(adapter);
		
		MainActivity.serviceManager.checkAllTask(list);
	}
	
	public void refresh(){
		//mainView.up
		init();		
	}
}
