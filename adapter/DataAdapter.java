package com.anytalk.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anytalk.utils.ScreenHeight;
import com.example.anytalk.R;

/**
 * 
 * @author 黄松炎 修改 方泽斯 查看信息数据适配器
 * 
 */
public class DataAdapter extends BaseAdapter {
	private Context mContext;
	private List<Object> mList;
	private Activity mActivity;
	Boolean uppull = false;

	private int duration = 1000;
	private Animation slide_top_to_bottom, slide_bottom_to_top;

	public DataAdapter(Context context, List<Object> list, Activity activity) {
		/*
		 * 成员变量
		 */
		mContext = context;
		this.mList = list;
		this.mActivity = activity;
	}

	public void setUp() {
		uppull = true;
	}

	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mList != null && mList.size() > 0 ? mList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		slide_top_to_bottom = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_top_to_bottom);
		slide_bottom_to_top = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_bottom_to_top);
		convertView = View.inflate(mContext, R.layout.list_msg_item, null);

		Object object = mList.get(position);
		if (object instanceof String) {
			if (((String) object).contains(".amr")) {
				LinearLayout layout = (LinearLayout) convertView
						.findViewById(R.id.msgsound);
				layout.setVisibility(View.VISIBLE);
			} else {
				TextView textView = (TextView) convertView
						.findViewById(R.id.msgtext);
				textView.setText(object.toString());
				textView.setVisibility(View.VISIBLE);
			}

		} else if (object instanceof Integer) {
			ImageView msgpic = (ImageView) convertView
					.findViewById(R.id.msgimg);
			msgpic.setVisibility(View.VISIBLE);
			msgpic.setImageResource((Integer) object);
		}
		if (uppull) {
			slide_top_to_bottom.setDuration(duration);
			convertView.setAnimation(slide_bottom_to_top);
			uppull = false;
		}

		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				ScreenHeight.getHeight(mActivity));
		convertView.setLayoutParams(lp);
		return convertView;
	}

	static class ViewHolder {

	}
}