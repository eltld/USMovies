package com.pps.usmovie.mobile.layout;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.WorksItem;
import com.pps.usmovie.mobile.main.MovieDetailsMainActivity;

public class ActorDetailsWorksLayout extends BaseLayoutAndData implements OnClickListener, IDetailsLayout {

	private ActorInfoItem actorInfoItem;
	private ImageView workImg;
	private View line;
	
	public ActorDetailsWorksLayout(Context context,ActorInfoItem item, DetailsDataType type) {
		super(context, type);
		this.actorInfoItem = item;
	}

	@Override
	public void setWidget(int size) {
		if(actorInfoItem==null || actorInfoItem.getWorkList().size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		int showNum = actorInfoItem.getWorkList().size()>size?size:actorInfoItem.getWorkList().size();
		for(int i=0; i<showNum; i++){
			WorksItem item = actorInfoItem.getWorkList().get(i);
			View itemView = View.inflate(context, R.layout.details_actor_layout, null);
			init(itemView,item);
			setBackGround(itemView, actorInfoItem.getWorkList().size(),i);
			setMoreLayout(actorInfoItem.getWorkList().size(),size);
			more.setOnClickListener(this);
			container.addView(itemView);
			if(showNum == i+1){
				if(actorInfoItem.getWorkList().size()<size){
					line.setVisibility(View.GONE);
				}
			}
		}
	}

	private void init(View itemView, final WorksItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieDetailsMainActivity.class);
				intent.putExtra("video_id", item.getVideo_id());
				context.startActivity(intent);
			}
		});
		workImg = (ImageView)itemView.findViewById(R.id.details_actor_img);
		TextView cnName = (TextView)itemView.findViewById(R.id.details_actor_name);
		TextView enName = (TextView)itemView.findViewById(R.id.details_actor_enName);
		TextView years = (TextView)itemView.findViewById(R.id.details_actor_years);
		line = itemView.findViewById(R.id.details_actor_line);
		if(!TextUtils.isEmpty(item.getPic_src())){
			imageLoader.DisplayImage(item.getPic_src(), workImg);
		}
		if(!TextUtils.isEmpty(item.getTitle())){
			cnName.setVisibility(View.VISIBLE);
			cnName.setText(item.getTitle());
		}
		if(!TextUtils.isEmpty(item.getEn_name())){
			enName.setVisibility(View.VISIBLE);
			enName.setText(item.getEn_name());
		}
		if(!TextUtils.isEmpty(item.getPlay_time())){
			years.setVisibility(View.VISIBLE);
			years.setText("("+item.getPlay_time()+")");
		}
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.details_piece_more:
			Toast.makeText(context, "相关作品更多", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}

}
