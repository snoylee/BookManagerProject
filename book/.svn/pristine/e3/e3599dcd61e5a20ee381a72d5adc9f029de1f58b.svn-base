package com.dbs.book.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dbs.book.R;
import com.dbs.book.net.ApiClient;
import com.dbs.book.ui.model.Response;
import com.dbs.book.utils.StringUtil;
import com.loopj.android.http.RequestParams;

public class ResetPasswordActivity extends BaseActivity {

	private EditText pswOld;
	private EditText pswNew;
	private EditText pswRepeat;
	private Button submit;
	
	private String strPswOld;
	private String strPswNew;
	
	private Handler mResetPasswordHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {

			if (null == this || null==ResetPasswordActivity.this || ResetPasswordActivity.this.isFinishing())
				return true;

			switch (msg.what) {
				case ApiClient.DATA_OK:
					onSuccess(msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(ResetPasswordActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;
		}
	});
	@Override
	protected int provideLayoutResId() {
		return R.layout.activity_reset_password;
	}

	protected void onSuccess(String string) {
		Response response = JSON.parseObject(string, Response.class);
		if(response != null){
			if(response.getError().equals("0")){
				Toast.makeText(ResetPasswordActivity.this, "恭喜你！修改密码成功、请记住新密码。", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, response.getError(), 0).show();
			}
		}else{
			Toast.makeText(this, ApiClient.DATA_TITLE, 0).show();
		}
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		pswOld = (EditText) findViewById(R.id.resetpsw_old);
		pswNew = (EditText) findViewById(R.id.resetpsw_new);
		pswRepeat = (EditText) findViewById(R.id.resetpsw_repeat);
		submit = (Button) findViewById(R.id.resetpsw_submit_btn);
	}

	@Override
	protected void initListener() {
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verify()){
					RequestParams params = new RequestParams();
					params.put("oldPassword", strPswOld);
					params.put("newPassword", strPswNew);
					ApiClient.resetPassword(params, mResetPasswordHandler);
				}
			}
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {}
	
	private boolean verify(){
		strPswOld = pswOld.getText().toString().trim();
		strPswNew = pswNew.getText().toString().trim();
		String strPswRepeat = pswRepeat.getText().toString().trim();
		if(StringUtil.isEmpty(strPswOld)){
			Toast.makeText(this, "原始密码不能为空", 0).show();
			return false;
		}
		if(StringUtil.isEmpty(strPswNew)){
			Toast.makeText(this, "密码不能为空", 0).show();
			return false;
		}
		if(StringUtil.isEmpty(strPswRepeat)){
			Toast.makeText(this, "密码不能为空", 0).show();
			return false;
		}
		if(!strPswRepeat.equals(strPswNew)){
			Toast.makeText(this, "两次密码输入不一致", 0).show();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onLeftClicked() {
		super.onLeftClicked();
		finish();
	}

}
