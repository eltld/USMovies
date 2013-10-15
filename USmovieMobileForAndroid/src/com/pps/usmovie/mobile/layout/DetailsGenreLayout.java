package com.pps.usmovie.mobile.layout;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.RoundItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.util.UIUtils;

/**
 * 详情-影片类型
 * @author zhangxiaole
 *
 */
public class DetailsGenreLayout extends BaseLayoutAndData implements OnClickListener,OnTouchListener, IDetailsLayout {

	private VideoDetailsItem videoDetailsItem;
	
	
	public DetailsGenreLayout(Context context, VideoDetailsItem item,DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
	}

	@Override
	public void setWidget(int size) {
		if(videoDetailsItem==null || videoDetailsItem.getClass_array()==null || videoDetailsItem.getClass_array().length<=0){
			return;
		}
		int height = UIUtils.dip2px(context, 45);
		for(int i=0; i<videoDetailsItem.getClass_array().length; i++){
			String genre = videoDetailsItem.getClass_array()[i];
			
			View itemView = View.inflate(context, R.layout.details_main_item, null);
			itemView.setOnClickListener(this);
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,height);
			itemView.setLayoutParams(lp);
			View line = itemView.findViewById(R.id.details_main_item_line);
			initData(itemView,genre);
			setBackGround(itemView, videoDetailsItem.getClass_array().length,i);
			if(videoDetailsItem.getClass_array().length == i+1){
				line.setVisibility(View.GONE);
			}
			container.addView(itemView);
		}
		
		int containerHeight = height * videoDetailsItem.getClass_array().length;
		LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,containerHeight);
		clp.weight=1;
		container.setLayoutParams(clp);
		
	}
	
	protected void setBackGround(View itemView, int listSize, int i) {
		Bundle bundle = new Bundle();
		bundle.putInt("index", i);
		bundle.putInt("num", listSize);
		itemView.setTag(bundle);
		itemView.setOnTouchListener(this);
		RoundItem.setItemBackGround_top(itemView);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setItemType(v, true);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			setItemType(v, false);
			break;
		default:
			break;
		}
		return false;
	}

	private void initData(View itemView, String genre) {
		TextView leftTV = (TextView)itemView.findViewById(R.id.details_main_leftTop_textView);
		TextView leftSubTV = (TextView)itemView.findViewById(R.id.details_main_leftBottom_textView);
		TextView RightTV = (TextView)itemView.findViewById(R.id.details_main_right_textView);
		ImageView arrow = (ImageView)itemView.findViewById(R.id.details_main_item_arrow);
		leftTV.setVisibility(View.GONE);
		if(!TextUtils.isEmpty(genre)){
			RightTV.setText(genre);
		}else RightTV.setVisibility(View.GONE);
	}

	@Override
	public void setListData(String... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitle(String title) {
		setTitleName(title);
	}

	@Override
	public View getLayout() {
		return pieceContainer;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	protected void setItemType(View v, boolean onTouchType) {
		RoundItem.setSelectItemBg_top(v, onTouchType);
		if (onTouchType) {

			TextView blackTitle = (TextView) v.findViewWithTag("black");
			if (blackTitle != null) {
				blackTitle.setTextColor(context.getResources().getColor(
						R.color.white));
			}
			TextView grayTitle = (TextView) v.findViewWithTag("gray");
			if (grayTitle != null) {
				grayTitle.setTextColor(context.getResources().getColor(
						R.color.white));
			}
			TextView black_secTitle = (TextView)v.findViewWithTag("black_sec");
			if (black_secTitle != null) {
				black_secTitle.setTextColor(context.getResources().getColor(
						R.color.white));
			}
		} else {
			TextView blackTitle = (TextView) v.findViewWithTag("black");
			if (blackTitle != null) {
				blackTitle.setTextColor(context.getResources().getColor(
						R.color.black));
			}
			TextView grayTitle = (TextView) v.findViewWithTag("gray");
			if (grayTitle != null) {
				grayTitle.setTextColor(context.getResources().getColor(
						R.color.details_subContent_textColor));
			}
			TextView black_secTitle = (TextView)v.findViewWithTag("black_sec");
			if (black_secTitle != null) {
				black_secTitle.setTextColor(context.getResources().getColor(
						R.color.black));
			}
		}
	}
}
