package com.dbs.book.ui.view.normal;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.book.R;
import com.dbs.book.app.BaseApplication;
import com.dbs.book.utils.PhoneUtil;


public class XViewPageSet extends LinearLayout{

	private final static String TAG = "ViewPagerSet";

    private final static int START_RUN = 1;
	private Context context;
	private View root;
	private ViewPager viewPager;
	private LinearLayout indicatorView;
	private LayoutInflater inflater;
	
	private int pageCount = 1;
    private long time;// 循环的时间
    
//    private float downX,downY,moveX,moveY;
//    private boolean selfHandle = false;
    
    
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case START_RUN:
                    int itemNumber = viewPager.getCurrentItem()+1;//控制循环的顺序从第几个图片开始
                    if(itemNumber == pageCount){
                        itemNumber = 0;
                    }
                    viewPager.setCurrentItem(itemNumber);
                    setStartAutoRun(time);
                    break;
            }
        }
    };
    
    public XViewPageSet(Context context) {
		super(context);
		this.context = context;
		setupViews();
		addView(root);
	}
	
	public XViewPageSet(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setupViews();
		addView(root);
	}
	
	private void setupViews() {
		inflater = LayoutInflater.from(context);
		root = inflater.inflate(R.layout.widget_xviewpager, null);
		viewPager = (ViewPager) root.findViewById(R.id.nf_viewPager);
		BaseApplication.getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				viewPager.getLayoutParams().height = PhoneUtil.getDisplayHeight(getContext()) / 4;
			}
		});
		indicatorView = (LinearLayout) root.findViewById(R.id.nf_icon_indocator);
	}
	
	public void setAdapter(PagerAdapter adapter) {
		viewPager.setAdapter(adapter);
        if(adapter.getCount() < 10) {
            viewPager.setCurrentItem(0);
        }else {
            viewPager.setCurrentItem(100 * pageCount);
        }
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        //viewPager.setOnTouchListener(new MyOnTouchListener());
    }

    public void setStartAutoRun(long time){
        dismissStartAutoRun();
        if(pageCount >= 2) {
            this.time = time != 0 ? time : 3000;
            mHandler.sendEmptyMessageDelayed(START_RUN, this.time);
        }
    }

    public void dismissStartAutoRun(){
        mHandler.removeMessages(START_RUN);
    }
	
	public int getCurrentIndex() {
		return viewPager.getCurrentItem();
	}
	
	public void setPageCount(int count){
		pageCount = count;
		addindicatorChildView();
	}
	

    public PagerAdapter getAdapter(){
        return viewPager.getAdapter();
    }

    public void hideIndicatorView(){
        indicatorView.setVisibility(View.INVISIBLE);
    }

	private void changeindicatorChildView(int id){
		for(int i = 0; i < pageCount; i++){
			ImageView view = (ImageView)indicatorView.getChildAt(i);
			if(i!= id){
				view.setImageResource(R.drawable.nf_arrows_2);
			}else {
				view.setImageResource(R.drawable.nf_arrows_1);
			}
		}
	}
	
	private void addindicatorChildView(){
		indicatorView.removeAllViews();
		for(int i = 0; i < pageCount; i++){
			ImageView view = new ImageView(context);
			if(i == 0)
				view.setImageResource(R.drawable.nf_arrows_1);
			else {
				view.setImageResource(R.drawable.nf_arrows_2);
			}
			view.setId(i);
			view.setPadding(3, 8, 3, 8);
			indicatorView.addView(view);
		}
	}
	/**
     * 页卡切换监听
*/
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            changeindicatorChildView(position%pageCount);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {



        }
    }

    public class MyOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_MOVE:
                	break;
                case MotionEvent.ACTION_DOWN:
                    dismissStartAutoRun();
                    break;
                case MotionEvent.ACTION_UP:
                    setStartAutoRun(0);
                    break;
            }
            return false;
        }
    }
    
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//    	if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
//    		downX = ev.getX();
//    		downY = ev.getY();
//            dismissStartAutoRun();
//        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {  
//        	moveX = ev.getX();
//        	moveY = ev.getY();
////        	CommonUtils.showToast(String.valueOf(moveX - downX)+"");
//        	// && moveY - downY < 40
//        	if(Math.abs(moveX - downX) > 40){
//        		selfHandle = true;
//        	}
//        	if(selfHandle){
//        		getParent().requestDisallowInterceptTouchEvent(true);
//        	}
//        } else if (ev.getAction() == MotionEvent.ACTION_UP) {  
//            setStartAutoRun(0);
//        }  
//  
//        return super.dispatchTouchEvent(ev);
//    }
	
}
