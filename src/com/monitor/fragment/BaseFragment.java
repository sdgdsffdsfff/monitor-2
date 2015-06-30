package com.monitor.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	
	//private Activity activity = null;
	
	@Override
	public void onAttach(Activity activity){
		//this.activity = activity;
		
		super.onAttach(activity);
	}
}
