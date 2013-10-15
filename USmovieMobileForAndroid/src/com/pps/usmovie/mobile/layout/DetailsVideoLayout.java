package com.pps.usmovie.mobile.layout;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.PlayVideo;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.data.VideoItem;

/**
 * 相关视频板块
 * @author zhangxiaole
 *
 */
public class DetailsVideoLayout extends BaseLayoutAndData implements OnClickListener, IDetailsLayout {

	private VideoDetailsItem videoDetailsItem;
	private ActorInfoItem actorInfoItem;
	private View line;
	
	public DetailsVideoLayout(Context context,VideoDetailsItem item, DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
	}

	public DetailsVideoLayout(Context context, ActorInfoItem item,DetailsDataType type) {
		super(context, type);
		actorInfoItem = item;
	}

	@Override
	public void setWidget(int size) {
		
		switch (type) {
		case DATA_YPET_VIDEO://详细界面  视频
			if(videoDetailsItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(videoDetailsItem.getVideoList(),size);
			break;
		case ACTOR_YPET_VIDEO://演员 界面  视频
			if(actorInfoItem == null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(actorInfoItem.getVideoList(),size);
			break;
		default:
			break;
		}
	}
	/**详情界面 */
	private void setDetailsWidget(ArrayList<VideoItem> videoList, int size) {
		if(videoList.size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		int showNum = videoList.size()>size?size:videoList.size();
		for(int i=0; i<showNum; i++){
			VideoItem item = videoList.get(i);
			View itemView = View.inflate(context, R.layout.details_video_layout, null);
			init(itemView,item);
			setBackGround(itemView, videoList.size(),i);
			setMoreLayout(videoList.size(),size);
			more.setOnClickListener(this);
			container.addView(itemView);
			if(showNum == i+1){
				if(videoList.size()<size){
					line.setVisibility(View.GONE);
				}
			}
		}
	}
	private void init(View itemView, final VideoItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlayVideo.playVideo(context, item.getVideo_src());
			}
		});
		ImageView videoImg = (ImageView)itemView.findViewById(R.id.details_video_img);
		TextView videoText = (TextView)itemView.findViewById(R.id.details_video_explan);
		line = itemView.findViewById(R.id.details_video_pic_view);
		if(!TextUtils.isEmpty(item.getPic_src())){
			imageLoader.DisplayImage(item.getPic_src(), videoImg);
		}
		if(!TextUtils.isEmpty(item.getVideo_title())){
			videoText.setText(item.getVideo_title());
		}
	}

	@Override
	public void setListData(String... params){
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.details_piece_more:
			Toast.makeText(context, "相关视频更多", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
