package com.pps.usmovie.mobile.common;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.util.ImageLoader;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.widget.ProgressDialog;

public class BaseLayoutAndData implements OnTouchListener{

	protected Context context;
	protected DetailsDataType type;
	protected View pieceContainer;
	protected LinearLayout container;
	protected RelativeLayout more;
	protected TextView titleView;
	protected ImageLoader imageLoader;
	protected TextView genreTitle;
	protected ProgressDialog progressDialog;

	public BaseLayoutAndData(Context context, DetailsDataType type) {
		this.context = context;
		this.type = type;
		imageLoader = new ImageLoader(context);
		
		switch (type) {
		case DATA_YPET_GENRE://视频详情-类型
			initGenre();
			break;
		default:
			init();
			break;
		}
	}

	public View getPieceContainer() {
		return pieceContainer;
	}

	private void init() {
		pieceContainer = View.inflate(context, R.layout.details_piece_layout,
				null);
		container = (LinearLayout) pieceContainer
				.findViewById(R.id.details_piece_container);
		more = (RelativeLayout) pieceContainer
				.findViewById(R.id.details_piece_more);
		titleView = (TextView) pieceContainer
				.findViewById(R.id.details_piece_title);
		progressDialog = new ProgressDialog(context, "");
	}
	
	/**视频详情-类型*/
	private void initGenre(){
		pieceContainer = View.inflate(context, R.layout.details_genre_piece_layout,
				null);
		genreTitle = (TextView) pieceContainer.findViewById(R.id.details_genre_title);
		container = (LinearLayout) pieceContainer
				.findViewById(R.id.details_genre_piece_container);
		titleView = (TextView) pieceContainer
				.findViewById(R.id.details_genre_piece_title);
		progressDialog = new ProgressDialog(context, "");
	}
	
	protected void setTitleName(String title){
		if(!TextUtils.isEmpty(title)){
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(title);
		}
	}

	/**
	 * 设置"更多"条目
	 * @param totalNum 共有多少条
	 * @param showNum 显示多少条[0:为不显示更多, -1:一定显示更多]
	 */
	protected void setMoreLayout(int totalNum, int showNum){
		
		if(showNum!=0 &&totalNum>=showNum){
			more.setVisibility(View.VISIBLE);
			more.setOnTouchListener(this);
		}else if(showNum == -1){
			more.setVisibility(View.VISIBLE);
			more.setOnTouchListener(this);
		}else{
			more.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 背景颜色
	 * 
	 * @param listSize
	 * @param i
	 */
	protected void setBackGround(View itemView, int listSize, int i) {
		Bundle bundle = new Bundle();
		bundle.putInt("index", i);
		bundle.putInt("num", listSize >= 3 ? 4 : listSize);
		itemView.setTag(bundle);
		itemView.setOnTouchListener(this);
		RoundItem.setItemBackGround(itemView);
	}
	
	protected void setBackGround_allList(View itemView, int listSize, int i) {
		Bundle bundle = new Bundle();
		bundle.putInt("index", i);
		bundle.putInt("num", listSize);
		itemView.setTag(bundle);
		itemView.setOnTouchListener(this);
		RoundItem.setItemBackGround(itemView);
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

	protected void setItemType(View v, boolean onTouchType) {
		RoundItem.setSelectItemBg(v, onTouchType);
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
