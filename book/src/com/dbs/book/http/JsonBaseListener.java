package com.dbs.book.http;

import org.json.JSONObject;

import com.dbs.book.log.Log;
import com.dbs.book.utils.CommonUtils;
import com.dbs.book.utils.PhoneUtil;
import com.dbs.book.utils.StringUtil;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;

/**
 * 作者：luoxing
 * 主要功能：
 * 创建日期：2014/11/4 12:28
 * 修改日期：2014/11/4 12:28
 */
public abstract class JsonBaseListener extends Listener<JSONObject>{

    private static final String RESPONSE_ERROR = "HasError";
    private static final String RESPONSE_MSG = "Msg";
    private static final String RESPONSE_STATUS = "Status";
    public static final String RESPONSE_RESPONSE = "Response";

    public JsonBaseListener() {
        super();
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        Log.d(jsonObject.toString());
        try {
            if(onSuccessToast(jsonObject)) return ;
            String _response = "";
            if(!jsonObject.isNull(JsonBaseListener.RESPONSE_RESPONSE)){
                _response = jsonObject.getString(JsonBaseListener.RESPONSE_RESPONSE);
            }
            parseResponse(_response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  abstract void parseResponse(String pResponse) throws Exception;

    @Override
    public void onError(NetroidError error) {
        onFailure(error);
    }

    protected boolean interfaceError(JSONObject response){
        try {
            if (response.getBoolean(RESPONSE_ERROR)) {
                if (Log.isEnabled()) {
                    CommonUtils.showToast("接口错误" + response.getString(RESPONSE_MSG));
                } else {
                    CommonUtils.showToast(response.getString(RESPONSE_MSG));
                }
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }


    protected boolean loginTimeOut(JSONObject response){
        try {
            if(response.getInt(RESPONSE_STATUS) == 2){
//                Intent intent = new Intent(ProjectApp.getApp(), LoginActivity.class);
//                ProjectApp.getApp().startActivity(intent);
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }

    protected boolean interfaceStatesException(JSONObject response){
        try {
            Integer Status = response.getInt(RESPONSE_STATUS);
            if (Status != 0 && Status != 1) {
                if (Log.isEnabled()) {
                    CommonUtils.showToast("接口异常" + response.getString(RESPONSE_MSG));
                } else {
                    CommonUtils.showToast(response.getString(RESPONSE_MSG));
                }
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }

    protected boolean isNullResponse(JSONObject pResponse){
        try {
            String _response = pResponse.getString(RESPONSE_RESPONSE);
            if(StringUtil.isEmpty(_response)){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }


    public boolean onSuccessToast(JSONObject pResponse) {
        if (loginTimeOut(pResponse)) return true;
        if (interfaceError(pResponse)) return true;
        if (interfaceStatesException(pResponse)) return true;
        return false;
    }


    private void onFailure(NetroidError error) {
        try {
            if (!PhoneUtil.isNetworkAvailable()) {
                CommonUtils.showToast("网络不给力");
                return;
            }
            if (Log.isEnabled()) {
                CommonUtils.showToast("网络异常" + error.getMessage());
            } else {
                CommonUtils.showToast("网络异常");
            }
        }catch (Exception e){

        }
    }

}
