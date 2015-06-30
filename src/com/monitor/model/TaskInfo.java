package com.monitor.model;

import java.io.Serializable;

public class TaskInfo implements Serializable {
	private static final long serialVersionUID = -395318302408921052L;
	
	public int taskId = 0;
	public String addTime = "";
	public String taskName = "";
	public int timeType = 0;
	public int startTime = 0;
	public int endTime = 0;
	public String repeatDay = "";
	public int interval = 0;
	public int switchStatus = 0;
	public int warningBeep = 0;
	public int errorBeep = 0;
	public String siteId = "";
	public int status = 0;
	
	public TaskInfo(){
		
	}
	
	public TaskInfo(int taskId, String addTime, String taskName, int timeType, int startTime, int endTime, String repeatDay, 
			int interval, int switchStatus, int warningBeep, int errorBeep, String siteId, int status){
		this.taskId = taskId;	
		this.addTime = addTime;
		this.taskName = taskName;
		this.timeType = timeType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.repeatDay = repeatDay;
		this.interval = interval;
		this.switchStatus = switchStatus;
		this.warningBeep = warningBeep;
		this.errorBeep = errorBeep;
		this.siteId = siteId;
		this.status = status;
	}
}
