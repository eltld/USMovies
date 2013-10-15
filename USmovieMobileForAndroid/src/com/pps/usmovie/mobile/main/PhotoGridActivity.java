package com.pps.usmovie.mobile.main;

import java.util.ArrayList;
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
import android.widget.GridView;
import android.widget.Toast;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.PhotoGridAdatper;
import com.pps.usmovie.mobile.common.BaseActivity;
import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.exception.USdramaException;
import com.pps.usmovie.mobile.parser.Result;
import com.pps.usmovie.mobile.parser.VideoDetailsListParser;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

public class PhotoGridActivity extends BaseActivity implements OnItemClickListener {

	private static final String TAG = "PhotoGridActivity";
	private String ONREFRESH = "onRefresh";
	private String ONLOADMORE = "onLoadMore";
	private int curr_page=0;
	private int total_page;
	private String total_count;
	private int nearer_page;
	private ArrayList<PicItem> picList;
	private String classType="1";
//	private ProgressDialog progressDialog;
	
	@Override
	public void doCreate(Bundle bundle) throws USdramaException {
		setContentView(R.layout.photo_grid_window);
		String videoId = getIntent().getStringExtra("videoId");
		picList = (ArrayList<PicItem>)getIntent().getSerializableExtra("picList");
		String type = getIntent().getStringExtra("classType");
		if(!TextUtils.isEmpty(type)) {
			classType = type;
		}
		init(picList);
		getPicList(videoId);
	}

	private void getPicList(String videoId) {
		GetPicListTask task = new GetPicListTask(this);
		task.execute(videoId, ONREFRESH);
		
	}

	private void init(ArrayList<PicItem> picList) {
		GridView gridView = (GridView)findViewById(R.id.photo_gridView);
		gridView.setOnItemClickListener(this);
		PhotoGridAdatper adapter = new PhotoGridAdatper(this, picList);
		gridView.setAdapter(adapter);
	}

	@Override
	public void doClick(View view) throws USdramaException {
		switch (view.getId()) {
		case R.id.title_left_img_btn:
			this.finish();
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(PhotoGridActivity.this, PhotoGalleryActivity.class);
		intent.putExtra("selectId", arg2);
		intent.putExtra("picList", picList);
		PhotoGridActivity.this.startActivity(intent);
	}
	
	@Override
	public void setTitle() throws USdramaException {
		titleTV.setText("图片预览");
		leftImgBtn.setVisibility(View.VISIBLE);
		leftImgBtn.setImageResource(R.drawable.title_back_img_selected_style);
		leftImgBtn.setOnClickListener(this);
	}

	class GetPicListTask extends AsyncTask<String, Integer, Object>{

		private Context context;
		public GetPicListTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}
		
		@Override
		protected Object doInBackground(String... params) {
			
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETRELATEDPICLIST, reqtime);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETRELATEDPICLIST);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("video_id", params[0]);
			map.put("class", classType);
			if(params[1].equalsIgnoreCase(ONLOADMORE)){
				map.put("curr_page", String.valueOf(curr_page++));
				map.put("per_page", "50");
			}else{
				map.put("curr_page", "0");
				map.put("per_page", "50");
			}
			
			Set<Map.Entry<String, String>> list = map.entrySet();
			Log.d(TAG, "..GetPicListTask Params list : " + list.toString());
			Result<PicItem> result = new Result<PicItem>();
			
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				
				result = VideoDetailsListParser.getPicItemListInfo(json);
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
			result.setTag(params[1]);
			return result;
		}
		
		@Override
		protected void onPostExecute(Object object) {
			progressDialog.dismiss();
			Result<PicItem> result = (Result<PicItem>)object;
			
			if(result!=null){
				if(result.getRet() == Constants.RET_SUCCESS_CODE){
					curr_page = result.getCurr_page();
					total_page = result.getTotal_page();
					total_count = result.getTotal_count();
					picList = result.getList();
					
					init(picList);
				}else{
					Toast.makeText(PhotoGridActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
