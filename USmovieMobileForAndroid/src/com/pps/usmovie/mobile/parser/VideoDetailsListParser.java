package com.pps.usmovie.mobile.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.CommentItem;
import com.pps.usmovie.mobile.data.MemberItem;
import com.pps.usmovie.mobile.data.NewsItem;
import com.pps.usmovie.mobile.data.PicItem;

public class VideoDetailsListParser {

	public static Result<CommentItem> getCommentItemInfo(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		Result<CommentItem> result = new Result<CommentItem>();
		Object obj = jsonObject.get("ret");
		if (obj instanceof JSONObject) {
			String ret = jsonObject.optString("ret");
			
			result.setRet(Constants.RET_SUCCESS_CODE);
			
			JSONObject retObject = new JSONObject(ret);
			
			String pager = retObject.optString("pager");
			JSONObject pagerObject = new JSONObject(pager);
			result.setCurr_page(pagerObject.optInt("curr_page"));
			result.setNearer_page(pagerObject.optInt("nearer_page"));
			result.setPer_page(pagerObject.optString("per_page"));
			result.setTotal_count(pagerObject.optString("total_count"));
			result.setTotal_page(pagerObject.optInt("total_page"));
			
			String rs = retObject.optString("rs");
			JSONArray rsArray = new JSONArray(rs);
			for(int i=0; i<rsArray.length(); i++){
				JSONObject reObject = rsArray.optJSONObject(i);
				CommentItem item = new CommentItem();
				item.setSource(reObject.optString("source"));
				item.setUser_name(reObject.optString("user_name"));
				item.setContent(reObject.optString("content"));
				item.setTitle(reObject.optString("title"));
				item.setUrl(reObject.optString("url"));
				result.addList(item);
			}
		}else{
			result.setRet(jsonObject.optInt("ret"));
		}
		return result;
	}

	public static Result<NewsItem> getNewsItemInfo(String json) throws JSONException {
		
		JSONObject jsonObject = new JSONObject(json);
		Result<NewsItem> result = new Result<NewsItem>();
		Object obj = jsonObject.get("ret");
		if (obj instanceof JSONObject) {
			String ret = jsonObject.optString("ret");
			
			result.setRet(Constants.RET_SUCCESS_CODE);
			
			JSONObject retObject = new JSONObject(ret);
			
			String pager = retObject.optString("pager");
			JSONObject pagerObject = new JSONObject(pager);
			result.setCurr_page(pagerObject.optInt("curr_page"));
			result.setNearer_page(pagerObject.optInt("nearer_page"));
			result.setPer_page(pagerObject.optString("per_page"));
			result.setTotal_count(pagerObject.optString("total_count"));
			result.setTotal_page(pagerObject.optInt("total_page"));
			
			String rs = retObject.optString("rs");
			JSONArray rsArray = new JSONArray(rs);
			for(int i=0; i<rsArray.length(); i++){
				JSONObject reObject = rsArray.optJSONObject(i);
				NewsItem item = new NewsItem();
				item.setId(reObject.optString("id"));
				item.setWiki_id(reObject.optString("wiki_id"));
				item.setContent(reObject.optString("content"));
				item.setTitle(reObject.optString("title"));
				item.setGet_url(reObject.optString("get_url"));
				item.setAdd_time(reObject.optString("add_time"));
				
				result.addList(item);
			}
		}else{
			result.setRet(jsonObject.optInt("ret"));
		}
		return result;
	}
	/**
	 * 图片列表
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static Result<PicItem> getPicItemListInfo(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		Result<PicItem> result = new Result<PicItem>();
		Object obj = jsonObject.get("ret");
		if (obj instanceof JSONObject) {
			String ret = jsonObject.optString("ret");
			
			result.setRet(Constants.RET_SUCCESS_CODE);
			
			JSONObject retObject = new JSONObject(ret);
			
			String pager = retObject.optString("pager");
			JSONObject pagerObject = new JSONObject(pager);
			result.setCurr_page(pagerObject.optInt("curr_page"));
			result.setNearer_page(pagerObject.optInt("nearer_page"));
			result.setPer_page(pagerObject.optString("per_page"));
			result.setTotal_count(pagerObject.optString("total_count"));
			result.setTotal_page(pagerObject.optInt("total_page"));
			
			String rs = retObject.optString("rs");
			JSONArray rsArray = new JSONArray(rs);
			for(int i=0; i<rsArray.length(); i++){
				JSONObject reObject = rsArray.optJSONObject(i);
				PicItem item = new PicItem();
				item.setPic_src(reObject.optString("pic_src"));
				item.setBig_pic_src(reObject.optString("big_pic_src"));
				
				result.addList(item);
			}
		}else{
			result.setRet(jsonObject.optInt("ret"));
		}
		return result;
	}

	/**
	 * 主演列表
	 * @param json
	 * @return
	 */
	public static Result<MemberItem> getActorItemInfo(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		Result<MemberItem> result = new Result<MemberItem>();
		Object obj = jsonObject.get("ret");
		if (obj instanceof JSONArray) {
			String ret = jsonObject.optString("ret");
			
			result.setRet(Constants.RET_SUCCESS_CODE);
			
			JSONArray rsArray = new JSONArray(ret);
			for(int i=0; i<rsArray.length(); i++){
				JSONObject reObject = rsArray.optJSONObject(i);
				MemberItem item = new MemberItem();
				item.setId(reObject.optString("id"));
				item.setZh_name(reObject.optString("zh_name"));
				item.setEn_name(reObject.optString("en_name"));
				item.setPic_src(reObject.optString("pic_src"));
				
				result.addList(item);
			}
		}else{
			result.setRet(jsonObject.optInt("ret"));
		}
		return result;
	}
}
