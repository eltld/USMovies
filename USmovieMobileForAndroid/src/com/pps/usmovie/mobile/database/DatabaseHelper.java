package com.pps.usmovie.mobile.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pps.usmovie.mobile.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private String TAG = "DatabaseHelper";
	private static final String DATABASE_NAME = "USdrama.db";
	private static final int VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			Log.d(TAG, "...DatabaseHelper onCreate begin...");
			/**我的收藏*/
			db.execSQL(CollectionTable.CREATE_COLLECTION_SQL);
			
			/**我的收藏*/
			db.execSQL(HistoryTable.CREATE_HISTORY_SQL);
			
		 }catch(Exception e){
	    	   Log.d(TAG, "...DatabaseHelper onCreate exception...");    	   
	    	   e.printStackTrace();
	       }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
