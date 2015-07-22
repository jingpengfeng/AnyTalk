package com.anytalk.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.anytalk.activiy.SendMessageActivity;
import com.example.anytalk.R;

/**
 * 
 * @author 方泽斯 联系人适配器
 * 
 */
public class ContactAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mList;
	private boolean[] hasChecked;
	private boolean isHasChecked = false;
	private ViewHolder holder = null;
	private Intent intent;
	public ContactAdapter(Context context, List<Map<String, Object>> list) {

		/*
		 * 成员变量
		 */
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
		hasChecked = new boolean[getCount()];
		intent = new Intent("android.intent.action.showInsivit");
	}

	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position) != null && mList.size() > 0 ? mList
				.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**  
     *   
     * 
     */
	private void checkedChange(int checkedID) {
		hasChecked[checkedID] = !hasChecked[checkedID];
	}

	/**  
     * 
     *
     * 
     */
	public boolean hasChecked(int checkedID) {
		return hasChecked[checkedID];
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		/*
		 * ViewHolder模式
		 */
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_contact_item, null);
			holder = new ViewHolder();
			holder.cb_inviteBtn = (CheckBox) convertView
					.findViewById(R.id.inviteBtn);
			holder.tv_nameTxt = (TextView) convertView
					.findViewById(R.id.contactName);
			holder.tv_numTxt = (TextView) convertView
					.findViewById(R.id.contactNum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_nameTxt.setText(mList.get(position).get("ContactName")
				.toString());
		holder.tv_numTxt.setText(mList.get(position).get("ContactNum")
				.toString());
		holder.cb_inviteBtn
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						// TODO Auto-generated method stub
						checkedChange(position);
						int checkedCount = 0;
						for (int i = 0; i < mList.size(); i++) {
							if (hasChecked[i] == true) {
								checkedCount++;
								if(!isHasChecked){
									intent.putExtra("insivit", true);
									LocalBroadcastManager.getInstance(mContext)
											.sendBroadcast(intent);
									isHasChecked = true;
								}
								break;
							}
						}
						if(checkedCount == 0){
							intent.putExtra("insivit", false);
							LocalBroadcastManager.getInstance(mContext)
									.sendBroadcast(intent);
							isHasChecked = false;
						}
					}
				});

		return convertView;
	}

	/*
	 * ViewHolder用来初始化控件内部内容减少因为new而消耗内存
	 */
	public class ViewHolder {
		CheckBox cb_inviteBtn;
		TextView tv_nameTxt, tv_numTxt;
	}

}
