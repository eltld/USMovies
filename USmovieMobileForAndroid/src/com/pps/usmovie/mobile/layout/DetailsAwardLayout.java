package com.pps.usmovie.mobile.layout;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.RoundItem;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.AwardItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.VideoDetailsItem;

public class DetailsAwardLayout extends BaseLayoutAndData implements OnClickListener, IDetailsLayout {

	private VideoDetailsItem videoDetailsItem;
	private ActorInfoItem actorInfoItem;
	private View line;
	
	public DetailsAwardLayout(Context context,VideoDetailsItem item, DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
	}

	public DetailsAwardLayout(Context context, ActorInfoItem item,
			DetailsDataType type) {
		super(context, type);
		actorInfoItem = item;
	}

	@Override
	public void setWidget(int size) {
		
		switch (type) {
		case DATA_YPET_AWARD://详情-奖项
			if(videoDetailsItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(videoDetailsItem.getAwardList(),size);
			break;
		case ACTOR_YPET_AWARD://演员-奖项
			if(actorInfoItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(actorInfoItem.getAwardList(),size);
			break;
		default:
			break;
		}
		
		
	}

	private void setDetailsWidget(ArrayList<AwardItem> awardList, int size) {
		if(awardList==null ||awardList.size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		
		for(int i=0; i<awardList.size(); i++){
			AwardItem item = awardList.get(i);
			View itemView = View.inflate(context, R.layout.details_founder_layout, null);
			init(itemView,item);
			setBackGround(itemView,awardList.size(),i);
			more.setOnClickListener(this);
			container.addView(itemView);
			if(awardList.size()==i+1){
				line.setVisibility(View.GONE);
			}
		}
		setMoreLayout(awardList.size(),0);
	}

	private void init(View itemView, final AwardItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
			}
		});
		TextView title = (TextView)itemView.findViewById(R.id.details_founder_title);
		TextView name = (TextView)itemView.findViewById(R.id.details_founder_name);
		line = itemView.findViewById(R.id.details_founder_line);
		
		if(item!=null){
			title.setVisibility(View.VISIBLE);
			title.setMaxLines(2);
			title.setText(item.getName()+"  " +item.getAward());
			if(!TextUtils.isEmpty(item.getRemark())){
				name.setVisibility(View.VISIBLE);
				name.setText(item.getRemark());
			}
		}
	}

	protected void setBackGround(View itemView, int listSize, int i) {
		Bundle bundle = new Bundle();
		bundle.putInt("index", i);
		bundle.putInt("num", listSize);
		itemView.setTag(bundle);
		itemView.setOnTouchListener(this);
		RoundItem.setItemBackGround(itemView);
	}
	
	@Override
	public void setListData(String... params)  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitle(String title) {
		setTitleName(title);
	}

	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return pieceContainer;
	}

	@Override
	public void onClick(View v) {
		
	}
}
