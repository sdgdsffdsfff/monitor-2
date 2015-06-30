package com.monitor.bll;

import android.content.Context;
import com.monitor.dal.TaskLogDAL;
import com.monitor.model.RecordInfo;
import com.monitor.model.TaskLogInfo;
import com.monitor.utils.DBDate;

public class TaskLogBLL {
	
	private static TaskLogInfo logInfo = null;
	
	public static int add(Context context, int logId, int taskId, String addTime, int startTime, 
			String endTime, int runTimes, int streamLength, int status){
		TaskLogInfo record = new TaskLogInfo(
			logId,
			taskId,
			addTime,
			startTime,
			endTime,
			runTimes,
			streamLength,
			status
		);
		
		return add(context, record);
	}
	
	public static int add(Context context, TaskLogInfo task){
		TaskLogDAL dal = new TaskLogDAL(context);
		int ret = 0;
		
		if(task.taskId > 0){
			ret = dal.update(task);
		}else{
			ret = dal.add(task);
		}
		dal.close();
		
		return ret;
	}
	
	public static int add(Context context, int taskId){
		TaskLogInfo record = new TaskLogInfo(
				0,
				taskId,
				DBDate.getString(),
				DBDate.getInt(),
				DBDate.getString(),
				0,
				0,
				0
			);
		
		TaskLogDAL dal = new TaskLogDAL(context);
		int rowId = dal.add(record);
		logInfo = dal.queryByRowID(rowId);
		dal.close();
		
		return logInfo.logId;
	}
	
	public static TaskLogInfo update(Context context, int logId, RecordInfo record){
		TaskLogDAL dal = new TaskLogDAL(context);
		if(logInfo == null || logInfo.logId != logId){
			logInfo = dal.query(logId);
		}
		if(logInfo.logId == logId){
			logInfo.endTime = DBDate.getString();
			logInfo.runTimes += 1;
			logInfo.streamLength += record.streamLength;
			logInfo.status = record.status;
			
			dal.update(logInfo);
		}
		dal.close();
		
		return logInfo;
	}
}
