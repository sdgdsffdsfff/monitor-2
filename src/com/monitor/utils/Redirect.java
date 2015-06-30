package com.monitor.utils;

import com.monitor.*;
import com.monitor.model.SiteInfo;
import com.monitor.model.TaskInfo;
import com.monitor.view.SiteView;
import com.monitor.view.TaskView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Redirect {
	public static void toAddSite(Context from,  SiteInfo siteInfo){
		Intent intent = new Intent(from, SiteInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("site", siteInfo);
		//intent.putExtra("site", siteInfo);
		intent.putExtras(bundle);
		
		((Activity)from).startActivityForResult(intent, SiteView.flag);
		((Activity)from).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void toSiteRecord(Context from, SiteInfo siteInfo){
		Intent intent = new Intent(from, SiteRecordActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("site", siteInfo);
		intent.putExtras(bundle);
		
		from.startActivity(intent);
		((Activity)from).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void toTaskLog(Context from, TaskInfo taskInfo){
		Intent intent = new Intent(from, TaskLogActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("task", taskInfo);
		intent.putExtras(bundle);
		
		from.startActivity(intent);
		((Activity)from).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void toAddTask(Context from, TaskInfo taskInfo){
		Intent intent = new Intent(from, TaskInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("task", taskInfo);
		intent.putExtras(bundle);
		
		((Activity)from).startActivityForResult(intent, TaskView.flag);
		((Activity)from).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void toAbout(Context from){
		Intent intent = new Intent(from, AboutActivity.class);
		from.startActivity(intent);
		((Activity)from).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
}
