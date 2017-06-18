package com.dbs.book.ui.view.read;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dbs.book.R;
import com.dbs.book.ui.activity.ContentsListActivity;
import com.dbs.book.ui.activity.ReadActivity;

public class Mymenu extends LinearLayout implements View.OnClickListener{
	ReadActivity mActivity;
	LayoutInflater inflater;
	Context context;

	public Mymenu(Context context) {
		super(context);
	}
	public Mymenu(Context context2, AttributeSet set) {
		super(context2, set);
		if (context2 instanceof ReadActivity)
		mActivity = (ReadActivity) context2;
		inflater = mActivity.getLayoutInflater();
		this.context = context2;
		initView();
	}

	private void initView() {
		View view = inflater.inflate(R.layout.menu_read, this);
		view.findViewById(R.id.read_contents).setOnClickListener(this);
		view.findViewById(R.id.read_download).setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.read_contents:
			context.startActivity(new Intent(context, ContentsListActivity.class));
			((ReadActivity)context).onMenuClicked();
			((ReadActivity)context).finish();
			break;
		case R.id.read_download:
			Toast.makeText(mActivity, "下载", 0).show();
			break;
		default:
			break;
		}
		
	}

}
