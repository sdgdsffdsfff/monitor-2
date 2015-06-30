package com.monitor.model;

public enum CStatus {
	OK(0), Warning(1), Error(2), Refresh(3), Network(4);
	
	private CStatus(int _value){
		code = _value;
	}
	
	@Override
	public String toString(){
		String text = "";
		switch(code){		
			case 0: text = "正常"; break;
			case 1: text = "警告"; break;
			case 2: text = "异常"; break;
			case 3: text = "正在检测..."; break;
			case 4: text = "网络异常"; break;
		}
		return text;
	}
	
	public static CStatus valueOfInt(int status){
		switch(status){			
			case 0: return OK;
			case 1: return Warning;
			case 2: return Error;
			case 3: return Refresh;
			case 4: return Network;
		}
		return OK;
	}
	
	private int code;
}
