package com.monitor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.TimePicker;

public class MyTimePicker extends TimePicker {

	public MyTimePicker(Context context) {
		super(context);
	}
	
	public MyTimePicker(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
	    if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)
	    {
	        ViewParent p = getParent();
	        if (p != null)
	            p.requestDisallowInterceptTouchEvent(true);
	    }

	    return false;
	}
}
