package com.monitor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.monitor.R;
import com.monitor.utils.Redirect;
import com.monitor.view.TaskView;

public class TaskFragment extends BaseFragment {
	
	private TaskView task = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.task_main, null);
		ImageView plus = (ImageView)view.findViewById(R.id.task_plus);
		
		ListView mainView = (ListView)view.findViewById(R.id.task_main_list);
		task = new TaskView(getActivity(), mainView);
		task.init();
		
		plus.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Redirect.toAddTask(getActivity(), null);
			}
		});
		
		return view;
	}
	
	public void refresh(){
		task.refresh();
	}
	
}
