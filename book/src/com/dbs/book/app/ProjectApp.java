package com.dbs.book.app;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.dbs.book.http.AppHttpClient;
import com.dbs.book.http.RequestManager;
import com.dbs.book.log.Log;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.toolbox.FileDownloader;
import com.duowan.mobile.netroid.toolbox.ImageLoader;

/**
 * 
 */

public class ProjectApp extends BaseApplication {

	public static ImageLoader mImageLoader;
	public static AppHttpClient mJsonClient;
	public static FileDownloader mFileDownloader;
    private static ProjectApp projectApp;
    private boolean userLogin = false;
    private static RequestQueue mImageRequestQueue;
    private static RequestQueue mJsonRequestQuene;
    private Header[] headers = null;

    @Override
    public void onCreate() {
        projectApp = this;
        mImageRequestQueue = RequestManager.getImageRequestQueue(this);
        mJsonRequestQuene = RequestManager.getHttpRequestQueue(this);
        mImageLoader = RequestManager.getSelfImageLoader(this, mImageRequestQueue);
        mJsonClient = new AppHttpClient(this, mJsonRequestQuene);
        mFileDownloader = new FileDownloader(mJsonRequestQuene, 1);
    }

    @Override
    public void onLowMemory() {
    }

    public boolean getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(boolean userLogin) {
        this.userLogin = userLogin;
    }

    public static ProjectApp getApp(){
        return projectApp;
    }

    public boolean isUserLogin() {
        return userLogin;
    }

    public void setHander(String name, String value){
        Log.d("cookie:"+value);
        if(headers == null){
            headers = new Header[1];
        }
        Header header = new BasicHeader(name, value);
        headers[0] = header;
    }

    public Header[] getHeader(){
        return headers;
    }
}
