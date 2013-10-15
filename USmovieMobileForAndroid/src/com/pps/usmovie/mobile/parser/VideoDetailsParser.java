package com.pps.usmovie.mobile.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.AwardItem;
import com.pps.usmovie.mobile.data.CommentItem;
import com.pps.usmovie.mobile.data.IssuItem;
import com.pps.usmovie.mobile.data.MemberItem;
import com.pps.usmovie.mobile.data.NewsItem;
import com.pps.usmovie.mobile.data.PicItem;
import com.pps.usmovie.mobile.data.PlayItem;
import com.pps.usmovie.mobile.data.VideoDetailsItem;
import com.pps.usmovie.mobile.data.VideoItem;
import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.util.DateUtil;
import com.pps.usmovie.mobile.util.MD5Util;
import com.pps.usmovie.mobile.webservice.WebService;

/**
 * 视频详情解析
 * 
 * @author zhangxiaole
 * 
 */
public class VideoDetailsParser {

	private static String databaseJson;
	public static void getVideoDetailsInfo(String video_id,String json, MovieDetailsCallBack movieDetailsCallBack) {
		if (TextUtils.isEmpty(video_id))
			return;
		databaseJson = json;
		GetMovieDetailsInfoTask task = new GetMovieDetailsInfoTask(movieDetailsCallBack);
		task.execute(video_id);
	}

	static class GetMovieDetailsInfoTask extends
			AsyncTask<String, Integer, Object> {

		private MovieDetailsCallBack callBack;

		public GetMovieDetailsInfoTask(MovieDetailsCallBack movieDetailsCallBack) {
			callBack = movieDetailsCallBack;
		}

		@Override
		protected Object doInBackground(String... params) {

			String reqtime = DateUtil.getCurrentTimeStamp();
			String token = MD5Util.getToken(Constants.GETVIDEOINFO, reqtime);

			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.GETVIDEOINFO);
			map.put("method", Constants.GET);
			map.put("token", token);
			map.put("reqtime", reqtime);
			map.put("video_id", params[0]);
			map.put("supply", "1");
			map.put("class", Constants.CLASS_MOVIE);

			Set<Map.Entry<String, String>> list = map.entrySet();
			VideoDetailsItem videoDetailsItem = new VideoDetailsItem();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
				// detailsJson = json;
				videoDetailsItem = getVideoInfo(json);
				videoDetailsItem.setJson(json);
				// 视频访问统计
				// incrVideoViewNum(params[0]);
			}

