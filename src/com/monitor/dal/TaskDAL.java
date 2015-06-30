package com.monitor.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.monitor.db.DBUtil;
import com.monitor.model.TaskInfo;

public class TaskDAL {
	private DBUtil db;
	private String table = "task";
	
	public TaskDAL(Context context){
		db = new DBUtil(context);
	}
	
	public int add(TaskInfo task){
		ContentValues values = new ContentValues();
		values.put("siteId", task.siteId);
		values.put("addTime", task.addTime);
		values.put("taskName", task.taskName);
		values.put("timeType", task.timeType);
		values.put("startTime", task.startTime);
		values.put("endTime", task.endTime);
		values.put("repeatDay", task.repeatDay);
		values.put("interval", task.interval);
		values.put("switchStatus", task.switchStatus);
		values.put("warningBeep", task.warningBeep);
		values.put("errorBeep", task.errorBeep);
		values.put("siteId", task.siteId);
		values.put("status", task.status);
		
		return (int)db.insert(table, values);
	}
	
	public int update(TaskInfo task){
		ContentValues values = new ContentValues();
		values.put("taskName", task.taskName);
		values.put("timeType", task.timeType);
		values.put("startTime", task.startTime);
		values.put("endTime", task.endTime);
		values.put("repeatDay", task.repeatDay);
		values.put("interval", task.interval);
		values.put("switchStatus", task.switchStatus);
		values.put("warningBeep", task.warningBeep);
		values.put("errorBeep", task.errorBeep);
		values.put("siteId", task.siteId);
		
		return db.update(table, values, "taskId=?", new String[]{ String.valueOf(task.taskId) });
	}
	
	private TaskInfo getInfo(Cursor cursor){
		TaskInfo task = new TaskInfo();
		task.taskId = cursor.getInt(0);		
		task.addTime = cursor.getString(1);
		task.taskName = cursor.getString(2);
		task.timeType = cursor.getInt(3);
		task.startTime = cursor.getInt(4);
		task.endTime = cursor.getInt(5);
		task.repeatDay = cursor.getString(6);
		task.interval = cursor.getInt(7);
		task.switchStatus = cursor.getInt(8);
		task.warningBeep = cursor.getInt(9);
		task.errorBeep = cursor.getInt(10);
		task.siteId = cursor.getString(11);
		task.status = cursor.getInt(12);
		
		return task;
	}
	
	public ArrayList<TaskInfo> queryAll(){
		ArrayList<TaskInfo> list = new ArrayList<TaskInfo>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public TaskInfo query(int taskId){
		Cursor cursor = db.rawQuery("select * from task where taskId = ?", new String[]{ String.valueOf(taskId)});
		TaskInfo task = null;
		if(cursor.moveToFirst()){
			task = getInfo(cursor);
		}else{
			task = new TaskInfo();
		}
		return task;
	}
	
	public int delete(int taskId){
		return db.delete(table, "taskId=?", new String[]{ String.valueOf(taskId) });
	}
	
	public void close(){
		db.close();
	}
}
