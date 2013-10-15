package com.pps.usmovie.mobile.layout;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.IssuItem;
import com.pps.usmovie.mobile.data.TextLayoutItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.util.UIUtils;

/**
 * 视频详情-详细信息
 * @author zhangxiaole
 *
 */
public class DetailsMainLayout extends BaseLayoutAndData implements IDetailsLayout,OnClickListener {

	private ArrayList<TextLayoutItem> textList;
	private ArrayList<IssuItem> issuList;
	private VideoDetailsItem videoDetailsItem;
	private String title;
	private OnClickListener mOnClickListener;
	
	public DetailsMainLayout(Context context, DetailsDataType type) {
		super(context, type);
		// TODO Auto-generated constructor stub
	}
	
	public DetailsMainLayout(Context context, DetailsDataType type,ArrayList<TextLayoutItem> list, String title) {
		super(context, type);
		this.textList = list;
		this.title = title;
		initWidget();
	}
	
	public DetailsMainLayout(Context context, DetailsDataType type,VideoDetailsItem item, String title) {
		super(context, type);
		this.videoDetailsItem = item;
		this.title = title;
		initWidget();
	}

	private void initWidget() {
		if(!TextUtils.isEmpty(title)){
			setTitleName(title);
		}
		if(textList==null || textList.size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		int height = UIUtils.dip2px(context, 45);
		for(int i=0; i<textList.size(); i++){
			TextLayoutItem item = textList.get(i);
			View itemView = View.inflate(context, R.layout.details_main_item, null);
			itemView.setOnClickListener(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,height);
			itemView.setLayoutParams(lp);
			View line = itemView.findViewById(R.id.details_main_item_line);
			initData(itemView,item);
			setBackGround_allList(itemView, textList.size(),i);
			setMoreLayout(textList.size(),0);
			more.setOnClickListener(this);
			if(textList.size() == i+1){
				line.setVisibility(View.GONE);
			}
			container.addView(itemView);
		}
	}

	private void initData(View itemView, TextLayoutItem item) {
		TextView leftTV = (TextView)itemView.findViewById(R.id.details_main_leftTop_textView);
		TextView leftSubTV = (TextView)itemView.findViewById(R.id.details_main_leftBottom_textView);
		TextView RightTV = (TextView)itemView.findViewById(R.id.details_main_right_textView);
		ImageView arrow = (ImageView)itemView.findViewById(R.id.details_main_item_arrow);
		if(!TextUtils.isEmpty(item.getLeftText())){
			leftTV.setText(item.getLeftText());
		}else leftTV.setVisibility(View.GONE);
		
		if(!TextUtils.isEmpty(item.getLeftSubText())){
			leftSubTV.setVisibility(View.VISIBLE);
			leftSubTV.setText(item.getLeftSubText());
		}
		
		if(!TextUtils.isEmpty(item.getRightText())){
			RightTV.setVisibility(View.VISIBLE);
			RightTV.setText(item.getRightText());
		}
		
		if(item.isArrow()) arrow.setVisibility(View.VISIBLE);
		else arrow.setVisibility(View.INVISIBLE);
	}

	@Override
	public void setWidget(int size) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setListData(String... params) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTitle(String title) {
	}

	@Override
	public View getLayout() {
		return pieceContainer;
	}

	@Override
	public void onClick(View v) {
		this.mOnClickListener.onClick(v);
	}
	
	public void setOnClickListener(OnClickListener mOnClickListener) {
		this.mOnClickListener = mOnClickListener;
	}
}
