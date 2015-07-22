package com.anytalk.fragment;

import java.util.ArrayList;
import java.util.List;
import com.anytalk.activiy.ReplyActivity;
import com.anytalk.adapter.DataAdapter;

import com.anytalk.widget.PullToRefreshView;
import com.anytalk.widget.PullToRefreshView.OnFooterRefreshListener;
import com.anytalk.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.example.anytalk.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 黄松炎 修改 SharonMasack 查看信息界面
 * 
 */
public class ReadMsgFrag extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener {

	// 上拉下拉控件
	private PullToRefreshView mPullToRefreshView;
	// 数据源
	private List<Object> mList = new ArrayList<Object>();
	// 存放数据列表
	private ListView lv_msgList;
	// 下拉信息叠加数据
	private static int i = 0;
	// 数据适配器
	private DataAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_read_msg, null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mList.add(getString(R.string.txt_pull));
	}

	@Override
	public void onStart() {

		initView();

		bindListener();

		super.onStart();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			lv_msgList.setSelection(lv_msgList.getBottom());
		}

	};

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		i++;

		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mList.remove(0);
				if (i % 3 == 1) {
					mList.add(R.drawable.i8);
				} else if (i % 3 == 2) {
					mList.add(".amr");
				} else if (i % 3 == 0) {
					mList.add("你好a ");
				}
				mAdapter.setUp();
				mAdapter.notifyDataSetChanged();
				mPullToRefreshView.onFooterRefreshComplete();
				handler.sendEmptyMessage(0);
			}
		}, 1500);

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

		mPullToRefreshView.onHeaderRefreshComplete();
		Intent intent = new Intent();
		intent.setClass(getActivity(), ReplyActivity.class);
		startActivity(intent);

	}

	/*
	 * 初始化控件方法
	 */
	public void initView() {
		mPullToRefreshView = (PullToRefreshView) getView().findViewById(
				R.id.main_pull_refresh_view);
		lv_msgList = (ListView) getView().findViewById(R.id.listview);
		mAdapter = new DataAdapter(getActivity(), mList, getActivity());
	}

	/*
	 * 绑定监听
	 */
	public void bindListener() {
		lv_msgList.setAdapter(mAdapter);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
	}
}
