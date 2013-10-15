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
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.CommentItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.NewsItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.layout.DetailsCommentLayout.GetCommentAsyncTask;
import com.pps.usmovie.mobile.main.MovieListContentActivity;
import com.pps.usmovie.mobile.main.MovieWebViewActivity;
import com.pps.usmovie.mobile.parser.Result;
import com.pps.usmovie.mobile.parser.VideoDetailsListParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

public class DetailsNewsLayout extends BaseLayoutAndData implements OnClickListener,IDetailsLayout {

	private VideoDetailsItem videoDetailsItem;
	private ActorInfoItem actorInfoItem;
	private View line;
	private String classType="1";
	
	public DetailsNewsLayout(Context context,VideoDetailsItem item, DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
		
	}

	public DetailsNewsLayout(Context context, ActorInfoItem item,DetailsDataType type) {
		super(context, type);
		actorInfoItem = item;
		classType = Constants.ACTOR_CLASS;
	}

	@Override
	public void setWidget(int size) {
		
		switch (type) {
		case DATA_YPET_NEWS://详情界面  新闻
			if(videoDetailsItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(videoDetailsItem.getNewsList(),size);
			break;
		case ACTOR_YPET_NEWS://演员界面  新闻
			if(actorInfoItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setDetailsWidget(actorInfoItem.getNewsList(),size);
			break;
			
		case ACTOR_YPET_INTRO://个人简介
			if(actorInfoItem==null){
				pieceContainer.setVisibility(View.GONE);
				return;
			}
			setIntroData(actorInfoItem.getIntro());
			break;
		default:
			break;
		}
		
	}
	
	private void setIntroData(String intro) {
		if(TextUtils.isEmpty(intro)){
			pieceContainer.setVisibility(View.GONE);
			return;
		}
		View itemView = View.inflate(context, R.layout.details_news_layout, null);
		itemView.setOnClickListener(this);
		TextView title = (TextView)itemView.findViewById(R.id.details_news_title);
		ImageView arrow = (ImageView)itemView.findViewById(R.id.details_news_arrow);
		arrow.setVisibility(View.INVISIBLE);
		title.setVisibility(View.VISIBLE);
		title.setMaxLines(30);
		title.setEllipsize(null);
		title.setText(intro);
		line = itemView.findViewById(R.id.details_news_line);
		line.setVisibility(View.GONE);
		setBackGround_allList(itemView, 1,0);
		setMoreLayout(1,0);
		more.setOnClickListener(this);
		container.addView(itemView);
	}

	private void setDetailsWidget(ArrayList<NewsItem> newsList, int size) {
		if(newsList==null || newsList.size()<=0){
			pieceContainer.setVisibility(View.GONE);
			return;
		} 
		int showNum = newsList.size()>size?size:newsList.size();
		for(int i=0; i<showNum; i++){
			NewsItem item = newsList.get(i);
			View itemView = View.inflate(context, R.layout.details_news_layout, null);
			init(itemView,item);
			setBackGround(itemView, newsList.size()+1,i);
			setMoreLayout(newsList.size(),-1);
			more.setOnClickListener(this);
			container.addView(itemView);
		}
	}

	private void init(View itemView, final NewsItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieWebViewActivity.class);
				intent.putExtra("url", item.getGet_url());
				context.startActivity(intent);
			}
		});
		TextView title = (TextView)itemView.findViewById(R.id.details_news_title);
		line = itemView.findViewById(R.id.details_news_line);
		if(!TextUtils.isEmpty(item.getTitle())){
			title.setVisibility(View.VISIBLE);
			title.setText(item.getTitle());
		}
	}

	@Override
	public void setListData(String... params){
		if(!TextUtils.isEmpty(params[0])){
			GetNewsListTask task = new GetNewsListTask();
			task.execute(params[0]);
		}
	}

	/**
	 * 填充新闻列表[全部]
	 * @param newsList
	 */
	public void initNewList(ArrayList<NewsItem> newsList) {
		if(newsList!=null && newsList.size()>0){
			setMoreLayout(newsList.size(),0);
			for(int i=0; i<newsList.size(); i++){
				NewsItem item = newsList.get(i);
				View itemView = View.inflate(context, R.layout.details_news_layout, null);
				init(itemView,item);
				setBackGround_allList(itemView, newsList.size(), i);
				container.addView(itemView);
				if(newsList.size() == i+1){
					line.setVisibility(View.GONE);
				}
			}
		}
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
			Intent intent = new Intent(context, MovieListContentActivity.class);
			switch (type) {
			case DATA_YPET_NEWS:
				intent.putExtra("videoDetailsItem", videoDetailsItem);
				intent.putExtra("type", "news");
				break;
			case ACTOR_YPET_NEWS:
				intent.putExtra("actorInfoItem", actorInfoItem);
				intent.putExtra("type", "actor_news");
				break;
			default:
				break;
			}
			context.startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	class GetNewsListTask extends AsyncTask<String, Integer, Object>{

		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}
		@Override
		protected Object doInBackground(String... params) {
			
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETNEWSLIST, reqtime);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETNEWSLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("video_id", params[0]);
			map.put("curr_page", "1");
			map.put("per_page", Constants.PER_PAGE_ALL);
			map.put("class", classType);
			Set<Map.Entry<String, String>> list = map.entrySet();
			Result<NewsItem> result = new Result<NewsItem>();
			
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				result = VideoDetailsListParser.getNewsItemInfo(json);
			} catch (JSONException e) {
				result.setRet(Constants.RET_ERROR_CODE);
				result.setMessage("json解析错误");
				e.printStackTrace();
			}catch (ConnectServerException e) {
				result.setRet(Constants.CONNECT_EXCEPTION_CODE);
				result.setMessage(e.getErrorMessage());
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(Object object) {
			progressDialog.dismiss();
			Result<NewsItem> result = (Result<NewsItem>)object;
			
			if(result!=null){
				if(result.getRet() == Constants.RET_SUCCESS_CODE){
					ArrayList<NewsItem> newsList = result.getList();
					initNewList(newsList);
				}else{
					Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
