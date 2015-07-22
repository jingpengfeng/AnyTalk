package com.anytalk.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anytalk.utils.Px2dp;
import com.example.anytalk.R;

/**
 * 
 * @author	 黄松炎
 * 阅读信息适配器
 * 
 */
public class MsgAdapter extends BaseAdapter {
	private Context mContext;
	private List<Object> mList;
	private LayoutInflater mInflater;

	public MsgAdapter(Context context, List<Object> list) {
		/*
		 * 成员变量
		 * 
		 */
		this.mContext = context;
		this.mList = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.list_msg_item, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.msgimg);
		imageView.setBackgroundResource((Integer) mList.get(position));
		int i = 800 - Px2dp.dip2px(mContext, 50);
		System.out.println("px-->" + i);
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, i);
		convertView.setLayoutParams(lp);
		return convertView;
	}

}
