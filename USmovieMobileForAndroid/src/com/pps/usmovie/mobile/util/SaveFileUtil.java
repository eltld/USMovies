package com.pps.usmovie.mobile.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pps.usmovie.mobile.common.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class SaveFileUtil {

	public static final int SAVESUCCEEDCODE = 0;
	public static final int SAVEERRORDCODE = 1;
	/**
	 * Drawable 保存为图片
	 * @param path
	 * @param fileName
	 * @param d
	 */
	public static int saveDrawableToImgae(String path,String fileName, Drawable d){
		
		BitmapDrawable bd = (BitmapDrawable) d;
		Bitmap bm = bd.getBitmap();
		BufferedOutputStream bos = null;
		try{
			checkFile(path);
	        File myCaptureFile = new File(path + fileName+Constants.JPEG);  
	        bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
	        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
	        return SAVESUCCEEDCODE;
		}catch(Exception e){
			 e.printStackTrace();
			 return SAVEERRORDCODE;
		}finally{
			 try {
				bos.flush();
				bos.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	/**
	 * 把file保存为图片
	 * @param fileName
	 * @return
	 */
	public static int saveFileToImage(String fileName) {
		File file = new File(Constants.PATH_SDCARD_IMAGES + String.valueOf(fileName.hashCode()));
		BufferedOutputStream bos = null;
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			if(bitmap==null){
				return SAVEERRORDCODE;
			}
			checkFile(Constants.PATH_SDCARD_VISIBLE_ROOT);
			File myCaptureFile = new File(Constants.PATH_SDCARD_VISIBLE_ROOT + String.valueOf(fileName.hashCode())+Constants.JPEG);  
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			return SAVESUCCEEDCODE;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SAVEERRORDCODE;
		}
	}
	
	
	private static void checkFile(String path){
		File dirFile = new File(path);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }
	}
}
