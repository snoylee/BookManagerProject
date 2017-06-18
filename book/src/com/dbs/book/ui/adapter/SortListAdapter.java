package com.dbs.book.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.book.R;
import com.dbs.book.ui.Contants;
import com.dbs.book.ui.model.SortInfo;


public class SortListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private List<SortInfo> data;
	private Resources res;
	private Drawable icon = null;
	private int page = 1;
	
	
	public SortListAdapter(Context context, List<SortInfo> data, Drawable icon, int page){
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.res = context.getResources();
		this.data = data;
		this.icon = icon;
		this.page = page;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(page == 1){
			if(position >= Contants.SORT_ICONS.length){
				icon = res.getDrawable(Contants.SORT_ICONS[0]);
			}else{
				icon = res.getDrawable(Contants.SORT_ICONS[position]);
			}
		}
		View view = null;
		ViewHolder viewHolder = null;
		if(convertView == null){
			view = mInflater.inflate(R.layout.sort_list_item_layout, null);
			viewHolder = new ViewHolder();
			
			TextView bookName = (TextView) view.findViewById(R.id.sort_list_item_first_book_name);
			ImageView selectBt = (ImageView) view.findViewById(R.id.sort_list_item_first_icon);
//			RelativeLayout selectRelatlay = (RelativeLayout) convertView.findViewById(R.id.fortlist_list_item_liner);
			viewHolder.bookName =bookName;
			viewHolder.selectBt = selectBt;
//			holder.selectRelatlay = selectRelatlay;
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		SortInfo info = data.get(position);
		if(info != null){
			viewHolder.bookName.setText(info.getName());
			viewHolder.selectBt.setBackground(icon);
		}
		return view;
	}
	
	class ViewHolder{
		public TextView bookName;
		public ImageView selectBt;
//		public RelativeLayout selectRelatlay;
	
	}
	
}
