package com.pps.usmovie.mobile.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.VideoMenuItem;

/**
 * 解析滑动菜单栏数据
 * @author zhangxiaole
 *
 */
public class VideoMenuParser {

	
	public static Result<VideoMenuItem> getVideoMenuList(String json) throws JSONException{
		Result<VideoMenuItem> result = new Result<VideoMenuItem>();
		
		JSONObject retObject = new JSONObject(json);
		Object obj = retObject.get("ret");
		if (obj instanceof JSONObject) {
			JSONObject messageObject = (JSONObject) obj;
			
			String pager = messageObject.optString("pager");
			JSONObject pagerObject = new JSONObject(pager);
			
			String total_count = pagerObject.optString("total_count");
			String per_page = pagerObject.optString("per_page");
			int curr_page = pagerObject.optInt("curr_page");
			int total_page = pagerObject.optInt("total_page");
			int nearer_page = pagerObject.optInt("nearer_page");
			
			result.setRet(Constants.RET_SUCCESS_CODE);
			result.setTotal_count(total_count);
			result.setPer_page(per_page);
			result.setCurr_page(curr_page);
			result.setTotal_page(total_page);
			result.setNearer_page(nearer_page);
			
			JSONArray rsArray = messageObject.optJSONArray("rs");
			if(rsArray!=null){
				for(int i=0; i<rsArray.length(); i++){
					JSONObject reObject = (JSONObject) rsArray.get(i);
					VideoMenuItem item = new VideoMenuItem();
					
					String id = reObject.optString("id");
					String name = reObject.optString("name");
					String status = reObject.optString("status");
					
					item.setId(id);
					item.setName(name);
					item.setStatus(status);
					
					result.addList(item);
				}
			}
		} else{
			result.setRet(retObject.optInt("ret"));
		}
		return result;
	}
}
