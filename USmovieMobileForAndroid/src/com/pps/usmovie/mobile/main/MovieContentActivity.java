package com.pps.usmovie.mobile.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.VideoInfoAdapter;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.common.NotifyObservable;
import com.pps.usmovie.mobile.common.NotifyObservable.UserData;
import com.pps.usmovie.mobile.common.USmovieApplication;
import com.pps.usmovie.mobile.data.VideoListInfo;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.parser.VideoListInfoParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;
import com.pps.usmovie.mobile.widget.ProgressDialog;
import com.pps.usmovie.mobile.widget.RefreshableListView;
import com.pps.usmovie.mobile.widget.RefreshableListView.OnRefreshListener;

/**
 * 首页内容界面
 * @author zhangxiaole
 *
 */
public class MovieContentActivity extends Activity implements OnItemClickListener,OnRefreshListener, OnScrollListener, Observer {

	private String TAG = "MovieContentActivity";
	private ProgressDialog progressDialog;
	private RefreshableListView mListView;
	/**刷新视频列表*/
	private String REFRESH_VIDEOLIST = "refreshVideoList";
	/**获取更多视频列表*/
	private String ADDMORE_VIDEOLIST = "addMoreVideoList";
	
	private int curr_page=1;
	private String total_count;
	private int firstVisibleItem;
	private int visibleLastIndex;
	/**滑动菜单 item*/
	private String bag_id = "";
	private String videoType;
	private USmovieApplication mApp;
	private VideoListInfo videoList = new VideoListInfo();
	private VideoInfoAdapter videoInfoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.title);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_content_listview_window);
		videoType = getIntent().getStringExtra("videoType");
		bag_id = getIntent().getStringExtra("bag_id")==null?"":getIntent().getStringExtra("bag_id");
		mApp = (USmovieApplication)getApplication();
		mApp.addObserver(this);
		mListView = (RefreshableListView)this.findViewById(R.id.video_content_listView);
		getVideoList(REFRESH_VIDEOLIST, videoType);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		mApp.removeObserver(this);
		super.onDestroy();
	}
	
	private void init(VideoListInfo videoInfo) {
		
		videoList.getVideoInfoList().addAll(videoInfo.getVideoInfoList());
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(this);
		videoInfoAdapter = new VideoInfoAdapter(this, videoList.getVideoInfoList());
		mListView.setAdapter(videoInfoAdapter);
	}

	protected void getVideoList(String getType, String videoType) {
		GetVideoListTask getVideo = new GetVideoListTask(this);
		getVideo.execute(getType,videoType);
	}  
	
	private void getVideoList(String type, String classify,
			String text_null) {
		GetVideoListTask getVideo = new GetVideoListTask(this, classify);
		getVideo.execute(type,Constants.PLAY_TIME);
	}

	
	@Override
	public void onRefresh(RefreshableListView listView) {
		getVideoList(REFRESH_VIDEOLIST, videoType);
	}

	@Override
	public void onLoadMore(int totalItemCount) {
		if(Integer.valueOf(total_count)>totalItemCount){//还有更多数据
			getVideoList(ADDMORE_VIDEOLIST, videoType);
		}else{
			mListView.setFullForList(total_count);
		}
	}
	
	class GetVideoListTask extends AsyncTask<String, Integer, Object>{

		private Context context;
		private String type;
		private String classify;
		
		public GetVideoListTask(Context context) {
			this.context = context;
		}
		
		public GetVideoListTask(Context context, String classify){
			this.context = context;
			this.classify = classify;
		}
		@Override
		protected Object doInBackground(String... params) {
			type = params[0];
			String videoType = params[1];
			if(TextUtils.isEmpty(videoType)){
				videoType = Constants.PLAY_TIME;
			}
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETVIDEOLIST, reqtime);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETVIDEOLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("title", "");
			map.put("order", videoType);
			map.put("bag_id", bag_id);
			
			map.put("class", Constants.CLASS_MOVIE);
			map.put("type", TextUtils.isEmpty(classify)?"":classify);
			
			if(params[0].equalsIgnoreCase(ADDMORE_VIDEOLIST)){
				map.put("curr_page", String.valueOf(++curr_page));
				map.put("per_page", Constants.PER_PAGE);//Constants.PER_PAGE
			}else{
				curr_page=1;
				map.put("curr_page", String.valueOf(curr_page));
				map.put("per_page", Constants.PER_PAGE);
			}
			
			Set<Map.Entry<String, String>> list = map.entrySet();
			Log.d(TAG, "..GetVideoListTask Params list : " + list.toString());
			VideoListInfo videoInfo=null;
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				
				videoInfo = VideoListInfoParser.getVideoList(json);
			}  catch (JSONException e) {
				videoInfo = new VideoListInfo();
				videoInfo.setRet(Constants.RET_ERROR_CODE);
				videoInfo.setMessage("json解析错误");
				e.printStackTrace();
			}catch (ConnectServerException e) {
				videoInfo = new VideoListInfo();
				videoInfo.setRet(Constants.CONNECT_EXCEPTION_CODE);
				videoInfo.setMessage(e.getErrorMessage());
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			videoInfo.setTag(params[0]);
			return videoInfo;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			VideoListInfo videoInfo = (VideoListInfo)result;
			total_count = videoInfo.getTotal_count();
			
			if(videoInfo.getTag().equalsIgnoreCase(REFRESH_VIDEOLIST)){
				videoList.getVideoInfoList().clear();
				mListView.completeRefreshing();
				init(videoInfo);
				UserData userData = new UserData(NotifyObservable.CONTACT_REFRESH_CLASSIFY, videoInfo.getVideoClassList());
				mApp.getObservable().notifyObservers(userData);
			}else if(videoInfo.getTag().equalsIgnoreCase(ADDMORE_VIDEOLIST)){
				videoInfoAdapter.refreshAddData(videoInfo.getVideoInfoList());
				mListView.completeLoadMore();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {//arg2==0头文件；arg2＝＝arg0.getAdapter().getCount() 脚文件；
		if(arg2==arg0.getAdapter().getCount()-2){
			return;
		}
		VideoListItem item = (VideoListItem)arg0.getAdapter().getItem(arg2+1);
		Intent intent = new Intent(this,MovieDetailsMainActivity.class);
		intent.putExtra("video_id", item.getVideo_id());
		startActivity(intent);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		visibleLastIndex = firstVisibleItem + visibleItemCount -1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = view.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1;//增加Footer
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
			
			Toast.makeText(MovieContentActivity.this, "最后一行，加载", Toast.LENGTH_SHORT).show();	
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		UserData userData = (UserData)data;
		if(userData.getKey().equals(NotifyObservable.REFRESH_CONENT)){
			bag_id = (String)userData.getValue();
			curr_page = 1;
			total_count= "0";
			getVideoList(REFRESH_VIDEOLIST, videoType);
			mListView.completeLoadMore();
		}else if(userData.getKey().equals(NotifyObservable.CLASSIFY_REFRESH)){
			String classify = (String)userData.getValue();
			curr_page = 1;
			total_count= "0";
			getVideoList(REFRESH_VIDEOLIST, classify,"");
			mListView.completeLoadMore();
		}
	}
}
