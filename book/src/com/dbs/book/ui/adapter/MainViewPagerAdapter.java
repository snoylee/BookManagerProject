package com.dbs.book.ui.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public class MainViewPagerAdapter extends PagerAdapter {

    public ArrayList<View> mListViews = new ArrayList<View>();
    private ArrayList<Drawable> drawables = new ArrayList<Drawable>();


    public Handler mHandler = new Handler();
    private Context mContext;

    public MainViewPagerAdapter(Context context, ArrayList<View> mListViews, ArrayList<Drawable> drawables) {
        this.drawables = drawables;
        this.mListViews = mListViews;
        mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	container.removeView(mListViews.get(position % mListViews.size()));
    }
    @Override
    public int getCount() {
        if(drawables != null){ //用于首页广告无限循环
            //return drawables != null ? (drawables.size() == 0 ? 0: Integer.MAX_VALUE) : 0;
        	return drawables != null?drawables.size() :0;
        }
    	return mListViews != null ? mListViews.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup group, int position) {
    	if(mListViews.size() == 0) return null;
        View view_image = mListViews.get(position % mListViews.size());
        if(group.getChildCount() > 0)group.removeView(view_image);
        group.addView(view_image, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if(drawables != null && drawables.size() > 0) {
            Drawable drawable = drawables.get(position % drawables.size());
            view_image.setBackgroundDrawable(drawable);
            }
        return view_image;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }
    
    @Override
    public Parcelable saveState() {
        return null;
    }


}

