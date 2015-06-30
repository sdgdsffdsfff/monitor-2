package com.monitor.view;

import android.app.Activity;
import android.widget.ListView;

public class BaseView {
	
	public Activity activity;
	public ListView mainView;
	
	public BaseView(Activity activity, ListView mainView){
		this.activity = activity;
		this.mainView = mainView;
	}
	
	public boolean refresh(int pullType){
		return false;
	}
	
	public void update(){
		
	}

}
