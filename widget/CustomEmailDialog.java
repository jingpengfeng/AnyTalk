package com.anytalk.widget;

import com.example.anytalk.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CustomEmailDialog extends Dialog{
	//yellow---自定义dialog，用于写邮箱找密码

	public CustomEmailDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CustomEmailDialog(Context context,int theme) {
		super(context,theme);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.custom_dialog);
	}

}
