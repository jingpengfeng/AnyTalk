package com.anytalk.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.anytalk.adapter.FrdListAdapter;
import com.anytalk.utils.ActionSheet;
import com.anytalk.utils.ActionSheet.ActionSheetListener;
import com.example.anytalk.R;

/**
 * 
 * @author SharonMasack 
 * 好友列表页面
 * 
 */
public class FriendListFrag extends Fragment implements OnClickListener,
		ActionSheetListener, OnItemClickListener {
	//数据源
	private List<Map<String, Object>> mList;
	//存放好友数据列表
	private ListView mFrdList;
	//确定按钮
	private TextView tv_confirmBtn;
	//备注好友输入框
	private EditText et_edtUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_frdlist, null);
	}

	@Override
	public void onStart() {
		super.onStart();

		initView();

		listDate();
	}

	/*
	 * 自定义菜单项
	 */
	public void showActionSheet() {
		ActionSheet
				.createBuilder(getActivity(), getChildFragmentManager())
				.setCancelButtonTitle(getString(R.string.txt_cancel))
				.setOtherButtonTitles(getString(R.string.btn_note),
						getString(R.string.btn_sheild),
						getString(R.string.btn_delete))
				.setCancelableOnTouchOutside(true).setListener(this).show();
	}

	/*
	 * 备注好友对话框
	 */
	public void showDialog() {

		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(getString(R.string.btn_note));
		builder.setMessage(getString(R.string.txt_notein));
		et_edtUser = new EditText(getActivity());
		et_edtUser.setGravity(Gravity.CENTER);
		builder.setView(et_edtUser);
		builder.setPositiveButton(getString(R.string.comfirm),
				new OnNameChangeListener());
		builder.setNegativeButton(getString(R.string.txt_cancel), null);
		builder.create().show();
	}

	/*
	 * 备注好友按钮监听
	 */
	public class OnNameChangeListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		}

	}

	/*
	 * 按钮监听 (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frdlist_confirmBtn:
			getActivity().finish();
			break;
		}
	}

	/*
	 * 自定义菜单按钮监听 (non-Javadoc)
	 * 
	 * @see
	 * com.anytalk.utils.ActionSheet.ActionSheetListener#onDismiss(com.anytalk
	 * .utils.ActionSheet, boolean)
	 */
	@Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
	}

	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		switch (index) {
		case 0:
			showDialog();
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}

	/*
	 * 初始化控件方法
	 */
	public void initView() {
		mFrdList = (ListView) getView().findViewById(R.id.frdList);
		tv_confirmBtn = (TextView) getView().findViewById(
				R.id.frdlist_confirmBtn);
		tv_confirmBtn.setOnClickListener(this);
	}

	/*
	 * 初始化数据源列表
	 */
	public void listDate() {
		mList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("head", R.drawable.i1);
		map.put("frdname", 213);
		mList.add(map);
		mFrdList.setAdapter(new FrdListAdapter(getActivity(), mList));
		mFrdList.setOnItemClickListener(this);
	}

	/*
	 * 数据列表监听 (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		getActivity().setTheme(R.style.ActionSheetStyleIOS7);
		showActionSheet();
	}
}
