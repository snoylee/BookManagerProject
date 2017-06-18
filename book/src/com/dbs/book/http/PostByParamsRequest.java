package com.dbs.book.http;

import com.duowan.mobile.netroid.AuthFailureError;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.request.StringRequest;

import java.util.Map;

/**
 * 作者：luoxing
 * 主要功能：
 * 创建日期：2014/11/4 18:00
 * 修改日期：2014/11/4 18:00
 */
public class PostByParamsRequest extends StringRequest {

    private Map<String, String> mParams;

    // 传入Post参数的Map集合
    public PostByParamsRequest(String url, Map<String, String> params, Listener<String> listener) {
        super(Method.POST, url, listener);
        mParams = params;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

}
