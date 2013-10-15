package com.pps.usmovie.mobile.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.ClassItem;
import com.pps.usmovie.mobile.data.VideoListInfo;
import com.pps.usmovie.mobile.data.VideoListItem;


public class VideoListInfoParser {

	/**
	 * 获取视频列表Json信息
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static VideoListInfo getVideoList(String json) throws JSONException{
		
		JSONObject retObject = new JSONObject(json);
		VideoListInfo videoInfo = new VideoListInfo();
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
			
			videoInfo.setRet(Constants.RET_SUCCESS_CODE);
			videoInfo.setTotal_count(total_count);
			videoInfo.setPer_page(per_page);
			videoInfo.setCurr_page(curr_page);
			videoInfo.setTotal_page(total_page);
			videoInfo.setNearer_page(nearer_page);
			
			JSONArray rsArray = messageObject.optJSONArray("rs");
			if(rsArray!=null){
				for(int i=0; i<rsArray.length(); i++){
					JSONObject reObject = (JSONObject) rsArray.get(i);
					VideoListItem item = new VideoListItem();
					
					String video_id = reObject.optString("video_id");
					String title = reObject.optString("title");
					String en_name = reObject.optString("en_name");
					String pic_src = reObject.optString("pic_src");
					String class_id = reObject.optString("class_id");
					String type = reObject.optString("type");
					String remark = reObject.optString("remark");
					
					String className = reObject.optString("class");
					String play_time = reObject.optString("play_time");
					String actor = reObject.optString("actor");
					String grade = reObject.optString("grade");
					
					item.setVideo_id(video_id);
					item.setTitle(title);
					item.setEn_name(en_name);
					item.setPic_src(pic_src);
					item.setClass_id(class_id);
					item.setType(type);
					item.setRemark(remark);
					item.setClassName(className);
					item.setPlayTime(play_time);
					item.setActor(actor);
					item.setGrade(grade);
					
					videoInfo.addVideoInfoList(item);
				}
			}
			
			JSONArray classArray = retObject.optJSONArray("class_list");
			if(classArray!=null&&classArray.length()>0){
				for(int i=0; i<classArray.length(); i++){
					JSONObject classObject = (JSONObject) classArray.get(i);
					ClassItem classItem = new ClassItem();
					String classId = classObject.optString("class_id");
					String className = classObject.optString("class_name");
					
					classItem.setClassId(classId);
					classItem.setClassName(className);
					videoInfo.addVideoClassList(classItem);
				}
			}
		} else{
			videoInfo.setRet(retObject.optInt("ret"));
		}
		return videoInfo;
	}
}
