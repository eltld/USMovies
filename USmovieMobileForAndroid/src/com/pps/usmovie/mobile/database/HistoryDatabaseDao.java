package com.pps.usmovie.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.pps.usmovie.mobile.common.Constants;
import com.pps.usmovie.mobile.data.CollectType;
import com.pps.usmovie.mobile.data.HistoryItem;
import com.pps.usmovie.mobile.util.Log;

/**
 * 播放历史,数据库处理
 * @author zhangxiaole
 *
 */
public abstract class HistoryDatabaseDao {

	private final static String TAG = "HistoryDatabaseDao";
	private Context mContext;
	public static final String SAVE = "saveHistory";
	public static final String GETHISTORY = "getHistory";
	public static final String DETELEHISTORY = "deteleHistory";
	private HistoryTask task;
	
	public HistoryDatabaseDao(Context context) {
		this.mContext = context;
		this.task = new HistoryTask();
	}
	
	/**
	 * 获取视频列表
	 * @param historyItem
	 */
	public void getHistoryVideoList(HistoryItem historyItem){
		task.execute(historyItem);
	}
	
	/**
	 * 删除视频
	 * @param item
	 */
	public void deteleVideo(HistoryItem item) {
		task.execute(item);
	}
	
	/**
	 * 保存视频
	 * @param historyItem
	 */
	public void saveHistoryVideo(HistoryItem historyItem) {
		task.execute(historyItem);
	}
	
