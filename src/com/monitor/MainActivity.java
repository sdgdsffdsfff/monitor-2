package com.monitor;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.monitor.R;
import com.monitor.adapter.BaseFragmentPagerAdapter;
import com.monitor.fragment.*;
import com.monitor.service.ServiceManager;
import com.monitor.utils.Redirect;
import com.monitor.view.SiteView;
import com.monitor.view.TaskView;

public class MainActivity extends FragmentActivity {
	
	//private int phoneWidth = 0;
	//private int phoneHeight = 0;
	private int itemCount = 0;
	private int currentItem = 0;
	private ViewPager viewPager = null;
	private ArrayList<BaseFragment> fragments = null;
	private ArrayList<ImageView> items = null;
	
	private SiteFragment site = null;
	private TaskFragment task = null;
	private DataFragment data = null;
	private SettingFragment setting = null;
	
	public static ServiceManager serviceManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		serviceManager = new ServiceManager(this);

		initItem();
		initFragment();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		ServiceManager.isStart = true;
		ServiceManager.stopVibrator();
		ServiceManager.stopPlay();
		
		super.onStart();	
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub	
		ServiceManager.isStart = false;
		
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		//com.monitor.utils.Log.i(featureId + "_" + item.getTitle().toString() + "_" + item.getItemId() + "_" + item.getOrder());
		switch(item.getOrder()){
			case 100: //setting
				((LinearLayout)findViewById(R.id.top_bar)).getChildAt(3).performClick();
				break;		
			case 101:  //about
				Redirect.toAbout(this);
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	protected void initItem(){
		items = new ArrayList<ImageView>();
		LinearLayout topbar = (LinearLayout)findViewById(R.id.top_bar);
		itemCount = topbar.getChildCount();
		
		for(int i = 0; i < itemCount; i++){
			ImageView image = (ImageView)topbar.getChildAt(i);
			
			image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for(int n = 0; n < itemCount; n++){
						ImageView view = items.get(n);
						if((ImageView)v == view){
							currentItem = n;
							
							viewPager.setCurrentItem(n);
							view.setSelected(true);
						}else{
							view.setSelected(false);
						}
					}
				}
			});
			
			items.add(image);
		}
		items.get(0).setSelected(true);
	}
	
	protected void initFragment(){
		fragments = new ArrayList<BaseFragment>();
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		
		site = new SiteFragment();
		task = new TaskFragment();
		data = new DataFragment();
		setting = new SettingFragment();
		
		fragments.add(site);
		fragments.add(task);
		fragments.add(data);
		fragments.add(setting);
		
		BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentItem);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				items.get(currentItem).setSelected(false);
				items.get(arg0).setSelected(true);
				
				currentItem = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		//super.onActivityResult(arg0, arg1, arg2);
		switch(requestCode){
		case SiteView.flag:
			//Log.i("onActivityReqult:" + requestCode + "_" + arg1);
			site.refresh();
			break;
		case TaskView.flag:
			task.refresh();
			break;
		}
		
	}
	
}
