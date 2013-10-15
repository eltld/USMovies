package com.pps.usmovie.mobile.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.ActorInfoItem;
import com.pps.usmovie.mobile.data.AwardItem;
import com.pps.usmovie.mobile.data.NewsItem;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.data.VideoItem;
import com.pps.usmovie.mobile.data.WorksItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;


public class ActorInfoParser {

	/**
	 * 获取演员详细信息
	 * @param actorId 演员Id
	 * @param movieDetailsCallBack
	 */
	public static void getActorInfo(String actorId,ActorInfoCallBack actorInfoCallBack) {
		if (TextUtils.isEmpty(actorId))
			return;

		GetActorInfoTask task = new GetActorInfoTask(actorInfoCallBack);
		task.execute(actorId);
	}

	
	static class GetActorInfoTask extends AsyncTask<String, Intent, ActorInfoItem>{

		private ActorInfoCallBack callBack;

		public GetActorInfoTask(ActorInfoCallBack actorInfoCallBack) {
			callBack = actorInfoCallBack;
		}
		
		@Override
		protected ActorInfoItem doInBackground(String... params) {
			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETSTARINFO, reqtime);

			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETSTARINFO);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("star_id", params[0]);
			map.put("supply", "1");

			Set<Map.Entry<String, String>> list = map.entrySet();
			ActorInfoItem actorInfoItem = new ActorInfoItem();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				actorInfoItem = getActorInfo(json);
				actorInfoItem.setJson(json);
			}

			catch (ConnectServerException e) {
				actorInfoItem.setRet(Constants.CONNECT_EXCEPTION_CODE);
				actorInfoItem.setMessage(e.getErrorMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				actorInfoItem.setRet(Constants.RET_ERROR_CODE);
				actorInfoItem.setMessage("json解析错误");
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return actorInfoItem;
		}
		
		@Override
		protected void onPostExecute(ActorInfoItem result) {
			callBack.onBackData(result);
		}
	}

	public static ActorInfoItem getActorInfo(String json) throws JSONException {
		ActorInfoItem actorInfoItem = new ActorInfoItem();

		JSONObject retObject = new JSONObject(json);
		Object obj = retObject.get("ret");
		if (obj instanceof JSONObject) {
			String ret = retObject.optString("ret");

			JSONObject messageObject = new JSONObject(ret);
			actorInfoItem.setRet(Constants.RET_SUCCESS_CODE);
			setMainData(actorInfoItem, messageObject);
			// 图片列表
			setPicData(actorInfoItem, messageObject);
			// 新闻列表
			setNewsData(actorInfoItem, messageObject);
			// 代表作品
			setWorksData(actorInfoItem, messageObject);
			//相关视频
			setVideoData(actorInfoItem,messageObject);
			//奖项
			setAwardData(actorInfoItem, messageObject);
		} else {
			actorInfoItem.setRet(retObject.optInt("ret"));
			actorInfoItem.setMessage("数据出错！");
		}
		return actorInfoItem;
	}


	private static void setAwardData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) throws JSONException {
		String awards = messageObject.optString("award");
		if (TextUtils.isEmpty(awards))
			return;
		JSONArray awardArray = new JSONArray(awards);
		for (int i = 0; i < awardArray.length(); i++) {
			JSONObject awardObject = (JSONObject) awardArray.get(i);
			AwardItem item = new AwardItem();
			item.setId(awardObject.optString("id", ""));
			item.setWikiId(awardObject.optString("wiki_id", ""));
			item.setName(awardObject.optString("name", ""));
			item.setAward(awardObject.optString("award", ""));
			item.setRemark(awardObject.optString("remark", ""));
			actorInfoItem.getAwardList().add(item);
		}
	}


	private static void setVideoData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) throws JSONException {
		String video = messageObject.optString("video");
		if (TextUtils.isEmpty(video))
			return;
		JSONArray videoArray = new JSONArray(video);
		for (int i = 0; i < videoArray.length(); i++) {
			JSONObject videoObject = (JSONObject) videoArray.get(i);
			VideoItem item = new VideoItem();
			item.setPic_src(videoObject.optString("pic_src"));
			item.setVideo_src(videoObject.optString("video_src"));
			item.setVideo_title(videoObject.optString("video_title"));

			actorInfoItem.getVideoList().add(item);
		}
	}


	private static void setWorksData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) throws JSONException {
		String works = messageObject.optString("works");
		if (TextUtils.isEmpty(works))
			return;
		JSONArray worksArray = new JSONArray(works);
		for (int i = 0; i < worksArray.length(); i++) {
			JSONObject worksObject = (JSONObject) worksArray.get(i);
			WorksItem item = new WorksItem();
			item.setTitle(worksObject.optString("title"));
			item.setVideo_id(worksObject.optString("video_id"));
			item.setEn_name(worksObject.optString("en_name"));
			item.setPic_src(worksObject.optString("pic_src"));
			item.setPlay_time(worksObject.optString("play_time"));
			actorInfoItem.getWorkList().add(item);
		}
	}

	private static void setNewsData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) throws JSONException {
		String news = messageObject.optString("news");
		if (TextUtils.isEmpty(news))
			return;
		JSONArray newsArray = new JSONArray(news);
		for (int i = 0; i < newsArray.length(); i++) {
			JSONObject newsObject = (JSONObject) newsArray.get(i);
			NewsItem item = new NewsItem();
			item.setAdd_time(newsObject.optString("add_time"));
			item.setContent(newsObject.optString("content"));
			item.setGet_url(newsObject.optString("get_url"));
			item.setId(newsObject.optString("id"));
			item.setTitle(newsObject.optString("title"));
			item.setWiki_id(newsObject.optString("wiki_id"));
			item.setClassId(newsObject.optString("class"));

			actorInfoItem.getNewsList().add(item);
		}
	}

	private static void setPicData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) throws JSONException {
		String pic = messageObject.optString("pic");
		if (TextUtils.isEmpty(pic))
			return;
		JSONArray picArray = new JSONArray(pic);
		for (int i = 0; i < picArray.length(); i++) {
			JSONObject picObject = (JSONObject) picArray.get(i);
			PicItem item = new PicItem();
			item.setPic_src(picObject.optString("pic_src"));
			item.setBig_pic_src(picObject.optString("big_pic_src"));
			actorInfoItem.getPicList().add(item);
		}
	}

	private static void setMainData(ActorInfoItem actorInfoItem,
			JSONObject messageObject) {
		actorInfoItem.setActorId(messageObject.optString("id"));
		actorInfoItem.setOut_id(messageObject.optString("out_id"));
		actorInfoItem.setZh_name(messageObject.optString("zh_name"));
		actorInfoItem.setEn_name(messageObject.optString("en_name"));
		actorInfoItem.setPic_src(messageObject.optString("pic_src"));
		actorInfoItem.setBig_pic(messageObject.optString("big_pic"));
		actorInfoItem.setPic_original(messageObject.optString("pic_original"));
		actorInfoItem.setAbo(messageObject.optString("abo"));
		actorInfoItem.setHeight(messageObject.optString("height"));
		actorInfoItem.setWeight(messageObject.optString("weight"));
		actorInfoItem.setRemark(messageObject.optString("remark"));
		actorInfoItem.setIntro(messageObject.optString("intro"));
		actorInfoItem.setConstellation(messageObject.optString("constellation"));
		actorInfoItem.setGrade(messageObject.optString("grade"));
		actorInfoItem.setSource(messageObject.optString("source"));
		actorInfoItem.setSite(messageObject.optString("site"));
		actorInfoItem.setAdd_time(messageObject.optString("add_time"));
		actorInfoItem.setStatus(messageObject.optString("status"));
	}
}
