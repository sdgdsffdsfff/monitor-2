package com.monitor.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class FragmentViewPager extends ViewPager {
	@Override
	protected boolean canScroll(View v, boolean arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub
		if(v != this && v instanceof ViewPager){
			return true;
		}
		return super.canScroll(v, arg1, arg2, arg3, arg4);
	}

	public FragmentViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FragmentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
}
