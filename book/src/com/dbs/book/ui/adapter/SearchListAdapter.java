package com.dbs.book.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.ui.model.BookInfo;
import com.duowan.mobile.netroid.image.NetworkImageView;

public class SearchListAdapter extends BaseAdapter{

	private List<BookInfo> data = new ArrayList<BookInfo>();
	private LayoutInflater inflater;
	
	public SearchListAdapter(){}
	
	public SearchListAdapter(Context context, List<BookInfo> data) {
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
			v = inflater.inflate(R.layout.shelf_book_list_item, null);
			holder.cover = (NetworkImageView) v.findViewById(R.id.shelf_list_face_iv);
			holder.title = (TextView) v.findViewById(R.id.shelf_list_name_tv);
			holder.type = (TextView) v.findViewById(R.id.shelf_list_sort_tv);
			holder.summary = (TextView) v.findViewById(R.id.shelf_list_intro_tv);
			v.findViewById(R.id.shelf_list_local_tv).setVisibility(View.GONE);
			v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
			BookInfo info = data.get(position);
		    holder.cover.setImageUrl(info.getCoverUrl(), ProjectApp.mImageLoader);
		    holder.title.setText(info.getName());
		    holder.type.setText("分类：" + info.getType());
		    holder.summary.setText(info.getSummary());
		
		
		return v;
	}
	
	class ViewHolder{
		private NetworkImageView cover;
		private TextView title;
		private TextView type;
		private TextView summary;
	}
	
	
	
	
	
	
	
	
	
	
	
}
