package com.monitor.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.monitor.R;
import com.monitor.dal.RecordDAL;
import com.monitor.model.RecordInfo;

public class DataFragment extends BaseFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.data_main, null);

		WebView web = (WebView)view.findViewById(R.id.data_main_web);
				
		WebSettings setting = web.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		web.setScrollBarStyle(0);
		web.loadUrl("file:///android_asset/data.html");
		web.addJavascriptInterface(new GetAllSiteData(getActivity()), "JavaScriptInterface");
		
		return view;
	}
}

class GetAllSiteData{
	private Context context;
	public GetAllSiteData(Context context){
		this.context = context;
	}
	
	public String getData(){
		JSONObject map;
		JSONArray array = new JSONArray();
		
		try {			
			RecordDAL dal = new RecordDAL(context);
			ArrayList<RecordInfo> list = dal.queryAll();
			dal.close();
			
			for(int i = 0; i < list.size(); i++){
				map = new JSONObject();
				map.put("recordId", list.get(i).recordId);
				map.put("siteId", list.get(i).siteId);
				map.put("addTime", list.get(i).addTime);
				map.put("streamLength", list.get(i).streamLength);
				array.put(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}			
		
		return array.toString();
	}
}