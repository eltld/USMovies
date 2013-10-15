package com.pps.usmovie.mobile.database;

/**
 * 我的收藏数据库数据
 * @author zhangxiaole
 *
 */
public interface CollectionTable extends BaseColumns  {
	
	static final String TABLE_NAME = "tb_collection";
	
	static final String VIDEO_ID = "videoId"; //视频Id
	static final String VIDEO_NAME = "videoName"; //视频名称
	static final String VIDEO_EN_NAME = "videoEnName";//英文名称
	static final String VIDEO_EXPLAN = "videoExplan";
	static final String PIC_URL = "pic_url";
	static final String JSON = "json";
	static final String DATE = "date";
	
	static final String CREATE_COLLECTION_SQL = 
		"CREATE TABLE " + TABLE_NAME + "(" +
			//_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		VIDEO_ID + " INTEGER," +
		VIDEO_NAME + " VARCHAR(50)," +
		VIDEO_EN_NAME + " VARCHAR(50)," +
		VIDEO_EXPLAN + " VARCHAR(50)," +
		PIC_URL + " VARCHAR(50)," +
		JSON + " VARCHAR(50)," +
		DATE + " VARCHAR(50)," +
		"PRIMARY KEY ("+VIDEO_ID+")" +
		")";
}
