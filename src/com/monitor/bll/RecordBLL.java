package com.monitor.bll;

import android.content.Context;
import com.monitor.dal.RecordDAL;
import com.monitor.model.RecordInfo;

public class RecordBLL {
	/*private static RecordDAL dal;
	
	public static void init(Context _context){
		//context = _context;
		dal = new RecordDAL(_context);
	}*/
	
	public static int add(Context context, int recordId, int siteId, String addTime, int addTime_, int connect, int size, int links, int scripts, String updateTime, int streamLength, int status){
		RecordInfo record = new RecordInfo(
			recordId,
			siteId,
			addTime,
			addTime_,
			connect,
			size,
			links,
			scripts,
			updateTime,
			streamLength,
			status
		);
		
		return add(context, record);
	}
	
	public static int add(Context context, RecordInfo record){
		RecordDAL dal = new RecordDAL(context);
		int ret = 0;
		
		if(record.recordId > 0){
			ret = dal.update(record);
		}else{
			ret = dal.add(record);
		}
		dal.close();
		
		return ret;
	}
}
