package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 播放列表
 * @author zhangxiaole
 *
 */
public class PlayItem implements Serializable {

	private String type;
	private String name;
	private String urls;
	
	public PlayItem() {
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}
}
