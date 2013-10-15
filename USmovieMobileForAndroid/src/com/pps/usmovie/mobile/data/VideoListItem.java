package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 视频列表详细信息
 * @author zhangxiaole
 *
 */
public class VideoListItem implements Serializable{

	private String video_id;
	private String title="";
	private String en_name="";
	private String pic_src;
	private String remark;
	private String class_id;
	private String type;
	private String date;//保存到我的收藏夹 时间
	private String json;//保存到我的收藏夹的json
	
	private String className;
	private String playTime;
	private String actor="";
	/**评分*/
	private String grade="";
	
	public VideoListItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "video_id=" + video_id + ",title=" + title
				+ ",en_name=" + en_name + ",pic_src=" + pic_src + ",class_id="
				+ class_id + ",type=" + type+ ",className=" + className+ ",playTime=" + playTime+ ",actor=" + actor+ ",grade=" + grade;

		return result;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getPic_src() {
		return pic_src;
	}

	public void setPic_src(String pic_src) {
		this.pic_src = pic_src;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
