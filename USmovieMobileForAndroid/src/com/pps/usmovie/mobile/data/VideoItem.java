package com.pps.usmovie.mobile.data;

import java.io.Serializable;

public class VideoItem implements Serializable{
	private String pic_src;
	private String video_title;
	private String video_src;
	
	public VideoItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "pic_src＝ "+pic_src+", video_title= "+video_title+", video_src= "+video_src;
		return result;
	}

	public String getPic_src() {
		return pic_src;
	}

	public void setPic_src(String pic_src) {
		this.pic_src = pic_src;
	}

	public String getVideo_title() {
		return video_title;
	}

	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}

	public String getVideo_src() {
		return video_src;
	}

	public void setVideo_src(String video_src) {
		this.video_src = video_src;
	}
}
