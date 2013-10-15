package com.pps.usmovie.mobile.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

/**
 * 视频详情,主要信息
 * @author zhangxiaole
 *
 */
public class VideoDetailsItem implements Serializable{

	private int ret;

	private String message = "";
	private String json="";
	
	private String video_id;
	private String title;
	private String en_name;
	/**别名*/
	private String alias;
	/**对应电驴ID*/
	private String lv_id;
	private String pic_src;
	/**原始图片*/
	private String pic_original;
	
	/**分类 :1剧集, 2其他*/
	private String class_id;
	/**分类名称(可能是数组)*/
	private String class_name;
	private String[] class_array;
	/** 类型：1美剧 ,2电影*/
	private String type;
	
	/** 这个字段重要：保证多季剧集此字段值一致*/
	private String f_id;
	/** 第几季*/
	private String series;
	/**地区*/
	private String state;
	/**集数*/
	private String num;
	/**导演*/
	private String director;
	/**编剧*/
	private String writer;
	/**制片*/
	private String producer;
	/**演员*/
	private String actor;
	/**制造商*/
	private String maker;
	/**时长*/
	private String mins;
	/**语言*/
	private String langue;
	/**首播时间*/
	private String play_time;
	/**影片级别*/
	private String rank;
	/**评分*/
	private String grade;
	/**简介*/
	private String intro;
	/**说明字段*/
	private String remark;
	/**点击数*/
	private String view_num;
	
	private String status;
	private String add_time;
	/**影片来源*/
	private String source;
	/**共有几季*/
	private int series_num;
	
	/**导演 列表详情*/
	private ArrayList<MemberItem> directorList = new ArrayList<MemberItem>();
	/**演员  列表详情*/
	private ArrayList<MemberItem> actorList = new ArrayList<MemberItem>();
	/**作者列表*/
	private ArrayList<MemberItem> writerList = new ArrayList<MemberItem>();
	/**制片人列表*/
	private ArrayList<MemberItem> producerList = new ArrayList<MemberItem>();
	/**发行公司列表*/
	private ArrayList<IssuItem> issuList = new ArrayList<IssuItem>();
	/**出品公司列表*/
	private ArrayList<IssuItem> makerList = new ArrayList<IssuItem>();
	/**奖项列表*/
	private ArrayList<AwardItem> awardList = new ArrayList<AwardItem>();
	
	/**图片列表*/
	private ArrayList<PicItem> picList = new ArrayList<PicItem>();
	/**相关视频*/
	private ArrayList<VideoItem> videoList = new ArrayList<VideoItem>();
	/**新闻列表*/
	private ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();
	/**评论列表*/
	private ArrayList<CommentItem> commentList = new ArrayList<CommentItem>();
	/**播放地址 列表*/
	private ArrayList<PlayItem> playUrlList = new ArrayList<PlayItem>();
	
	/**单集剧评*/
	private int plot_more;
	private String comment;
	
	public VideoDetailsItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
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

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}

	public String getPic_src() {
		return pic_src;
	}

	public void setPic_src(String pic_src) {
		this.pic_src = pic_src;
	}

	public String getPic_original() {
		return pic_original;
	}

	public void setPic_original(String pic_original) {
		this.pic_original = pic_original;
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

	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String[] getGradeArray(){
		String[] gradeArray = null;
		if(!TextUtils.isEmpty(grade)){
			gradeArray = grade.split(" ");
		}
		return gradeArray;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getView_num() {
		return view_num;
	}

	public void setView_num(String view_num) {
		this.view_num = view_num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public int getSeries_num() {
		return series_num;
	}

	public void setSeries_num(int series_num) {
		this.series_num = series_num;
	}

	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getClass_name() {
		return class_name;
	}
	
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	
	public String[] getClass_array() {
		if(!TextUtils.isEmpty(class_name)){
			String[] classArray = class_name.split(" ");
			return classArray;
		}
		return class_array;
	}
	
	public String getMaker() {
		return maker;
	}
	
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	public String getMins() {
		return mins;
	}
	
	public void setMins(String mins) {
		this.mins = mins;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public ArrayList<MemberItem> getDirectorList() {
		return directorList;
	}

	public void setDirectorList(ArrayList<MemberItem> directorList) {
		this.directorList = directorList;
	}
	
	public void addDirectorList(MemberItem directorItem){
		this.directorList.add(directorItem);
	}

	public ArrayList<MemberItem> getActorList() {
		return actorList;
	}

	public void setActorList(ArrayList<MemberItem> actorList) {
		this.actorList = actorList;
	}
	
	public void addActorList(MemberItem actorsItem){
		this.actorList.add(actorsItem);
	}

	public ArrayList<IssuItem> getIssuList() {
		return issuList;
	}
	
	public void setIssuList(ArrayList<IssuItem> issuList) {
		this.issuList = issuList;
	}
	
	public void addIssu(IssuItem item){
		this.issuList.add(item);
	}
	
	public ArrayList<IssuItem> getMakerList() {
		return makerList;
	}
	
	public void setMakerList(ArrayList<IssuItem> makerList) {
		this.makerList = makerList;
	}

	public ArrayList<PicItem> getPicList() {
		return picList;
	}

	public void setPicList(ArrayList<PicItem> picList) {
		this.picList = picList;
	}
	
	public void addPicList(PicItem picItem){
		this.picList.add(picItem);
	}

	public ArrayList<VideoItem> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<VideoItem> videoList) {
		this.videoList = videoList;
	}
	
	public void addVideoList(VideoItem videoItem){
		this.videoList.add(videoItem);
	}

	public ArrayList<NewsItem> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<NewsItem> newsList) {
		this.newsList = newsList;
	}
	
	public void addNewsList(NewsItem newsItem){
		this.newsList.add(newsItem);
	}

//	public ArrayList<PlotItem> getPlotList() {
//		return plotList;
//	}
//	
//	public void setPlotList(ArrayList<PlotItem> plotList) {
//		this.plotList = plotList;
//	}
//	
//	public void addPlotList(PlotItem item){
//		this.plotList.add(item);
//	}
	
	public int getPlot_more() {
		return plot_more;
	}

	public void setPlot_more(int plot_more) {
		this.plot_more = plot_more;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public ArrayList<CommentItem> getCommentList() {
		return commentList;
	}
	
	public void setCommentList(ArrayList<CommentItem> commentList) {
		this.commentList = commentList;
	}
	
	public void addCommentList(CommentItem item){
		this.commentList.add(item);
	}
	
	public ArrayList<PlayItem> getPlayUrlList() {
		return playUrlList;
	}
	
	public void setPlayUrlList(ArrayList<PlayItem> playUrlList) {
		this.playUrlList = playUrlList;
	}
	
	public void addPlayUrl(PlayItem item){
		this.playUrlList.add(item);
	}
	
	public void addProducer(MemberItem item){
		this.producerList.add(item);
	}
	
	public ArrayList<MemberItem> getProducerList() {
		return producerList;
	}
	
	public void setProducerList(ArrayList<MemberItem> producerList) {
		this.producerList = producerList;
	}
	
	public ArrayList<MemberItem> getWriterList() {
		return writerList;
	}
	
	public void setWriterList(ArrayList<MemberItem> writerList) {
		this.writerList = writerList;
	}
	
	public void addWriter(MemberItem item){
		this.writerList.add(item);
	}
	
	public ArrayList<AwardItem> getAwardList() {
		return awardList;
	}
	
	public void setAwardList(ArrayList<AwardItem> awardList) {
		this.awardList = awardList;
	}
	
	public void addAward(AwardItem awardItem){
		this.awardList.add(awardItem);
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
