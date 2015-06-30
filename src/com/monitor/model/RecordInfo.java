package com.monitor.model;

import java.io.Serializable;

public class RecordInfo implements Serializable {
	private static final long serialVersionUID = 4573691629968236918L;
	
	public int recordId = 0;
	public int siteId = 0;
	public String addTime = "";
	public int source = 0;
	public int connect = 0;
	public int size = 0;
	public int links = 0;
	public int scripts = 0;
	public String updateTime = "";
	public int streamLength = 0;
	public int status = 0;
	
	public RecordInfo(){
		
	}
	
	public RecordInfo(int recordId, int siteId, String addTime, int source, int connect, int size, int links, int scripts, String updateTime, int streamLength, int status){
		this.recordId = recordId;
		this.siteId = siteId;
		this.addTime = addTime;
		this.source = source;
		this.connect = connect;
		this.size = size;
		this.links = links;
		this.scripts = scripts;
		this.updateTime = updateTime;
		this.streamLength = streamLength;
		this.status = status;
	}
}
