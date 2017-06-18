package com.dbs.book.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dbs.book.R;
import com.dbs.book.net.ApiClient;
import com.dbs.book.ui.adapter.ContentsListAdapter;
import com.dbs.book.ui.model.ContentsInfo;
import com.loopj.android.http.RequestParams;

public class ContentsListActivity extends BaseActivity  {

	private ListView mListView;
	private List<ContentsInfo> data;
	private ContentsListAdapter adapter;
	private int justShow = 0;
	
	private Handler mContentsHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (null == this || null==ContentsListActivity.this || ContentsListActivity.this.isFinishing())
				return true;
			switch (msg.what) {
				case ApiClient.DATA_OK:
					onSuccess(msg.obj.toString());
					Log.e("========", msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(ContentsListActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;

		}
	});
	
	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_contents_list;
	}

	protected void onSuccess(String string) {
		data = JSON.parseArray(string, ContentsInfo.class);
		adapter = new ContentsListAdapter(this, data);
		mListView.setAdapter(adapter);
	}
	@Override
	protected void initView(Bundle savedInstanceState) {
		mListView = (ListView) findViewById(R.id.contents_list);
		RequestParams params = new RequestParams();
		params.add("limit", "-1");
		params.add("bookId", ReadActivity.bookId);
		justShow = getIntent().getIntExtra("justShow", 0);
		ApiClient.getContentsByBookId(params, mContentsHandler);
	}
	@Override
	protected void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//if(justShow == 1) return;
				if(justShow == 1){
					Intent intent = new Intent(ContentsListActivity.this, ReadActivity.class);
					Log.d("offset", data.get(position).getOffset()+"");
					intent.putExtra(ReadActivity.offsetStr, data.get(position).getOffset());
					intent.putExtra("justShow", justShow);
					ContentsListActivity.this.startActivity(intent);
					finish();
				}
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		data = new ArrayList<ContentsInfo>();
		adapter = new ContentsListAdapter(this, data);
		mListView.setAdapter(adapter);
	}

	@Override
	public void onLeftClicked() {
		super.onLeftClicked();
		if(justShow == 0 )
			startActivity(new Intent(this, ReadActivity.class));
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			onLeftClicked();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	
}
