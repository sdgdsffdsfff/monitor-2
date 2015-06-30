package com.monitor.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.monitor.db.DBUtil;
import com.monitor.model.SettingInfo;

public class SettingDAL {
	private DBUtil db;
	private String table = "setting";
	
	public SettingDAL(Context context){
		db = new DBUtil(context);
	}
	
	public int add(SettingInfo setting){
		ContentValues values = new ContentValues();
		values.put("settingId", setting.settingId);
		values.put("timeout", setting.timeout);
		values.put("warningLink", setting.warningLink);
		values.put("warningScript", setting.warningScript);
		values.put("vibrator", setting.vibrator);
		values.put("ringWarning", setting.ringWarning);
		values.put("ringError", setting.ringError);
		values.put("ringRepeat", setting.ringRepeat);
		values.put("status", setting.status);
		
		return (int)db.insert(table, values);
	}
	
	public int update(SettingInfo setting){
		ContentValues values = new ContentValues();
		values.put("timeout", setting.timeout);
		values.put("warningLink", setting.warningLink);
		values.put("warningScript", setting.warningScript);
		values.put("vibrator", setting.vibrator);
		values.put("ringWarning", setting.ringWarning);
		values.put("ringError", setting.ringError);
		values.put("ringRepeat", setting.ringRepeat);
		
		return db.update(table, values, "settingId=?", new String[]{ String.valueOf(setting.settingId) });
	}
	
	private SettingInfo getInfo(Cursor cursor){
		SettingInfo setting = new SettingInfo();
		setting.settingId = cursor.getInt(0);
		setting.timeout = cursor.getInt(1);
		setting.warningLink = cursor.getInt(2);		
		setting.warningScript = cursor.getInt(3);
		setting.vibrator = cursor.getInt(4);
		setting.ringWarning = cursor.getString(5);
		setting.ringError = cursor.getString(6);
		setting.ringRepeat = cursor.getInt(7);
		setting.status = cursor.getInt(8);
		
		return setting;
	}
	
	public ArrayList<SettingInfo> queryAll(){
		ArrayList<SettingInfo> list = new ArrayList<SettingInfo>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){	
			list.add( getInfo(cursor) );
		}
		
		return list;
	}
	
	public SettingInfo query(int settingId){
		Cursor cursor = db.rawQuery("select * from setting where settingId = ?", new String[]{ String.valueOf(settingId)});
		SettingInfo setting = null;
		if(cursor.moveToFirst()){
			setting = getInfo(cursor);
		}else{
			setting = new SettingInfo();
		}
		return setting;
	}
	
	public int delete(int settingId){
		return db.delete(table, "settingId=?", new String[]{ String.valueOf(settingId) });
	}
	
	public void close(){
		db.close();
	}
}
