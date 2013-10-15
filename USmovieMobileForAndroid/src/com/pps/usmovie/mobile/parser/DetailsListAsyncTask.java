package com.pps.usmovie.mobile.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.os.AsyncTask;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.CommentItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.Log;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

public class DetailsListAsyncTask extends AsyncTask<String, Integer, Object> {

	public DetailsListAsyncTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object doInBackground(String... params) {
		
		String interfaceName = params[1];
		String video_id = params[2];
		String reqtime = DateUtil.getCurrentTimeStamp();
		String token = MD5Util.getToken(Constants.GETCOMMENTLIST, reqtime);

//		Map<String, String> map = new HashMap<String, String>();
//		map.put("app", Constants.APP);
//		map.put("interface", Constants.GETCOMMENTLIST);
//		map.put("method", Constants.GET);
//		map.put("token", token);
//		map.put("reqtime", reqtime);
//		map.put("video_id", params[0]);
//		map.put("curr_page", "0");
//		map.put("per_page", Constants.PER_PAGE_ALL);
//		Set<Map.Entry<String, String>> list = map.entrySet();
//		Result<CommentItem> result = new Result<CommentItem>();
//		try {
//			String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
//			result = VideoDetailsListParser.getCommentItemInfo(json);
//		} catch (JSONException e) {
//			result.setRet(Constants.RET_ERROR_CODE);
//			result.setMessage("json解析错误");
//			e.printStackTrace();
//		} catch (ConnectServerException e) {
//			result.setRet(Constants.CONNECT_EXCEPTION_CODE);
//			result.setMessage(e.getErrorMessage());
//			e.printStackTrace();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		result.setTag(params[1]);
		return null;
	}
	
	
}
