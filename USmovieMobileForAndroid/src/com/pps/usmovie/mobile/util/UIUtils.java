package com.pps.usmovie.mobile.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class UIUtils {

//	/** 获取全局的Application对象  */
//	public static BaiYingApplication getApplication(Activity act) {
//		return (BaiYingApplication) act.getApplication();
//	}
	
	/** dip转化成px */
	public static int dip2px(Context context, float dipValue) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		final float scale = dm.density;
		return (int) (dipValue * scale + 0.5f);
	}

	/** px转化成dip */
	public static int px2dip(Context context, float pxValue) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		final float scale = dm.density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/** 判断字符串是否为空  */
	public static boolean isNullOrEmpty(CharSequence str) {
		return (str==null || str.length()==0 || str.equals("null") || str.equals("NULL"));
	}
}
