package com.dbs.book.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dbs.book.R;
import com.dbs.book.ui.view.titlebar.ITitleBar;
import com.dbs.book.ui.view.titlebar.TitleBar;

public abstract class BaseActivity extends FragmentActivity implements ITitleBar {

	protected View mRootView;
	protected TitleBar mTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int layoutId = provideLayoutResId();
		if(layoutId > 0){
			mRootView = getLayoutInflater().inflate(layoutId, null);
			setContentView(mRootView);
		}
		
		initView(savedInstanceState);
		initListener();
		
		mTitleBar = (TitleBar)findViewById(R.id.titlebar);
		if(hasTitleBar()){
			mTitleBar.setTitlebarListener(this);
			initTitleBar();
		}
		
		initData(savedInstanceState);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	protected View getRootView(){
		return mRootView;
	}
	
	protected boolean hasTitleBar(){
		return null != mTitleBar;
	}
	
	public TitleBar getTitleBar(){
		return mTitleBar;
	}
	
	protected void initTitleBar(){}
	
	protected abstract int provideLayoutResId();
	
	/**
     * 步骤一：初始化View，比如findViewById等操作
     */
    protected abstract void initView(Bundle savedInstanceState) ;

    /**
     * 步骤二：初始化View的Listener，比如onClick等监听器
     */
    protected abstract void initListener() ;

    /**
     * 步骤三：初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState) ;

	@Override
	public void onRightClicked() {
		startActivity(new Intent(this, SearchActivity.class));
	}

	@Override
	public void onLeftClicked() {}

	@Override
	public void onActionClicked() {}

}
