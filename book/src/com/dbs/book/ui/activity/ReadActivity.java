package com.dbs.book.ui.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dbs.book.R;
import com.dbs.book.ui.view.read.BookPageFactory;
import com.dbs.book.ui.view.read.Mymenu;
import com.dbs.book.ui.view.read.PageWidget;

import com.dbs.book.utils.PreferenceManager;
import com.dbs.book.utils.StringUtil;

public class ReadActivity extends Activity {

	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	File filepath;
	public static String fileName = "21963";
	public static String offsetStr;
	public int offset;
	
	public static String bookId = "";
	private int screenWidth = 0;
	private int screenHeight = 0;
	// --------------------卢---------------
	public Mymenu mMenu;
	SharedPreferences pre;
	SharedPreferences.Editor ed;
	// --------------------lu--------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_read);
		try {
			getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		} catch (Exception e2) {
		}  

		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		Resources res = getResources();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		mPageWidget = new PageWidget(this, screenWidth, screenHeight);
		
		pagefactory = new BookPageFactory(this, bitmaps, screenWidth,
				screenHeight);
		// ---------------------卢---------------------------------------

		pre = getSharedPreferences("activit",MODE_WORLD_WRITEABLE);
		ed = pre.edit();
		int b = pre.getInt("progress", 0);

		LinearLayout liner = (LinearLayout) findViewById(R.id.reader);
		liner.addView(mPageWidget);
		mMenu = (Mymenu) findViewById(R.id.men);

		// ----------------------------------------------

		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,Bitmap.Config.ARGB_8888);
		
		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bg));
		try {
			Intent intent = getIntent();
			File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/dingboshi/", fileName + ".txt");
			
			int ofset = intent.getIntExtra(offsetStr,0);
			int justShow = intent.getIntExtra("justShow", 0);
			//int offset = (Integer) PreferenceManager.get(string, 0);
			//int offset =  Integer.parseInt(string);
//			if(!StringUtil.isEmpty(string)){
//				offset = ofset;//2015-5
//			}
			offset = ofset;
			filepath = file;
			pagefactory.openbook(filepath);
			pagefactory.setBuffEnd(offset,justShow);//2015-5-24
			pagefactory.onDraw(mCurPageCanvas); 
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在,请将test.txt放在SD卡根目录下",
					Toast.LENGTH_SHORT).show();
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.dragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagefactory.isFirstPage())
								return false;
							mPageWidget.abortAnimation();
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagefactory.isLastPage())
								return false;
							mPageWidget.abortAnimation();
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}
		});
	}
	public void setBgBitmap() {
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bg));
		pagefactory.onDraw(mCurPageCanvas);
		pagefactory.onDraw(mNextPageCanvas);
		mPageWidget.invalidate();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_DOWN){
			onMenuClicked();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onMenuClicked(){
		if (mMenu != null) {
			if (mMenu.isShown()) {
				mMenu.setVisibility(View.GONE);
				mMenu.startAnimation(AnimationUtils.loadAnimation(this,R.anim.gone));
			} else {
			mMenu.setVisibility(View.VISIBLE);
			mMenu.startAnimation(AnimationUtils.loadAnimation(this,R.anim.show));
			}
		}	
	}
	public String getBookId(){
		return bookId;
	}
	
}