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
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.common.BaseLayoutAndData;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.CommentItem;
import com.pps.usmovie.mobile.data.DetailsDataType;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.main.MovieListContentActivity;
import com.pps.usmovie.mobile.main.MovieWebViewActivity;
import com.pps.usmovie.mobile.parser.Result;
import com.pps.usmovie.mobile.parser.VideoDetailsListParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

/**
 * 评论板块
 * @author zhangxiaole
 *
 */
public class DetailsCommentLayout extends BaseLayoutAndData implements OnClickListener, IDetailsLayout{

	private VideoDetailsItem videoDetailsItem;
	private View line;
	
	public DetailsCommentLayout(Context context,VideoDetailsItem item, DetailsDataType type) {
		super(context, type);
		this.videoDetailsItem = item;
	}

	@Override
	public void setWidget(int size) {
		if(videoDetailsItem==null || videoDetailsItem.getActorList().size()<=0){
			pieceContainer.setVisibility(View.GONE);
			 return;
		}
		int showNum = videoDetailsItem.getCommentList().size()>size?size:videoDetailsItem.getCommentList().size();
		for(int i=0; i<showNum; i++){
			CommentItem item = videoDetailsItem.getCommentList().get(i);
			View itemView = View.inflate(context, R.layout.details_comment_layout, null);
			init(itemView,item);
			setBackGround(itemView, videoDetailsItem.getCommentList().size(),i);
			more.setOnClickListener(this);
			container.addView(itemView);
		}
		setMoreLayout(videoDetailsItem.getCommentList().size(),showNum);
	}
	
	private void init(View itemView, final CommentItem item) {
		itemView.setTag(item);
		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MovieWebViewActivity.class);
				intent.putExtra("url", item.getUrl());
				context.startActivity(intent);
			}
		});
		TextView content = (TextView)itemView.findViewById(R.id.details_comment_content);
		TextView source = (TextView)itemView.findViewById(R.id.details_comment_come);
		line = itemView.findViewById(R.id.details_comment_line);
		if(!TextUtils.isEmpty(item.getContent())){
			content.setVisibility(View.VISIBLE);
			content.setText(item.getContent());
		}
		if(!TextUtils.isEmpty(item.getSource())){
			source.setVisibility(View.VISIBLE);
			source.setText("---来自"+item.getSource()+"  "+ item.getUser_name());
		}
	}

	@Override
	public void setListData(String... params) {
		if(!TextUtils.isEmpty(params[0])){
			GetCommentAsyncTask task = new GetCommentAsyncTask();
			task.execute(params[0]);
		}
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

	/**
	 * 填充评论列表[全部]
	 * @param commentList
	 */
	public void initCommentList(ArrayList<CommentItem> commentList) {
		if(commentList!=null && commentList.size()>0){
			setMoreLayout(videoDetailsItem.getCommentList().size(),0);
			for(int i=0; i<commentList.size(); i++){
				CommentItem item = commentList.get(i);
				View itemView = View.inflate(context, R.layout.details_comment_layout, null);
				init(itemView,item);
				setBackGround_allList(itemView, commentList.size(), i);
				container.addView(itemView);
				if(commentList.size() == i+1){
					line.setVisibility(View.GONE);
				}
			}
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.details_piece_more:
			Intent intent = new Intent(context, MovieListContentActivity.class);
			intent.putExtra("videoDetailsItem", videoDetailsItem);
			intent.putExtra("type", "comment");
			context.startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	class GetCommentAsyncTask extends AsyncTask<String, Integer, Result<CommentItem>>{

		@Override
		protected void onPreExecute() {
			if(progressDialog!=null) progressDialog.show();
		}
		@Override
		protected Result<CommentItem> doInBackground(String... params) {
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETCOMMENTLIST, reqtime);

			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETCOMMENTLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("video_id", params[0]);
			map.put("curr_page", "0");
			map.put("per_page", Constants.PER_PAGE_ALL);
			Set<Map.Entry<String, String>> list = map.entrySet();
			Result<CommentItem> result = new Result<CommentItem>();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				result = VideoDetailsListParser.getCommentItemInfo(json);
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
		protected void onPostExecute(Result<CommentItem> result) {
			if(progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
			if(result.getRet()==Constants.RET_SUCCESS_CODE){
				ArrayList<CommentItem> commentList = result.getList();
				initCommentList(commentList);
			}else{
				Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}