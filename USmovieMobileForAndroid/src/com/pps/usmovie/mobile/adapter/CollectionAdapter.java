package com.pps.usmovie.mobile.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.database.CollectionDatabaseUtil;
import com.pps.usmovie.mobile.util.ImageLoader;

public class CollectionAdapter extends BaseAdapter{

	private Context context;
	private List<VideoListItem> videoList;
	private ImageLoader imageLoader;
	private boolean isDetele = false;
	
	public CollectionAdapter(Context context, List<VideoListItem> videoList) {
		this.context = context;
		this.imageLoader = new ImageLoader(context);
		this.videoList = videoList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return videoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder mHolder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.personal_collection_item, null);
			mHolder = new ViewHolder();
			mHolder.collection_img = (ImageView)convertView.findViewById(R.id.collection_img);
			mHolder.collection_cn_title = (TextView)convertView.findViewById(R.id.collection_cn_title);
			mHolder.collection_e_title = (TextView)convertView.findViewById(R.id.collection_e_title);
			mHolder.collection_arrow = (ImageView)convertView.findViewById(R.id.collection_arrow);
			mHolder.collection_detele = (ImageView)convertView.findViewById(R.id.collection_detele);
			
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		final VideoListItem item = videoList.get(position);
		
		mHolder.collection_cn_title.setText(item.getTitle());
		mHolder.collection_e_title.setText(item.getEn_name());
		
		if(!TextUtils.isEmpty(item.getPic_src())){
			imageLoader.DisplayImage(item.getPic_src(),mHolder.collection_img);
		}
		
		if(!isDetele){
			mHolder.collection_detele.setVisibility(View.GONE);
			mHolder.collection_arrow.setVisibility(View.VISIBLE);
		}else{
			mHolder.collection_detele.setVisibility(View.VISIBLE);
			mHolder.collection_arrow.setVisibility(View.GONE);
			final int index = position;
			mHolder.collection_detele.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					videoList.remove(index);
					notifyDataSetChanged();
					CollectionDatabaseUtil task = new CollectionDatabaseUtil(context);
					task.deteleVideo(item.getVideo_id());
				}});
		}
		
		return convertView;
	}
	
	public void setDetele(boolean isDetele) {
		this.isDetele = isDetele;
		notifyDataSetChanged();
	}
	
	public boolean getDetele(){
		return isDetele;
	}
	
	static class ViewHolder {
		ImageView collection_img;
		TextView collection_cn_title;
		TextView collection_e_title;
		ImageView collection_arrow;
		ImageView collection_detele;
	}
}
