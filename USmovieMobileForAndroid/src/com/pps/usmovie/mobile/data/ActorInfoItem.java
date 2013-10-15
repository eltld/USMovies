package com.pps.usmovie.mobile.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 演员详细信息
 * @author zhangxiaole
 *
 */
public class ActorInfoItem implements Serializable {
	
	private int ret;

	private String message = "";
	private String json="";
	
	private String actorId;
	private String out_id;
	private String zh_name;
	private String en_name;
	private String pic_src;
	private String big_pic;
	private String pic_original;
	private String abo;
	private String height;
	private String weight;
	private String remark;
	private String intro;
	/**星座*/
	private String constellation;
	/**评分*/
	private String grade;
	/**信息来源*/
	private String source;
	/**信息来源名称*/
	private String site;
	private String add_time;
	private String status;

	private ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();
	/**代表作品*/
	private ArrayList<WorksItem> workList = new ArrayList<WorksItem>();
	
	private ArrayList<PicItem> picList = new ArrayList<PicItem>();
	
	private ArrayList<VideoItem> videoList = new ArrayList<VideoItem>();
	/**奖项列表*/
	private ArrayList<AwardItem> awardList = new ArrayList<AwardItem>();
	
	public ActorInfoItem() {
		// TODO Auto-generated constructor stub
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public String getZh_name() {
		return zh_name;
	}

	public void setZh_name(String zh_name) {
		this.zh_name = zh_name;
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

	public String getBig_pic() {
		return big_pic;
	}

	public void setBig_pic(String big_pic) {
		this.big_pic = big_pic;
	}

	public String getPic_original() {
		return pic_original;
	}

	public void setPic_original(String pic_original) {
		this.pic_original = pic_original;
	}

	public String getAbo() {
		return abo;
	}

	public void setAbo(String abo) {
		this.abo = abo;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<NewsItem> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<NewsItem> newsList) {
		this.newsList = newsList;
	}

	public ArrayList<WorksItem> getWorkList() {
		return workList;
	}

	public void setWorkList(ArrayList<WorksItem> workList) {
		this.workList = workList;
	}

	public ArrayList<PicItem> getPicList() {
		return picList;
	}

	public void setPicList(ArrayList<PicItem> picList) {
		this.picList = picList;
	}
	
	public ArrayList<VideoItem> getVideoList() {
		return videoList;
	}
	
	public void setVideoList(ArrayList<VideoItem> videoList) {
		this.videoList = videoList;
	}
	
	public ArrayList<AwardItem> getAwardList() {
		return awardList;
	}
	
	public void setAwardList(ArrayList<AwardItem> awardList) {
		this.awardList = awardList;
	}
	
	public String getMessage() {
		/**　Json字符串
		　　形如 {"ret":203}
		　　200 非获取列表操作动作成功
		　　201 失败
		　　202 传递参数有误
		　　203 加密不对
		　　204 其他错误*/
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
	
	public int getRet() {
		return ret;
	}
	
	public void setRet(int ret) {
		this.ret = ret;
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
}
