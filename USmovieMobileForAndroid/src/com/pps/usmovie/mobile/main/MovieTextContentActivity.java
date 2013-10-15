package com.pps.usmovie.mobile.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.USdramaException;

/**
 * 文本内容界面
 * @author zhangxiaole
 *
 */
public class MovieTextContentActivity extends BaseActivity{

	private VideoDetailsItem videoDetailsItem;
	private String type;
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.details_text_content_window);
		Intent intent = getIntent();
		
		String title = intent.getStringExtra("title");
		String time = intent.getStringExtra("time");
		String content = intent.getStringExtra("content");
		String source = intent.getStringExtra("source");
		if(TextUtils.isEmpty(title)&&TextUtils.isEmpty(time)&&TextUtils.isEmpty(content)){
			this.finish();
		}
		inin(title, time, content, source);
	}

	private void inin(String title, String time, String content, String source) {
		TextView contentTitle = (TextView)findViewById(R.id.details_text_content_title);
		TextView contentTime = (TextView)findViewById(R.id.details_text_content_time);
		TextView contentTextView = (TextView)findViewById(R.id.details_text_content);
		TextView contentSource = (TextView)findViewById(R.id.details_text_content_source);
		
		if(!TextUtils.isEmpty(title)){
			contentTitle.setVisibility(View.VISIBLE);
			contentTitle.setText(title);
		}
		if(!TextUtils.isEmpty(time)){
			contentTime.setVisibility(View.VISIBLE);
			contentTime.setText(time);
		}
		if(!TextUtils.isEmpty(content)){
			contentTextView.setText(content);
		}
		if(!TextUtils.isEmpty(source)){
			contentSource.setVisibility(View.VISIBLE);
			contentSource.setText(source);
		}
	}

	@Override
	public void setTitle() throws USdramaException {
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
	}

	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn://返回
			this.finish();
			break;
		default:
			break;
		}
	}
}
