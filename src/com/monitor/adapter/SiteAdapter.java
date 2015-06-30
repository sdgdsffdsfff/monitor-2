package com.monitor.adapter;

import java.util.ArrayList;

import com.monitor.R;
import com.monitor.dal.RecordDAL;
import com.monitor.dal.SiteDAL;
import com.monitor.model.RecordInfo;
import com.monitor.model.SiteInfo;
import com.monitor.model.CStatus;
import com.monitor.utils.Config;
import com.monitor.utils.Redirect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SiteAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<SiteInfo> siteList;
	private ArrayList<ItemView> itemList;
	
	public class ItemView{
		TextView sitename;
		TextView siteurl;
		TextView refreshtime;
		TextView status;
		TextView info;
		ImageView edit;
		ImageView delete;
	}
	
	public SiteAdapter(Context context, ArrayList<SiteInfo> list){
		this.context = context;
		this.siteList = list;
		this.itemList = new ArrayList<ItemView>();
		for(int i = 0; i < list.size(); i++){
			itemList.add(null);
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return siteList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int index = position;
		ItemView item = null;

		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.site_main_item, null);
			item = new ItemView();
			item.sitename = (TextView)convertView.findViewById(R.id.site_main_item_sitename);
			item.siteurl = (TextView)convertView.findViewById(R.id.site_main_item_siteurl);
			item.refreshtime = (TextView)convertView.findViewById(R.id.site_main_item_refreshtime);
			item.status = (TextView)convertView.findViewById(R.id.site_main_item_status);
			item.info = (TextView)convertView.findViewById(R.id.site_main_item_info);
			item.edit = (ImageView)convertView.findViewById(R.id.site_main_item_edit);
			item.delete = (ImageView)convertView.findViewById(R.id.site_main_item_delete);
			
			item.edit.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Redirect.toAddSite(context, siteList.get(index));
				}
			});			
			item.delete.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context).setTitle("是否确认删除？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确认", new DialogInterface.OnClickListener() {					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							int ret = new SiteDAL(context).delete(siteList.get(index).siteId);
							if(ret > 0){
								siteList.remove(index);							
								notifyDataSetChanged();
								Toast.makeText(context, "网站删除成功！", Toast.LENGTH_LONG).show();
							}else{
								Toast.makeText(context, "网站删除失败！", Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("取消", null).show();
				}
			});
			
			convertView.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Redirect.toSiteRecord(context, siteList.get(index));
				}
			});
			
			convertView.setTag(item);
			itemList.set(index, item);
		}else{
			item = (ItemView)convertView.getTag();
		}	
		
		SiteInfo site = siteList.get(index);
		item.sitename.setText(site.siteName);
		item.siteurl.setText(site.siteUrl);
		
		//com.monitor.utils.Log.i(site.siteId + "_" + site.addTime);
		//最新record
		RecordDAL dal = new RecordDAL(context);
		RecordInfo record = dal.querySiteRecord(site.siteId);
		dal.close();

		if(record.recordId > 0){
			item.refreshtime.setText(record.addTime);
			item.info.setText(record.links + "个链接,  " + record.scripts + "个脚本,  " + (record.size / 1024) 
					+ "KB,  " + record.connect + "ms");
			setItemStatus(item, record.status);
		}else{
			setItemStatus(item, site.status);
		}
		
		return convertView;
	}
	
	public void setItemStatus(ItemView item, int status){
		CStatus S = CStatus.valueOfInt(status);
		item.status.setText(S.toString());
		item.status.setTextColor(Config.colors[status]);
		/*switch(S){
			case Refresh: item.status.setTextColor(Color.rgb(1, 50, 100));break;
			case Warning: item.status.setTextColor(Color.rgb(255, 100, 0));break;
			case Error: item.status.setTextColor(Color.rgb(200, 0, 0));break;
			case OK: item.status.setTextColor(Color.rgb(0, 160, 0)); break;
			case Network:item.status.setTextColor(Color.rgb(196, 139, 255)); break;
		}*/
	}
	public void setItemStatus(int index, int status){
		ItemView item = itemList.get(index);
		if(item == null) return;
		setItemStatus(item, status);
	}

}
