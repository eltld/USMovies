package com.pps.usmovie.mobile.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片缓存
 * @author zhangxiaole
 *
 */
public class MemoryCache {

	private static final String TAG = "MemoryCache";
	// 放入缓存时是个同步操作
	// LinkedHashMap构造方法的最后一个参数true代表这个map里的元素将按照最近使用次数由少到多排列，即LRU
	// 这样的好处是如果要将缓存中的元素替换，则先遍历出最近最少使用的元素来替换以提高效率
	private Map<String, Bitmap> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	private Map<String, ImageView> imageViewCache = new HashMap<String, ImageView>();
	//LinkedHashMap<String> a = LinkedHashMap<String>();
	// 缓存中图片所占用的字节，初始0，将通过此变量严格控制缓存所占用的堆内存
	private long size = 0;// current allocated size
	// 缓存只能占用的最大堆内存
	private long limit = 300000;// max memory in bytes
	private long bitmapSize = 0;

	public MemoryCache() {
		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 6);
	}

	public void setLimit(long new_limit) {
		limit = new_limit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			return cache.get(id);
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public void put(String id, Bitmap bitmap) {
		
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();

		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	public void putImageView(String url, ImageView imageView){
		try{
			if(!imageViewCache.containsKey(url)){
				imageViewCache.put(url, imageView);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setImageToNull(String url){
		try{
			if(!imageViewCache.containsKey(url)){
				ImageView img = imageViewCache.get(url);
				img.setImageBitmap(null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 严格控制堆内存，如果超过将首先替换最近最少使用的那个图片缓存
	 * 
	 */
	private void checkSize() {

		Log.v(TAG, "cache size=" + size + " length=" + cache.size());

		if (size > limit) {
			// 先遍历最近最少使用的元素
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();

			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();

				if (entry.getValue() != null && !entry.getValue().isRecycled()) {

					try {
						cache.remove(entry.getKey());
						setImageToNull(entry.getKey());
						entry.getValue().recycle();
						System.gc();
//						Bitmap bitmap = (Bitmap)entry.getValue();// 这里最好加上这一句
//						bitmap = null;
					} catch (Exception e) {
						e.printStackTrace();
					}

					 
					Log.d(TAG, "=============recycle bitmap=======");
				}

				size -= getSizeInBytes(entry.getValue());
				iter.remove();

				Log.d(TAG, "获得整个程序的内存总数totalMemory ===== "
						+ Runtime.getRuntime().totalMemory());
				Log.d(TAG, "可用内存总数freeMemory  ===== "
						+ Runtime.getRuntime().freeMemory());
				Log.d(TAG, "最大内存量maxMemory ===== "
						+ Runtime.getRuntime().maxMemory());

				if (size <= limit)
					break;
			}
			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}

	public void clear() {
		cache.clear();
	}

	/**
	 * 图片占用的内存
	 * 
	 * @param bitmap
	 * @return
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
