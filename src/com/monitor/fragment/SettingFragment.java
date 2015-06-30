package com.monitor.fragment;

import android.app.Activity;
import android.graphics.Color;
//import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.monitor.R;
import com.monitor.bll.SettingBLL;
import com.monitor.dal.SettingDAL;
import com.monitor.model.SettingInfo;
import com.monitor.service.ServiceManager;
import com.monitor.view.SwitchButton;

public class SettingFragment extends BaseFragment {
	
	private Activity activity = null;
	private View view = null;
	private SettingInfo setting = null;
	private String[] array = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		activity = getActivity();
		view = LayoutInflater.from(activity).inflate(R.layout.setting_main, null);
		array = getResources().getStringArray(R.array.timeout);
		
		//new GetDataTask().execute();
		setting = ServiceManager.setting;
		
		initView();
		
		return view;
	}
	
	public void initView(){
		final Spinner timeout = (Spinner)view.findViewById(R.id.setting_timeout);
		final SwitchButton warningLink = (SwitchButton)view.findViewById(R.id.setting_warninglink);
		final SwitchButton warningScript = (SwitchButton)view.findViewById(R.id.setting_warningscript);
		final SwitchButton vibrator = (SwitchButton)view.findViewById(R.id.setting_vibrator);
		final TextView ringWarning = (TextView)view.findViewById(R.id.setting_ringwarning);
		final TextView ringError = (TextView)view.findViewById(R.id.setting_ringerror);
		final SwitchButton ringRepeat = (SwitchButton)view.findViewById(R.id.setting_ringrepeat);
			
		for(int i = 0; i < array.length; i++){
			if(Integer.parseInt(array[i].replace("秒", "")) == setting.timeout){
				timeout.setSelection(i);
				break;
			}
		}
		
		if(setting.ringWarning.length() == 0){
			ringWarning.setText(SettingInfo.ringDefault);
			ringWarning.setTextColor(Color.GRAY);
		}else{
			ringWarning.setText(setting.ringWarning);
		}
		if(setting.ringError.length() == 0){
			ringError.setText(SettingInfo.ringDefault);
			ringError.setTextColor(Color.GRAY);
		}else{
			ringError.setText(setting.ringError);
		}
		if(setting.warningLink == 0) warningLink.setFlage(false);
		if(setting.warningScript == 0) warningScript.setFlage(false);
		if(setting.vibrator == 0) vibrator.setFlage(false);
		if(setting.ringRepeat == 0) ringRepeat.setFlage(false);
		
		Button save = (Button)view.findViewById(R.id.setting_save);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setting.timeout = Integer.parseInt(timeout.getSelectedItem().toString().replace("秒", ""));
				setting.ringWarning = ringWarning.getText().toString().replace(SettingInfo.ringDefault, "");
				setting.ringError = ringError.getText().toString().replace(SettingInfo.ringDefault, "");
				setting.ringRepeat = ringRepeat.getFlage() ? 1 : 0;
				setting.vibrator = vibrator.getFlage() ? 1 : 0;
				setting.warningLink = warningLink.getFlage() ? 1 : 0;
				setting.warningScript = warningScript.getFlage() ? 1 : 0;
				
				int ret = SettingBLL.add(activity, setting);
				if(ret > 0){
					Toast.makeText(activity, "设置成功！", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(activity, "设置失败！", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		ServiceManager.setting = setting;
	}
	
	/**/
	
	class GetDataTask extends AsyncTask<Void, Integer, SettingInfo>{
		@Override
		protected SettingInfo doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SettingDAL dal = new SettingDAL(activity);
			SettingInfo setting = dal.query(1);
			dal.close();
			
			return setting;
		}

		@Override
		protected void onPostExecute(SettingInfo result) {
			// TODO Auto-generated method stub
			setting = result;
			initView();
			
			super.onPostExecute(result);
		}
	}
	
}
