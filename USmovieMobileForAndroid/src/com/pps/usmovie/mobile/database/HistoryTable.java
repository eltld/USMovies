package com.pps.usmovie.mobile.database;

/**
 * 播放历史数据库数据
 * @author zhangxiaole
 *
 */
public interface HistoryTable extends BaseColumns  {
	
	static final String TABLE_NAME = "tb_playHistory";
	
	static final String VIDEO_ID = "videoId"; //视频Id
	static final String VIDEO_NAME = "videoName"; //视频名称
	static final String VIDEO_SERIES = "series"; //第几季
	static final String VIDEO_NUM = "videoNum"; //第几集
	static final String VIDEO_URL = "videoUrl"; //视频连接
	static final String VIDEO_ENNAME = "enName"; //英文名称
	static final String JSON = "json";
	static final String DATE = "date";
	
	static final String CREATE_HISTORY_SQL = 
		"CREATE TABLE " + TABLE_NAME + "(" +
			//_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		VIDEO_ID + " INTEGER," +
		VIDEO_NAME + " VARCHAR(50)," +
		VIDEO_SERIES + " VARCHAR(50)," +
		VIDEO_NUM + " VARCHAR(50)," +
		VIDEO_URL + " VARCHAR(50)," +
		VIDEO_ENNAME + " VARCHAR(50)," +
		JSON + " VARCHAR(50)," +
		DATE + " VARCHAR(50)," +
		"PRIMARY KEY ("+VIDEO_ID+")" +
		")";
}
