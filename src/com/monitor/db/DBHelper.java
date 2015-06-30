package com.monitor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DB_Name = "monitor.db";
	public static final int DB_Version = 2;
	private static DBHelper instance = null;

	public DBHelper(Context context){
		super(context, DB_Name, null, DB_Version);
	}
	
	public static synchronized DBHelper getInstance(Context context){
		if(instance == null){
			instance = new DBHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createTableSite(db);
		createTableRecord(db);
		createTableTask(db);
		createTableTaskLog(db);
		createTableSetting(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}
	
	private void createTableSite(SQLiteDatabase db){
		String sql = "create table if not exists [site](" +
			"[siteId] INTEGER NOT NULL PRIMARY KEY autoincrement, " +
			"[siteName] TEXT NOT NULL, " +
			"[siteUrl] TEXT NOT NULL, " +
			"[encoding] TEXT NOT NULL, " +
			"[addTime] INTEGER DEFAULT 0, " +
			"[status] INTEGER DEFAULT 0);";
		
		db.execSQL(sql);
	}
	
	private void createTableRecord(SQLiteDatabase db){
		String sql = "create table if not exists [record] (" +
		  "[recordId] INTEGER NOT NULL PRIMARY Key autoincrement, " +
		  "[siteId] INTEGER NOT NULL, " +
		  "[addTime] TEXT, " +
		  "[source] INTEGER DEFAULT 0, " +
		  "[connect] INTEGER DEFAULT 0, " +
		  "[size] INTEGER DEFAULT 0, " +
		  "[links] INTEGER DEFAULT 0, " +
		  "[scripts] INTEGER DEFAULT 0, " +
		  "[updateTime] TEXT, " +
		  "[streamLength] INTEGER DEFAULT 0, " +
		  "[status] INTEGER DEFAULT 0);";
		
		db.execSQL(sql);
	}
	
	private void createTableTask(SQLiteDatabase db){
		String sql = "create table if not exists [task] (" +
			  "[taskId] INTEGER NOT NULL PRIMARY Key autoincrement, " +
			  "[addTime] TEXT, " +
			  "[taskName] TEXT, " +
			  "[timeType] INTEGER DEFAULT 0, " +
			  "[startTime] INTEGER DEFAULT 0, " +
			  "[endTime] INTEGER DEFAULT 0, " +
			  "[repeatDay] TEXT, " +
			  "[interval] INTEGER DEFAULT 0, " +
			  "[switchStatus] INTEGER DEFAULT 0, " +
			  "[warningBeep] INTEGER DEFAULT 0, " +
			  "[errorBeep] INTEGER DEFAULT 0, " +
			  "[siteId] TEXT, " +
			  "[status] INTEGER DEFAULT 0);";
		db.execSQL(sql);
	}
	
	private void createTableTaskLog(SQLiteDatabase db){
		String sql = "create table if not exists [tasklog] (" +
			  "[logId] INTEGER NOT NULL PRIMARY Key autoincrement, " +
			  "[taskId] INTEGER NOT NULL, " +
			  "[addTime] TEXT, " +
			  "[startTime] INTEGER DEFAULT 0, " +
			  "[endTime] TEXT, " +
			  "[runTimes] INTEGER DEFAULT 0, " +
			  "[streamLength] INTEGER DEFAULT 0, " +
			  "[status] INTEGER DEFAULT 0);";
		db.execSQL(sql);
	}
	
	private void createTableSetting(SQLiteDatabase db){
		String sql = "create table if not exists [setting] (" +
			  "[settingId] INTEGER NOT NULL PRIMARY Key, " +
			  "[timeout] INTEGER DEFAULT 0, " +
			  "[warningLink] INTEGER DEFAULT 0, " +
			  "[warningScript] INTEGER DEFAULT 0, " +
			  "[vibrator] INTEGER DEFAULT 0, " +
			  "[ringWarning] TEXT, " +
			  "[ringError] TEXT, " +
			  "[ringRepeat] INTEGER DEFAULT 0, " +
			  "[status] INTEGER DEFAULT 0);";
		db.execSQL(sql);
	}
}
