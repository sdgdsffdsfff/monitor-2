package com.monitor.utils;

public class Log {
	private static String TAG = "com.monitor";
	private static boolean showLog = true;
	
	public static void i(String tag, String message){
		if(showLog && Config.isDebug){
			android.util.Log.i(tag, message);
		}
	}
	
	public static void i(String message){
		i(TAG, message);
	}
}
