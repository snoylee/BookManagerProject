package com.dbs.book.http;

import android.content.Context;

import com.dbs.book.utils.CommonUtils;
import com.dbs.book.utils.ImageViewLoadUtil;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.cache.BitmapImageCache;
import com.duowan.mobile.netroid.cache.DiskCache;
import com.duowan.mobile.netroid.toolbox.ImageLoader;

/**
 * Manager for the queue
 *
 * @author Trey Robinson
 */
public class RequestManager {

    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 50; //图片缓存大小
    private static int MEMORY_CACHE_SIZE = 1024 * 1024 * 5; //内存缓存大小

    public RequestManager() {
        // no instances
    }

    /**
     * 获取图片的请求队列
     * @param pContext
     * @return
     */
    public static RequestQueue getImageRequestQueue(Context pContext) {
        return Netroid.newRequestQueue(pContext, new DiskCache(ImageViewLoadUtil.getDiskCacheDir(pContext), DISK_IMAGECACHE_SIZE));
    }

    /**
     * 获取图片Loader
     * @param pContext
     * @return
     */
    public static ImageLoader getSelfImageLoader(Context pContext, RequestQueue pRequestQueue){
        return new SelfImageLoader(pRequestQueue, new BitmapImageCache(MEMORY_CACHE_SIZE), pContext.getResources(), pContext.getAssets());
    }

    /**
     * 获取网络数据的请求队列
     * @param pContext
     * @return
     */
    public static RequestQueue getHttpRequestQueue(Context pContext) {
        return Netroid.newRequestQueue(pContext, new DiskCache(CommonUtils.getFileCacheDir(pContext), DISK_IMAGECACHE_SIZE));
    }
}
