package com.pps.usmovie.mobile.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.VideoInfoAdapter;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.VideoListInfo;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.parser.VideoListInfoParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

public class SearchActivity extends BaseActivity implements OnItemClickListener{
	private static final String TAG = "SearchActivity";
	private EditText searchEdit;
	private int curr_page = 1;
	private ListView listView;
	private TextView hint;
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.search_window);
		init();
	}

	private void init() {
		searchEdit = (EditText)findViewById(R.id.search_EditText);
		hint = (TextView)findViewById(R.id.search_hint);
		Button searchBtn = (Button)findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(this);
		listView = (ListView)findViewById(R.id.search_listView);
		listView.setOnItemClickListener(this);
	}
	
	public void setData(VideoListInfo videoInfo) {
		VideoInfoAdapter adapter = new VideoInfoAdapter(this, videoInfo.getVideoInfoList());
		listView.setAdapter(adapter);
	}

	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn:
			this.finish();
			break;
		case R.id.search_btn:
			String searchText = searchEdit.getText().toString();
			if(!TextUtils.isEmpty(searchText)){
				GetVideoTask task = new GetVideoTask(SearchActivity.this);
				task.execute(searchText);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		VideoListItem item = (VideoListItem)arg0.getAdapter().getItem(arg2);
		Intent intent = new Intent(this,MovieDetailsMainActivity.class);
		intent.putExtra("video_id", item.getVideo_id());
		startActivity(intent);
		
	}

	@Override
	public void setTitle() throws USdramaException {
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setOnClickListener(this);
	}
	
	class GetVideoTask extends AsyncTask<String, Integer, Object>{

		private Context context;
		public GetVideoTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}
		@Override
		protected Object doInBackground(String... params) {
			
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETVIDEOLIST, reqtime);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETVIDEOLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("title", params[0]);
			map.put("order", Constants.PLAY_TIME);
			map.put("bag_id", "");
			map.put("curr_page", String.valueOf(curr_page));
			map.put("per_page", Constants.PER_PAGE_ALL);
			map.put("class", Constants.CLASS_MOVIE);
			map.put("type", "");
			
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
//			videoInfo.setTag(params[0]);
			return videoInfo;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			progressDialog.dismiss();
			searchEdit.setText("");
			VideoListInfo videoInfo = (VideoListInfo)result;
			if(videoInfo==null) return;
			
			if(videoInfo.getRet()==Constants.RET_SUCCESS_CODE){
			if(videoInfo.getTotal_count().equals("0")){
				hint.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
			}else{
				hint.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				setData(videoInfo);
			}}
			else{
				Toast.makeText(context, videoInfo.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
