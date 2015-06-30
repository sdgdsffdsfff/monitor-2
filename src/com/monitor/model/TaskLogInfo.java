package com.monitor.model;

import java.io.Serializable;

public class TaskLogInfo implements Serializable{
	private static final long serialVersionUID = -2714708706299097935L;
	
	public int logId = 0;
	public int taskId = 0;
	public String addTime = "";
	public int startTime = 0;
	public String endTime = "";
	public int runTimes = 0;
	public int streamLength = 0;
	public int status = 0;
	
	public TaskLogInfo(){
		
	}
	
	public TaskLogInfo(int logId, int taskId, String addTime, int startTime, String endTime, int runTimes,
			int streamLength, int status){
		this.logId = logId;
		this.taskId = taskId;
		this.addTime = addTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.runTimes = runTimes;
		this.streamLength = streamLength;
		this.status = status;
	}
}
