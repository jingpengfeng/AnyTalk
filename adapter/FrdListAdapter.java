package com.anytalk.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;

/**
 * 
 * @author 方泽斯 好友列表适配器
 * 
 */
public class FrdListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mList;
	private ViewHolder holder = null;

	public FrdListAdapter(Context context, List<Map<String, Object>> list) {
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
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
		/*
		 * ViewHolder模式
		 */
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_frdlist, null);
			holder = new ViewHolder();
			holder.iv_headImg = (CircularImage) convertView
					.findViewById(R.id.headImg);
			holder.tv_frdTxt = (TextView) convertView
					.findViewById(R.id.frd_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.iv_headImg.setImageResource((Integer) mList.get(position).get(
				"head"));
		holder.tv_frdTxt.setText(mList.get(position).get("frdname").toString());
		return convertView;
	}

	/*
	 * ViewHolder用来初始化控件内部内容减少因为new而消耗内存
	 * 
	 */
	static class ViewHolder {
		CircularImage iv_headImg;
		TextView tv_frdTxt;
	}
}
