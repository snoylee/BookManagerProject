package com.dbs.book.ui.adapter;

import java.util.List;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.TextView;


import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.ui.model.BookInfo;
import com.duowan.mobile.netroid.image.NetworkImageView;

public class BookListAdapter extends BaseAdapter{

	private List<BookInfo> data;
	private Context mContext;
	NetworkImageView cover ;
	TextView name ;
	TextView type ;
	TextView summary ;
	TextView isLoacl  ;
	
	public BookListAdapter(Context context,List<BookInfo> data){
		this.mContext = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		if(v == null)
			 v = View.inflate(mContext,R.layout.shelf_book_list_item, null);
			 cover = (NetworkImageView) v.findViewById(R.id.shelf_list_face_iv);
			 name = (TextView) v.findViewById(R.id.shelf_list_name_tv);
			 type = (TextView) v.findViewById(R.id.shelf_list_sort_tv);
			 summary = (TextView) v.findViewById(R.id.shelf_list_intro_tv);
			 isLoacl = (TextView) v.findViewById(R.id.shelf_list_local_tv);
		
			 BookInfo book = (BookInfo) getItem(position);
			 Log.d("bookImage",book.getName()+"url =  "+book.getCoverUrl());
		
			 cover.setImageUrl(book.getCoverUrl(), ProjectApp.mImageLoader);
			 name.setText(book.getName());
			 type.setText(book.getType());
			 summary.setText(book.getSummary());
		return v;
	}
}
