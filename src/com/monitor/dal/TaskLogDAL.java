package com.monitor.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.monitor.db.DBUtil;
import com.monitor.model.TaskLogInfo;

public class TaskLogDAL {
	private DBUtil db;
	private String table = "tasklog";
	
	public TaskLogDAL(Context context){
		db = new DBUtil(context);
	}
	
	public int add(TaskLogInfo log){
		ContentValues values = new ContentValues();
		values.put("taskId", log.taskId);
		values.put("addTime", log.addTime);
		values.put("startTime", log.startTime);
		values.put("endTime", log.endTime);
		values.put("runTimes", log.runTimes);
		values.put("streamLength", log.streamLength);
		values.put("status", log.status);
		
		return (int)db.insert(table, values);
	}
	
	public int update(TaskLogInfo log){
		ContentValues values = new ContentValues();
		values.put("endTime", log.endTime);
		values.put("runTimes", log.runTimes);
		values.put("streamLength", log.streamLength);
		values.put("status", log.status);
		
		return db.update(table, values, "logId=?", new String[]{ String.valueOf(log.logId) });
	}
	
	private TaskLogInfo getInfo(Cursor cursor){
		TaskLogInfo task = new TaskLogInfo();
		task.logId = cursor.getInt(0);
		task.taskId = cursor.getInt(1);
		task.addTime = cursor.getString(2);		
		task.startTime = cursor.getInt(3);
		task.endTime = cursor.getString(4);
		task.runTimes = cursor.getInt(5);
		task.streamLength = cursor.getInt(6);
		task.status = cursor.getInt(7);
		
		return task;
	}
	
	public ArrayList<TaskLogInfo> queryAll(){
		ArrayList<TaskLogInfo> list = new ArrayList<TaskLogInfo>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public TaskLogInfo query(int logId){
		Cursor cursor = db.rawQuery("select * from tasklog where logId = ?", new String[]{ String.valueOf(logId)});
		TaskLogInfo task = null;
		if(cursor.moveToFirst()){
			task = getInfo(cursor);
		}else{
			task = new TaskLogInfo();
		}
		return task;
	}
	
	public TaskLogInfo queryByRowID(int rowId){
		Cursor cursor = db.rawQuery("select * from tasklog where rowId = ?", new String[]{ String.valueOf(rowId)});
		TaskLogInfo task = null;
		if(cursor.moveToFirst()){
			task = getInfo(cursor);
		}else{
			task = new TaskLogInfo();
		}
		return task;
	}
	
	public ArrayList<TaskLogInfo> queryTaskAllLog(int taskId, int pageSize, int page){
		int start = (page - 1) * pageSize;
		Cursor cursor =  db.rawQuery("select * from tasklog where taskId = ? order by logId desc limit ? offset ?", 
				new String[]{ String.valueOf(taskId), String.valueOf(pageSize), String.valueOf(start)});
		ArrayList<TaskLogInfo> list = new ArrayList<TaskLogInfo>();
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public int delete(int logId){
		return db.delete(table, "logId=?", new String[]{ String.valueOf(logId) });
	}
	
	public void close(){
		db.close();
	}
}