	public CollectType<HistoryItem> deteleVideoByUrl(HistoryItem historyItem) {
		CollectType<HistoryItem> type = new CollectType<HistoryItem>();
		DatabaseHelper helper = null;
		try
		{   
		    helper = new DatabaseHelper(mContext);
		    SQLiteDatabase wirelessAudDataBase = helper.getWritableDatabase();
        	int code = wirelessAudDataBase.delete(HistoryTable.TABLE_NAME, HistoryTable.VIDEO_URL + "=?", new String[]{historyItem.getVideoUrl()});
        }
		catch (Exception e)
		{
		   Log.d(TAG, "---don't delete item " + historyItem.getVideoUrl());			
           e.printStackTrace();
        }
		finally
		{
			if(helper != null)
			{				
				helper.close();
			}
		}
		return type;
	}
	
	
	public CollectType<HistoryItem> getVideo(HistoryItem historyItem) {
		DatabaseHelper helper = null;		
//		List<HistoryItem> videoList = new ArrayList<HistoryItem>();
		CollectType<HistoryItem> collectType = new CollectType<HistoryItem>();
		try
		{
			Log.d(TAG, "getVideo begin...");
			
		    helper = new DatabaseHelper(mContext);
		    SQLiteDatabase wirelessAudDataBase = helper.getReadableDatabase();
		    
			Cursor cursor = wirelessAudDataBase.query(HistoryTable.TABLE_NAME, null, null, null, null, null, null);
			
			if(cursor == null || cursor.getCount() < 1)
			{
				Log.d(TAG, "there is no data.");
				return collectType;
			}
	
			Log.d(TAG, "there are " + cursor.getCount() + "VideoListItem.");
			
			int i = 0;
			while(cursor.moveToNext())
			{
				i = 0;
				String videoId = cursor.getString(i++);
				String videoName 	= cursor.getString(i++);
				String series     =  cursor.getString(i++);
				String videoNum 		=  cursor.getString(i++);
				String videoUrl 		=  cursor.getString(i++);
				String enName 		=  cursor.getString(i++);
				String json  =   cursor.getString(i++);	
				String date 	=  cursor.getString(i++);
				
				HistoryItem item = new HistoryItem();	
				item.setVideoId(videoId);
				item.setVideoName(videoName);
				item.setVideoNum(videoNum);
				item.setSeries(series);
				item.setVideoUrl(videoUrl);
				item.setEnName(enName);
				item.setDate(date);
				item.setJson(json);
				
				collectType.getData().add(item);
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
		return collectType;	
	}
	
	
	
	
	
	public CollectType<HistoryItem> saveVideo(HistoryItem historyItem) {
		if(historyItem==null||TextUtils.isEmpty(historyItem.getVideoId())){
			return null;
		}
		
		//检查内存空间;	
		DatabaseHelper helper = null;
		SQLiteDatabase wirelessAudDataBase = null;
		CollectType<HistoryItem> collectType = null;
		try
		{
		    helper = new DatabaseHelper(mContext);
		    wirelessAudDataBase = helper.getWritableDatabase();
		    wirelessAudDataBase.beginTransaction();
		    collectType = new CollectType<HistoryItem>();
		    
		    if(checkIsSave(wirelessAudDataBase,historyItem.getVideoUrl())){//视频已经存在
		    	collectType.setTypeCode(Constants.COLLECT_EXISTENCE_CODE);
		    	Log.d(TAG, "---the video had saved");
		    }else{
		    	collectType = saveVideoItem(wirelessAudDataBase, historyItem);
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
	
	private static CollectType<HistoryItem> saveVideoItem(SQLiteDatabase wirelessAudDataBase,
			HistoryItem historyItem) {
		CollectType<HistoryItem> type = new CollectType<HistoryItem>();
		if(TextUtils.isEmpty(historyItem.getVideoId()))
		{
			type.setTypeCode(Constants.COLLECT_ERROR_CODE);
			return type;
		}
		
		  ContentValues initialValues = new ContentValues();
		  
		  initialValues.put(HistoryTable.VIDEO_ID,    	historyItem.getVideoId()   == null? "":historyItem.getVideoId().trim());
		  initialValues.put(HistoryTable.VIDEO_NAME,    historyItem.getVideoName() == null? "":historyItem.getVideoName().trim());
		  initialValues.put(HistoryTable.VIDEO_NUM, 	historyItem.getVideoNum()  == null? "":historyItem.getVideoNum() .trim());
		  initialValues.put(HistoryTable.VIDEO_SERIES, 	historyItem.getSeries()	   == null? "":historyItem.getSeries().trim());
		  initialValues.put(HistoryTable.VIDEO_URL, 	historyItem.getVideoUrl()  == null? "":historyItem.getVideoUrl().trim());
		  initialValues.put(HistoryTable.VIDEO_ENNAME, 	historyItem.getEnName()      == null? "":historyItem.getEnName().trim());
		  initialValues.put(HistoryTable.DATE,     		historyItem.getDate()      == null? "":historyItem.getDate().trim());
		  initialValues.put(HistoryTable.JSON, 			historyItem.getJson()      == null? "":historyItem.getJson().trim());
		  
		  try
		  {
			  wirelessAudDataBase.insertOrThrow(HistoryTable.TABLE_NAME, null, initialValues);
			  type.setTypeCode(Constants.COLLECT_SUCCESS_CODE);
			  Log.d(TAG, "---save the Item successfully:" + historyItem.getVideoName());
		  }
		  catch(Exception e)
		  {
			  type.setTypeCode(Constants.COLLECT_ERROR_CODE);
			  Log.d(TAG, "---save saveVideoItem error:" + historyItem.getVideoName());
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
	private static boolean checkIsSave(SQLiteDatabase wirelessAudDataBase, String videoUrl) {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(videoUrl))		
		{			
			return false;
		}
		
		Cursor cursor = wirelessAudDataBase.query(HistoryTable.TABLE_NAME, new String[]{HistoryTable.VIDEO_ID}, 
				HistoryTable.VIDEO_URL + "=? ", new String[]{videoUrl}, null, null, null);
		
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
	
	
	
	
	
	class HistoryTask extends AsyncTask<HistoryItem, Integer, CollectType<HistoryItem>>{

		@Override
		protected CollectType<HistoryItem> doInBackground(HistoryItem... params) {
			CollectType<HistoryItem> collectType = null;
			if(params[0].getType().equalsIgnoreCase(SAVE)){//保存
				collectType = saveVideo(params[0]);
			}else if(params[0].getType().equalsIgnoreCase(GETHISTORY)){//获取列表
				collectType = getVideo(params[0]);
			}else if(params[0].getType().equalsIgnoreCase(DETELEHISTORY)){//删除视频
				collectType = deteleVideoByUrl(params[0]);
			}
			return collectType;
		}
		
		@Override
		protected void onPostExecute(CollectType<HistoryItem> result) {
			onCallBack(result);
		}
	}

	public abstract void onCallBack(CollectType<HistoryItem> result);

}
