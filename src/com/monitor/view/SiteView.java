package com.monitor.view;

import java.util.ArrayList;

//import com.handmark.pulltorefresh.library.*;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.*;

import com.monitor.adapter.SiteAdapter;
import com.monitor.bll.SiteBLL;
import com.monitor.dal.SiteDAL;
import com.monitor.model.CSource;
import com.monitor.model.CStatus;
import com.monitor.model.RecordInfo;
import com.monitor.model.SiteInfo;
import com.monitor.service.ServiceManager;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class SiteView extends BaseView {
	
	public static final int flag = 1000;
	private ArrayList<SiteInfo> list = null;
	private SiteAdapter adapter = null;
	private int refreshCount = 0, checkCount = 0;
	//private PullToRefreshListView mRefreshView;
	//private LoadingLayoutProxy loadingText;

	public SiteView(Activity activity, ListView mainView) {
		super(activity, mainView);
		
		ServiceManager.siteView = this;
		SiteBLL.init(activity);
	}
	
	public void init(){
		new GetDataTask().execute();
	}

	class GetDataTask extends AsyncTask<Void, Integer, ArrayList<SiteInfo>>{
		@Override
		protected ArrayList<SiteInfo> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SiteDAL dal = new SiteDAL(activity);
			ArrayList<SiteInfo> list = dal.queryAll();
			dal.close();
			
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<SiteInfo> result) {
			// TODO Auto-generated method stub
			list = result;
			initView();
			
			super.onPostExecute(result);
		}
	}
	
	class RefreshSite extends AsyncTask<SiteInfo, Integer, RecordInfo>{

		private int index = 0;
		private CSource source;
		
		public RefreshSite(int i, CSource cs){
			index = i;
			source = cs;
		}
		
		@Override
		protected RecordInfo doInBackground(SiteInfo... site) {
			// TODO Auto-generated method stub
			if(site.length > 0){
				return SiteBLL.connectSite(site[0], source);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(RecordInfo result){
			refreshResult(index, source, result);
			
			super.onPostExecute(result);
		}
	}
	
	private void initView(){
		//View view = LayoutInflater.from(activity).inflate(resource, root)
		adapter = new SiteAdapter(activity, list);
		mainView.setAdapter(adapter);
		
		//ServiceManager.sitelist = list;
	}
	
	public void refreshAllSite(){
		refreshCount = 0;
		checkCount = list.size();
		for(int i = 0; i < list.size(); i++){
			//list.get(i).status = -1;
			adapter.setItemStatus(i, CStatus.Refresh.ordinal());
			new RefreshSite(i, CSource.User).execute(list.get(i));
		}
	}
	
	public void refreshCheckSite(String checkId){
		checkCount = checkId.split(",").length;
		checkId = "," + checkId + ",";
		String id;
		for(int i = 0; i < list.size(); i++){
			id = "," + list.get(i).siteId + ",";
			if(checkId.indexOf(id) >= 0){
				adapter.setItemStatus(i, CStatus.Refresh.ordinal());
				new RefreshSite(i, CSource.Task).execute(list.get(i));
			}
		}
	}
	
	public void refreshResult(int index, CSource source, RecordInfo record){
		adapter.setItemStatus(index, record.status);
		refreshCount++;
		if(refreshCount >= list.size() || refreshCount >= checkCount){
			initView();
		}
		//add log
		if(source == CSource.Task){
			ServiceManager.addLog(record);
			
			//check status
			if(record.status == CStatus.Warning.ordinal() || record.status == CStatus.Error.ordinal()){
				ServiceManager.startVibrator();
				ServiceManager.playMusic();
			}
		}	
	}
	
	public void refresh(){
		//mainView.up
		init();
	}
	
	/*
	private void initView(){
		View view = LayoutInflater.from(activity).inflate(R.layout.site_main, null);
		mRefreshView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		mRefreshView.setMode(Mode.PULL_FROM_START);
		loadingText = (LoadingLayoutProxy) mRefreshView.getLoadingLayoutProxy();
		
		RefreshTime.add(ID);
		
		mRefreshView.setOnPullEventListener(new OnPullEventListener<ListView>(){

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				// TODO Auto-generated method stub
				if(direction.showHeaderLoadingLayout()){
					loadingText.setPullLabel("下拉可以刷新");
					loadingText.setReleaseLabel("释放开始刷新");
					loadingText.setRefreshingLabel("正在刷新");
					if(state == State.PULL_TO_REFRESH){
						loadingText.setLastUpdatedLabel(RefreshTime.getDownTime(ID) + "前更新");
					}
				}else{
					loadingText.setPullLabel("上拉加载更多");
					loadingText.setReleaseLabel("释放开始加载");
					loadingText.setRefreshingLabel("正在加载");
					loadingText.setLastUpdatedLabel("");
				}
			}
		});
		
		mRefreshView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub			
				new RefreshConnectTask(refreshView).execute(PullType.Down);
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new RefreshConnectTask(refreshView).execute(PullType.Up);			
			}
		});
	}

	private class RefreshConnectTask extends AsyncTask<Integer, Integer, Boolean> {
		PullToRefreshBase<ListView> mRefreshedView;

		public RefreshConnectTask(PullToRefreshBase<ListView> refreshedView) {
			mRefreshedView = refreshedView;
		}
		@Override
		protected Boolean doInBackground(Integer... pullType) {
			return refresh(pullType[0]);
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				update();				
			}
			mRefreshedView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	*/
}
