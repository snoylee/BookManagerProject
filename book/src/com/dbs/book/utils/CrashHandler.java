package com.dbs.book.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.content.res.Resources;

import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.log.Log;

/**
 * Created by Administrator on 2014/3/29.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private ProjectApp projectApp;
    private Resources resources;

    private boolean hasException = false;

    public CrashHandler(){
        projectApp = ProjectApp.getApp();
        resources = projectApp.getResources();
        Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static void init(){
        new CrashHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try{
            Log.setEnabled(true);
            Log.setTag(resources.getString(R.string.app_name));
            if(CommonUtils.getSdCacheRootPath(projectApp) != null) {
//                Log.setPath(CommonUtils.getSdCacheRootPath(projectApp) + resources.getString(R.string.log_dir), "Exception_log", "txt");
//                Log.setPolicy(resources.getInteger(R.integer.log_policy));
            }
            Log.e("App VersionName: " + PhoneUtil.getVersionName() + "[" + PhoneUtil.getVersionCode() + "]");
            Log.e(getErrorInfo(ex));
        }catch (Exception e){
            e.printStackTrace();
            Log.e(e.getMessage());
        }
        //杀掉出异常的应用
        android.os.Process.killProcess(android.os.Process.myPid());
        //重启应用
        if(!hasException) {
//            Intent intent = new Intent(projectApp, MainAct.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            projectApp.startActivity(intent);
//            hasException = true;
        }
    }

    /**
     　　* 获取错误的信息
     　　* @param arg1
     　　* @return
     　　*/
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error= writer.toString();
        return error;
    }
}
