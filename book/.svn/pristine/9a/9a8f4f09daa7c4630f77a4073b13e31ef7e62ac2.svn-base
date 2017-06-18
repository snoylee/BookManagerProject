package com.dbs.book.ui.view.normal;

import com.dbs.book.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


public class UserInfoDialog extends Dialog{
    private int layoutResID;
    private Window dialogWindow ;
    public UserInfoDialog(Context context,int layoutResID) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);         //去掉dialog标题
        dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);

        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutResID);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();       //布局
        WindowManager windowManager = dialogWindow.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth();
        dialogWindow.setAttributes(lp);
    }
}
