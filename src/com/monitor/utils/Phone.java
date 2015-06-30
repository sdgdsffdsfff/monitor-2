package com.monitor.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Phone {
	
	/** ��ȡ��Ļ�Ŀ�� */
	public final static int getWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	public final static int getHeight(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
}
