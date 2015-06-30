package com.monitor.bll;

import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.monitor.dal.RecordDAL;
import com.monitor.dal.SiteDAL;
import com.monitor.http.HttpUtils;
import com.monitor.model.CSource;
import com.monitor.model.CStatus;
import com.monitor.model.RecordInfo;
import com.monitor.model.SiteInfo;
import com.monitor.service.ServiceManager;
import com.monitor.utils.DBDate;

import android.annotation.SuppressLint;
import android.content.Context;

public class SiteBLL {
	
	private static Context context;
	private static SiteDAL dal;
	private static Pattern pLink = Pattern.compile("<a ", Pattern.CASE_INSENSITIVE);
	private static Pattern pScript = Pattern.compile("<script ", Pattern.CASE_INSENSITIVE);
	
	public static void init(Context _context){
		context = _context;
		dal = new SiteDAL(_context);
	}
	
	public static int add(Context context, int siteId, String siteName, String siteUrl, String encoding){
		SiteDAL dal = new SiteDAL(context);
		SiteInfo site = new SiteInfo(
			siteId,
			siteName,
			siteUrl,
			encoding,
			DBDate.getInt(),
			0
		);
		
		int ret = 0;
		if(siteId > 0){
			ret = dal.update(site);
		}else{
			ret = dal.add(site);
		}
		dal.close();
		
		return ret;
	}
	
	private static int getCount(Matcher m){
		int count = 0;
		while(m.find()){
			count++;
		}
		return count;
	}
	
	@SuppressLint("DefaultLocale")
	private static void parseContent(RecordInfo record, String body, int siteId){
		record.streamLength = body.length();
		if(body.indexOf("[#IndexInfo=") < 0){ //.html
			record.size = body.length();
			record.links = getCount( pLink.matcher(body) );
			record.scripts = getCount( pScript.matcher(body));
			record.updateTime = "";
		}else{//format text
			String[] lines = body.split("\n");
			
			for(int i = 0; i < lines.length; i++){
				String[] data = lines[i].split("=");
				if(data.length == 2){
					data[0] = data[0].trim().toLowerCase();
					data[1] = data[1].trim().replace("\r", "");
					if("size".equals(data[0])){
						record.size = Integer.parseInt(data[1]);
					}
					if("link".equals(data[0])){
						record.links = Integer.parseInt(data[1]);
					}
					if("script".equals(data[0])){
						record.scripts = Integer.parseInt(data[1]);
					}
					if("updatetime".equals(data[0])){
						record.updateTime = data[1];
					}
				}
			}
		}
		record.status = CStatus.OK.ordinal();
		
		if((record.links == 0 && ServiceManager.setting.warningLink == 1) 
			|| (record.scripts == 0 && ServiceManager.setting.warningScript == 1)){
			record.status = CStatus.Warning.ordinal();
		}else{
			RecordDAL dal = new RecordDAL(context);
			RecordInfo latest = dal.querySiteRecordX(siteId);
			dal.close();
			if(latest.recordId > 0 && 
					((latest.links != record.links && ServiceManager.setting.warningLink == 1)
					|| (latest.scripts != record.scripts && ServiceManager.setting.warningScript == 1))){
				record.status = CStatus.Warning.ordinal();
			}
		}
	}
	
	public static RecordInfo connectSite(SiteInfo site, CSource source){	
		RecordInfo record = new RecordInfo(
			0, site.siteId, DBDate.getString(), source.ordinal(), 0, 0, 0, 0, "", 0, CStatus.Error.ordinal()
		);
		if(HttpUtils.isNetworkConnect(context)){
			long startTime = new Date().getTime();
			String body = HttpUtils.getUrlContent(site.siteUrl, site.encoding, ServiceManager.setting.timeout);
			record.connect = (int)(new Date().getTime() - startTime);
			if(body.length() > 0){			
				parseContent(record, body, site.siteId);
			}
		}else{
			record.status = CStatus.Network.ordinal();
		}
		RecordBLL.add(context, record);
		//com.monitor.utils.Log.i("status:" + record.status + "_" + CStatus.OK.ordinal() + "_" + CStatus.Error.ordinal());
		
		return record;
	}
	public static RecordInfo connectSite(int siteId, CSource source){
		return connectSite(dal.query(siteId), source);
	}
}
