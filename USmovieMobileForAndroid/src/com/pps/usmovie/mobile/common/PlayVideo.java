package com.pps.usmovie.mobile.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class PlayVideo {

	/**
	 * 播放视频
	 */
	public static void playVideo(Context context, String url){
		if(TextUtils.isEmpty(url)){
			return;
		}
		Intent intent= new Intent();        
	    intent.setAction("android.intent.action.VIEW");    
	    Uri content_url = Uri.parse(url);   
	    intent.setData(content_url);  
	    context.startActivity(intent);
	}
}
