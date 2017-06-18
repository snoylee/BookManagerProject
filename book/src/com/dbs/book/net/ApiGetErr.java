package com.dbs.book.net;

import android.content.Context;

/**
 * @ClassName  �? ApiReturnData 
 * @Description: 处理特殊接口返回值处�?
 * @author     �? jiaxue 
 * @date       �? 2015-3-19 下午4:32:45 

 */

public class ApiGetErr {
	
	/**
	 * 返回 更新购物车错误代码�?
	 * @param obj
	 * @param context
	 * @return
	 */
	public static String UpdateCartListErr(Object obj,Context context) {
		
		try {
//			Err err = new Gson().fromJson(obj.toString(), Err.class);
//			return getCodeStr(err.getCode(),context);
			return "chucuol";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "操作失败";
	}
	
	
	private static String getCodeStr(int code,Context context) {
		switch(code) {
		case 100:
			return null;
		case 110:
			return null;
		case 101:
			return null;
		case 102:
			return null;
		case 103:
			return null;
		}
		return null;
		
	}

}
