package com.pps.usmovie.mobile.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.widget.ProgressDialog;

public abstract class BaseActivity extends Activity implements OnClickListener {

	public ProgressDialog progressDialog;
	public Button leftBtn = null;
	public ImageButton leftImgBtn = null;
	public Button rightBtn = null;
	public ImageButton rightImgBtn = null;
	public TextView titleTV = null;
//	public ProgressBar progressBar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try{
			setTheme(R.style.title);
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			progressDialog = new ProgressDialog(this, "");
			doCreate(savedInstanceState);
			initBasical();
		}catch(USdramaException e){
			Log.e("BaseActivity", "------>全局错误： " + e.getCauseMsg()+"-----"+e.toString());
		}
	}
	
	private void initBasical() throws USdramaException {
		titleTV = (TextView)findViewById(R.id.title_textview);
		leftBtn = (Button)findViewById(R.id.title_left_btn);
		leftImgBtn = (ImageButton)findViewById(R.id.title_left_img_btn);
		rightBtn = (Button)findViewById(R.id.title_right_btn);
		rightImgBtn = (ImageButton)findViewById(R.id.title_right_img_btn);
		
		if(leftBtn!=null) leftBtn.setOnClickListener(this);
		if(leftImgBtn!=null) leftImgBtn.setOnClickListener(this);
		if(rightBtn!=null) rightBtn.setOnClickListener(this);
		if(rightImgBtn!=null) rightImgBtn.setOnClickListener(this);
		setTitle();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			finish();
			break;

		default:
			try {
				doClick(v);
			} catch (USdramaException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public abstract void doCreate(Bundle bundle) throws USdramaException;
	public abstract void doClick(View view) throws USdramaException;
	public void setTitle() throws USdramaException{}

}
