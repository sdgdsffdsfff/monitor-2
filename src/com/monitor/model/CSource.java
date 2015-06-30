package com.monitor.model;

public enum CSource {
	User(0), Task(1);
	
	private CSource(int _value){
		code = _value;
	}
	
	@Override
	public String toString(){
		String text = "";
		switch(code){		
			case 0: text = "手动"; break;
			case 1: text = "自动"; break;
		}
		return text;
	}
	
	public static CSource valueOfInt(int status){
		switch(status){			
			case 0: return User;
			case 1: return Task;
		}
		return User;
	}
	
	private int code;
}
