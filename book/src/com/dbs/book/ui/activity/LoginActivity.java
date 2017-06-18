package com.dbs.book.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dbs.book.R;
import com.dbs.book.net.ApiClient;
import com.dbs.book.ui.Contants;
import com.dbs.book.ui.adapter.CommonListAdapter;
import com.dbs.book.ui.model.Response;
import com.dbs.book.ui.model.User;
import com.dbs.book.utils.DialogFactory;
import com.dbs.book.utils.PreferenceManager;
import com.dbs.book.utils.StringUtil;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private LinearLayout gradAndClazz;
	private EditText mUsername_ET;
	private EditText mPassword_ET;
	private TextView mGrad_TV;
	private TextView mClass_TV;
	private Button mLogin_BTN;
	private Dialog dialog;
	
	private String mUsername;
	private String mPassword;
	private String mGrad;
	private String mClass;
	private String mClass_temp;
	private boolean isTeacher = false;
	private User user;
	
	private List<String> grads = new ArrayList<String>();
	private List<String> classes = new ArrayList<String>();
	
	private Handler mLoginHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (null == this || null==LoginActivity.this || LoginActivity.this.isFinishing())
				return true;
			switch (msg.what) {
				case ApiClient.DATA_OK:
					onSuccess(msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(LoginActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;
		}
	});
	private void onSuccess(String json) {
		Log.e("login", json);
		Response response = JSON.parseObject(json, Response.class);
		if(response != null){
			if(response.getError().equals("0")){
				PreferenceManager.save("user", user);
				startActivity(new Intent(this, MainActivity.class));
				finish();
			}else{
				Toast.makeText(this, response.getError(), 0).show();
				}
		}else{
			Toast.makeText(this, ApiClient.DATA_TITLE, 0).show();
		}
	}
	
	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_login;
	}
	@Override
	protected void initView(Bundle savedInstanceState) {
		mUsername_ET = (EditText) findViewById(R.id.login_username_et);
		mPassword_ET = (EditText) findViewById(R.id.login_password_et);
		mGrad_TV = (TextView) findViewById(R.id.login_grad_tv);
		mClass_TV = (TextView) findViewById(R.id.login_class_tv);
		mLogin_BTN = (Button) findViewById(R.id.login_submit_btn);
		gradAndClazz = (LinearLayout) findViewById(R.id.login_student_ll);
		
	}
	@Override
	protected void initListener() {
		mGrad_TV.setOnClickListener(this);
		mClass_TV.setOnClickListener(this);
		mLogin_BTN.setOnClickListener(this);
	
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		grads = Contants.arrayToList(Contants.GRADS);
		List<Integer> list = Contants.arrayToList(Contants.CLASSES);
		for(Integer i : list){
			classes.add( i + "班");
		}
		
		user = (User) PreferenceManager.get("user", new User());
		if(user != null){
			if(!isTeacher){
				mGrad_TV.setText(user.getGrade());
				mClass_TV.setText(user.getClazz());
			}
			mUsername_ET.setText(user.getAccount());
			mPassword_ET.setText(user.getPassword());
		}
	}
	
	private boolean verifyUserInfo(){
		mUsername = mUsername_ET.getText().toString().trim();
		mPassword = mPassword_ET.getText().toString().trim();
		
		if(StringUtil.isEmpty(mUsername)){
			Toast.makeText(this, "账号不能为空", 0).show();
			return false;
		}
		if(StringUtil.isEmpty(mPassword)){
			Toast.makeText(this, "密码不能为空", 0).show();
			return false;
		}
		if(!isTeacher){
			mGrad = mGrad_TV.getText().toString().trim();
			mClass_temp = mClass_TV.getText().toString().trim();
			if(StringUtil.isEmpty(mGrad)){
				Toast.makeText(this, "请选择年级", 0).show();
				return false;
			}
			if(StringUtil.isEmpty(mClass_temp)){
				Toast.makeText(this, "请选择班级", 0).show();
				return false;
			}
			mClass = mClass_temp.substring(0, mClass_temp.length() - 1);
		}
		user = new User(mUsername, mPassword, mGrad, mClass_temp);
		return true;
	}
	
	@Override
	protected void initTitleBar() {
		super.initTitleBar();
		mTitleBar.hiddenLeftIcon();
		mTitleBar.hiddenRightIcon();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_grad_tv:
			selectGrad();
			break;
		case R.id.login_class_tv:
			selectClass();
			break;
		case R.id.login_submit_btn:
			login();
			break;
		default:
			break;
		}
	}

	private void selectGrad() {
		if(dialog != null && dialog.isShowing()){
			 dialog.dismiss();
			 return;
		}
		CommonListAdapter adapter = new CommonListAdapter(this, grads);
		dialog = DialogFactory.showListDialog(this, "年级选择", adapter, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mGrad_TV.setText(grads.get(position));
				dialog.dismiss();
			}
		});
	}

	private void selectClass() {
		if(dialog != null && dialog.isShowing()){
			 dialog.dismiss();
			 return;
		}
		CommonListAdapter adapter = new CommonListAdapter(this, classes);
		dialog = DialogFactory.showListDialog(this, "班级选择", adapter, new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mClass_TV.setText(classes.get(position));
				dialog.dismiss();
			}
		});
	}
	private void login() {
		if(verifyUserInfo()){
			RequestParams params = new RequestParams();
			params.put("account", mUsername);
			params.put("password", mPassword);
			params.put("grade", mGrad);
			params.put("clazz", mClass);
			if(isTeacher){
				ApiClient.teacherLogin(params, mLoginHandler);
			}else{
				ApiClient.studentLogin(params, mLoginHandler);
			}
		}
	}
	@Override
	public void onActionClicked() {
		isTeacher = !isTeacher;
		if(isTeacher){
			mTitleBar.setTitle("老师登陆");
			gradAndClazz.setVisibility(View.GONE);
		}else{
			mTitleBar.setTitle("学生登陆");
			gradAndClazz.setVisibility(View.VISIBLE);
		}
	}
}
