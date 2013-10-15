package com.pps.usmovie.mobile.layout;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.RoundItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.MemberItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.main.MovieTextContentActivity;

public class DetailsFounderLayout extends BaseLayoutAndData implements OnClickListener,IDetailsLayout {

	private VideoDetailsItem videoDetailsItem;
	private View line;
	private ArrayList<MemberItem> showList = new ArrayList<MemberItem>();
	
	public DetailsFounderLayout(Context context, VideoDetailsItem item, DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
	}

	@Override
	public void setWidget(int size) {
		if(videoDetailsItem.getDirectorList().size()<=0&&videoDetailsItem.getWriterList().size()<=0&&videoDetailsItem.getProducerList().size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		//导演
		ArrayList<MemberItem> directorList = videoDetailsItem.getDirectorList();
		setMemberData(directorList,"导演");
		//编辑
		ArrayList<MemberItem> writerList = videoDetailsItem.getWriterList();
		setMemberData(writerList,"编剧");
		//制片
		ArrayList<MemberItem> producerList = videoDetailsItem.getProducerList();
		setMemberData(producerList,"制片");
		
		for(int i=0; i<showList.size(); i++){
			MemberItem item = showList.get(i);
			View itemView = View.inflate(context, R.layout.details_founder_layout, null);
			//填充数据
			init(itemView,item,i);
			setBackGround(itemView, showList.size(),i);
			setMoreLayout(showList.size(),0);
			container.addView(itemView);
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

	private void setMemberData(ArrayList<MemberItem> list, String classify) {
		if(list!=null && list.size()>0){
			MemberItem item = list.get(0);
			item.setClassify(classify);
			if(list.size()>1){
				item.setSingle(false);
			}else{
				item.setSingle(true);
			}
			showList.add(item);
		}
	}
	
	private void init(View itemView, final MemberItem item, int index) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {//每一项触发事件
//				Intent intent = new Intent(context, MovieTextContentActivity.class);
//				intent.putExtra("title", value)
			}
		});
		TextView title = (TextView)itemView.findViewById(R.id.details_founder_title);
		TextView name = (TextView)itemView.findViewById(R.id.details_founder_name);
		line = itemView.findViewById(R.id.details_founder_line);
		if(showList.size()==index+1){
			line.setVisibility(View.GONE);
		}
		if(!TextUtils.isEmpty(item.getClassify())){
			title.setVisibility(View.VISIBLE);
			title.setText(item.getClassify());
		}
		if(!TextUtils.isEmpty(item.getZh_name())||!TextUtils.isEmpty(item.getEn_name())){
			name.setVisibility(View.VISIBLE);
			String memberName="";
			if(item.getSingle()){
				memberName = item.getZh_name()+item.getEn_name();
			}else{
				memberName = item.getZh_name()+item.getEn_name()+"  等";
			}
			name.setText(memberName);
		}
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
		// TODO Auto-generated method stub
		
	}

}
