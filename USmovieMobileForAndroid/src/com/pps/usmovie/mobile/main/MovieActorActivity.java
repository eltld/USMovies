package com.pps.usmovie.mobile.main;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.common.LayoutAndDataFactory;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.layout.IDetailsLayout;
import com.pps.usmovie.mobile.parser.ActorInfoCallBack;
import com.pps.usmovie.mobile.parser.ActorInfoParser;
import com.pps.usmovie.mobile.util.ImageLoader;
import com.pps.usmovie.mobile.util.UIUtils;

public class MovieActorActivity extends BaseActivity {

	private String TAG = "MovieActorActivity";
	private LinearLayout container;
	private ActorInfoItem actorInfoItem;
	private String json;
	private ImageLoader imageLoader;
	private static int SHOW_NUM = 3;
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.movie_actor_details_window);
		String actorId = getIntent().getStringExtra("actorId");
		json = getIntent().getStringExtra("json");
		if(!TextUtils.isEmpty(actorId)){
			ActorInfoParser.getActorInfo(actorId, new ActorInfoCallBack() {
				@Override
				public void onBackData(ActorInfoItem item) {
					if(progressDialog!=null) progressDialog.dismiss();
					setData(item);
				}
			});
			progressDialog.show();
		}
		container = (LinearLayout)findViewById(R.id.movie_actor_details_container);
	}
	
	protected void setData(ActorInfoItem item) {
		if (item != null) {
			if (item.getRet() == Constants.RET_SUCCESS_CODE) {
				actorInfoItem = item;
				initWidget(item);
			} else {
				if (!TextUtils.isEmpty(json)) {
					try {
						ActorInfoItem actorItem = ActorInfoParser.getActorInfo(json);
						actorInfoItem = actorItem;
						initWidget(actorItem);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				Toast.makeText(MovieActorActivity.this,item.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void initWidget(ActorInfoItem item) {
		imageLoader = new ImageLoader(this);
		//主要信息
		initMain(item);
		//相关视频
		initLayout(item,"相关视频",DetailsDataType.ACTOR_YPET_VIDEO, SHOW_NUM);
		//代表作品
		initLayout(item,"代表作品",DetailsDataType.ACTOR_YPET_WORKS, SHOW_NUM);
		//个人简介
		initLayout(item,"个人简介", DetailsDataType.ACTOR_YPET_INTRO, 0);
		//奖项
		initLayout(item,"奖项",DetailsDataType.ACTOR_YPET_AWARD, 0);
		//新闻
		initLayout(item,"新闻",DetailsDataType.ACTOR_YPET_NEWS, 1);
	}
	
	private void initLayout(ActorInfoItem item, String title,DetailsDataType type, int showNum) {
		IDetailsLayout layout = LayoutAndDataFactory.getActorLayoutInstance(this, item, type);
		if(layout==null) return;
		layout.setWidget(showNum);
		layout.setTitle(title);
		container.addView(layout.getLayout());
	}

	
	private void initMain(ActorInfoItem item) {
		LinearLayout details_main = (LinearLayout) findViewById(R.id.actor_details_main);
		details_main.setVisibility(View.VISIBLE);
		
		ImageView image = (ImageView) findViewById(R.id.actor_details_main_image);
		imageLoader.DisplayImage(item.getPic_src(), image);
		
		TextView mainTitle = (TextView) findViewById(R.id.details_main_cn_title);
		mainTitle.setText(item.getZh_name());
		TextView en_title = (TextView) findViewById(R.id.details_main_en_title);
		en_title.setText(item.getEn_name());
		TextView enName = (TextView) findViewById(R.id.actor_details_main_enName);
		enName.setText(item.getEn_name());
		TextView remark = (TextView) findViewById(R.id.actor_details_main_remark);
		remark.setText(item.getRemark());
		
		//相关图片
		setPicData(item);
	}

	private void setPicData(ActorInfoItem item) {
		RelativeLayout details_pic_RelativeLayout = (RelativeLayout)findViewById(R.id.actor_details_pic_RelativeLayout);
		details_pic_RelativeLayout.setOnClickListener(this);
		LinearLayout details_pic_LinearLayout = (LinearLayout) findViewById(R.id.actor_details_pic_LinearLayout);
		
		if (item.getPicList() != null && item.getPicList().size() > 0) {
			details_pic_RelativeLayout.setVisibility(View.VISIBLE);
			details_pic_RelativeLayout.setOnClickListener(this);
			for (int i = 0; i < item.getPicList().size(); i++) {
				if (i >= 4)
					break;
				final PicItem picItem = item.getPicList().get(i);
				ImageView imageView = new ImageView(this);
				imageLoader.DisplayImage(picItem.getPic_src(), imageView);
				int height = UIUtils.dip2px(this, 63);
				int marginRight = UIUtils.dip2px(this, 3);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						height, height);
				lp.rightMargin = marginRight;
				lp.gravity = Gravity.CENTER_VERTICAL;
				
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setBackgroundResource(R.drawable.imget_square_bg);
				details_pic_LinearLayout.addView(imageView);
			}
		}else {
			details_pic_RelativeLayout.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void setTitle() throws USdramaException {
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
		leftImgBtn.setOnClickListener(this);
	}
	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn:
			this.finish();
			break;
		case R.id.actor_details_pic_RelativeLayout://头部图片
			Intent intent = new Intent(this, PhotoGridActivity.class);
			intent.putExtra("videoId", actorInfoItem.getActorId());
			intent.putExtra("classType", Constants.ACTOR_CLASS);
			intent.putExtra("picList", actorInfoItem.getPicList());
			this.startActivity(intent);
			break;
		default:
			break;
		}
	}
}
