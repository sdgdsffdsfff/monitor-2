package com.monitor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.monitor.dal.RecordDAL;
import com.monitor.model.RecordInfo;
import com.monitor.model.SiteInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class SiteRecordActivity extends BaseActivity {
	
	private WebView web = null;
	private SiteInfo site = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.site_record);
		
		site = (SiteInfo)getIntent().getSerializableExtra("site");
		
		initView();
		initBackButton("²é¿´ÍøÕ¾¼ì²â¼ÇÂ¼");
	}
	
	protected void initView(){
		web = (WebView)findViewById(R.id.site_record_web);
		WebSettings setting = web.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		web.setScrollBarStyle(0);
		web.loadUrl("file:///android_asset/record.html");
		web.addJavascriptInterface(new GetSiteAllRecord(site, this), "JavaScriptInterface");
	}
}

class GetSiteAllRecord{
	private SiteInfo siteInfo;
	private Context context;
	private int pageSize = 15;
	
	public GetSiteAllRecord(SiteInfo siteInfo, Context context){
		this.siteInfo = siteInfo;
		this.context = context;
	}
	
	public String getData(int page){
		JSONObject map;
		JSONArray array = new JSONArray();
		
		try {
			map = new JSONObject();
			map.put("siteName", siteInfo.siteName);
			map.put("siteUrl", siteInfo.siteUrl);
			map.put("pageSize", pageSize);
			array.put(map);
			
			RecordDAL dal = new RecordDAL(context);
			ArrayList<RecordInfo> list = dal.querySiteAllRecord(siteInfo.siteId, pageSize, page);
			dal.close();
			
			for(int i = 0; i < list.size(); i++){
				map = new JSONObject();
				map.put("recordId", list.get(i).recordId);
				map.put("addTime", list.get(i).addTime);
				map.put("status", list.get(i).status);
				map.put("connect", list.get(i).connect);
				map.put("links", list.get(i).links);
				map.put("scripts", list.get(i).scripts);
				map.put("size", list.get(i).size / 1024);
				array.put(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}			
		
		return array.toString();
	}
}
