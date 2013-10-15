package com.pps.usmovie.mobile.util;

import com.pps.usmovie.mobile.common.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferencesUtil {

private static String DEFAULT_VALUE = "";
	
	
	public static String getUserPreference(Context context, String key)
    {//获得登陆相关信息指定键的值;
    	SharedPreferences sp = context.getSharedPreferences(Constants.STAT_PREFERENCE, Context.MODE_PRIVATE);   
    	String result = sp.getString(key, DEFAULT_VALUE);
    	
    	return result;
    }
	
	/**
	 * 记录用户信息
	 */
	public static void saveStatInfo(Context context, String channel)
    {//统计信息
    	SharedPreferences sp = context.getSharedPreferences(Constants.STAT_PREFERENCE, Context.MODE_PRIVATE);   
		Editor editor = sp.edit();
		
		editor.putString(Constants.CHANNEL_PREFERENCES,   channel);		
		editor.commit();
    }
}
