package com.dbs.book.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.ui.Contants;
import com.dbs.book.ui.activity.BookDetailActivity;
import com.dbs.book.ui.activity.SearchListActivity;
import com.dbs.book.ui.model.BookInfo;
import com.dbs.book.ui.model.IndexInfo;
import com.dbs.book.utils.PhoneUtil;
import com.duowan.mobile.netroid.image.NetworkImageView;

public class MainListAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private Context mContext;
	private List<IndexInfo> data;
	
	public View icon;
	public View line;
	public NetworkImageView iv1;
	public NetworkImageView iv2;
	public NetworkImageView iv3;
	public TextView bookName1;
	public TextView bookName2;
	public TextView bookName3;
	public TextView showMore;
	public TextView title;
	
	List<BookInfo> books;
	//ViewHolder holder = null;
	public MainListAdapter(Context context, List<IndexInfo> data){
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size() == 0 ? 0: data.size();
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
		int color = -1;
		if(position >= Contants.COLORS.length){
			color = Contants.COLORS[0];
		}else{
			color = Contants.COLORS[position];
		}
//		if(convertView == null){
			//holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.main_list_item_layout, null);
			View line = convertView.findViewById(R.id.mainlist_item_topline);
			View icon = convertView.findViewById(R.id.mainlist_item_icon);
			line.setBackgroundColor(mContext.getResources().getColor(color));
			icon.setBackgroundColor(mContext.getResources().getColor(color));
			iv1 = (NetworkImageView) convertView.findViewById(R.id.book_image_1);
			iv2 = (NetworkImageView) convertView.findViewById(R.id.book_image_2);
			iv3 = (NetworkImageView) convertView.findViewById(R.id.book_image_3);
			bookName1 = (TextView) convertView.findViewById(R.id.book_name_1);
			bookName2 = (TextView) convertView.findViewById(R.id.book_name_2);
			bookName3 = (TextView) convertView.findViewById(R.id.book_name_3);
			showMore = (TextView) convertView.findViewById(R.id.mainlist_item_more);
			title = (TextView) convertView.findViewById(R.id.mainlist_item_title);
			line = line;
			icon = icon;
			int height = PhoneUtil.getDisplayWidth(mContext) / 9  * 4;
			iv1.getLayoutParams().height = height;
			iv2.getLayoutParams().height = height;
			iv3.getLayoutParams().height = height;
			//convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
		
		    final IndexInfo info = data.get(position);
			Log.d("listinfo", "lis------info-------"+data.size());
			Log.d("listinfo", "lis------info---position----"+info.getBookList());
		if(info != null){
			title.setText(info.getName());
			final List<BookInfo> books = info.getBookList();
			Log.d("maindata", "maindate ====="+books.toString());
			if(books != null ){
				if(books.size() > 0){
					iv1.setImageUrl(books.get(0).getCoverUrl(), ProjectApp.mImageLoader);
					bookName1.setText(books.get(0).getName());
					iv1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							showDetail(books.get(0));
						}
					});
				}
				if(books.size() > 1){
					iv2.setImageUrl(books.get(1).getCoverUrl(), ProjectApp.mImageLoader);
					bookName2.setText(books.get(1).getName());
					iv2.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							showDetail(books.get(1));
						}
					});
				}
				if(books.size() >2){
					iv3.setImageUrl(books.get(2).getCoverUrl(), ProjectApp.mImageLoader);
					bookName3.setText(books.get(2).getName());
					iv3.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDetail(books.get(2));
						}
					});
				}
			}
			
		
//		if(books != null ){
//			switch (books.size()) {
//			case 1:
//				setfirstData();
//				break;
//			case 2:
//				setSecondData();
//				break;
//			case 3:
//				setThirdData();
//				break;
//			default:
//				break;
//			}
//		}
		
			showMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showMore(info.getId(), info.getChild());
				}
			});
		}
//		line.setBackgroundColor(mContext.getResources().getColor(color));
//		icon.setBackgroundColor(mContext.getResources().getColor(color));
		return convertView;
	}

//	private void setThirdData() {
//		setSecondData();
//		holder.iv3.setImageUrl(books.get(2).getCoverUrl(), ProjectApp.mImageLoader);
//		holder.bookName3.setText(books.get(2).getName());
//		holder.iv3.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDetail(books.get(2));
//			}
//		});
//	}
//
//	private void setSecondData() {
//		setfirstData();
//		holder.iv2.setImageUrl(books.get(1).getCoverUrl(), ProjectApp.mImageLoader);
//		holder.bookName2.setText(books.get(1).getName());
//		holder.iv2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDetail(books.get(1));
//			}
//		});
//	}
//	private void setfirstData() {
//		holder.iv1.setImageUrl(books.get(0).getCoverUrl(), ProjectApp.mImageLoader);
//		holder.bookName1.setText(books.get(0).getName());
//		holder.iv1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				showDetail(books.get(0));
//			}
//		});
//		
//	}

//	class ViewHolder{
//		public View icon;
//		public View line;
//		public NetworkImageView iv1;
//		public NetworkImageView iv2;
//		public NetworkImageView iv3;
//		public TextView bookName1;
//		public TextView bookName2;
//		public TextView bookName3;
//		public TextView showMore;
//		public TextView title;
//	}
	
	private void showMore(String typeId, String child){
		if(child.equals("1"))SearchListActivity.isTop = true;
		SearchListActivity.searchType = 0;
		Intent intent = new Intent(mContext, SearchListActivity.class);
		intent.putExtra("typeId", typeId);
		mContext.startActivity(intent);
	}
	
	private void showDetail(BookInfo book){
		if(book == null){
			return;
		}
		Intent intent = new Intent(mContext, BookDetailActivity.class);
		intent.putExtra(BookDetailActivity.BOOKINFO, book);
		mContext.startActivity(intent);
	}
	
}
