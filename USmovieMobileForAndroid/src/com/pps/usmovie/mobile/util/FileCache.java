package com.pps.usmovie.mobile.util;

import java.io.File;

import com.pps.usmovie.mobile.common.Constants;

import android.content.Context;

/**
 * 文件缓存本地
 * @author zhangxiaole
 *
 */
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
            // 如果有SD卡则在SD卡中建一个LazyList的目录存放缓存的图片
            // 没有SD卡就放在系统的缓存目录中
    	cacheDir= new File(Constants.PATH_SDCARD_IMAGES);
           /* if (android.os.Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED))
                    cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
                                    ".USdrama/images");
            else
                    cacheDir = context.getCacheDir();*/
            if (!cacheDir.exists())
                    cacheDir.mkdirs();
    }

    public File getFile(String url)
    {
        // 将 url 的 hashcode 作为缓存的文件名
        String filename = String.valueOf(url.hashCode());
        // Another possible solution
        // String filename = URLEncoder.encode(url);
        File file = new File(cacheDir, filename);
        return file;
    }

    public void clear() {
            File[] files = cacheDir.listFiles();
            if (files == null)
                    return;
            for (File f : files)
                    f.delete();
    }

    public void saveImg(){
    	
    }
}