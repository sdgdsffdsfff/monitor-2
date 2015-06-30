package com.monitor.service;

import com.monitor.MainActivity;
import com.monitor.R;
import com.monitor.model.CStatus;
import com.monitor.model.RecordInfo;
import com.monitor.model.TaskLogInfo;
import com.monitor.utils.Config;
import com.monitor.utils.DBDate;
import com.monitor.utils.Log;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class TaskService extends Service {

	TaskTimer timer = null;
	private static NotificationManager manager;
	private static Notification notification;
	private final static int NOTIFY_ID = 0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.i("on onCreate");
		timer = new TaskTimer();
		timer.start();
		
		ServiceManager.initLog();
		manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.i("on onDestroy");
		timer.stop();
		
		//stopForeground(true);
		manager.cancel(NOTIFY_ID);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		notification = new Notification(R.drawable.ic_launcher,
				getText(R.string.app_name), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		//notification.setLatestEventInfo(this, getText(R.string.app_name), 
		//		getText(R.string.notification_text) + ServiceManager.taskName, pendingIntent);
		
		RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);
		remoteView.setTextViewText(R.id.notification_taskname, ServiceManager.taskName);
		remoteView.setTextViewText(R.id.notification_time, DBDate.getDate());
		
		notification.contentView = remoteView;
		notification.contentIntent = pendingIntent;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		manager.notify(NOTIFY_ID, notification);
		
		//startForeground(Notification.FLAG_ONGOING_EVENT, notification);
		//startForeground(NOTIFY_ID, notification);
		
		//return super.onStartCommand(intent, flags, startId);
		return START_NOT_STICKY;
	}
	
	public static void update(RecordInfo record, TaskLogInfo log){
		CStatus S = CStatus.valueOfInt(record.status);
		RemoteViews remoteView = notification.contentView;
		
		Log.i("更新notification");
		remoteView.setTextViewText(R.id.notification_status, S.toString());
		remoteView.setTextColor(R.id.notification_status, Config.colors[record.status]);
		String text = DBDate.getShortTime() + "检测,  已检测" + log.runTimes + "次";
		remoteView.setTextViewText(R.id.notification_run, text);
		
		manager.notify(NOTIFY_ID, notification);
		//notification.notify();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		Log.i("on onStart");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("on onUnbind");
		
		return super.onUnbind(intent);		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		Log.i("on onUnbind");
		return null;
	}

}
