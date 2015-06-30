package com.monitor.model;

import java.io.Serializable;

public class SiteInfo implements Serializable {
	private static final long serialVersionUID = -9185227840801403085L;
	
	public int siteId = 0;
	public String siteName = "";
	public String siteUrl = "";
	public String encoding = "";
	public int addTime = 0;
	public int status = 0;
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public SiteInfo(){
		
	}
	
	public SiteInfo(int siteId, String siteName, String siteUrl, String encoding, int addTime, int status){
		this.siteId = siteId;
		this.siteName = siteName;
		this.siteUrl = siteUrl;
		this.encoding = encoding;
		this.addTime = addTime;
		this.status = status;
	}
}
