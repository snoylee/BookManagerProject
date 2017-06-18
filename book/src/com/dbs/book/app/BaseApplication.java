package com.dbs.book.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class BaseApplication extends Application{

	private static Handler sHandler;
	private static Application sApplication;
	
	/**
	 * @return 整个APP可以使用的Handler（为主线程）
	 */
	public static Handler getHandler() {
		checkHandler();
		return sHandler;
	}
	
	/**
	 * @return 整个APP可以使用的Context
	 */
	public static Application getAppContext() {
		checkNull(sApplication);
		return sApplication;
	}
	
	private static void checkNull(Object obj) {
		if (null == obj) {
			throw new RuntimeException("check whether the app has a Application "
					+ "class extends BaseApplication ? or forget to " 
					+ "invoke super class's constructor first!");
		}
	}
	
	private static void checkHandler() {
		if (null == sHandler) {
			sHandler = new Handler(Looper.getMainLooper());
		}
	}
	
	public BaseApplication() {
		sApplication = this;
	}
	
	/**
	 * 强杀本进程
	 */
	public static void killProcess() {
		Process.killProcess(Process.myPid());
	}
}
