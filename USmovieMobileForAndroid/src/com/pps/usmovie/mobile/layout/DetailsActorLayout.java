package com.pps.usmovie.mobile.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.MemberItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.main.MovieActorActivity;
import com.pps.usmovie.mobile.main.MovieListContentActivity;
import com.pps.usmovie.mobile.parser.Result;
import com.pps.usmovie.mobile.parser.VideoDetailsListParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

public class DetailsActorLayout extends BaseLayoutAndData  implements OnClickListener,IDetailsLayout{

	private VideoDetailsItem videoDetailsItem;
	private ImageView actorImg;
	private View line;
	
	public DetailsActorLayout(Context context, VideoDetailsItem videoInfo,DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = videoInfo;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.details_piece_more:
			Intent intent = new Intent(context, MovieListContentActivity.class);
			intent.putExtra("videoDetailsItem", videoDetailsItem);
			intent.putExtra("type", "actor");
			context.startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void setWidget(int size) {
		if(videoDetailsItem==null || videoDetailsItem.getActorList().size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		int showNum = videoDetailsItem.getActorList().size()>size?size:videoDetailsItem.getActorList().size();
		for(int i=0; i<showNum; i++){
			MemberItem item = videoDetailsItem.getActorList().get(i);
			View itemView = View.inflate(context, R.layout.details_actor_layout, null);
			init(itemView,item);
			setBackGround(itemView, videoDetailsItem.getActorList().size(),i);
			setMoreLayout(videoDetailsItem.getActorList().size(),showNum);
			more.setOnClickListener(this);
			container.addView(itemView);
		}
	}

	private void init(View itemView, final MemberItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieActorActivity.class);
				intent.putExtra("actorId", item.getId());
				context.startActivity(intent);
			}
		});
		actorImg = (ImageView)itemView.findViewById(R.id.details_actor_img);
		TextView cnName = (TextView)itemView.findViewById(R.id.details_actor_name);
		TextView enName = (TextView)itemView.findViewById(R.id.details_actor_enName);
		line = itemView.findViewById(R.id.details_actor_line);
		if(!TextUtils.isEmpty(item.getPic_src())){
			imageLoader.DisplayImage(item.getPic_src(), actorImg);
		}
		if(!TextUtils.isEmpty(item.getZh_name())){
			cnName.setVisibility(View.VISIBLE);
			cnName.setText(item.getZh_name());
		}
		if(!TextUtils.isEmpty(item.getEn_name())){
			enName.setVisibility(View.VISIBLE);
			enName.setText(item.getEn_name());
		}
	}

	@Override
	public void setListData(String... params){
		if(!TextUtils.isEmpty(params[0])){
			GetActorAsyncTask task = new GetActorAsyncTask();
			task.execute(params[0]);
		}
	}
	
	/**填充列表*/
	public void initActorList(ArrayList<MemberItem> actorList) {
		if(actorList!=null && actorList.size()>0){
			setMoreLayout(actorList.size(),0);
			for(int i=0; i<actorList.size(); i++){
				MemberItem item = actorList.get(i);
				View itemView = View.inflate(context, R.layout.details_actor_layout, null);
				init(itemView,item);
				setBackGround_allList(itemView, actorList.size(), i);
				container.addView(itemView);
				if(actorList.size() == i+1){
					line.setVisibility(View.GONE);
				}
			}
		}
	}
	
	@Override
	public View getLayout() {
		// TODO Auto-generated method stub
		return pieceContainer;
	}

	@Override
	public void setTitle(String title) {
		setTitleName(title);
	}
	
	
	class GetActorAsyncTask extends AsyncTask<String, Integer, Result<MemberItem>>{

		@Override
		protected void onPreExecute() {
			if(progressDialog!=null) progressDialog.show();
		}
		@Override
		protected Result<MemberItem> doInBackground(String... params) {
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETSTARLIST, reqtime);

			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETSTARLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("video_id", params[0]);
			Set<Map.Entry<String, String>> list = map.entrySet();
			Result<MemberItem> result = new Result<MemberItem>();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				result = VideoDetailsListParser.getActorItemInfo(json);
			} catch (JSONException e) {
				result.setRet(Constants.RET_ERROR_CODE);
				result.setMessage("json解析错误");
				e.printStackTrace();
			} catch (ConnectServerException e) {
				result.setRet(Constants.CONNECT_EXCEPTION_CODE);
				result.setMessage(e.getErrorMessage());
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(Result<MemberItem> result) {
			if(progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
			if(result.getRet()==Constants.RET_SUCCESS_CODE){
				ArrayList<MemberItem> actorList = result.getList();
				initActorList(actorList);
			}else{
				Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
