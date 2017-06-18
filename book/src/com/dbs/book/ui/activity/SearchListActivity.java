package com.dbs.book.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dbs.book.R;
import com.dbs.book.net.ApiClient;
import com.dbs.book.ui.adapter.SearchListAdapter;
import com.dbs.book.ui.model.BookInfo;
import com.loopj.android.http.RequestParams;

public class SearchListActivity extends BaseActivity {

	public static final String KEYWORD = "KEYWORD";
	public static final String TYPEID = "typeId";
	public static int searchType = 0;
	public static boolean isTop = false;
	public static String name = "";
	private List<BookInfo> data = new ArrayList<BookInfo>();
	private ListView mListView;
	private TextView mTextView ;
	private String keyword = "搜索结果";
	private int currentPage;
	private String typeId;
	private SearchListAdapter mAdapter;
	
	private Handler mSearchListHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (null == this || null==SearchListActivity.this || SearchListActivity.this.isFinishing())
				return true;

			switch (msg.what) {
				case ApiClient.DATA_OK:
					onSuccess(msg.obj.toString());
					Log.e("=========", msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(SearchListActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;
		}
	});
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	protected void onSuccess(String string) {
		data = JSON.parseArray(string, BookInfo.class);
		Log.d("DATA", "DATA==="+ data);
		Log.d("DATA", "DATA SIZE==="+ data.size());
		if(data.size()== 0){
			mTextView.setVisibility(View.VISIBLE); 
			mListView.setVisibility(View.GONE);
		}
		mAdapter = new SearchListAdapter(this, data);
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_search_list;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		keyword = getIntent().getStringExtra(KEYWORD);
		if(searchType == 0) typeId = getIntent().getStringExtra(TYPEID);
		mListView = (ListView) findViewById(R.id.search_list_listview);
		mTextView = (TextView) findViewById(R.id.search_text_view);
	}

	@Override
	protected void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchListActivity.this, BookDetailActivity.class);
				intent.putExtra(BookDetailActivity.BOOKINFO, data.get(position));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		mTitleBar.setTitle(keyword);
		RequestParams params = new RequestParams();
		params.add("page", String.valueOf(currentPage));
		if(searchType == 0){
			params.add(TYPEID, typeId);
			if(isTop){
				ApiClient.getBookListByTopSortId(params, mSearchListHandler);
			}else{
				ApiClient.getBookListBySortId(params, mSearchListHandler);
			}
		}else{
			params.add("name", keyword);
			ApiClient.getBookListByName(params, mSearchListHandler);
		}
	}
	
	@Override
	public void onLeftClicked() {
		super.onLeftClicked();
		finish();
	}
	

}
