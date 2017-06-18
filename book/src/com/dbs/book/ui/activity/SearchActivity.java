package com.dbs.book.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.dbs.book.R;
import com.dbs.book.ui.adapter.AssociateListAdapter;
import com.dbs.book.ui.fragment.ShelfFragment;
import com.dbs.book.utils.PreferenceManager;
import com.dbs.book.utils.StringUtil;

public class SearchActivity extends BaseActivity{
	
	private static final int MAX_HISTORY = 10; 
	private static final String HISTORY_CACHE = "History_cache";
	private EditText mSearch_ET;
	private TextView mCancle_TV;
	private ImageView mNoHistory_IV;
	private ListView mListView_LV;
	private String keyword;
	private List<String> history = new ArrayList<String>();
	private List<String> data = new ArrayList<String>();
	private List<String> associate = new ArrayList<String>();
	private AssociateListAdapter adapter;

	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_search;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mSearch_ET = (EditText) findViewById(R.id.search_editext);
		mCancle_TV = (TextView) findViewById(R.id.search_cancle);
		mNoHistory_IV = (ImageView) findViewById(R.id.search_no_history);
		mListView_LV = (ListView) findViewById(R.id.serch_history_list);
	}

	@Override
	protected void initListener() {
		mCancle_TV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String history = mSearch_ET.getText().toString().trim();
				if (!StringUtil.isEmpty(history)) {
		    		   submit(history);
				}
			}
		});
		mSearch_ET.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
		       public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  {
		    	   if ((actionId==EditorInfo.IME_ACTION_SEND ||(event != null && event.getKeyCode()== KeyEvent.KEYCODE_ENTER))) {
		    		   mCancle_TV.performClick();
		        	   return true;  
		           }    
		           return false;    
		       }    
		   }); 
		
		mListView_LV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSearch_ET.setText(data.get(position));
				mCancle_TV.performClick();
			}
		});
	}
	
	private void submit(String history){
		updateHistory(history);
		search(history);
//		associal(history);
	}
	
	private void search(String history) {
		SearchListActivity.searchType = 1;
		Intent intent = new Intent(this, SearchListActivity.class);
		intent.putExtra(SearchListActivity.KEYWORD, history);
		startActivity(intent);
	}
	
	private void associal(String keyword){
		
		
	}

	private void updateHistory(String history){
		saveHistory(history);
	 	data.clear();
	 	data.addAll(getHistory());
	 	if(data.size() > 0){
			showHistory();
		}else{
			showNoHistory();
		}
	 	adapter.notifyDataSetChanged();
	 	mSearch_ET.setText("");
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		data.clear();
		data.addAll(getHistory());
		if(data.size() > 0){
			showHistory();
		}else{
			showNoHistory();
		}
		adapter = new AssociateListAdapter(this, data);
		mListView_LV.setAdapter(adapter);
	}
	
	private void showNoHistory(){
		mListView_LV.setVisibility(View.GONE);
		mNoHistory_IV.setVisibility(View.VISIBLE);
	}
	
	private void showHistory(){
		mListView_LV.setVisibility(View.VISIBLE);
		mNoHistory_IV.setVisibility(View.GONE);
	}

	@Override
	public void onLeftClicked() {
		finish();
	}
	
	@Override
	public void onActionClicked() {
		MainActivity.WHICH = MainActivity.SHELF;
		startActivity(new Intent(this, MainActivity.class));
	}
	
	private void saveHistory(String history){
		List<String> data = new ArrayList<String>();
		data.addAll(getHistory());
		if(data.size() >= 10){
			data.remove(data.size() - 1);
		}
		data.add(0, history);
		String json = JSONArray.toJSONString(data);
		PreferenceManager.save(HISTORY_CACHE, json);
	}
	
	private List<String> getHistory(){
		List<String> data = new ArrayList<String>();
		String json = (String) PreferenceManager.get(HISTORY_CACHE, new String());
		if(!StringUtil.isEmpty(json)){
			data.addAll(JSONArray.parseArray(json, String.class));
		}
		return data;
	}
}