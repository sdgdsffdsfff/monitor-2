package com.monitor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	private DBHelper dbhelper = null;
	private SQLiteDatabase sqlite = null;
	
	public DBUtil(Context context){
		//dbhelper = new DBHelper(context);
		dbhelper = DBHelper.getInstance(context);
		sqlite = dbhelper.getWritableDatabase();
	}
	
	public long insert(String table, ContentValues values){
		return sqlite.insert(table, null, values);
	}
	
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		return sqlite.update(table, values, whereClause, whereArgs);
	}
	
	public int delete(String table, String whereClause, String[] whereArgs){
		return sqlite.delete(table, whereClause, whereArgs);
	}
	
	public Cursor query(String table, String[] columns, String selection, 
			String[] selectionArgs, String groupBy, String having, String orderBy){
		return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, "");
	}
	
	public Cursor query(String table, String[] columns, String selection, 
			String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	public Cursor query(Boolean distinct, String table, String[] columns, String selection, 
			String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		return sqlite.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	public Cursor rawQuery(String sql, String[] selectionArgs){
		return sqlite.rawQuery(sql, selectionArgs);
	}
	
	
	public void close(){
		dbhelper.close();
		dbhelper = null;
		
		sqlite.close();
		sqlite = null;
	}
}
