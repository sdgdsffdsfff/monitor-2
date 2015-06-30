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
			case 0: text = "����"; break;
			case 1: text = "����"; break;
			case 2: text = "�쳣"; break;
			case 3: text = "���ڼ��..."; break;
			case 4: text = "�����쳣"; break;
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
