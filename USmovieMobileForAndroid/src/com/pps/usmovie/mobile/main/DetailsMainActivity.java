package com.pps.usmovie.mobile.main;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.common.LayoutAndDataFactory;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.IssuItem;
import com.pps.usmovie.mobile.data.TextLayoutItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.layout.DetailsGenreLayout;
import com.pps.usmovie.mobile.layout.DetailsMainLayout;
import com.pps.usmovie.mobile.layout.IDetailsLayout;

public class DetailsMainActivity extends BaseActivity {

	private LinearLayout container;
	ArrayList<TextLayoutItem> textList = new ArrayList<TextLayoutItem>();
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.movie_details_window);
		
		VideoDetailsItem item = (VideoDetailsItem)getIntent().getSerializableExtra("videoDetailsItem");
		
		container = (LinearLayout)findViewById(R.id.movie_details_container);
		init(item);
	}
	
	private void init(VideoDetailsItem item) {
		
		if(item==null){
			return;
		}
	
	//基本信息
	textList.clear();
	TextLayoutItem titleItem = new TextLayoutItem("中文名", item.getTitle(), false);
	textList.add(titleItem);
	TextLayoutItem aliasItem = new TextLayoutItem("别名", item.getAlias(), false);
	textList.add(aliasItem);
	TextLayoutItem enItem = new TextLayoutItem("英文名", item.getEn_name(), false);
	textList.add(enItem);
	setData(textList, "基本信息");
	
	//上映时间
	textList.clear();
	TextLayoutItem playTimeItem = new TextLayoutItem("上映时间", item.getPlay_time(), false);
	textList.add(playTimeItem);
	String mins = TextUtils.isEmpty(item.getMins())?item.getMins():item.getMins()+"分钟";
	TextLayoutItem longItem = new TextLayoutItem("片长", mins, false);
	textList.add(longItem);
	TextLayoutItem stateItem = new TextLayoutItem("地区", item.getState(), false);
	textList.add(stateItem);
	TextLayoutItem langueItem = new TextLayoutItem("对白语言", item.getLangue(), false);
	textList.add(langueItem);
	TextLayoutItem rankItem = new TextLayoutItem("分级", item.getRank(), false);
	textList.add(rankItem);
	setData(textList, "");
	
	//类型
	setGenre(item);
	//发行公司
	initLayout(item,"发行公司",DetailsDataType.DATA_YPET_ISSU, 0);
	//出品公司
	initMakerLayout(item);
	}
	/**出品公司 模块*/
	private void initMakerLayout(VideoDetailsItem item) {
		textList.clear();
		for(int i=0; i<item.getMakerList().size(); i++){
			IssuItem issuItem = item.getMakerList().get(i);
			TextLayoutItem textLayoutItem = new TextLayoutItem();
			textLayoutItem.setLeftText(issuItem.getIssuing());
			textList.add(textLayoutItem);
		}
		setData(textList, "出品公司");
	}

	/**类型 模块*/
	private void setGenre(VideoDetailsItem item) {
		IDetailsLayout layout = new DetailsGenreLayout(this, item, DetailsDataType.DATA_YPET_GENRE);
		layout.setWidget(0);
		container.addView(layout.getLayout());
	}


	/**发行公司*/
	private void initLayout(VideoDetailsItem item, String title,DetailsDataType type, int showNum) {
		textList.clear();
		for(int i=0; i<item.getIssuList().size(); i++){
			IssuItem issuItem = item.getIssuList().get(i);
			TextLayoutItem textLayoutItem = new TextLayoutItem();
			textLayoutItem.setLeftText(issuItem.getIssuing());
			textList.add(textLayoutItem);
		}
		setData(textList, "发行公司");
	}
	
	private void setData(ArrayList<TextLayoutItem>list, String title){
		IDetailsLayout layout = new DetailsMainLayout(this, DetailsDataType.VIEW_TYPE_SINGLETEXT, list, title);
		container.addView(layout.getLayout());
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

		default:
			break;
		}
		
	}

}
