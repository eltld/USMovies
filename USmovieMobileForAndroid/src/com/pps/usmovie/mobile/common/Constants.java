package com.pps.usmovie.mobile.common;

public class Constants {

	public final static String PATH_SDCARD_ROOT = android.os.Environment.getExternalStorageDirectory()+"/.USmovie_mobile/";
	public final static String PATH_SDCARD_VISIBLE_ROOT = android.os.Environment.getExternalStorageDirectory()+"/HANBAO_movie/";
	public final static String PATH_SDCARD_CONF_EVENT_NAME = "config_event";
	public final static String PATH_SDCARD_CONF_TEXT_NAME = "config_text";
	public final static String PATH_SDCARD_FILES = PATH_SDCARD_ROOT + "files/";
	public final static String PATH_SDCARD_IMAGES = PATH_SDCARD_ROOT + "images/";
	public final static String PATH_SDCARD_AUDIO = PATH_SDCARD_ROOT + "audio/";
	public final static String PATH_SDCARD_CONF_ROOT = PATH_SDCARD_ROOT + "conf/";
	public final static String PATH_SDCARD_IMAGE_ROOT = PATH_SDCARD_VISIBLE_ROOT + "images/";
	
	public final static String JPEG = ".JPEG";
	public static int    SPASH_TIME = 2000;//延时2秒;
	
	public static final String STAT_PREFERENCE = "USmovie_mobile_stat";
	public static final String CHANNEL_PREFERENCES = "channel_movie";
	
	public static int RET_SUCCESS_CODE = 200;
	public static int RET_ERROR_CODE = 300;
	public static int CONNECT_EXCEPTION_CODE = 301;
	
	/**
	 * 方法名称
	 */
	public static String PER_PAGE = "20";
	public static String PER_PAGE_ALL = "100";
	public static String APP = "wiki";
	public static String POST = "post";
	public static String GET = "get";
	
	/**从侧边栏通知  刷新界面*/
	public static boolean ISREFURBISH_MENU = false;
	
	public static String CLASS_MOVIE = "2";
	public static String ACTOR_CLASS = "2";
	
	/**收藏视频成功*/
	public static int COLLECT_SUCCESS_CODE = 200;
	/**收藏视频 已存在*/
	public static int COLLECT_EXISTENCE_CODE = 300;
	/**收藏视频失败*/
	public static int COLLECT_ERROR_CODE = 300;
	
	/**最新*/
	public static String PLAY_TIME = "play_time";
	/**好评*/
	public static String GRADE = "grade";
	
	/**url主接口*/
	public static String URL_WIKI_MAIN = "http://stat.baiying.com/wiki/app.php";
	/**统计主接口*/
	public static String URL_STATISTICS_MAIN = "http://stat.baiying.com/api/bying.php";
	
	
	/**获取视频列表*/
	public static String GETVIDEOLIST = "getVideoList";
	/**获取左侧栏列表*/
	public static String GETBAGLIST =  "getBagList";
	/**获取视频详细信息*/
	public static String GETVIDEOINFO =  "getVideoInfo";
	/**获取分集剧情 列表*/
	public static String GETPLOTLIST =  "getPlotList";
	/**获取评论列表*/
	public static String GETCOMMENTLIST =  "getCommentList";
	/**主演列表*/
	public static String GETSTARLIST =  "getStarList";
	/**获取新闻 列表*/
	public static String GETNEWSLIST =  "getNewsList";
	/**获取相关图片 列表*/
	public static String GETRELATEDPICLIST =  "getRelatedPicList";
	/**获取相关视频 列表*/
	public static String GETRELATEDVIDEOLIST =  "getRelatedVideoList";
	/**演员详细信息*/
	public static String GETSTARINFO =  "getStarInfo";
	/**视频访问统计*/
	public static String INCRVIDEOVIEWNUM =  "incrVideoViewNum";
	/**下载量统计*/
	public static String INSTALLSTAT =  "installStat";
	/**播放列表图片*/
	public static String PLAY =  "play";
}
