package com.anytalk.activiy;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anytalk.adapter.FragPagerAdapter;
import com.anytalk.fragment.ReplyFrag;
import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;
import com.example.soundtouchdemo.SoundTouchClient;

public class ReplyActivity extends FragmentActivity implements OnTouchListener {

	// 待修改
	private ImageView iv_back;
	// 好友名字
	private TextView tv_friendName;
	// 匿名公开
	private ImageView tv_anonymity;
	// 切换聊天方式
	private ViewPager vp_msgType;
	// 好友头像
	private CircularImage iv_headPic;
	// 顶部小圆点
	private ImageView dot1, dot2, dot3;

	// 装载聊天方式
	private List<Fragment> list;
	// 当前页数
	private int currentIndex = 0;

	private SoundTouchClient soundTouchClient;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_rpl);
		initView();
		bindListener();
		initViewPager();
		dotView();
		setPageHeight();
	}

	@SuppressWarnings("deprecation")
	public void setPageHeight(){
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, width);
		vp_msgType.setLayoutParams(params);
	}
	
	public void initView() {
		vp_msgType = (ViewPager) findViewById(R.id.vp_msgType);
		iv_headPic = (CircularImage) findViewById(R.id.iv_headPic);
		iv_headPic.setImageResource(R.drawable.i7);
		iv_headPic.setOnTouchListener(this);
	}

	public void bindListener() {
		vp_msgType.setOnPageChangeListener(new PageListener());
	}

	public void initViewPager() {
		list = new ArrayList<Fragment>();
		for (int i = 0; i < 3; i++) {
			Fragment fragment = ReplyFrag.newInstance(i);
			list.add(fragment);
		}
		FragPagerAdapter adapter = new FragPagerAdapter(
				getSupportFragmentManager(), list);
		vp_msgType.setAdapter(adapter);
	}

	public void dotView() {
		dot1 = (ImageView) findViewById(R.id.dot1);
		dot2 = (ImageView) findViewById(R.id.dot2);
		dot3 = (ImageView) findViewById(R.id.dot3);
	}

	class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int page) {

			currentIndex = page;
			switch (page) {
			case 0:
				dot1.setImageResource(R.drawable.soild);
				dot2.setImageResource(R.drawable.hollow);
				dot3.setImageResource(R.drawable.hollow);
				break;
			case 1:
				dot1.setImageResource(R.drawable.hollow);
				dot2.setImageResource(R.drawable.soild);
				dot3.setImageResource(R.drawable.hollow);
				break;
			case 2:
				dot1.setImageResource(R.drawable.hollow);
				dot2.setImageResource(R.drawable.hollow);
				dot3.setImageResource(R.drawable.soild);
				break;
			}
		}

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (currentIndex == 0) {
				soundTouchClient = new SoundTouchClient(new Handler(), this);
				soundTouchClient.start();
			} else if (currentIndex == 1) {
				Intent intent = new Intent("android.intent.action.SendPic");
				LocalBroadcastManager.getInstance(ReplyActivity.this)
						.sendBroadcast(intent);
				Toast.makeText(ReplyActivity.this, "发照片", 1000).show();
			} else {
				Toast.makeText(ReplyActivity.this, "发信息", 1000).show();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (currentIndex == 0) {
				Intent intent = new Intent(
						"android.intent.action.CART_BROADCAST");
				LocalBroadcastManager.getInstance(ReplyActivity.this)
						.sendBroadcast(intent);
				soundTouchClient.stop();
			}
			break;
		}
		return false;
	}
}
