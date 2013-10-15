package com.pps.usmovie.mobile.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.data.VideoMenuItem;

public class VideoMenuAdapter extends BaseAdapter {

	private Context context;
	private List<VideoMenuItem> list;
	/**当前选中的Item*/
	private VideoMenuItem currItem;
	
	
	public VideoMenuAdapter(Context context,
			List<VideoMenuItem> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
			convertView = View.inflate(context, R.layout.movie_menu_item, null);
			mHolder = new ViewHolder();
			mHolder.menuText = (TextView)convertView.findViewById(R.id.menu_listview_item_text);
			
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		VideoMenuItem item = list.get(position);
		
		mHolder.menuText.setText(item.getName());
		
		if(currItem!=null && currItem.getId().equalsIgnoreCase(item.getId())){
			mHolder.menuText.setTextColor(context.getResources().getColor(R.color.selected_bg_color));
			convertView.setBackgroundResource(R.color.black);
		}else{
			mHolder.menuText.setTextColor(context.getResources().getColor(R.color.menu_item_textColor));
			convertView.setBackgroundResource(R.drawable.menu_selected_style);
		}
		
		return convertView;
	}

	public VideoMenuItem getCurrItem() {
		return currItem;
	}
	
	public void setCurrItem(VideoMenuItem currItem) {
		this.currItem = currItem;
		notifyDataSetChanged();
	}
	

	static class ViewHolder {
		TextView menuText;
	}
}
