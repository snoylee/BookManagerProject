package com.dbs.book.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dbs.book.R;

public class CommonListAdapter extends BaseAdapter {

	private List<String> data;
	private LayoutInflater mInflater;
	
	public CommonListAdapter(Context mContext, List<String> data){
		this.data = data;
		this.mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return (data == null) ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return (data == null) ? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) mInflater.inflate(R.layout.common_list_item, null);
		v.setText(data.get(position));
		return v;
	}

}
