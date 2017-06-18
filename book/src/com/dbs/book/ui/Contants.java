package com.dbs.book.ui;

import java.util.ArrayList;
import java.util.List;

import com.dbs.book.R;



public class Contants {

	public static final int[] COLORS = {R.color.main_color, R.color.blue_color, R.color.orange_color, R.color.purple_color, 
		R.color.red_color, R.color.yellow_color, R.color.blue_light_color, R.color.brown_color, R.color.green_light_color,R.color.main_color, R.color.blue_color};
	public static final String[] BOOK_NAMES ={"语文","数学","物理","化学","英语","美术","体育","心理学","德语","学术"};
	public static final String[] BOOK_NAMES_one ={"美术","体育","心理学","德语","学术","语文","数学","物理","化学","英语"};
	public static final int[] SORT_ICONS = {R.drawable.second_ico, R.drawable.second_ico_01, R.drawable.second_ico_02,  R.drawable.second_ico_03,
		 R.drawable.second_ico_04,  R.drawable.second_ico_05,  R.drawable.second_ico_06,  R.drawable.second_ico_07,  R.drawable.second_ico_08};
	
	
	public static final String[] GRADS = {"一年级","二年级","三年级","四年级","五年级","六年级","七年级","八年级","九年级","高一","高二","高三"};
	public static final Integer[] CLASSES = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	
	
	public static <T> ArrayList<T> arrayToList(T[] arr){
		ArrayList<T> list = new ArrayList<T>();
		for(int i = 0; i < arr.length; i++){
			list.add(arr[i]);
		}
		return list;
	}
}
