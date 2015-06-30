package com.monitor.service;

import java.util.ArrayList;

import com.monitor.model.TaskInfo;
import com.monitor.utils.DBDate;
import com.monitor.utils.Log;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class TaskTimer {
	
	private int status = 0;
	private int count = 0;
	private Handler handler = null;
	private ArrayList<TaskInfo> tasklist = null;
	private TaskInfo task;
	
	public void start(){
		status = 0;
		count = 0;
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 111 && status == 0){
					//Log.i("recvie...");
					
					checkTask();
				}
			}
			
		};
		new Thread(new TimerThread()).start();
	}
	
	public void pause(){
		status = 1;
	}
	
	public void restart(){
		status = 0;
		count = 0;
	}
	
	public void stop(){
		status = 2;
	}
	
	private void checkTask(){
		count++;
		tasklist = ServiceManager.tasklist;
		for(int time, week, i = 0; i < tasklist.size(); i++){
			task = tasklist.get(i);
			//Log.i("count=" + count + ",interval=" + task.interval + ",switch=" + task.switchStatus);
			if(task.switchStatus == 1 && (count % task.interval == 0)){ //开, 周期
				week = DBDate.getWeekday();
				//Log.i("week=" + week + ",repeatDay=" + task.repeatDay);
				if(task.repeatDay.indexOf(week + "") >= 0){ //重复天数
					time = DBDate.getHourMinitue();
					Log.i("time=" + time + ",timeType=" + task.timeType + ",start=" + task.startTime + ",end=" + task.endTime);
					if(task.timeType == 0
						|| (task.startTime < task.endTime && time >= task.startTime && time <= task.endTime)
						|| (task.startTime > task.endTime && (time >= task.startTime || time <= task.endTime))
						){ //时间
						
						//refresh(task.siteId);
						//Log.i("正在检测:" + task.siteId);
						ServiceManager.siteView.refreshCheckSite(task.siteId);
					}
				}
			}
		}
	}
	
	class TimerThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				while(true){
					if(status == 2) break;
					if(status == 0){
						Thread.sleep(60 * 1000);
						Message msg = new Message();
						msg.what = 111;
						handler.sendMessage(msg);
						
						//Log.i("send...");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
