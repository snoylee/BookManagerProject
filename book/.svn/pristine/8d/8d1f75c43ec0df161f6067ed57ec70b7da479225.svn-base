package com.dbs.book.utils;

import java.io.File;

import android.content.Context;

import com.dbs.book.log.Log;

/**
 * Created by lenovo on 2014/4/3.
 */
public class ImageViewLoadUtil {

    public static File getDiskCacheDir(Context context) {
        return new File(getImageCacheDir(context));
    }

    public static String getImageCacheDir(Context context) {
        String image_cache_dir = "imagescache";
        String sd_path = CommonUtils.getSdCacheRootPath(context);
        if (sd_path == null) {
            Log.d("sdPath is null");
            sd_path = context.getCacheDir().getPath();
        }
        return sd_path + image_cache_dir;
    }

}
