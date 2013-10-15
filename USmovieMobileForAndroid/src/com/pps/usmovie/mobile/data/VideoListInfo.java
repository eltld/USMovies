package com.pps.usmovie.mobile.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 视频列表信息
 * @author zhangxiaole
 *
 */
public class VideoListInfo implements Serializable{

	private int ret;

	private String message = "";
	
	private String total_count;
	private String per_page;
	private int curr_page;
	private int total_page;
	private int nearer_page;
	private String tag;
	
	private ArrayList<VideoListItem> videoInfoList = new ArrayList<VideoListItem>();
	
	private ArrayList<ClassItem> videoClassList = new ArrayList<ClassItem>();
	
	public VideoListInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "total_count=" +total_count+",per_page="+per_page+",curr_page="+curr_page+",total_page="+total_page+",nearer_page="+nearer_page;
	
		result += "\r\nvideoInfoList :";
		for(int i = 0; i < videoInfoList.size(); i++)
		{
			result += "\r\n" + videoInfoList.get(i).toString();
		}
		
		result += "\r\nvideoClassList:";
		for(int i = 0; i < videoClassList.size(); i++)
		{
			result += "\r\n" + videoClassList.get(i).toString();
		}
		
		return result;
	}

	public int getRet() {
		return ret;
	}


	public void setRet(int ret) {
		this.ret = ret;
	}


	public String getMessage() {
		if(ret == 201){
			setMessage("获取失败失败");
		}else if(ret == 202){
			setMessage("传递参数有误");
		}else if(ret == 203){
			setMessage("参数加密不对");
		}else if(ret == 204){
			setMessage("其他错误");
		}
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getTotal_count() {
		return total_count;
	}


	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}


	public String getPer_page() {
		return per_page;
	}


	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}


	
	public int getCurr_page() {
		return curr_page;
	}


	public void setCurr_page(int curr_page) {
		this.curr_page = curr_page;
	}


	public int getTotal_page() {
		return total_page;
	}


	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}


	public int getNearer_page() {
		return nearer_page;
	}


	public void setNearer_page(int nearer_page) {
		this.nearer_page = nearer_page;
	}


	public ArrayList<VideoListItem> getVideoInfoList() {
		return videoInfoList;
	}
	
	public void setVideoInfoList(ArrayList<VideoListItem> videoInfoList) {
		this.videoInfoList = videoInfoList;
	}
	
	public void addVideoInfoList(VideoListItem item){
		this.videoInfoList.add(item);
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public ArrayList<ClassItem> getVideoClassList() {
		return videoClassList;
	}
	
	public void setVideoClassList(ArrayList<ClassItem> videoClassList) {
		this.videoClassList = videoClassList;
	}
	
	public void addVideoClassList(ClassItem item){
		this.videoClassList.add(item);
	}
}
