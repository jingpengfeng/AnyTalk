package com.anytalk.activiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.anytalk.fragment.AddFriendFrag;
import com.anytalk.fragment.FriendListFrag;
import com.anytalk.utils.SoundUtil;
import com.example.anytalk.R;

/**
 * 
 * @author 黄松炎
 * 
 * 添加好友界面
 */

public class AddContactActivity extends FragmentActivity implements
		OnClickListener {
	/*
	 * 底部菜单按钮
	 */
	private ImageButton ibtn_addFriend;
	private ImageButton ibtn_friendList;
	
	private LinearLayout layout_invisit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_addcontact);
		
		initView();
		bindListener();
		receiveBroadCast();
		//用fragment事务替换布局
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_up, new AddFriendFrag()).commit();

	}

	/*
	 * 初始化view
	 */
	public void initView() {
		ibtn_addFriend = (ImageButton) findViewById(R.id.ibtn_addFriend);
		ibtn_friendList = (ImageButton) findViewById(R.id.ibtn_friendList);
		
		layout_invisit = (LinearLayout) findViewById(R.id.layout_invisit);
	}

	/*
	 * 绑定监听
	 */
	public void bindListener() {
		ibtn_addFriend.setOnClickListener(this);
		ibtn_friendList.setOnClickListener(this);
	}

	/*
	 * 事件监听
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ibtn_addFriend:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_up, new AddFriendFrag()).commit();

			break;
		case R.id.ibtn_friendList:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_up, new FriendListFrag()).commit();
			break;
		}
	}
	
	/*
	 * 广播接收
	 */
	public void receiveBroadCast() {
		final Animation animation=AnimationUtils.loadAnimation(this, R.anim.toumingdu);
        final Animation animation2=AnimationUtils.loadAnimation(this, R.anim.toumingdu1);
        animation2.setFillAfter(true);
        animation2.setFillBefore(false);
		// 邀请过滤
		IntentFilter insivitFilter = new IntentFilter();
		insivitFilter.addAction("android.intent.action.showInsivit");
		// 广播接收
		BroadcastReceiver soundReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(intent.getBooleanExtra("insivit", false)){
					layout_invisit.setVisibility(View.VISIBLE);
					layout_invisit.startAnimation(animation);
				}else{
					layout_invisit.startAnimation(animation2);
					layout_invisit.setVisibility(View.GONE);
				}
			}

		};
		/*
		 * 注册广播
		 */
		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(AddContactActivity.this);
		broadcastManager.registerReceiver(soundReceiver, insivitFilter);
	}

}
