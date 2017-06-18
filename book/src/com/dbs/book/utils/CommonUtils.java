package com.dbs.book.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.log.Log;


/**
 * @author allen
 * @ClassName: CommonUtils
 * @Description: 公共的工具类
 * @date 2012-11-1 下午3:15:52
 */
public class CommonUtils {
	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {   
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }                  

    /**
     * 显示Toast 消息提示
     *
     * @param msg
     */
    public static void showToast(String msg) {
        try {
            CommonUtils.showToast(msg, Toast.LENGTH_SHORT);
        }catch (Exception e){

        }
    }

    /**
     * 显示Toast 消息提示
     *
     * @param msgid
     */
    public static void showToast(int msgid) {
        try {
        	LayoutInflater inflater = (LayoutInflater) ProjectApp.getApp().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        	TextView layout = (TextView) inflater.inflate(R.layout.toast_view, null);
        	layout.setText(msgid);
        	Toast toast = new Toast(ProjectApp.getApp());  
        	toast.setDuration(Toast.LENGTH_SHORT);  
        	toast.setView(layout);  
        	toast.show(); 
//            Toast.makeText(ProjectApp.getApp(), msgid, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }

    /**
     * @param msg      提示消息
     * @param duration 提示的时间
     * @Title: showToast
     * @Description: 显示Toast提示
     * @returnType void
     */
    public static void showToast(String msg, int duration) {
        try {
        	LayoutInflater inflater = (LayoutInflater) ProjectApp.getApp().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        	TextView layout = (TextView) inflater.inflate(R.layout.toast_view, null);
        	layout.setText(msg);
        	Toast toast = new Toast(ProjectApp.getApp());  
        	toast.setDuration(duration);  
        	toast.setView(layout);  
        	toast.show(); 
//            Toast.makeText(ProjectApp.getApp(), msg, duration).show();
        }catch (Exception e){

        }
    }


    /**
     * 输入框错误提醒
     * @param editText
     * @param errorString
     */
    public static void showEditError(TextView editText, String errorString){
        editText.requestFocus();
        editText.setError(errorString);
    }



   
    /**
     * 全屏popWindow，类似Dialog
     *
     * @param mContext
     * @param mView           要显示的View
     * @param mainView        要点击的View
     * @param popWindowHeight popwindow的高度
     * @param offsetY  the popup's y location offset
     * @return
     */
    public static PopupWindow showCustomViewPopupWindow(Context mContext, View mView, View mainView, int popWindowHeight, int offsetY) {
        PopupWindow popupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.FILL_PARENT,
                popWindowHeight);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(mainView, Gravity.TOP, 0, offsetY);
        popupWindow.update();
        return popupWindow;
    }
    
    public static boolean isEditTextEmpty(EditText et) {
        boolean isEmpty = false;
        if (et.getText().toString().equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * 隐藏键盘
     *
     * @author allen
     * @version 2013-7-23 下午9:48:05
     */
    public static void hideKeyBoard(Context mcontext) {

        InputMethodManager imm = (InputMethodManager) mcontext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())  //一直是true
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     *
     * @param context
     * @param fileName The name of the asset to open. This name can be hierarchical.
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName) {
        if (context == null || StringUtil.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件的缓存目录
     * @param pContext
     * @return
     */
    public static File getFileCacheDir(Context pContext) {
        String _FileCacheDir = pContext.getResources().getString(R.string.file_cache_dir);
        String _FilePath = getSdCacheRootPath(pContext) + _FileCacheDir;
        return new File(_FilePath);
    }

    /**
     * 获取应用的缓存根目录
     *
     * @param context
     * @return
     */
    public static String getSdCacheRootPath(Context context) {
        String sdPath = PhoneUtil.getSDpath();
        if (sdPath == null) {
            Log.d("sdPath is null");
            sdPath = context.getCacheDir().getPath();
            return sdPath;
        }
        return String.format(context.getResources().getString(R.string.cache_root_home), sdPath);
    }


    public static SpannableString getPriceSpannableString(String price, float textSize, int colorId) {
        if (price == null) return null;
        int priceLength = price.length();
        int _colorId = ProjectApp.getApp().getResources().getColor(colorId);
        SpannableString spannableString = new SpannableString(price);
        spannableString.setSpan(new ForegroundColorSpan(_colorId), 0, priceLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (priceLength > 2) {
            spannableString.setSpan(new AbsoluteSizeSpan((int) textSize), 1, priceLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    
    public static SpannableString getSpannableString(String titleName, float textsize, int colorresourceId) {
        if (titleName == null) return null;
        titleName = " " + titleName + " ";
        int colorId = ProjectApp.getApp().getResources().getColor(colorresourceId);
        SpannableString spannableString = new SpannableString(titleName);
        spannableString.setSpan(new BackgroundColorSpan(colorId), 0, titleName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, titleName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan((int) textsize), 0, titleName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getSpannableMiddleString(String titleName, float textsize, int colorresourceId) {
        if (titleName == null) return null;
        int colorId = ProjectApp.getApp().getResources().getColor(colorresourceId);
        SpannableString spannableString = new SpannableString(titleName);
        //spannableString.setSpan(new BackgroundColorSpan(colorId),1, titleName.length() -1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(colorId), 1, titleName.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan((int) textsize), 1, titleName.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getSpannableBoldString(String titleName, int colorresourceId, int fromIndex, int toIndex) {
        if (titleName == null) return null;
        int colorId = ProjectApp.getApp().getResources().getColor(colorresourceId);
        SpannableString spannableString = new SpannableString(titleName);
        spannableString.setSpan(new ForegroundColorSpan(colorId), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString getSpannableForeColorString(String titleName, float textsize, int colorresourceId, int fromIndex, int toIndex) {
        if (titleName == null) return null;
        int colorId = ProjectApp.getApp().getResources().getColor(colorresourceId);
        SpannableString spannableString = new SpannableString(titleName);
        spannableString.setSpan(new ForegroundColorSpan(colorId), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan((int) textsize), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString getSpannableSizeString(String titleName, float textsize, int fromIndex, int toIndex) {
        if (titleName == null) return null;
        SpannableString spannableString = new SpannableString(titleName);
        spannableString.setSpan(new AbsoluteSizeSpan((int) textsize), fromIndex, toIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 判断字符串是否数值
     *
     * @param str
     * @return true:是数值 ；false：不是数值
     */
    public static boolean isPhoneNumber(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0,6,7,8])|(14[5,7]))\\d{8}$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }


    /**
     * 匹配邮箱
     *
     * @param str
     * @return
     */
    public static boolean isEmailNumber(String str) {
        Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 匹配身份证号码
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }



    /**
     * 匹配护照
     *
     * @param id
     * @return
     */
    public static boolean isPassportId(String id) {
        Pattern pattern = Pattern.compile("(^1[4-5]{1}[0-9]{7}$)|^[a-zA-Z]{1}[0-9]{8}$");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }

    /**
     * 通行证
     * @param id
     * @return
     */
    public static Boolean IsSafeConduct(String id){
        Pattern pattern = Pattern.compile("^[a-zA-Z]{1}[0-9]{8}$");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }

    /**
     * 回乡证
     * @param id
     * @return
     */
    public static Boolean IsReturnBackConduct(String id){
        Pattern pattern = Pattern.compile("^[A-Za-z]{1}\\d{6}[\\(|（)]\\d{1}[\\)|）]$");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }

    /**
     * 台胞证
     * @param id
     * @return
     */
    public static Boolean IsTaiwanConduct(String id){
        Pattern pattern = Pattern.compile("^[a-zA-Z]{1}[0-9]{9}$");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }
    /**
     * 警官证
     * @param id
     * @return
     */
    public static boolean isPoliceCard(String id){
        Pattern pattern = Pattern.compile("[0-9]\\d{5}(?!\\d)");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }

    /**
     * 士兵证
     * @param id
     * @return
     */
    public static boolean isSoldierCard(String id){
        Pattern pattern = Pattern.compile("[0-9]\\d{6}(?!\\d)");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }


    /**
     * 军官证
     * @param id
     * @return
     */
    public static boolean isOfficerCard(String id){
        Pattern pattern = Pattern.compile("[0-9]\\d{7}(?!\\d)");
        Matcher match = pattern.matcher(id);
        return match.matches();
    }

    /**
     * 解决ScollView中嵌套ListView的情况
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.get
            // Count()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);
    }


    public static Bitmap readLocalIcon(String AbsolutePath,Context context) {
        FileInputStream inStream = null;
        try{
            inStream = context.openFileInput(AbsolutePath);
            ByteArrayOutputStream outStream =new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=-1;
            while((len=inStream.read(buffer))!=-1){
                outStream.write(buffer,0,len);
            }
            outStream.close();
            inStream.close();
            byte[] data=outStream.toByteArray();
            Bitmap bm= BitmapFactory.decodeByteArray(data, 0, data.length);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean isEmail(String email){     
	     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(email);     
	        return m.matches();     
	    }
    
    
    
    public static int parseInt(String string){
    	int i = 0;
    	try {
			i = Integer.parseInt(string);
		} catch (Exception e) {}
    	return i;
    }
    
    public static float parseFloat(String string){
    	float i = 0;
    	try {
			i = Float.parseFloat(string);
		} catch (Exception e) {}
    	return i;
    }


    public static  Drawable getResourceDrawable(int resourceId){
        Drawable drawable = ProjectApp.getApp().getResources().getDrawable(resourceId);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        return drawable;
    }
    
  //国际姓名匹配
    public static boolean isOverseaRightName(String name){
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s/]+$");
        Matcher match = pattern.matcher(name);
        return match.matches();
    }
    
    // dip 转换成  px
    public static int dip2px(Context context, float dipValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
    } 
    
    // px 转换成  dip
    public static int px2dip(Context context, float pxValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(pxValue / scale + 0.5f); 
    } 
    

    /**
     * 友盟添加测试设备的代码
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);
            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }
            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
