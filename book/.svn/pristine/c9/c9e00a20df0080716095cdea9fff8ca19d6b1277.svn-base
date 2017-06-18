package com.dbs.book.ui.view.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.book.R;
import com.dbs.book.utils.StringUtil;

public class TitleBar extends RelativeLayout implements OnClickListener{
	
	protected ImageView mLeftIcon_IV;
	protected ImageView mRightIcon_IV;
	protected TextView mTitle_TV;
	protected TextView mAction_TV;
	protected ITitleBar mITitleBar;
	
	public TitleBar(Context context) {
		super(context);
		init(null, 0);
	}
	
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}
	
	public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr);
	}

	public void init(AttributeSet attrs, int defStyleAttr){
		inflate(getContext(), R.layout.widget_titlebar, this);
		mLeftIcon_IV = (ImageView) findViewById(R.id.titlebar_lefticon);
		mRightIcon_IV = (ImageView) findViewById(R.id.titlebar_righticon);
		mTitle_TV = (TextView) findViewById(R.id.titlebar_title);
		mAction_TV = (TextView) findViewById(R.id.titlebar_action);
		
		TypedArray tArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
		String title = tArray.getString(R.styleable.TitleBar_title);
		String action = tArray.getString(R.styleable.TitleBar_action);
		Drawable leftIcon = tArray.getDrawable(R.styleable.TitleBar_leftIcon);
		Drawable rightIcon = tArray.getDrawable(R.styleable.TitleBar_rightIcon);
		
		if(!StringUtil.isEmpty(title)){
			mTitle_TV.setText(title);
		}
		if(!StringUtil.isEmpty(action)){
			mRightIcon_IV.setVisibility(View.GONE);
			mAction_TV.setVisibility(View.VISIBLE);
			mAction_TV.setText(action);
		}
		if(null != leftIcon) mLeftIcon_IV.setImageDrawable(leftIcon);
		if(null != rightIcon) mRightIcon_IV.setImageDrawable(rightIcon);
		
		mLeftIcon_IV.setOnClickListener(this);
		mRightIcon_IV.setOnClickListener(this);
		mAction_TV.setOnClickListener(this);
	}
	
	 public void setTitlebarListener(ITitleBar listener) {
	        mITitleBar = listener;
	    }
	
	@Override
	public void onClick(View v) {
		if(mITitleBar == null){
			return;
		}
		if(v == mAction_TV){
			mITitleBar.onActionClicked();
		}else if(v == mLeftIcon_IV){
			mITitleBar.onLeftClicked();
		}else if(v == mRightIcon_IV){
			mITitleBar.onRightClicked();
		}
		
	}

	public void hiddenLeftIcon(){
		mLeftIcon_IV.setVisibility(View.GONE);
	}
	
	public void hiddenRightIcon(){
		mRightIcon_IV.setVisibility(View.GONE);
	}
	
	public void showLeftIcon(){
		mLeftIcon_IV.setVisibility(View.VISIBLE);
	}
	
	public void showRightIcon(){
		mRightIcon_IV.setVisibility(View.VISIBLE);
	}
	
	public void hiddenTitle(){
		mTitle_TV.setVisibility(View.GONE);
	}
	
	public void setTitle(String title){
		mTitle_TV.setText(title);
	}
	
	public ImageView getLeftView(){
		return this.mLeftIcon_IV;
	}
}
