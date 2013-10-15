package com.pps.usmovie.mobile.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.util.ImageLoader;

public class PhotoGridAdatper extends BaseAdapter{

	private Context context;
	private ArrayList<PicItem> picList;
	private ImageLoader imageLoader;
	
	public PhotoGridAdatper(Context context, ArrayList<PicItem> list) {
		this.context = context;
		this.picList = list;
		this.imageLoader = new ImageLoader(context);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return picList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return picList.get(position);
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
			mHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.photo_grid_item, null);
			mHolder.img =(ImageView)convertView.findViewById(R.id.photo_grid_img);
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		PicItem item = picList.get(position);
		
		if(!TextUtils.isEmpty(item.getPic_src())){
			imageLoader.DisplayImage(item.getPic_src(), mHolder.img);
		}
		
		
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
	}
}
