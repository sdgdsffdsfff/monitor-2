package com.monitor.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.monitor.db.DBUtil;
import com.monitor.model.CStatus;
import com.monitor.model.RecordInfo;

public class RecordDAL {
	private DBUtil db;
	private String table = "record";
	
	public RecordDAL(Context context){
		db = new DBUtil(context);
	}
	
	public int add(RecordInfo record){
		ContentValues values = new ContentValues();
		//values.put("siteId", record.siteId);
		values.put("siteId", record.siteId);
		values.put("addTime", record.addTime);
		values.put("source", record.source);
		values.put("connect", record.connect);
		values.put("size", record.size);
		values.put("links", record.links);
		values.put("scripts", record.scripts);
		values.put("updateTime", record.updateTime);
		values.put("streamLength", record.streamLength);
		values.put("status", record.status);
		
		return (int)db.insert(table, values);
	}
	
	public int update(RecordInfo record){
		ContentValues values = new ContentValues();
		values.put("connect", record.connect);
		values.put("size", record.size);
		values.put("links", record.links);
		values.put("scripts", record.scripts);
		values.put("updateTime", record.updateTime);
		values.put("streamLength", record.streamLength);
		values.put("status", record.status);
		
		return db.update(table, values, "recordId=?", new String[]{ String.valueOf(record.recordId) });
	}
	
	private RecordInfo getInfo(Cursor cursor){
		RecordInfo record = new RecordInfo();
		record.recordId = cursor.getInt(0);
		record.siteId = cursor.getInt(1);
		record.addTime = cursor.getString(2);
		record.source = cursor.getInt(3);
		record.connect = cursor.getInt(4);
		record.size = cursor.getInt(5);
		record.links = cursor.getInt(6);
		record.scripts = cursor.getInt(7);
		record.updateTime = cursor.getString(8);
		record.streamLength = cursor.getInt(9);
		record.status = cursor.getInt(10);
		
		return record;
	}
	
	public ArrayList<RecordInfo> queryAll(){
		ArrayList<RecordInfo> list = new ArrayList<RecordInfo>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public RecordInfo query(int recordId){
		Cursor cursor = db.rawQuery("select * from record where recordId = ?", new String[]{ String.valueOf(recordId)});
		RecordInfo record = null;
		if(cursor.moveToFirst()){
			record = getInfo(cursor);
		}else{
			record = new RecordInfo();
		}
		return record;
	}
	
	public RecordInfo querySiteRecord(int siteId){
		Cursor cursor =  db.rawQuery("select * from record where siteId = ? order by recordId desc limit 1", new String[]{ String.valueOf(siteId)});
		RecordInfo record = null;
		if(cursor.moveToFirst()){
			record = getInfo(cursor);
		}else{
			record = new RecordInfo();
		}
		return record;
	}
	
	public RecordInfo querySiteRecordX(int siteId){
		Cursor cursor =  db.rawQuery("select * from record where siteId = ? and status < ? order by recordId desc limit 1", 
				new String[]{ String.valueOf(siteId), String.valueOf(CStatus.Network.ordinal()) });
		RecordInfo record = null;
		if(cursor.moveToFirst()){
			record = getInfo(cursor);
		}else{
			record = new RecordInfo();
		}
		return record;
	}
	
	public ArrayList<RecordInfo> querySiteAllRecord(int siteId, int pageSize, int page){
		int start = (page - 1) * pageSize;
		Cursor cursor =  db.rawQuery("select * from record where siteId = ? order by recordId desc limit ? offset ?", 
				new String[]{ String.valueOf(siteId), String.valueOf(pageSize), String.valueOf(start)});
		ArrayList<RecordInfo> list = new ArrayList<RecordInfo>();
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public int delete(int recordId){
		return db.delete(table, "recordId=?", new String[]{ String.valueOf(recordId) });
	}
	
	public void close(){
		db.close();
	}
}
