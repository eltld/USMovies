package com.pps.usmovie.mobile.widget;

import com.pps.usmovie.mobile.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ProgressDialog extends Dialog {

	private static int default_width = 160; //default width
    private static int default_height = 120;//default height
    
    public ProgressDialog(Context context){
    	 super(context, R.style.Theme_dialog);
    }
    
   public ProgressDialog(Context context, String message){
	   super(context, R.style.Theme_dialog);
	   setContentView(R.layout.layout_dialog);
  	 	//set window params
         Window window = getWindow();
         WindowManager.LayoutParams params = window.getAttributes();
         
         //set width,height by density and gravity
         float density = getDensity(context);
         params.width = (int) (default_width*density);
         params.height = (int) (default_width*density);
         params.gravity = Gravity.CENTER;
         
         window.setAttributes(params);
         TextView messageText = (TextView)findViewById(R.id.message);
         if(TextUtils.isEmpty(message)){
      	   messageText.setVisibility(View.GONE);
         }else{
      	   messageText.setVisibility(View.VISIBLE);
      	   messageText.setText(message);
         }
    }
    
	public ProgressDialog(Context context, int layout, int style) {
        this(context, default_width, default_height, layout, style);
    }
    
    public ProgressDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        
        //set content
        setContentView(layout);
        
        //set window params
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        
        //set width,height by density and gravity
        float density = getDensity(context);
        params.width = (int) (width*density);
        params.height = (int) (height*density);
        params.gravity = Gravity.CENTER;
        
        window.setAttributes(params);
    }
    
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

}
