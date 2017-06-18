package com.dbs.book.ui.adapter;

import com.dbs.book.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class VedioListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	
	public VedioListAdapter(Context context){
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return 15;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.shelf_vedio_list_item, null);
		return view;
	}

}
