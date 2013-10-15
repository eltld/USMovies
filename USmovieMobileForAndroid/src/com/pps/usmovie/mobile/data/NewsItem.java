	package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 新闻列表详情
 * @author zhangxiaole
 *
 */
public class NewsItem implements Serializable{

	private String id;
	private String wiki_id;
	private String title;
	private String classId;
	private String content;
	
	private String get_url;
	private String add_time;
	
	
	public NewsItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "id＝ " + id + ", wiki_id= " + wiki_id + ", title= "
				+ title + ", content= " + content + ", get_url= " + get_url
				+ ", add_time= " + wiki_id+ ", classId= " + classId;
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWiki_id() {
		return wiki_id;
	}

	public void setWiki_id(String wiki_id) {
		this.wiki_id = wiki_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGet_url() {
		return get_url;
	}

	public void setGet_url(String get_url) {
		this.get_url = get_url;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	
	public String getClassId() {
		return classId;
	}
	
	public void setClassId(String classId) {
		this.classId = classId;
	}
}
