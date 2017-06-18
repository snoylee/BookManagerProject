package com.dbs.book.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dbs.book.R;
import com.dbs.book.ui.model.ContentsInfo;

public class ContentsListAdapter extends BaseAdapter {
	Context context;
	List<ContentsInfo> data;
	LayoutInflater inflater;
	
	public ContentsListAdapter(Context context, List<ContentsInfo> data){
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
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
		ViewHolder holder = null;
		if(v == null){
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.search_list_item, null);
			holder.name = (TextView) v.findViewById(R.id.search_list_text);
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		holder.name.setText(data.get(position).getName());
		return v;
	}
	
	class ViewHolder{
		private TextView name;
	}

}