			catch (ConnectServerException e) {
				videoDetailsItem.setRet(Constants.CONNECT_EXCEPTION_CODE);
				videoDetailsItem.setMessage(e.getErrorMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				videoDetailsItem.setRet(Constants.RET_ERROR_CODE);
				videoDetailsItem.setMessage("json解析错误");
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return videoDetailsItem;
		}

		/** 视频访问统计 */
		private void incrVideoViewNum(String videoId) {
			String incrReqtime = DateUtil.getCurrentTimeStamp();
			String incrIoken = MD5Util.getToken(Constants.INCRVIDEOVIEWNUM,
					incrReqtime);

			Map<String, String> map = new HashMap<String, String>();
			map.put("app", Constants.APP);
			map.put("interface", Constants.INCRVIDEOVIEWNUM);
			map.put("method", Constants.GET);
			map.put("token", incrIoken);
			map.put("reqtime", incrReqtime);
			map.put("video_id", incrIoken);
			Set<Map.Entry<String, String>> list = map.entrySet();
			try {
				String json = WebService.doGet(list, Constants.URL_WIKI_MAIN);
			} catch (ConnectServerException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			 VideoDetailsItem videoInfo = (VideoDetailsItem) result;
			 if (videoInfo != null) {
				 if(videoInfo.getRet() == Constants.RET_SUCCESS_CODE){
					 callBack.onBackData(videoInfo);
				 }else if(!TextUtils.isEmpty(databaseJson)){
					 try {
							VideoDetailsItem videoDetailsItem = VideoDetailsParser.getVideoInfo(databaseJson);
							callBack.onBackData(videoDetailsItem);
						} catch (JSONException e) {
							e.printStackTrace();
						}
				 }else{
					 callBack.onBackData(videoInfo);
				 }
			 }
		}
	}

	public static VideoDetailsItem getVideoInfo(String json)
			throws JSONException {

		VideoDetailsItem videoDetailsItem = new VideoDetailsItem();

		JSONObject retObject = new JSONObject(json);
		Object obj = retObject.get("ret");
		if (obj instanceof JSONObject) {
			String ret = retObject.optString("ret");

			JSONObject messageObject = new JSONObject(ret);
			videoDetailsItem.setRet(Constants.RET_SUCCESS_CODE);
			setMainData(videoDetailsItem, messageObject);
			// 导演信息
			setDirectorsData(videoDetailsItem, messageObject);
			// 演员信息
			setActorsData(videoDetailsItem, messageObject);
			// 制片
			setProducerData(videoDetailsItem, messageObject);
			// 作者
			setWriterData(videoDetailsItem, messageObject);
			//奖项
			setAwardData(videoDetailsItem, messageObject);
			// 图片列表
			setPicData(videoDetailsItem, messageObject);
			// 视频列表
			setVideoData(videoDetailsItem, messageObject);
			// 新闻列表
			setNewsData(videoDetailsItem, messageObject);
			// 评论列表
			setCommentsData(videoDetailsItem, messageObject);
			//发行公司
			setIssuData(videoDetailsItem, messageObject);
			//出品公司
			setMakerData(videoDetailsItem, messageObject);
			//播放地址
			setPlayData(videoDetailsItem, messageObject);
			
		} else {
			videoDetailsItem.setRet(retObject.optInt("ret"));
			videoDetailsItem.setMessage("数据出错！");
		}
		return videoDetailsItem;
	}

	private static void setPlayData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String play_urls = messageObject.optString("play_urls");
		if (TextUtils.isEmpty(play_urls))
			return;
		JSONArray playArray = new JSONArray(play_urls);
		for (int i = 0; i < playArray.length(); i++) {
			JSONObject playObject = (JSONObject) playArray.get(i);
			PlayItem item = new PlayItem();
			item.setType(playObject.optString("type"));
			item.setName(playObject.optString("name"));
			item.setUrls(playObject.optString("urls"));
			
			videoDetailsItem.addPlayUrl(item);
		}
	}

