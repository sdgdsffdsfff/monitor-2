package com.monitor.service;

import java.util.ArrayList;

import com.monitor.bll.TaskLogBLL;
import com.monitor.dal.SettingDAL;
import com.monitor.model.RecordInfo;
import com.monitor.model.SettingInfo;
import com.monitor.model.TaskInfo;
import com.monitor.model.TaskLogInfo;
import com.monitor.utils.Log;
import com.monitor.view.SiteView;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class ServiceManager {
	
	private static Activity activity = null;
	private Intent service = null;
	private static Vibrator vibrator = null;
	private static MediaPlayer mediaPlayer = null;
	private static boolean isVibrator = false;
	
	public static ArrayList<TaskInfo> tasklist = null;
	public static SiteView siteView = null;
	public static String taskName = "";
	public static int taskId = 0;
	public static int logId = 0;
	public static SettingInfo setting = null;
	public static boolean isStart = true;
	
	public ServiceManager(Activity _activity){
		activity = _activity;
		service = new Intent(activity, com.monitor.service.TaskService.class);
		SettingDAL dal = new SettingDAL(activity);
		setting = dal.query(1);
		dal.close();
	}
	
	public void start(){
		com.monitor.utils.Log.i("start service");
		activity.startService(service);
	}
	
	public void stop(){
		com.monitor.utils.Log.i("stop service");
		activity.stopService(service);
	}
	
	public void checkAllTask(ArrayList<TaskInfo> list){
		int taskStatus = 0;
		
		tasklist = list;
		taskName = "¡¾";
		
		for(int i = 0; i < list.size(); i++){
			taskStatus += list.get(i).switchStatus;
			if(list.get(i).switchStatus > 0){
				if(taskName.length() > 1) taskName += "£¬ ";
				taskName += list.get(i).taskName;
				taskId = list.get(i).taskId;
			}
		}
		taskName += "¡¿";
		if(taskStatus == 0){
			stop();
		}else{
			start();
		}
	}
	
	public static void initLog(){
		logId = TaskLogBLL.add(activity, taskId);
	}
	
	public static void addLog(RecordInfo record){
		TaskLogInfo log = TaskLogBLL.update(activity, logId, record);
		TaskService.update(record, log);
	}
	
	public static void startVibrator(){
		if(setting.vibrator == 0 || isStart) return;
		if(vibrator == null){
			vibrator = (Vibrator)activity.getSystemService(Service.VIBRATOR_SERVICE);
		}
		Log.i("start vibrator");
		vibrator.vibrate(new long[]{100, 400}, 1);
		isVibrator = true;
	}
	public static void stopVibrator(){
		if(vibrator != null && isVibrator){
			Log.i("stop vibrator");
			vibrator.cancel();
			isVibrator = false;
		}
	}
	public static void playMusic(){
		if(isStart) return;
		Log.i(isStart ? "true" : "false");
		
		if(mediaPlayer == null){
			mediaPlayer = MediaPlayer.create(activity, getDefaultRing()); //new MediaPlayer();
		}
		Log.i("start music");
		mediaPlayer.start();
	}
	public static void stopPlay(){
		if(mediaPlayer != null && mediaPlayer.isPlaying()){		
			Log.i("stop music");
			mediaPlayer.stop();
		}
	}
	private static Uri getDefaultRing(){
		return RingtoneManager.getActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_RINGTONE);
	}
}
