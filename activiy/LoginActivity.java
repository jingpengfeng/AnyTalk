package com.anytalk.activiy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anytalk.widget.CircularImage;
import com.anytalk.widget.CustomEmailDialog;
import com.example.anytalk.R;

/**
 * 
 * @author 黄松炎
 * 登陆界面
 *
 *
 *未完成功能 头像存储，网络，头像图标
 */



public class LoginActivity extends Activity implements OnClickListener{
	//头像
	private CircularImage iv_signInPic;
	//用户名，密码
	private EditText et_userName,et_userPwd;
	//登陆
	private Button btn_login;
	
	//注册，忘记密码
	private TextView tv_signUp,tv_forgetPwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.act_signin);
		initView();
		bindListener();
	}
	
	/*
	 * 初始化view
	 */
	private void initView(){
		iv_signInPic = (CircularImage) findViewById(R.id.iv_signInPic);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_userPwd = (EditText) findViewById(R.id.et_userPwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_signUp = (TextView) findViewById(R.id.tv_signUp);	
		tv_forgetPwd = (TextView) findViewById(R.id.tv_forgetPwd);
		
	}
	
	/*
	 * 绑定监听
	 */
	private void bindListener(){
		btn_login.setOnClickListener(this);
		tv_signUp.setOnClickListener(this);
		tv_forgetPwd.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_login:
			/*
			 * 进行网路处理，暂且不管，直接跳主界面
			 */
			intent.setClass(LoginActivity.this,SendMessageActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_signUp:
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_forgetPwd:
			/**
			 * @author 国平
			 */
			Dialog dialog=new Dialog(this, R.style.MyDialog);
			dialog.setContentView(R.layout.custom_dialog);
			dialog.show();
		}
		
	}
}
