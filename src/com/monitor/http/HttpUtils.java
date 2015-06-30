package com.monitor.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.monitor.utils.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("DefaultLocale")
public class HttpUtils {
	
	private static boolean isCheck = false;
	private static boolean isVB = false;
	
	public static String addTime(String url){
		if(url.indexOf("?") > 0){
			url += "&";
		}else{
			url += "?";
		}
		url += (new Date()).getTime();
		
		return url;
	}
	
	public static String getUrlContent(String url_path, String encoding, int timeout){
		url_path = addTime(url_path);
		Log.i("正在读取：" + url_path);
		try {
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(timeout * 1000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setRequestProperty("Accept-Charset", encoding);
			connection.setRequestProperty("contentType", encoding);
			//connection.connect();
			int code = connection.getResponseCode();
			if(code == 200){
				//Log.i("responseMessage:" + connection.getResponseMessage());
				//Log.i("contentLength:" + connection.getContentLength());

				return changeInputStream(connection.getInputStream(), encoding);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	private static String changeInputStream(InputStream inputStream, String encoding) {
		String content = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while((len = inputStream.read(data)) != -1){
				outputStream.write(data, 0, len);
			}
			content = new String(outputStream.toByteArray(), encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;
	}
	
	
	public static boolean isNetworkConnect(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = cm.getActiveNetworkInfo();
		if(net != null && net.isConnectedOrConnecting()){
			return true;
		}
		if(!isCheck){
			if("eeepc".equals(android.os.Build.DEVICE.toLowerCase())){
				isVB = true;
			}
			isCheck = true;
		}
		Log.i("真实网络连接状态:false");
		//return Config.isDebug;
		return isVB;
	}
}
