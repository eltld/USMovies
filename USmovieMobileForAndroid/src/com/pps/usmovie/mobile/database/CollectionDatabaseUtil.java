package com.pps.usmovie.mobile.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.CollectType;
import com.pps.usmovie.mobile.data.VideoListItem;
import com.pps.usmovie.mobile.util.Log;

/**
 * 我的收藏数据库操作
 * @author zhangxiaole
 *
 */
public class CollectionDatabaseUtil {

	private static String TAG = "CollectionDatabaseUtil";
	private static String DETELE_VIDEO = "deteleVideo";
	private Context mContext;
	private CollectionDatabaseTask task ;
	
	public CollectionDatabaseUtil(Context context) {
		this.mContext = context;
		this.task = new CollectionDatabaseTask(mContext);
	}
	/**
	 * 获取 收藏夹所有内容
	 * @param context
	 * @return
	 */
	public static List<VideoListItem> getCollectVideoInfo(Context context){
		
		DatabaseHelper helper = null;		
		List<VideoListItem> videoList = new ArrayList<VideoListItem>();
		try
		{
			Log.d(TAG, "getCollectVideoInfo begin...");
			
		    helper = new DatabaseHelper(context);
		    SQLiteDatabase wirelessAudDataBase = helper.getReadableDatabase();
		    
			Cursor cursor = wirelessAudDataBase.query(CollectionTable.TABLE_NAME, null, null, null, null, null, null);
			
			if(cursor == null || cursor.getCount() < 1)
			{
				Log.d(TAG, "there is no data.");
				return videoList;
			}
	
			Log.d(TAG, "there are " + cursor.getCount() + "VideoListItem.");
			
			int i = 0;
			while(cursor.moveToNext())
			{
				i = 0;
				String videoId = cursor.getString(i++);
				String videoName 	= cursor.getString(i++);
				String videoEnName     =  cursor.getString(i++);
				String videoExplan 		=  cursor.getString(i++);
				String pic_url 		=  cursor.getString(i++);
				String json  =   cursor.getString(i++);	
				String date 	=  cursor.getString(i++);
				
				VideoListItem item = new VideoListItem();	
				item.setVideo_id(videoId);
				item.setTitle(videoName);
				item.setEn_name(videoEnName);
				item.setRemark(videoExplan);
				item.setPic_src(pic_url);
				item.setDate(date);
				item.setJson(json);
				
				videoList.add(item);
			}
			cursor.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(helper != null)
			{
				helper.close();
			}
		}
		return videoList;	
	}

	
	public static CollectType<String> saveVideoInfo(Context context, VideoListItem videoListItem) {
		if(videoListItem==null||TextUtils.isEmpty(videoListItem.getVideo_id())){
			return null;
		}
		
		//检查内存空间;	
		DatabaseHelper helper = null;
		SQLiteDatabase wirelessAudDataBase = null;
		CollectType<String> collectType = null;
		try
		{
		    helper = new DatabaseHelper(context);
		    wirelessAudDataBase = helper.getWritableDatabase();
		    wirelessAudDataBase.beginTransaction();
		    collectType = new CollectType<String>();
		    
//		    boolean a = checkIsSave(wirelessAudDataBase,videoListItem.getVideo_id());
		    if(checkIsSave(wirelessAudDataBase,videoListItem.getVideo_id())){//视频已经存在
		    	collectType.setTypeCode(Constants.COLLECT_EXISTENCE_CODE);
		    	Log.d(TAG, "---the video had saved");
		    }else{
		    	collectType = saveVideoItem(wirelessAudDataBase, videoListItem);
		    }
		    
		    wirelessAudDataBase.setTransactionSuccessful();
		    return collectType;
		}
		catch(Exception e)
		{
			collectType.setTypeCode(Constants.COLLECT_ERROR_CODE);
			e.printStackTrace();
			return collectType;
		}
		finally
		{
			if(wirelessAudDataBase != null)
			{
			   wirelessAudDataBase.endTransaction();			   
			}
			
			if(helper != null)
			{
				helper.close();
			}
		}
		
	}
	
