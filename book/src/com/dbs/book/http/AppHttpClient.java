package com.dbs.book.http;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.log.Log;
import com.dbs.book.utils.StringUtil;
import com.duowan.mobile.netroid.DefaultRetryPolicy;
import com.duowan.mobile.netroid.Request;
import com.duowan.mobile.netroid.Request.Method;
import com.duowan.mobile.netroid.RequestQueue;
import com.duowan.mobile.netroid.request.JsonObjectRequest;

/**
 * 作者：luoxing
 * 主要功能：json httpClient
 * 创建日期：2014/11/4 13:58
 * 修改日期：2014/11/4 13:58
 */
public class AppHttpClient {

    public static final int REQUEST_TIME_OUT = 10000;
    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final int CACHE_TIME = 5;

    public static String sRootUrl = ProjectApp.getApp().getResources().getString(R.string.root_url);

    private Context mContext;
    private RequestQueue mJsonRequestQuene;


    public AppHttpClient(Context pContext, RequestQueue pRequestQueue){
        this.mContext = pContext;
        mJsonRequestQuene =  pRequestQueue;
    }

    public void post(String pUrl, JsonBaseListener pListener) {
        post(pUrl, null, pListener);
    }

    public void post(String pUrl, String pTag, JsonBaseListener pListener) {
        post(pUrl, pTag, null, pListener);
    }

    public void post(String pUrl,  String pTag, HashMap<String, String> pParams, JsonBaseListener pListener) {
        post(pUrl, pTag, pParams, null, pListener);
    }

    public void post(String pUrl, String pTag, HashMap<String, String> pParams, HashMap<String, String> pHeaders, JsonBaseListener pListener){
        if(pParams == null){
            pParams = new HashMap<String, String>();
        }
        JsonObjectRequest _Requset = new JsonObjectRequest(Method.POST, getRootUrl() + pUrl, new JSONObject(pParams), pListener);
        setHeads(_Requset, pHeaders);

        _Requset.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME_OUT, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(!StringUtil.isEmpty(pTag)){
            _Requset.setTag(pTag);
        }
        Log.d(getRootUrl() + pUrl);
        Log.d(pParams.toString());

        mJsonRequestQuene.add(_Requset);
    }



    public void getAbsolutePath( String pUrl, JsonBaseListener pListener) {
        baseGet(pUrl, null, null, null, pListener);
    }

    public void get( String pUrl, JsonBaseListener pListener) {
        get(pUrl, null, pListener);
    }

    public void get(String pUrl, String pTag, JsonBaseListener pListener) {
         get(pUrl, pTag, null, pListener);
    }

    public void get(String pUrl, String pTag, HashMap<String, String> pParams, JsonBaseListener pListener) {
         get(pUrl, pTag, pParams, null, pListener);
    }

    public void get(String pUrl, String pTag, HashMap<String, String> pParams,HashMap<String, String> pHeaders, JsonBaseListener pListener) {
        pUrl = getRootUrl() + pUrl;
        baseGet(pUrl ,pTag, pParams, pHeaders, pListener);
    }

    private void baseGet(String pUrl, String pTag, HashMap<String, String> pParams,HashMap<String, String> pHeaders, JsonBaseListener pListener) {
        if(pParams == null){
            pParams = new HashMap<String, String>();
        }
        pParams.put("orderFrom","5");//订单来源于Android
        JsonObjectRequest _Requset = new JsonObjectRequest(Method.GET, getUrlWithQueryString(pUrl, pParams), null, pListener);
        setHeads(_Requset, pHeaders);
        _Requset.setCacheExpireTime(TimeUnit.SECONDS , CACHE_TIME);

        _Requset.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME_OUT, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(!StringUtil.isEmpty(pTag)){
            _Requset.setTag(pTag);
        }
        Log.d(getUrlWithQueryString(pUrl, pParams));
        mJsonRequestQuene.add(_Requset);
    }

    public static String getRootUrl(){
        return sRootUrl;
    }

    private void setHeads(Request pRequest, HashMap<String,String> pHeads){
        if(pHeads != null){
            for(String key : pHeads.keySet()){
                pRequest.addHeader(key, pHeads.get(key));
            }
        }
        Header[] _headers = ProjectApp.getApp().getHeader();
        if(_headers != null) {
            for (int i = 0; i < _headers.length; i++){
                pRequest.addHeader(_headers[i].getName(), _headers[i].getValue());
            }
        }
    }

    private String getUrlWithQueryString(String url, HashMap<String, String> params) {
        String _utf = "utf-8";
        try {
            if (params != null) {
                // Construct the query string and trim it, in case it
                // includes any excessive white spaces.
                String paramString = "";
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
                    paramString += URLEncoder.encode(entry.getKey(), _utf) + "=" + URLEncoder.encode(entry.getValue(), _utf);
                    paramString += "&";
                }
                paramString = paramString.substring(0, paramString.length() - 1);
                // Only add the query string if it isn't empty and it
                // isn't equal to '?'.
                if (!paramString.equals("") && !paramString.equals("?")) {
                    url += url.contains("?") ? "&" : "?";
                    url += paramString;
                }
            }
        }catch (Exception e){

        }
        return url;
    }



}