	private static void setMakerData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String maker = messageObject.optString("maker");
		if (TextUtils.isEmpty(maker)||maker.equals("null"))
			return;
		JSONArray makerArray = new JSONArray(maker);
		for (int i = 0; i < makerArray.length(); i++) {
			String maker2 = makerArray.getString(i);
			IssuItem item = new IssuItem();
			item.setIssuing(maker2);
			videoDetailsItem.addIssu(item);
		}
	}

	private static void setIssuData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String issuing = messageObject.optString("issuing");
		if (TextUtils.isEmpty(issuing)||issuing.equals("null"))
			return;
		JSONArray issuingArray = new JSONArray(issuing);
		for (int i = 0; i < issuingArray.length(); i++) {
			String issu = issuingArray.getString(i);
			IssuItem item = new IssuItem();
			item.setIssuing(issu);
			videoDetailsItem.addIssu(item);
		}
	}

	private static void setAwardData(VideoDetailsItem videoDetailsItem,
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
			videoDetailsItem.addAward(item);
		}
	}

	/**
	 * 作者
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setWriterData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String writers = messageObject.optString("writers");
		if (TextUtils.isEmpty(writers))
			return;
		JSONArray writerArray = new JSONArray(writers);
		for (int i = 0; i < writerArray.length(); i++) {
			JSONObject writerObject = (JSONObject) writerArray.get(i);
			MemberItem item = new MemberItem();
			item.setEn_name(writerObject.optString("en_name", ""));
			item.setZh_name(writerObject.optString("zh_name", ""));
			item.setId(writerObject.optString("id", ""));
			item.setPic_src(writerObject.optString("pic_src", ""));
			videoDetailsItem.addWriter(item);
		}
	}

	/**
	 * 制片信息
	 */
	private static void setProducerData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String producers = messageObject.optString("producers");
		if (TextUtils.isEmpty(producers))
			return;
		JSONArray producerArray = new JSONArray(producers);
		for (int i = 0; i < producerArray.length(); i++) {
			JSONObject producerObject = (JSONObject) producerArray.get(i);
			MemberItem item = new MemberItem();
			item.setEn_name(producerObject.optString("en_name", ""));
			item.setZh_name(producerObject.optString("zh_name", ""));
			item.setId(producerObject.optString("id", ""));
			item.setPic_src(producerObject.optString("pic_src", ""));
			videoDetailsItem.addProducer(item);
		}

	}

	/**
	 * 评论列表
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setCommentsData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String comment = messageObject.optString("comment");
		if (TextUtils.isEmpty(comment))
			return;
		JSONArray commentArray = new JSONArray(comment);
		for (int i = 0; i < commentArray.length(); i++) {
			JSONObject commentObject = (JSONObject) commentArray.get(i);
			CommentItem item = new CommentItem();
			item.setContent(commentObject.optString("content"));
			item.setSource(commentObject.optString("source"));
			item.setUser_name(commentObject.optString("user_name"));
			item.setTitle(commentObject.optString("title"));
			item.setUrl(commentObject.optString("url"));
			videoDetailsItem.getCommentList().add(item);
		}
	}

	/**
	 * 新闻列表
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setNewsData(VideoDetailsItem videoDetailsItem,
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

			videoDetailsItem.getNewsList().add(item);
		}
	}

	/**
	 * 视频列表
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setVideoData(VideoDetailsItem videoDetailsItem,
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

			videoDetailsItem.getVideoList().add(item);
		}
	}

	private static void setPicData(VideoDetailsItem videoDetailsItem,
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
			videoDetailsItem.getPicList().add(item);
		}
	}

	/**
	 * 播放列表
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	// private static void setPlayUrls(VideoDetailsItem videoDetailsItem,
	// JSONObject messageObject) throws JSONException {
	//
	// String play_urls = messageObject.optString("play_urls");
	// if(TextUtils.isEmpty(play_urls)){
	// return;
	// }
	// JSONArray playUrlsArray = new JSONArray(play_urls);
	// for(int i=0; i<playUrlsArray.length(); i++){
	// JSONObject playUrlsObject = (JSONObject) playUrlsArray.get(i);
	// PlayUrlItem item = new PlayUrlItem();
	// item.setType(playUrlsObject.optString("type"));
	// item.setUrls(playUrlsObject.optString("urls"));
	//
	// videoDetailsItem.getPlayUrlList().add(item);
	// }
	// }

	/**
	 * 共有季数信息
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	// private static void setSeriesInfo(VideoDetailsItem videoDetailsItem,
	// JSONObject messageObject) throws JSONException {
	// String seriesInfo = messageObject.optString("series_info");
	// if(TextUtils.isEmpty(seriesInfo)) return;
	// JSONArray seriesInfoArray = new JSONArray(seriesInfo);
	// for(int i=0; i<seriesInfoArray.length(); i++){
	// JSONObject seriesInfoObject = (JSONObject) seriesInfoArray.get(i);
	// SeriesInfoItem item = new SeriesInfoItem();
	// item.setVideo_id(seriesInfoObject.optString("video_id"));
	// item.setTitle(seriesInfoObject.optString("title"));
	// item.setSeries(seriesInfoObject.optString("series"));
	// videoDetailsItem.getSeriesInfoList().add(item);
	// }
	// }

	/**
	 * 演员信息
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setActorsData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {

		String actors = messageObject.optString("actors");
		if (TextUtils.isEmpty(actors))
			return;
		JSONArray actorArray = new JSONArray(actors);
		for (int i = 0; i < actorArray.length(); i++) {
			JSONObject actorObject = (JSONObject) actorArray.get(i);
			MemberItem item = new MemberItem();
			item.setEn_name(actorObject.optString("en_name", ""));
			item.setZh_name(actorObject.optString("zh_name", ""));
			item.setId(actorObject.optString("id", ""));
			item.setPic_src(actorObject.optString("pic_src", ""));
			videoDetailsItem.getActorList().add(item);
		}
	}

	/**
	 * 导演信息
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 * @throws JSONException
	 */
	private static void setDirectorsData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) throws JSONException {
		String directors = messageObject.optString("directors");
		if (TextUtils.isEmpty(directors))
			return;
		JSONArray directorArray = new JSONArray(directors);
		for (int i = 0; i < directorArray.length(); i++) {
			JSONObject directorObject = (JSONObject) directorArray.get(i);
			MemberItem item = new MemberItem();
			item.setEn_name(directorObject.optString("en_name", ""));
			item.setZh_name(directorObject.optString("zh_name", ""));
			item.setId(directorObject.optString("id", ""));
			item.setPic_src(directorObject.optString("pic_src", ""));
			videoDetailsItem.getDirectorList().add(item);
		}
	}

	/**
	 * 主体JSON
	 * 
	 * @param videoDetailsItem
	 * @param messageObject
	 */
	private static void setMainData(VideoDetailsItem videoDetailsItem,
			JSONObject messageObject) {

		videoDetailsItem.setVideo_id(messageObject.optString("video_id"));
		videoDetailsItem.setTitle(messageObject.optString("title"));
		videoDetailsItem.setEn_name(messageObject.optString("en_name"));
		videoDetailsItem.setLv_id(messageObject.optString("lv_id"));
		videoDetailsItem.setPic_src(messageObject.optString("pic_src"));
		videoDetailsItem.setPic_original(messageObject
				.optString("pic_original"));
		videoDetailsItem.setClass_id(messageObject.optString("class_id"));
		videoDetailsItem.setType(messageObject.optString("type"));
		videoDetailsItem.setF_id(messageObject.optString("f_id"));
		videoDetailsItem.setSeries(messageObject.optString("series"));
		videoDetailsItem.setState(messageObject.optString("state"));
		videoDetailsItem.setNum(messageObject.optString("num"));
		videoDetailsItem.setDirector(messageObject.optString("director"));
		videoDetailsItem.setWriter(messageObject.optString("writer"));
		videoDetailsItem.setActor(messageObject.optString("actor"));
		videoDetailsItem.setLangue(messageObject.optString("langue"));
		videoDetailsItem.setPlay_time(messageObject.optString("play_time"));
		videoDetailsItem.setGrade(messageObject.optString("grade"));
		videoDetailsItem.setIntro(messageObject.optString("intro"));
		videoDetailsItem.setRemark(messageObject.optString("remark"));
		videoDetailsItem.setView_num(messageObject.optString("view_num"));
		videoDetailsItem.setStatus(messageObject.optString("status"));
		videoDetailsItem.setAdd_time(messageObject.optString("add_time"));

		videoDetailsItem.setAlias(messageObject.optString("alias", ""));
		videoDetailsItem.setClass_name(messageObject.optString("class", ""));
		videoDetailsItem.setMaker(messageObject.optString("maker", ""));
		videoDetailsItem.setMins(messageObject.optString("mins", ""));
		videoDetailsItem.setProducer(messageObject.optString("producer", ""));
		videoDetailsItem.setRank(messageObject.optString("rank", ""));
		videoDetailsItem.setSource(messageObject.optString("source", ""));

		// videoDetailsItem.setSeries_num(messageObject.optInt("series_num"));

		// videoDetailsItem.setPlot_more(messageObject.optInt("plot_more"));
	}

}
