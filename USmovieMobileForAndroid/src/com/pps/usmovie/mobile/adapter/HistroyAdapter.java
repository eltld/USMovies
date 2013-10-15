package com.pps.usmovie.mobile.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.CollectType;
import com.pps.usmovie.mobile.data.HistoryItem;
import com.pps.usmovie.mobile.database.HistoryDatabaseDao;


public class HistroyAdapter extends BaseAdapter{
	private Context context;
	private List<HistoryItem> historyList;
	private HistoryDatabaseDao dao;
	private boolean isDetele = false;
	
	public HistroyAdapter(Context context, List<HistoryItem> historyList) {
		this.context = context;
		this.historyList = historyList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historyList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return historyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.personal_history_item, null);
			mHolder = new ViewHolder();
			mHolder.history_title = (TextView)convertView.findViewById(R.id.history_title);
			mHolder.history_arrow = (ImageView)convertView.findViewById(R.id.history_arrow);
			mHolder.history_detele = (ImageView)convertView.findViewById(R.id.history_detele);
			
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		HistoryItem item = historyList.get(position);
		
		String name = item.getVideoName() +"   "+ item.getEnName();
		mHolder.history_title.setText(name);
		
		setRightImg(mHolder,item, position);
		
		return convertView;
	}
	private void setRightImg(ViewHolder mHolder,final HistoryItem item, int position) {
		if(!isDetele){
			mHolder.history_detele.setVisibility(View.GONE);
			mHolder.history_arrow.setVisibility(View.VISIBLE);
		}else{
			mHolder.history_detele.setVisibility(View.VISIBLE);
			mHolder.history_arrow.setVisibility(View.GONE);
			final int index = position;
			mHolder.history_detele.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					historyList.remove(index);
					notifyDataSetChanged();
					dao = new HistoryDatabaseDao(context) {
						
						@Override
						public void onCallBack(CollectType<HistoryItem> result) {
							// TODO Auto-generated method stub
							
						}
					};
					HistoryItem historyItem = new HistoryItem();
					historyItem.setType(HistoryDatabaseDao.DETELEHISTORY);
					historyItem.setVideoUrl(item.getVideoUrl());
					dao.deteleVideo(historyItem);
				}});
		}
		
	}

	public void setDetele(boolean isDetele) {
		this.isDetele = isDetele;
		notifyDataSetChanged();
	}
	
	public boolean getDetele(){
		return isDetele;
	}
	
	static class ViewHolder {
		
		TextView history_title;
		ImageView history_arrow;
		ImageView history_detele;
	}
	
	
}
