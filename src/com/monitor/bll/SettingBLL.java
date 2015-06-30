package com.monitor.bll;

import android.content.Context;
import com.monitor.dal.SettingDAL;
import com.monitor.model.SettingInfo;

public class SettingBLL {
	
	public static int add(Context context, SettingInfo setting){
		SettingDAL dal = new SettingDAL(context);
		int ret = 0;
		
		if(setting.settingId > 0){
			ret = dal.update(setting);
		}else{
			setting.settingId = 1;
			ret = dal.add(setting);
		}
		dal.close();
		
		return ret;
	}
}
