package com.anytalk.activiy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.anytalk.adapter.MsgListAdapter;
import com.anytalk.utils.ActionSheet;
import com.anytalk.utils.ActionSheet.ActionSheetListener;
import com.example.anytalk.R;


/**
 * 
 * @author 方泽斯
 * 修改  黄松炎
 * 信息箱
 */

public class MsgBoxActivity extends FragmentActivity implements OnClickListener,
		ActionSheetListener, OnItemClickListener {
	
	//顶部菜单按钮
	private ImageView iv_boxBack,iv_filter;
	
	//搜索联系人短信
	private EditText et_searchMsg;
	
	//信息列表
	private ListView lv_msg;
	//ListView数据
	private List<Map<String, Object>> list_msg;
	//信息列表适配器
	private MsgListAdapter adapter_msgList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_msgbox);
		
		initView();
		
		bindListener();
		
		listDate();
		
	}
	
	/*
	 * 初始化view
	 */
	public void initView(){
		iv_boxBack = (ImageView) findViewById(R.id.iv_boxBack);
		iv_filter = (ImageView) findViewById(R.id.iv_filter);
		
		et_searchMsg = (EditText) findViewById(R.id.et_searchMsg);
		
		lv_msg = (ListView) findViewById(R.id.lv_msg);
	}
	
	/*
	 * 绑定监听
	 */
	public void bindListener(){
		iv_boxBack.setOnClickListener(this);
		iv_filter.setOnClickListener(this);
		
		et_searchMsg.addTextChangedListener(new MyWatcher());
		
		lv_msg.setOnItemClickListener(this);
	}
	
	/*
	 * listview数据源,数据待修改
	 */
	public void listDate(){
		int img[] = { R.drawable.i1, R.drawable.i2, R.drawable.i3,
				R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7,
				R.drawable.i8, R.drawable.i9, R.drawable.i10, R.drawable.i11 };
		list_msg = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < 11; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userPic", img[i]);
			map.put("userName", "路人"+i);
			map.put("msgCount", i);
			list_msg.add(map);
		}
		
		adapter_msgList = new MsgListAdapter(MsgBoxActivity.this,list_msg);
		lv_msg.setAdapter(adapter_msgList);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_boxBack:
			finish();
			break;
		case R.id.iv_filter:
			showMsgFilter();
		}
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent();
		intent.setClass(MsgBoxActivity.this, ReadMsgActivity.class);
		startActivity(intent);
	}
	private class MyWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
//			list = db.fuzzyQuery(s.toString());
//			ContactsAdapter adapter = new ContactsAdapter(MsgBoxActivity.this, list);
//			msgContacts.setAdapter(adapter);
		}

		@Override
		public void afterTextChanged(Editable s) {

		}

	}
	
	
	
	/*
	 * 显示信息筛选菜单
	 */
	public void showMsgFilter() {
		setTheme(R.style.ActionSheetStyleIOS7);
		ActionSheet.createBuilder(this, getSupportFragmentManager())
				.setCancelButtonTitle("取消")
				.setOtherButtonTitles("显示所有信息", "显示实名信息", "显示匿名信息")
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}
	
	/*
	 * 菜单消失回调
	 */
	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

	}

	/*
	 * 菜单点击回调
	 */
	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		switch (index) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}
}
