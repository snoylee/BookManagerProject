package com.dbs.book.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import com.dbs.book.app.ProjectApp;
import com.dbs.book.ui.activity.BaseActivity;


/**
 * 跟手机有关的工具类
 * 
 * @author carlos carlosk@163.com
 * @version 创建时间：2013-3-10 上午11:30:38 类说明
 */

public class PhoneUtil {

	public static int getSdkVersion() {
		return Build.VERSION.SDK_INT;
	}
	/**
	 * 根据路径安装apk
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2012-9-19 上午10:38:25
	 * @param
	 */
	public static void installAPK(String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + filePath),
				"application/vnd.android.package-archive");
		ProjectApp.getApp().startActivity(i);
	}
	/**
	 * 获取版本号
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2013-3-17 下午2:56:17
	 * @return
	 */
	public static String getVersionCode() {

		String versionCode = "";
		try {
			versionCode = ""+ProjectApp.getApp().getPackageManager().getPackageInfo(ProjectApp.getApp().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	  /**
     * 获取屏幕的宽（像素值）
     * @param context
     * @return
     */
    public static int getWidth(Context context){
        return  ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕的高（像素值）
     * @param context
     * @return
     */
    public static int getHeight(Context context){
        return ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
    }
    

	/**
	 * 获取版本名称
	 * 
	 * @author carlos carlosk@163.com
	 * @version 创建时间：2013-3-17 下午2:58:57
	 * @return
	 */
	public static String getVersionName() {
		String versionName = "";
		try {
			versionName = ProjectApp.getApp().getPackageManager().getPackageInfo(ProjectApp.getApp().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

    /**
     * 判断SD卡是否在手机上
     * @return
     */
    public static boolean getSDState(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    /**
     * 获取SD卡路径
     * @return
     */
    public static String getSDpath(){
        if(getSDState()){
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    /**
     * 是否有可用的网络
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)ProjectApp.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }
        return networkInfo.isAvailable();
    }


    /**
     * 获取屏幕的像素
     * @param context
     * @return
     */
    public static String getDisplayMetrics(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels + "*" + dm.heightPixels;
    }

    /**
     * 获取屏幕的宽（像素值）
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高（像素值）
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels ;
    }

    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     * @param context
     * @return
     */
    public static float getDisplayDensity(Context context){
       return context.getResources().getDisplayMetrics().density;
    }


    /**
     * 屏幕密度（每寸像素：120/160/240/320）
     * @param context
     * @return
     */
    public static float getDisplayDensityDpi(Context context){
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取手机通知栏高度
     * @param context
     * @return
     */
    public static int getNotTitleHeight(Context context){
        Rect outRect = new Rect();
        View view = ((BaseActivity)context).getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }


    /**
     * 获取Activity的高度
     * @param context
     * @return
     */
    public static int getActivityHeight(Context context){
        View view = ((BaseActivity)context).getWindow().getDecorView();
        return view.getHeight();
    }
    /**
     * 获取手机状态栏高度
     * @param context
     * @return
     */
    public static  int getStatusBarHeight(Context context) {
  	  int result = 0;
  	  int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
  	  if (resourceId > 0) {
  	      result = context.getResources().getDimensionPixelSize(resourceId);
  	  }
  	  return result;
  	}
    
    
}
