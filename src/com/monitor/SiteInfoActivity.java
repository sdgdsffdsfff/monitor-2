package com.monitor;

import com.monitor.bll.SiteBLL;
import com.monitor.model.SiteInfo;
import com.monitor.view.SiteView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SiteInfoActivity extends BaseActivity {
	
	private SiteInfo site = null;
	private int siteId = 0;
	private String action = "Ìí¼Ó";
	private String[] encodingList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.site_add);
		
		site = (SiteInfo)getIntent().getSerializableExtra("site");
		if(site != null){
			siteId = site.siteId;
		}
		//siteId = intent.getIntExtra("siteId", 0);
		
		initEncoding();
		initView();
		initBackButton(action + "Õ¾µãÐÅÏ¢");
	}
	
	protected void initEncoding(){
		if(encodingList != null) return;
		encodingList = this.getResources().getStringArray(R.array.encoding);
	}
	
	protected void initView(){
		
		Button save = (Button)findViewById(R.id.site_info_save);
		final EditText sitename = (EditText)findViewById(R.id.site_info_sitename);
		final EditText siteurl = (EditText)findViewById(R.id.site_info_siteurl);
		final Spinner encoding = (Spinner)findViewById(R.id.site_info_encoding);
		
		if(siteId > 0){
			action = "±à¼­";
			//Log.i(siteId + "_" + action + "_");
			sitename.setText(site.siteName);
			siteurl.setText(site.siteUrl);
			for(int i = 0; i < encodingList.length; i++){
				if(site.encoding.equals(encodingList[i])){
					encoding.setSelection(i);
					break;
				}
			}
		}

		save.setOnClickListener(new View.OnClickListener() {		
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = sitename.getText().toString();
				String url = siteurl.getText().toString();
				String encode = encoding.getSelectedItem().toString();
				
				if((name != null && name.trim().length() == 0)
						|| (url != null && url.length() == 0) ){
					Toast.makeText(SiteInfoActivity.this, "ÇëÌîÐ´ÍøÕ¾Ãû³ÆºÍµØÖ·£¡", Toast.LENGTH_LONG).show();
					
					return;
				}
				
				int ret = SiteBLL.add(SiteInfoActivity.this, siteId, name, url, encode);
				if(ret > 0){
					Toast.makeText(SiteInfoActivity.this, "ÍøÕ¾" + action + "³É¹¦£¡", Toast.LENGTH_LONG).show();
					
					setResult(SiteView.flag);
					exit();
				}else{
					Toast.makeText(SiteInfoActivity.this, "ÍøÕ¾" + action + "Ê§°Ü£¡", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
