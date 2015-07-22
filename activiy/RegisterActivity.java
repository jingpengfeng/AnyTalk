package com.anytalk.activiy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.anytalk.utils.ActionSheet;
import com.anytalk.utils.ActionSheet.ActionSheetListener;
import com.anytalk.utils.UserPic;
import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 黄松炎 注册界面
 * 
 *         拍照路径待修改
 */

public class RegisterActivity extends FragmentActivity implements
		OnClickListener, ActionSheetListener {

	private TextView tv_signUpBack;
	private CircularImage iv_signUpPic;
	private EditText et_signUpName;
	private EditText et_singUpEmail;
	private EditText et_signUpPwd;
	private Button btn_signUp;
	private RelativeLayout layout_register;

	// 提示窗口
	private PopupWindow alterWindow;

	private String strImgPath;// 头像保存路径

	private File out;// 文件

	private Uri uri;// 文件uri

	private boolean isShowAlter = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_signup);

		initView();

		bindListener();

		creatPicPath();

		uri = Uri.fromFile(out);
	}

	private void initView() {
		tv_signUpBack = (TextView) findViewById(R.id.tv_signUpBack);
		iv_signUpPic = (CircularImage) findViewById(R.id.iv_signUpPic);
		et_signUpName = (EditText) findViewById(R.id.et_signUpName);
		et_singUpEmail = (EditText) findViewById(R.id.et_singUpEmail);
		et_signUpPwd = (EditText) findViewById(R.id.et_signUpPwd);
		btn_signUp = (Button) findViewById(R.id.btn_signUp);
		layout_register = (RelativeLayout) findViewById(R.id.layout_register);
	}

	public void initAlterWindow(String alter) {
		// 获取自定义布局文件pop.xml的视图
		View customView = getLayoutInflater().inflate(R.layout.alter_window,
				null, false);
		// 创建PopupWindow实例,设置宽度和高度
		TextView tv_alter = (TextView) customView.findViewById(R.id.tv_alter);
		tv_alter.setText(alter);
		alterWindow = new PopupWindow(customView, layout_register.getWidth(),
				50);
		 //设置PopupWindow外部区域是否可触摸
		alterWindow.setOutsideTouchable(true);
        // 自定义view添加触摸事件  
        customView.setOnTouchListener(new OnTouchListener() {  
  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                if (alterWindow != null && alterWindow.isShowing()) {  
                	alterWindow.dismiss();  
                    alterWindow = null;  
                }  
  
                return false;  
            }  
        });  
	}

	private void bindListener() {
		tv_signUpBack.setOnClickListener(this);
		iv_signUpPic.setOnClickListener(this);
		btn_signUp.setOnClickListener(this);
	}

	/*
	 * 头像存储路径
	 */
	public void creatPicPath() {
		strImgPath = Environment.getExternalStorageDirectory().toString()
				+ "/dlion/";
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()) + ".jpg";
		out = new File(strImgPath);
		if (!out.exists()) {
			out.mkdirs();
		}
		out = new File(strImgPath, fileName);
		strImgPath = strImgPath + fileName;
	}

	/*
	 * 打开ios7菜单样式
	 */
	public void showActionSheet() {
		ActionSheet.createBuilder(this, getSupportFragmentManager())
				.setCancelButtonTitle("取消")
				.setOtherButtonTitles("拍照", "从手机相册选择")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * ios7菜单消失回调
	 */
	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		// TODO Auto-generated method stub
	}

	/*
	 * ios菜单子项监听
	 */
	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		// TODO Auto-generated method stu
		switch (index) {
		case 0:
			// 拍照
			UserPic.takePicture(RegisterActivity.this, uri);
			break;
		case 1:
			UserPic.fromPicList(RegisterActivity.this, uri);
			break;
		}
	}

	/*
	 * 拍照回调
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				UserPic.cropPicture(RegisterActivity.this, uri);
			}
			break;
		case 2:
			Bitmap bmp = BitmapFactory.decodeFile(strImgPath);
			iv_signUpPic.setImageBitmap(bmp);
			break;
		case 3:
			Bitmap bmp2 = data.getParcelableExtra("data");
			iv_signUpPic.setImageBitmap(bmp2);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_signUpBack:
			finish();
			break;
		case R.id.iv_signUpPic:
			setTheme(R.style.ActionSheetStyleIOS7);
			showActionSheet();
			break;
		case R.id.btn_signUp:
			userValidation();
			// Intent intent = new Intent();
			// intent.setClass(RegisterActivity.this,
			// SendMessageActivity.class);
			// startActivity(intent);
		}
	}

	/*
	 * 进行验证
	 */
	public void userValidation() {
		if(et_signUpName.getText() == null||et_signUpName.getText().toString().equals("")){
			initAlterWindow("用户名不能为空");
		}else if(et_singUpEmail.getText() == null||et_singUpEmail.getText().toString().equals("")){
			initAlterWindow("邮箱不能为空");
		}else if(et_signUpPwd.getText() == null||et_signUpPwd.getText().toString().equals("")){
			initAlterWindow("密码不能为空");
		}
		
		alterWindow.showAsDropDown(layout_register);
		
	}
}
