package com.pps.usmovie.mobile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.pps.usmovie.mobile.common.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

/**
 * 图片处理
 * 
 * @author zhangxiaole
 * 
 */
public class ImageUtil {

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param pixels
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * bitmap保存为文件
	 * 
	 * @param bitName
	 * @throws IOException
	 */
	public static String saveMyBitmap(Context context, Bitmap mBitmap) throws IOException {
		
		String bitName = String.valueOf(DateUtil.getCurrentTime().hashCode());
		File dirFile;
		
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			dirFile = new File(Constants.PATH_SDCARD_IMAGES);
		else
			dirFile = context.getCacheDir();
		File parent = dirFile.getParentFile();
		if(!parent.exists()) {
		     //dst.mkdirs();   //问题源头，由于此行执行，将整个文件也作为文件夹创建，导致提示“(Is a directory)”  
		     parent.mkdirs(); 
		} 

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		 File mFile = new File(dirFile, bitName);
		
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(mFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return Constants.PATH_SDCARD_IMAGES + bitName + ".JPEG";
		return Constants.PATH_SDCARD_IMAGES + bitName;
	}

	  /**
     * 根据系统不同，有的返回URI为空
     * 假如返回的getdata中的uri不存在，则把返回的bitmap保存到本地
     * @param data
	 * @param j 
	 * @param widht 
     * @return
     */
    public static HashMap<String,Object> getBitmapByActivityResult(Context mContext, Intent data, int width, int heigh) {
    	Bitmap bitmap = null;
    	//拍照
    	HashMap<String,Object> map = new HashMap<String,Object>();
		Uri originalUri = data.getData();
		if(originalUri != null ){
			try {
				String path = getAbsoluteImagePath(mContext,originalUri);
				if(!TextUtils.isEmpty(path)){
//					Options option = new Options();
					bitmap =decodeFile(new File(path), width, heigh);
				}
				if(bitmap == null){
					path = originalUri.getPath();
					if(!TextUtils.isEmpty(path)){
						bitmap = ImageUtil.decodeFile(new File(path), 480, 800);
					}
					if(bitmap!=null){
						path = Constants.PATH_SDCARD_IMAGES+ DateUtil.getTimeStamps(new Date()).hashCode()+"";
						saveBitmapToLocal(bitmap, path);
						if(bitmap!=null){
							bitmap.recycle();
							bitmap = null;
						}
						bitmap =decodeFile(new File(path), width, heigh);
					}
				}
				
				if(bitmap != null) {
					map.put("bitmap",  bitmap);
					map.put("path", path );
				}  
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			//无法返回URI 则需要拷贝到自己的目录下
			Bundle extras = data.getExtras();
			if(extras != null){
				bitmap = (Bitmap) extras.get("data");
				String imgPic = Constants.PATH_SDCARD_IMAGES+ DateUtil.getTimeStamps(new Date()).hashCode()+"";
				saveBitmapToLocal(bitmap, imgPic);
				if(bitmap!=null){
					bitmap.recycle();
					bitmap = null;
				}
				bitmap =decodeFile(new File(imgPic), width, heigh);
//				bitmap =decodeFile(new File(imgPic), -1);
				map.put("path", imgPic);
				map.put("bitmap",  bitmap);

			}
		}
		return map;
	}

    public static File saveBitmapToLocal(Bitmap bitmap, String localPath) {

		File myCaptureFile = null;
		FileOutputStream b= null;
		try {
			//  将照片保存在SD卡的根目录（文件名是camera.jpg）  
			myCaptureFile = new File(localPath);
//			if(myCaptureFile.getParentFile().exists())
			myCaptureFile.getParentFile().mkdirs();
			if(myCaptureFile.exists()) {
				myCaptureFile.delete();
			}
			try {  
				b = new FileOutputStream(localPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件 
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(b!=null){
				try {
					b.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return myCaptureFile;
	}
    
    
	public static File decodeBitmap(Bitmap bitmap, String localPath) {

		File myCaptureFile = null;
		FileOutputStream b= null;
		try {
			//  将照片保存在SD卡的根目录（文件名是camera.jpg）  
			myCaptureFile = new File(localPath);
//			if(myCaptureFile.getParentFile().exists())
			myCaptureFile.getParentFile().mkdirs();
			if(myCaptureFile.exists()) {
				myCaptureFile.delete();
			}
			try {  
				b = new FileOutputStream(localPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件 
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(b!=null){
				try {
					b.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return myCaptureFile;
	}
    
	protected static String getAbsoluteImagePath(Context mContext, Uri uri)
    {
    	// can post image
    	String [] proj={MediaStore.Images.Media.DATA};
    	Cursor cursor = ((Activity)mContext).managedQuery( uri,
    			proj, // Which columns to return
    			null, // WHERE clause; which rows to return (all rows)
    			null, // WHERE clause selection arguments (none)
    			null); // Order-by clause (ascending by name)
    	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	cursor.moveToFirst();
    	return cursor.getString(column_index);
    }
	public static Drawable bitmapToDrawble(Bitmap bitmap ){
		if(bitmap!=null){
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}
		return null;
	}
	
	public static Bitmap drawbleToBitmap(Drawable d ){
		if(d instanceof BitmapDrawable){
			BitmapDrawable bd = (BitmapDrawable) d;
			Bitmap bm = bd.getBitmap();
			return bm;
		}
		return null;
	}
	
	
	public static Bitmap decodeFile(File f, int reqWidth,int  reqHeigh) {
		try {
			// decode image size
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options.inPurgeable = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, options);
			if(reqWidth == 0 || reqHeigh == 0){
//				options.inSampleSize = 1;
			} else {
				int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeigh);
				options.inSampleSize = inSampleSize;
			}
			options.inJustDecodeBounds = false;
			options.inTempStorage = new byte[10 * 1048]; 
			return BitmapFactory.decodeStream(new FileInputStream(f), null, options);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally{
		}
	}

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            final float totalPixels = width * height;

            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    
   static int ScaleTimes=1;
    
	public static Bitmap rorateBitmap(Bitmap bmp, int ScaleAngle) {
		int widthOrig = bmp.getWidth();
		int heightOrig = bmp.getHeight();
		 int newWidth=widthOrig*ScaleTimes;
         int newHeight=heightOrig*ScaleTimes;
         float scaleWidth=((float)newWidth)/widthOrig;
         float scaleHeight=((float)newHeight)/heightOrig;
         Matrix matrix=new Matrix();
         matrix.postScale(scaleWidth, scaleHeight);
         matrix.setRotate( ScaleAngle);
         Bitmap resizeBitmap=Bitmap.createBitmap(bmp, 0, 0, widthOrig, heightOrig, matrix, true);
//         BitmapDrawable myNewBitmapDrawable=new BitmapDrawable(resizeBitmap);
//         mImageView.setImageDrawable(myNewBitmapDrawable);
         return resizeBitmap;
	}
	
	public   static Bitmap getCircleBitmap(Bitmap bitmap) {
		int shadowColor = 0xff424242;
		int width = bitmap.getWidth();
		int height  = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(width,  height, Config.ARGB_8888);
		/**
		 *	bitmapCanvas的目的是将图片转成圆形图案
		 *	这里采用先画个白底带有阴影的图片,然后采用paint的Mode.SRC_IN方式把圆图划出来
		 */
		Canvas bitmapCanvas = new Canvas(output);  
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		bitmapCanvas.drawARGB(0, 0, 0, 0);  
		paint.setColor(shadowColor);
		Rect rect = null;
		rect = new Rect(0,0,width,height);
		if(width > height ){
			bitmapCanvas.drawCircle(width/2, height/2, height/2 - 10, paint);
		} else {
			bitmapCanvas.drawCircle(width/2, height/2, width/2 - 10, paint);
		}
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		bitmapCanvas.drawBitmap(bitmap, rect,rect, paint); 
		
	
		paint.reset();
		
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		/**
		 * 把圆图和目前画好的白底灰阴影的图片组合起来
		 * radius 是半径 阴影颜色是灰色
		 */
		paint.setShadowLayer(5f, 5.0f, 5.0f, shadowColor);
		 
		Bitmap retBitmap = Bitmap.createBitmap(width+10,  height+10, Config.ARGB_8888);
		Canvas canvas = new Canvas(retBitmap);
		
		if(width > height ){
			canvas.drawCircle(width/2, height/2, height/2, paint);
		} else {
			canvas.drawCircle(width/2, height/2, width/2, paint);
		}
		paint.reset();
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
		//下层图片覆盖到上层显示
		canvas.drawBitmap(output, rect, rect, paint);
		
		return retBitmap;
	}  
}


