package com.anytalk.activiy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.anytalk.adapter.FragPagerAdapter;
import com.anytalk.adapter.GridViewAdapter;
import com.anytalk.fragment.MsgFrag;
import com.anytalk.utils.ActionSheet;
import com.anytalk.utils.UserPic;
import com.anytalk.utils.ActionSheet.ActionSheetListener;
import com.anytalk.utils.GridViewHeight;
import com.anytalk.widget.BadgeView;
import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;
import com.example.soundtouchdemo.SoundTouchClient;

public class SendMessageActivity extends FragmentActivity implements
		OnClickListener, ActionSheetListener {

	// 用户头像
	private CircularImage iv_userPic;
	// 匿名，加好友，消息
	private ImageView iv_anonymity, iv_addFriend, iv_msg;
	// viewPager
	private ViewPager vp_sendMsg;
	// gridview 列表
	private GridView gv_contact;
	// 顶部菜单
	private RelativeLayout layout_top;
	// 底部菜单
	private RelativeLayout layout_bottom;
	// viewpager小圆点
	private LinearLayout viewGroup;
	// 三个圆点
	private ImageView dot1, dot2, dot3;

	// viewPager的容器装了三个fragment
	private List<Fragment> list_pv;

	// gridView的数据容器
	private List<Map<String, Object>> list_contact;
	// 设置gridView的高度
	private int gv_height;
	// gridView适配器
	private GridViewAdapter gridViewAdapter;

	// 判断用户是否长按 以用于监听录音
	private boolean isLongClick = false;

	// 录音类
	private SoundTouchClient soundTouchClient;

	// viewPager的当前页数
	private int currentIndex = 0;

	// 标识菜单的级别
	private int level = 0;
	// 菜单是否选择
	private boolean isChoose = false;

	// 是否公开身份
	private boolean isOpenId = false;

	/*
	 * 暂且保存
	 */
	private String strImgPath;// 头像保存路径

	private File out;// 文件

	private Uri uri;// 文件uri

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_sendmsg);
		initView();
		initPageView();
		initGridView();
		bindListener();
		creatPicPath();
		uri = Uri.fromFile(out);

		// 设置顶部菜单置于最上层
		layout_top.bringToFront();
		// 设置底部菜单置于最上层
		layout_bottom.bringToFront();
		// 设置小圆点到最上层
		viewGroup.bringToFront();

		// 设置底部菜单的位置
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.setMargins(0, 0, 0, gv_height);
		layout_bottom.setLayoutParams(params);
		viewGroup.setLayoutParams(params);
	}

	public void initView() {

		iv_userPic = (CircularImage) findViewById(R.id.iv_userPic);
		iv_anonymity = (ImageView) findViewById(R.id.iv_anonymity);
		iv_addFriend = (ImageView) findViewById(R.id.iv_addFriend);
		iv_msg = (ImageView) findViewById(R.id.iv_msg);
//		BadgeView badge = new BadgeView(this, iv_msg);
//		badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//		badge.setText("1");
//		badge.toggle(true);

		vp_sendMsg = (ViewPager) findViewById(R.id.vp_sendMsg);

		gv_contact = (GridView) findViewById(R.id.gv_contact);

		layout_top = (RelativeLayout) findViewById(R.id.layout_top);

		layout_bottom = (RelativeLayout) findViewById(R.id.layout_bottom);

		viewGroup = (LinearLayout) findViewById(R.id.viewGroup);

		dot1 = (ImageView) findViewById(R.id.dot1);
		dot2 = (ImageView) findViewById(R.id.dot2);
		dot3 = (ImageView) findViewById(R.id.dot3);
	}

	public void bindListener() {
		iv_userPic.setOnClickListener(this);
		iv_anonymity.setOnClickListener(this);
		iv_addFriend.setOnClickListener(this);
		iv_msg.setOnClickListener(this);

		vp_sendMsg.setOnPageChangeListener(new MyOnPageChangeListener());

		gv_contact.setOnItemClickListener(new GVOnItemClickListener());
		gv_contact.setOnItemLongClickListener(new GVOnItemLongClickListener());
		gv_contact.setOnTouchListener(new GVOnTouchListener());

	}

	public void initPageView() {
		list_pv = new ArrayList<Fragment>();
		for (int i = 0; i < 3; i++) {
			Fragment fragment = MsgFrag.newInstance(i);
			list_pv.add(fragment);
		}
		FragPagerAdapter adapter = new FragPagerAdapter(
				getSupportFragmentManager(), list_pv);
		vp_sendMsg.setAdapter(adapter);
	}

	public void initGridView() {
		/*
		 * 模拟数据，待修改
		 */
		int img[] = { R.drawable.i1, R.drawable.i2, R.drawable.i3,
				R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7,
				R.drawable.i8, R.drawable.i9, R.drawable.i10, R.drawable.i11 };
		list_contact = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 11; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", img[i]);
			map.put("name", "路人" + i);
			list_contact.add(map);
		}
		gridViewAdapter = new GridViewAdapter(this, list_contact);
		gv_contact.setAdapter(gridViewAdapter);

		// 设置gridview的高度
		gv_height = GridViewHeight.setHeight(gv_contact);
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
	 * viewPager监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 0:
				viewGroup.setVisibility(View.GONE);
				break;
			case 1:
				viewGroup.setVisibility(View.VISIBLE);
				break;
			case 2:
				viewGroup.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int index) {
			switch (index) {
			case 0:
				currentIndex = 0;
				dot1.setImageResource(R.drawable.soild);
				dot2.setImageResource(R.drawable.hollow);
				dot3.setImageResource(R.drawable.hollow);
				break;
			case 1:
				dot1.setImageResource(R.drawable.hollow);
				dot2.setImageResource(R.drawable.soild);
				dot3.setImageResource(R.drawable.hollow);
				currentIndex = 1;
				break;
			case 2:
				dot1.setImageResource(R.drawable.hollow);
				dot2.setImageResource(R.drawable.hollow);
				dot3.setImageResource(R.drawable.soild);
				currentIndex = 2;
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_userPic:
			showActionSheet();
			break;
		case R.id.iv_anonymity:
			/*
			 * 进行匿名切换代做
			 */
			if (isOpenId) {
				iv_anonymity.setImageResource(R.drawable.anonymity);
				isOpenId = false;
			} else {
				iv_anonymity.setImageResource(R.drawable.open);
				isOpenId = true;
			}

			break;
		case R.id.iv_addFriend:
			intent.setClass(SendMessageActivity.this, AddContactActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_msg:
			intent.setClass(SendMessageActivity.this, MsgBoxActivity.class);
			startActivity(intent);
			break;
		}
	}

	public class GVOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			System.out.println("CLICK");
			switch (currentIndex) {
			case 1:
				Intent intent = new Intent("android.intent.action.Pic");
				LocalBroadcastManager.getInstance(SendMessageActivity.this)
						.sendBroadcast(intent);
				break;
			case 2:
				Intent intent2 = new Intent("android.intent.action.Text");
				LocalBroadcastManager.getInstance(SendMessageActivity.this)
						.sendBroadcast(intent2);
				break;
			}
		}
	}

	/*
	 * gridView长按监听
	 */
	public class GVOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			if (currentIndex == 0) {
				soundTouchClient = new SoundTouchClient(new Handler(),
						SendMessageActivity.this);
				soundTouchClient.start();
				isLongClick = true;
			}
			return false;
		}

	}

	public class GVOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			// TODO Auto-generated method stub
			if (isLongClick) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					soundTouchClient.stop();
					isLongClick = false;
					Intent intent = new Intent("android.intent.action.Up");
					LocalBroadcastManager.getInstance(SendMessageActivity.this)
							.sendBroadcast(intent);
				}
			}
			return false;
		}

	}

	/*
	 * 显示顶部item
	 */
	public void showActionSheet() {
		setTheme(R.style.ActionSheetStyleIOS7);
		ActionSheet
				.createBuilder(SendMessageActivity.this,
						getSupportFragmentManager()).setCancelButtonTitle("取消")
				.setOtherButtonTitles("编辑个人资料", "关于我们")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * 显示关于我们
	 */
	public void showAboutUs() {
		ActionSheet
				.createBuilder(SendMessageActivity.this,
						getSupportFragmentManager()).setCancelButtonTitle("取消")
				.setOtherButtonTitles("关于", "反馈", "分享到微信")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * 显示个人资料
	 */
	public void showUserMsg() {
		ActionSheet
				.createBuilder(SendMessageActivity.this,
						getSupportFragmentManager()).setCancelButtonTitle("取消")
				.setOtherButtonTitles("用户名", "更改头像", "更改邮箱", "登出")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * 显示头像选择
	 */
	public void showUserPic() {
		ActionSheet.createBuilder(this, getSupportFragmentManager())
				.setCancelButtonTitle("取消")
				.setOtherButtonTitles("拍照", "从手机相册选择")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * item关闭回调
	 */
	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		// TODO Auto-generated method stub
		if (isChoose) {
			isChoose = false;
		} else {
			level = 0;
		}
	}

	/*
	 * item点击关闭回调
	 */
	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		// TODO Auto-generated method stub
		isChoose = true;
		if (level == 0) {
			switch (index) {
			case 0:
				showUserMsg();
				level = 1;
				break;
			case 1:
				showAboutUs();
				level = 2;
				break;
			}
		} else if (level == 1) {
			switch (index) {
			case 0:
				break;
			case 1:
				showUserPic();

				break;
			case 2:
				Dialog email_dialog = new Dialog(this, R.style.MyDialog);
				email_dialog.setContentView(R.layout.change_email_dialog);
				email_dialog.show();
				break;
			case 3:
				Dialog exit_dialog = new Dialog(this, R.style.MyDialog);
				exit_dialog.setContentView(R.layout.exit_dialog);
				exit_dialog.show();
				break;
			}

			if (index == 1) {
				level = 3;
			} else {
				level = 0;
			}

		} else if (level == 2) {
			switch (index) {
			case 0:
				Toast.makeText(SendMessageActivity.this, "更改头像", 1000).show();
				break;
			case 1:
				Toast.makeText(SendMessageActivity.this, "更改邮箱", 1000).show();
				break;
			case 2:
				Toast.makeText(SendMessageActivity.this, "登出", 1000).show();
				break;
			}
			level = 0;
		} else if (level == 3) {
			switch (index) {
			case 0:
				// 拍照
				UserPic.takePicture(SendMessageActivity.this, uri);
				break;
			case 1:
				UserPic.fromPicList(SendMessageActivity.this, uri);
				break;
			}
			level = 0;
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
				UserPic.cropPicture(SendMessageActivity.this, uri);
			}
			break;
		case 2:
			Bitmap bmp = BitmapFactory.decodeFile(strImgPath);
			iv_userPic.setImageBitmap(bmp);
			break;
		case 3:
			Bitmap bmp2 = data.getParcelableExtra("data");
			iv_userPic.setImageBitmap(bmp2);
			break;
		}
	}

}
