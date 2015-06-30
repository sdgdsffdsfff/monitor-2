package com.monitor.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.monitor.db.DBUtil;
import com.monitor.model.SiteInfo;

public class SiteDAL {

	private DBUtil db;
	private String table = "site";
	
	public SiteDAL(Context context){
		db = new DBUtil(context);
	}
	
	public int add(SiteInfo site){
		ContentValues values = new ContentValues();
		//values.put("siteId", site.siteId);
		values.put("siteName", site.siteName);
		values.put("siteUrl", site.siteUrl);
		values.put("encoding", site.encoding);
		values.put("addTime", site.addTime);		
		values.put("status", site.status);
		
		return (int)db.insert(table, values);
	}
	
	public int update(SiteInfo site){
		ContentValues values = new ContentValues();
		values.put("siteName", site.siteName);
		values.put("siteUrl", site.siteUrl);
		values.put("encoding", site.encoding);
		
		return db.update(table, values, "siteId=?", new String[]{ String.valueOf(site.siteId) });
	}
	
	public ArrayList<SiteInfo> queryAll(){
		ArrayList<SiteInfo> list = new ArrayList<SiteInfo>();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			SiteInfo site = new SiteInfo();
			site.siteId = cursor.getInt(0);
			site.siteName = cursor.getString(1);
			site.siteUrl = cursor.getString(2);
			site.encoding = cursor.getString(3);
			site.addTime = cursor.getInt(4);
			site.status = cursor.getInt(5);
			
			list.add(site);
		}
		
		return list;
	}
	
	public SiteInfo query(int siteId){
		Cursor cursor = db.rawQuery("select * from site where siteId = ?", new String[]{ String.valueOf(siteId)});
		SiteInfo site = new SiteInfo();
		if(cursor.moveToFirst()){
			site.siteId = cursor.getInt(0);
			site.siteName = cursor.getString(1);
			site.siteUrl = cursor.getString(2);
			site.encoding = cursor.getString(3);
			site.addTime = cursor.getInt(4);
			site.status = cursor.getInt(5);
		}
		return site;
	}
	
	public int delete(int siteId){
		return db.delete(table, "siteId=?", new String[]{ String.valueOf(siteId) });
	}
	
	public void close(){
		db.close();
	}
}
