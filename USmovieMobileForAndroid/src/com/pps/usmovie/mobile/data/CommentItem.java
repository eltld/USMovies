package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 评论
 * 
 * @author zhangxiaole
 * 
 */
public class CommentItem implements Serializable {

	private String source;
	private String user_name;
	private String content;
	private String title;
	private String url;
	/** 仅用于判断item是第几项 */
	private int index;
	private int num;

	public CommentItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "source = " + source + ", user_name = " + user_name
				+ ", content = " + content+ ", title = " + title
				+ ", url = " + url;
		return result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
