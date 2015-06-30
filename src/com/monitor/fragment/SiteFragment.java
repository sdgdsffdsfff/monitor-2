package com.monitor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.monitor.R;
import com.monitor.utils.Redirect;
import com.monitor.view.SiteView;

public class SiteFragment extends BaseFragment {
	
	private SiteView site = null;

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
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.site_main, null);
		ImageView plus = (ImageView)view.findViewById(R.id.site_plus);
		ImageView refresh = (ImageView)view.findViewById(R.id.site_refresh);
		
		ListView mainView = (ListView)view.findViewById(R.id.site_main_list);
		site = new SiteView(getActivity(), mainView);
		site.init();
		
		plus.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Redirect.toAddSite(getActivity(), null);
			}
		});
		refresh.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				site.refreshAllSite();
			}
		});
		
		return view;
	}
	
	public void refresh(){
		site.refresh();
	}
}
