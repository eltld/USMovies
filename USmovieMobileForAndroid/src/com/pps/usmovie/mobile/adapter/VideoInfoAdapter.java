package com.pps.usmovie.mobile.adapter;

import java.util.ArrayList;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.util.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class VideoInfoAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<VideoListItem> videoList;
	ImageLoader imageLoader = new ImageLoader(context);
	public VideoInfoAdapter(Context context, ArrayList<VideoListItem> videoList) {
		this.context = context;
		this.videoList = videoList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return videoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder mHolder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.video_content_item, null);
			mHolder = new ViewHolder();
			mHolder.video_content_img = (ImageView)convertView.findViewById(R.id.video_content_img);
			mHolder.video_content_cn_title = (TextView)convertView.findViewById(R.id.video_content_cn_title);
			mHolder.video_content_e_title = (TextView)convertView.findViewById(R.id.video_content_e_title);
			mHolder.video_content_addTime = (TextView)convertView.findViewById(R.id.video_content_addTime);
			mHolder.video_content_actor = (TextView)convertView.findViewById(R.id.video_content_actor);
			mHolder.video_content_grade = (TextView)convertView.findViewById(R.id.video_content_grade);
			
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		VideoListItem item = videoList.get(position);
		
		mHolder.video_content_cn_title.setText(item.getTitle());
		mHolder.video_content_e_title.setText(item.getEn_name());
		mHolder.video_content_addTime.setText(item.getPlayTime());
		mHolder.video_content_actor.setText(item.getActor());
		mHolder.video_content_grade.setText(item.getGrade()+"分");
		
		if(!TextUtils.isEmpty(item.getPic_src())){
//			Bitmap bitmap = BitmapCacheUtils.getMapThumbFromServer(context, new File(Constants.PATH_SDCARD_IMAGES+ item.getPic_src().hashCode()),item.getPic_src(),120, 170);
			imageLoader.DisplayImage(item.getPic_src(),mHolder.video_content_img, new CallBackBitmap(){
				@Override
				public void setBitmap(Bitmap bitmap) {
					mHolder.video_content_img.setImageBitmap(bitmap);
				}
			});
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		ImageView video_content_img;
		TextView video_content_cn_title;
		TextView video_content_e_title;
		TextView video_content_addTime;//上映时间
		TextView video_content_actor;//主演
		TextView video_content_grade;//评分
	}

	public void refreshAddData(ArrayList<VideoListItem> videoInfoList) {
		videoList.addAll(videoInfoList);
		notifyDataSetChanged();
	}
}
