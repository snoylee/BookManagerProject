package com.dbs.book.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dbs.book.R;

public class AssociateListAdapter extends BaseAdapter {

	List<String> data = new ArrayList<String>();
	LayoutInflater mInflater = null;
	Context mContext;
	
	public AssociateListAdapter(Context context, List<String> data){
		this.data = data;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return (data == null) ? 0 : (data.size() > 10) ? 10: data.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.search_list_item	, null);
		TextView text = (TextView) view.findViewById(R.id.search_list_text);
		text.setText(data.get(position));
		return view;
	}

}