	private static CollectType<String> saveVideoItem(SQLiteDatabase wirelessAudDataBase,
			VideoListItem videoListItem) {
		Log.d(TAG, "---saveVideoItem begin: " + videoListItem.getTitle());
		CollectType<String> type = new CollectType<String>();
		if(TextUtils.isEmpty(videoListItem.getVideo_id()))
		{
			type.setTypeCode(Constants.COLLECT_ERROR_CODE);
			return type;
		}
		
		  ContentValues initialValues = new ContentValues();
		  
		  initialValues.put(CollectionTable.VIDEO_ID,    	videoListItem.getVideo_id()   == null? "":videoListItem.getVideo_id().trim());
		  initialValues.put(CollectionTable.VIDEO_NAME,     videoListItem.getTitle()    == null? "":videoListItem.getTitle().trim());
		  initialValues.put(CollectionTable.VIDEO_EN_NAME, 	videoListItem.getEn_name()     == null? "":videoListItem.getEn_name() .trim());
		  initialValues.put(CollectionTable.VIDEO_EXPLAN, 	videoListItem.getRemark()== null? "":videoListItem.getRemark().trim());
		  initialValues.put(CollectionTable.PIC_URL, 		videoListItem.getPic_src()== null? "":videoListItem.getPic_src().trim());
		  initialValues.put(CollectionTable.DATE,     		videoListItem.getDate()   == null? "":videoListItem.getDate().trim());
		  initialValues.put(CollectionTable.JSON, 			videoListItem.getJson() == null? "":videoListItem.getJson().trim());
		  try
		  {
			  wirelessAudDataBase.insertOrThrow(CollectionTable.TABLE_NAME, null, initialValues);
			  type.setTypeCode(Constants.COLLECT_SUCCESS_CODE);
			  Log.d(TAG, "---save the Item successfully:" + videoListItem.getTitle());
		  }
		  catch(Exception e)
		  {
			  type.setTypeCode(Constants.COLLECT_ERROR_CODE);
			  Log.d(TAG, "---save saveVideoItem error:" + videoListItem.getTitle());
			  e.printStackTrace();
		  } 
		Log.d(TAG, "----saveVideoItem finish...");
		
		return type;
	}
	
	/**
	 * 检查视频是否存在
	 * @param wirelessAudDataBase 
	 * @param videoListItem
	 * @return
	 */
	private static boolean checkIsSave(SQLiteDatabase wirelessAudDataBase, String  videoId) {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(videoId))		
		{			
			return false;
		}
		
		Cursor cursor = wirelessAudDataBase.query(CollectionTable.TABLE_NAME, new String[]{CollectionTable.VIDEO_ID}, 
				CollectionTable.VIDEO_ID + "=? ", new String[]{videoId}, null, null, null);
		
		if(cursor == null)
		{			
			return false;
		}
		
		int count = cursor.getCount();
		cursor.close();
		
		if(count > 0)
		  return true;
		else
		  return false;
	}

	
	public void deteleVideo(String videoId){
		task.execute(DETELE_VIDEO, videoId);
	}
	/**
	 * 删除视频
	 * @param context
	 * @param videoId
	 */
	public int deteleVideoById(String videoId){
		
		DatabaseHelper helper = null;
		SQLiteDatabase wirelessAudDataBase = null;
		int result = 0 ;
		try
		{
		    helper = new DatabaseHelper(mContext);
		    wirelessAudDataBase = helper.getWritableDatabase();
		    
		    if(!TextUtils.isEmpty(videoId)){
		    	
		    	result = deleteVideo(wirelessAudDataBase, videoId);
		    }
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(helper != null)
			{
				helper.close();
			}
		}
		return result;
	}
	
	/**
	 * 删除操作
	 * @param wirelessAudDataBase
	 * @param videoId
	 * @return
	 */
	private static int deleteVideo(SQLiteDatabase wirelessAudDataBase,
			String videoId) {
		return wirelessAudDataBase.delete(CollectionTable.TABLE_NAME, CollectionTable.VIDEO_ID + "=?" , new String[]{videoId});
	}


	class CollectionDatabaseTask extends AsyncTask<String, Integer, Object>{

		private Context context;
		public CollectionDatabaseTask(Context context) {
			this.context = context;
		}
		@Override
		protected Object doInBackground(String... params) {
			
			int result = 0;
			if(params[0].equalsIgnoreCase(DETELE_VIDEO)){
				result = deteleVideoById(params[1]);
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Object result) {
		}
	}
}
