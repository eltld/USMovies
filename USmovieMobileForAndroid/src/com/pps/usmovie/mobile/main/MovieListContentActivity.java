package com.pps.usmovie.mobile.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.common.LayoutAndDataFactory;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.layout.IDetailsLayout;

public class MovieListContentActivity extends BaseActivity {

	private static final int SHOW_COUNT = 100;//列表显示条目
	private LinearLayout container;
	private VideoDetailsItem videoDetailsItem;
	private ActorInfoItem actorInfoItem;
	private String type;
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.details_list_content_window);
		
		videoDetailsItem = (VideoDetailsItem)getIntent().getSerializableExtra("videoDetailsItem");
		actorInfoItem = (ActorInfoItem)getIntent().getSerializableExtra("actorInfoItem");
		
		type = getIntent().getStringExtra("type");
		
		container = (LinearLayout)findViewById(R.id.movie_details_list_content_container);
		init();
	}

	private void init() {
		if(!TextUtils.isEmpty(type)){
			if(type.equals("comment")){//评论列表
				initLayout(videoDetailsItem,"影评",DetailsDataType.DATA_YPET_COMMENT, SHOW_COUNT);
			}else if(type.equals("news")){//新闻列表
				initLayout(videoDetailsItem,"新闻",DetailsDataType.DATA_YPET_NEWS, SHOW_COUNT);
			}else if(type.equals("actor")){//演员列表
				initLayout(videoDetailsItem,"演员",DetailsDataType.DATA_YPET_ACTIOR, SHOW_COUNT);
			}else if(type.equals("actor_news")){//演员信息-新闻列表
				initActorLayout(actorInfoItem, "新闻", DetailsDataType.ACTOR_YPET_NEWS, SHOW_COUNT);
			}
		}
	}

	private void initLayout(VideoDetailsItem item, String title,DetailsDataType type, int showNum) {
		IDetailsLayout layout = LayoutAndDataFactory.getLayoutInstance(this, item, type);
		if(layout==null) return;
		layout.setListData(item.getVideo_id());
		container.addView(layout.getLayout());
	}
	private void initActorLayout(ActorInfoItem item, String title,DetailsDataType type, int showNum) {
		IDetailsLayout layout = LayoutAndDataFactory.getActorLayoutInstance(this, item, type);
		if(layout==null) return;
		layout.setListData(item.getActorId());
		container.addView(layout.getLayout());
	}
	
	@Override
	public void setTitle() throws USdramaException {
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
	}
	
	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn:
			this.finish();
			break;
		default:
			break;
		}
		
	}

}
