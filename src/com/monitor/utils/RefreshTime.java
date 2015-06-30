package com.monitor.utils;

import java.util.Calendar;
import java.util.Date;
import android.util.SparseArray;

final class TimeInfo{
	public Date down;
	public Date up;
}

public class RefreshTime {
	
	private static SparseArray<TimeInfo> listDate;	
	
	public static void init(){
		listDate = new SparseArray<TimeInfo>();
		loadConfig();
	}
	
	public static void add(int channelId){
		TimeInfo time = listDate.get(channelId);
		if(time == null){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			
			time = new TimeInfo();
			time.down = calendar.getTime();
			time.up = calendar.getTime();
			
			listDate.put(channelId, time);
		}
	}
	
	public static String DateToString(Date date1, Date date2){
		String text = "";
		long time = date1.getTime() - date2.getTime();
		
		long day = time / (1000 * 24 * 60 * 60);
		if(day > 0){
			time -= day * (1000 * 24 * 60 * 60);
			text = day + "天";
		}else{
			long hour = time / (1000 * 60 * 60);
			if(hour > 0){
				time -= hour * (1000 * 60 * 60);
				text = hour + "小时";
			}else{
				long minute = time / (1000 * 60);
				if(minute == 0) minute = 1;
				text = minute + "分钟";
			}
		}
		
		return text;
	}
	
	public static String getDownTime(int channelId){		
		Calendar calendar = Calendar.getInstance();
		TimeInfo time = listDate.get(channelId);

		String text = DateToString(calendar.getTime(), time.down);
		time.down = calendar.getTime();
		
		return text;
	}
	
	public static String getUpTime(int channelId){
		Calendar calendar = Calendar.getInstance();
		TimeInfo time = listDate.get(channelId);

		String text = DateToString(calendar.getTime(), time.up);
		time.up = calendar.getTime();
		
		return text;
	}
	
	public static void loadConfig(){
		
	}
	
	public static void saveConfig(){
		
	}
}
