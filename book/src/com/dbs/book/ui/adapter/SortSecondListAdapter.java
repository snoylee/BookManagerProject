package com.dbs.book.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.book.R;
import com.dbs.book.ui.Contants;


public class SortSecondListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	ViewHolder holder = null;
	
	public SortSecondListAdapter(Context context){
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return 10;
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
		String color = null;
		if(position >= Contants.BOOK_NAMES_one.length){
			color = Contants.BOOK_NAMES_one[0];
		}else{
			color = Contants.BOOK_NAMES_one[position];
		}
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.sort_list_item_layout, null);
			convertView.setVisibility(View.GONE);
			TextView bookName = (TextView) convertView.findViewById(R.id.sort_list_item_first_book_name);
			ImageView selectBt = (ImageView) convertView.findViewById(R.id.sort_list_itme_first_chick_button);
			RelativeLayout selectRelatlay = (RelativeLayout) convertView.findViewById(R.id.fortlist_list_item_liner);
			
			holder = new ViewHolder();
			holder.bookName =bookName;
			holder.selectBt = selectBt;
			holder.selectRelatlay = selectRelatlay;
			holder.bookName.setText(color);
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
			holder.bookName.setText(color);
		}
		convertView.setVisibility(View.VISIBLE);
		return convertView;
	}
	class ViewHolder{
		public TextView bookName;
		public ImageView selectBt;
		public RelativeLayout selectRelatlay;
	
	}
	
}
