package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 代表作品
 * 
 * @author zhangxiaole
 * 
 */
public class WorksItem implements Serializable {

	private String video_id;
	private String title;
	private String en_name;
	private String pic_src;
	private String play_time;

	public WorksItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "video_id=" + video_id + ",title=" + title
				+ ",en_name=" + en_name + ",pic_src" + pic_src + ",play_time"
				+ play_time;
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

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}
}
