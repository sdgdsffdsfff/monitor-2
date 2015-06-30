package com.monitor.bll;

import android.content.Context;
import com.monitor.dal.TaskDAL;
import com.monitor.model.TaskInfo;

public class TaskBLL {
	
	public static int add(Context context, int taskId, String addTime, String taskName, int timeType, int startTime, int endTime, 
			String repeatDay, int interval, int switchStatus, int warningBeep, int errorBeep, String siteId, int status){
		TaskInfo record = new TaskInfo(
			taskId,
			addTime,
			taskName,
			timeType,
			startTime,
			endTime,
			repeatDay,
			interval,
			switchStatus,
			warningBeep,
			errorBeep,
			siteId,
			status
		);
		
		return add(context, record);
	}
	
	public static int add(Context context, TaskInfo task){
		TaskDAL dal = new TaskDAL(context);
		int ret = 0;
		
		if(task.taskId > 0){
			ret = dal.update(task);
		}else{
			ret = dal.add(task);
		}
		dal.close();
		
		return ret;
	}
}
