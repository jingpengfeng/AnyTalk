package com.anytalk.adapter;

import java.util.List;
import java.util.Map;

import com.anytalk.widget.BadgeView;
import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 黄松炎 
 * 修改 方泽斯
 * 信息箱适配器
 * 
 */

public class MsgListAdapter extends BaseAdapter {

	/*
	 * 成员变量
	 */
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mList;
	private ViewHolder holder = null;

	public MsgListAdapter(Context context,List<Map<String, Object>> list){
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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * ViewHolder模式
		 * 
		 */
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.tv_nameTxt = (TextView) convertView.findViewById(R.id.name);
			holder.iv_headIc = (CircularImage) convertView.findViewById(R.id.iv_type);
			holder.tv_msgTxt = (TextView) convertView.findViewById(R.id.msg);
			holder.badge = new BadgeView(mContext, holder.tv_msgTxt);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_nameTxt.setText(mList.get(position).get("userName").toString());
		holder.iv_headIc.setImageResource((Integer) mList.get(position).get(
				"userPic"));
//		holder.tv_msgTxt
//				.setText(mList.get(position).get("msgCount").toString());
		holder.badge.setText(mList.get(position).get("msgCount").toString());
		holder.badge.show();
		return convertView;
	}

	/*
	 * ViewHolder用来初始化控件内部内容减少因为new而消耗内存
	 * 
	 */
	static class ViewHolder {
		TextView tv_nameTxt;
		CircularImage iv_headIc;
		TextView tv_msgTxt;
		BadgeView badge;
	}

}
