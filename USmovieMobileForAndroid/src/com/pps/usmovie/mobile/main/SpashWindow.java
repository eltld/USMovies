package com.pps.usmovie.mobile.main;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

public class SpashWindow extends Activity 
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		setTheme(R.style.title);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	 setContentView(R.layout.spash_window);
         Message message = new Message();
         message.what = 1;
         
         //发送一个延时消息;
         handler.sendMessageDelayed(message, Constants.SPASH_TIME);
         
         String channel = SharedPreferencesUtil.getUserPreference(this, Constants.CHANNEL_PREFERENCES);
         if(TextUtils.isEmpty(channel)){
//        	 StatisticsTask task = new StatisticsTask(this);
//        	 task.star();
         }
    }
	

	private Handler handler = new Handler()
	{
		public void handleMessage(Message message)
		{
			switch(message.what)
			{
			   case 1://闪屏结束
				   Intent intent = new Intent();
				   intent.setClass(SpashWindow.this, MovieHomeActivity.class);
				   startActivity(intent);
				   SpashWindow.this.finish();
                   break;  
			}			
			super.handleMessage(message);
		}
	};
	
}