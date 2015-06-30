package com.monitor.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DBDate {
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static SimpleDateFormat format2 = new SimpleDateFormat("MM-dd HH:mm");
	static SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
	
	public static int getInt(){
		long time = new Date().getTime();
		return (int)(time / 1000);
	}
	
	public static String getString(int itime){
		long time = itime * 1000;
		Date date = new Date(time);
		
		
		return format.format(date);
	}
	
	public static String getString(){	
		return format.format(new Date());
	}
	
	public static String getDate(){
		return format2.format(new Date());
	}
	
	public static String getShortTime(){
		return format3.format(new Date());
	}
	
	public static String getTime(int startTime, int endTime){
		String sh = (startTime / 60) + ":";
		int sm = (startTime % 60);
		String start = sh + (sm < 10 ? "0" + sm : sm);
		
		String eh = (endTime / 60) + ":";
		int em = (endTime % 60);
		String end = eh + (em < 10 ? "0" + em : em);
		
		return start + "гн" + end;
	}
	
	public static int getHourMinitue(){
		Calendar c = Calendar.getInstance();
		return ( c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE) );
	}
	
	public static int getWeekday(){
		Calendar c = Calendar.getInstance();
		int week = c.get(Calendar.WEDNESDAY) - 1;
		if(week < 0){
			week = 0;
		}
		return week;
	}
}
