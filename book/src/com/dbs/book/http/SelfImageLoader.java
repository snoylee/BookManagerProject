package com.dbs.book.http;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.duowan.mobile.netroid.NetworkResponse;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.request.ImageRequest;
import com.duowan.mobile.netroid.toolbox.ImageLoader;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 作者：luoxing
 * 主要功能：处理整个项目的图片加载类
 * 创建日期：2014/11/3 10:38
 * 修改日期：2014/11/3 10:38
 */
public class SelfImageLoader extends ImageLoader {

    public static final int CASET_DAY_TIME = 1;

    public static final String RES_ASSETS = "assets://";
    public static final String RES_SDCARD = "sdcard://";
    public static final String RES_DRAWABLE = "drawable://";
    public static final String RES_HTTP = "http://";

    private AssetManager mAssetManager;
    private Resources mResources;

    public SelfImageLoader(RequestQueue pQueue, ImageCache pCache, Resources pResources, AssetManager pAssetManager) {
        super(pQueue, pCache);
        mResources = pResources;
        mAssetManager = pAssetManager;
    }

    @Override
    public ImageRequest buildRequest(String pRequestUrl, int pMaxWidth, int pMaxHeight) {
        ImageRequest _request;
        if (pRequestUrl.startsWith(RES_ASSETS)) {
            _request = new ImageRequest(pRequestUrl.substring(RES_ASSETS.length()), pMaxWidth, pMaxHeight) {
                @Override
                public NetworkResponse perform() {
                    try {
                        return new NetworkResponse(toBytes(mAssetManager.open(getUrl())), HTTP.UTF_8);
                    } catch (IOException e) {
                        return new NetworkResponse(new byte[1], HTTP.UTF_8);
                    }
                }
            };
        }
        else if (pRequestUrl.startsWith(RES_SDCARD)) {
            _request = new ImageRequest(pRequestUrl.substring(RES_SDCARD.length()), pMaxWidth, pMaxHeight) {
                @Override
                public NetworkResponse perform() {
                    try {
                        return new NetworkResponse(toBytes(new FileInputStream(getUrl())), HTTP.UTF_8);
                    } catch (IOException e) {
                        return new NetworkResponse(new byte[1], HTTP.UTF_8);
                    }
                }
            };
        }
        else if (pRequestUrl.startsWith(RES_DRAWABLE)) {
            _request = new ImageRequest(pRequestUrl.substring(RES_DRAWABLE.length()), pMaxWidth, pMaxHeight) {
                @Override
                public NetworkResponse perform() {
                    try {
                        int resId = Integer.parseInt(getUrl());
                        Bitmap bitmap = BitmapFactory.decodeResource(mResources, resId);
                        return new NetworkResponse(bitmap2Bytes(bitmap), HTTP.UTF_8);
                    } catch (Exception e) {
                        return new NetworkResponse(new byte[1], HTTP.UTF_8);
                    }
                }
            };
        }
        else if (pRequestUrl.startsWith(RES_HTTP)) {
            _request = new ImageRequest(pRequestUrl, pMaxWidth, pMaxHeight);
        }
        else {
            return null;
        }

        makeRequest(_request);
        return _request;
    }

    public void makeRequest(ImageRequest pRequest) {
        pRequest.setCacheExpireTime(TimeUnit.DAYS, CASET_DAY_TIME);
    }

    public static byte[] toBytes(InputStream pIs) throws IOException {
        ByteArrayOutputStream _buffer = new ByteArrayOutputStream();

        int _read;
        byte[] data = new byte[16384];
        while ((_read = pIs.read(data, 0, data.length)) != -1) {
            _buffer.write(data, 0, _read);
        }
        _buffer.flush();

        return _buffer.toByteArray();
    }

    public static byte[] bitmap2Bytes(Bitmap pbm) {
        ByteArrayOutputStream _baos = new ByteArrayOutputStream();
        pbm.compress(Bitmap.CompressFormat.PNG, 100, _baos);
        return _baos.toByteArray();
    }
}
