package com.pps.usmovie.mobile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pps.usmovie.mobile.R;
import com.pps.usmovie.mobile.adapter.CallBackBitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 图片下载
 * @author zhangxiaole
 *
 */
public class ImageLoader {
	private static final String TAG = "ImageLoader";
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	HashMap<String, String> threadMap = new HashMap<String, String>();
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	// 线程池
	ExecutorService executorService;
	/**是否裁剪图片大小，默认是裁剪为小图*/
	private boolean isCutPic = true;
	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}
	
	public ExecutorService getExecutor(){
		
		return executorService;
	}

	// 当进入listview时默认的图片，可换成你自己的默认图片
	final int stub_id = R.drawable.img_bg;
	private CallBackBitmap callBackBitmap;

	// 最主要的方法
	public void DisplayImage(String url, ImageView imageView) {
		if(TextUtils.isEmpty(url)) return;
		imageViews.put(imageView, url);
		// 先从内存缓存中查找
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			try{
				imageView.setImageBitmap(bitmap);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		else {
			// 若没有的话则开启新线程加载图片
			queuePhoto(url, imageView);
//			imageView.setImageResource(stub_id);
		}
	}
	
	/**根据回调接口  显示图片*/
	public void DisplayImage(String url,ImageView imageView, CallBackBitmap callBackBitmap) {
		
		if(TextUtils.isEmpty(url)) return;
		imageViews.put(imageView, url);
		// 先从内存缓存中查找
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			callBackBitmap.setBitmap(bitmap);
		else {
			// 若没有的话则开启新线程加载图片
			queuePhoto(url, imageView);
		}
	}
	
	/**
	 * 显示带圆角的图片
	 * @param url
	 * @param imageView
	 * @param roundPx 圆角的度数，数值越大，圆角越大
	 */
	public void DisplayRoundedCornerImage(String url, ImageView imageView,float roundPx) {
		
		imageViews.put(imageView, url);
		// 先从内存缓存中查找
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null){
			imageView.setImageBitmap(ImageUtil.getRoundedCornerBitmap(bitmap, roundPx));
		}
		else {
			// 若没有的话则开启新线程加载图片
			queueRoundedCornerPhoto(url, imageView,roundPx);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		
		if(TextUtils.isEmpty(threadMap.get(url))){
			threadMap.put(url, url);
			executorService.submit(new PhotosLoader(p));
		}
	}
	
	private void queueRoundedCornerPhoto(String url, ImageView imageView, float roundPx) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, roundPx);
		executorService.submit(new PhotosLoader(p));
	}
	

	private Bitmap getBitmap(String url) throws IOException {
		
		File file = fileCache.getFile(url);
		// 先从文件缓存中查找是否有
		Bitmap bitmapCache = decodeFile(file);
		
		if (bitmapCache != null)
			return bitmapCache;

		// 最后从指定的url中下载图片
		OutputStream os = null;
		InputStream is = null;
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			is = conn.getInputStream();
			os = new FileOutputStream(file);
			CopyStream(is, os);

			bitmap = decodeFile(file);
			
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			is.close();
			os.close();
		}
	}

	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	private Bitmap decodeFile(File f) {
		
		try {
			// decode image size
			
			if(isCutPic){
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(new FileInputStream(f), null, options);

				// Find the correct scale value. It should be the power of 2.
				int REQUIRED_SIZE = 70;
				int width_tmp = options.outWidth, height_tmp = options.outHeight;
				if(options.mCancel|| width_tmp==-1|| height_tmp == -1){
					return null;
				}
				int scale = 1;
				while (true) {
					if (width_tmp / 2 < REQUIRED_SIZE|| height_tmp / 2 < REQUIRED_SIZE)
						break;
					width_tmp /= 2;
					height_tmp /= 2;
					scale *= 2;
				}

				// decode with inSampleSize
				BitmapFactory.Options o2 = new BitmapFactory.Options();
				o2.inSampleSize = scale;

				return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			}else{
				return BitmapFactory.decodeStream(new FileInputStream(f));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public float roundPx;
		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
		public PhotoToLoad(String u, ImageView i,float r) {
			url = u;
			imageView = i;
			roundPx = r;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			
			if (imageViewReused(photoToLoad))
				return;
			
			Bitmap bmp = null;
			try {
				bmp = getBitmap(photoToLoad.url);
			} catch (IOException e) {				
				e.printStackTrace();
			}

			if(photoToLoad.roundPx!=0.0){
				bmp = ImageUtil.getRoundedCornerBitmap(bmp, photoToLoad.roundPx);
			}
			memoryCache.put(photoToLoad.url, bmp);
			memoryCache.putImageView(photoToLoad.url, photoToLoad.imageView);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			if(callBackBitmap!=null){
				callBackBitmap.setBitmap(bd.bitmap);
				threadMap.remove(photoToLoad.url);
			}else{
				// 更新的操作放在UI线程中
				Activity a = (Activity) photoToLoad.imageView.getContext();
				a.runOnUiThread(bd);
				threadMap.remove(photoToLoad.url);
			}
		}
	}

	/**
	 * 防止图片错位
	 * 
	 * @param photoToLoad
	 * @return
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// 用于在UI线程中更新界面
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null){
				photoToLoad.imageView.setImageBitmap(bitmap);
//				photoToLoad.imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}
			else{
				photoToLoad.imageView.setImageResource(stub_id);
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	
	// 回调接口
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String imageUrl);
	}

	public void setCutPic(boolean isCutPic) {
		this.isCutPic = isCutPic;
	}
	
	public boolean getCutPic(){
		return this.isCutPic;
	}
}
