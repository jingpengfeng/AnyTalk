package com.anytalk.adapter;

import java.util.List;
import java.util.Map;

import com.anytalk.widget.CircularImage;
import com.example.anytalk.R;

import android.content.Context;
import android.graphics.Color;
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
 * 联系人发送表格适配器
 * 
 */
public class GridViewAdapter extends BaseAdapter {
	private List<Map<String, Object>> mList;
	private LayoutInflater mInflater;
	private Context mContext;
	private boolean isColor = true;
	private int clickTemp = -1;

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	// int [] color
	// ={R.drawable.grid_bg_1,R.drawable.grid_bg_2,R.drawable.grid_bg_3,R.drawable.grid_bg_4,R.drawable.grid_bg_4,R.drawable.grid_bg_3,R.drawable.grid_bg_2,R.drawable.grid_bg_1,R.drawable.grid_bg_1,R.drawable.grid_bg_2,R.drawable.grid_bg_3};
	int[] color = { R.color.gv_item_1, R.color.gv_item_2 };

	public GridViewAdapter(Context context, List<Map<String, Object>> list) {

		if (list.size() % 4 != 0) {
			int a = 4 - list.size() % 4;
			for (int i = 0; i < a; i++) {
				list.add(null);
			}
		}
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList != null && mList.size() > 0 ? mList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// if(convertView == null){
		convertView = mInflater.inflate(R.layout.grid_item, null);
		System.out.println("position--->" + position);
		System.out.println("iscolor-->" + isColor);
		if (isColor) {
			if (position % 4 == 0) {
				convertView.setBackgroundResource(color[0]);
			} else {
				convertView.setBackgroundResource(color[1]);
				isColor = false;
			}
		} else {
			if (position % 4 == 0) {
				convertView.setBackgroundResource(color[1]);
			} else {
				convertView.setBackgroundResource(color[0]);
				isColor = true;
			}
		}
		if (mList.get(position) != null) {
			CircularImage imageView = (CircularImage) convertView
					.findViewById(R.id.contactPic);
			TextView tv = (TextView) convertView.findViewById(R.id.contactName);
			ImageView iv_picBg = (ImageView) convertView
					.findViewById(R.id.iv_picBg);
			imageView
					.setImageResource((Integer) mList.get(position).get("pic"));
			tv.setText(mList.get(position).get("name").toString());
			iv_picBg.setImageResource(R.drawable.me_head_bg);
		}
		return convertView;
	}

	static class ViewHolder {
		public static TextView textView;
		public static ImageView imageView;
	}

}
