package com.monitor;

import com.monitor.R;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		exit();
	}
	
	public void exit(){
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	protected void initBackButton(String titleText){
		ImageView back = (ImageView)findViewById(R.id.btn_back);
		TextView title = (TextView)findViewById(R.id.site_info_title);
		
		title.setText(titleText);
		
		back.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exit();
			}
		});
	}
	
	protected void showMessage(String message){
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
