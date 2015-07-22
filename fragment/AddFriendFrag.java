package com.anytalk.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path.FillType;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anytalk.adapter.ContactAdapter;
import com.anytalk.utils.ListViewHeight;
import com.example.anytalk.R;

public class AddFriendFrag extends Fragment implements OnClickListener {
	
	
	private TextView tv_confirmBtn;
	private LinearLayout layout_addFriend;
	private LinearLayout layout_inviteFriend;
	
	
	private ListView lv_addContact, lv_frdRequest;
	private TextView requestTxt;
	private List<Map<String, Object>> list;
	private EditText edtfindUser = null;
	private String number, cName;
	private ContactAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_addfrd, null);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		initView();
		bindListener();
		
		list = new ArrayList<Map<String, Object>>();
		list = getContact(getActivity());
		adapter = new ContactAdapter(getActivity(), list);
		lv_addContact.setAdapter(adapter);
		ListViewHeight.setListViewHeight(lv_addContact);
		if (lv_frdRequest == null) {
			requestTxt.setVisibility(View.GONE);
		}

	}
	
	public void initView(){
		tv_confirmBtn = (TextView) getView().findViewById(R.id.tv_confirmBtn);
		layout_addFriend = (LinearLayout) getView().findViewById(R.id.layout_addFriend);
		layout_inviteFriend = (LinearLayout) getView().findViewById(R.id.layout_inviteFriend);
		
		lv_addContact = (ListView) getView().findViewById(R.id.lv_addContact);
		lv_frdRequest = (ListView) getView().findViewById(R.id.lv_frdRequest);
		requestTxt = (TextView) getView().findViewById(R.id.requestTxt);
		
	}
	
	public void bindListener(){
		tv_confirmBtn.setOnClickListener(this);
		layout_addFriend.setOnClickListener(this);
		layout_inviteFriend.setOnClickListener(this);
	}
	

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("recive");
			adapter = new ContactAdapter(getActivity(), list);
			lv_addContact.setAdapter(adapter);
			ListViewHeight.setListViewHeight(lv_addContact);
		}

	};

	public void getContacts() {
		ContentResolver cr = getActivity().getContentResolver();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, "sort_key");

		while (cursor.moveToNext()) {
			int nameFieldColumnIndex = cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String name = cursor.getString(nameFieldColumnIndex);
			System.out.println("name--->" + name);

			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);

			while (phone.moveToNext()) {
				String strPhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				// string += (":" + strPhoneNumber);
				System.out.println(name);
				System.out.println(strPhoneNumber);
				System.out.println("name--->" + name);
				cName = name;
				number = strPhoneNumber;

			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("invite", false);
			map.put("ContactName", name);
			map.put("ContactNum", number);

			tempList.add(map);
			list = tempList;
			phone.close();
			if (list.size() == 10) {
				handler.sendEmptyMessage(0);
			}
		}
		cursor.close();
		handler.sendEmptyMessage(0);
	}

	// 设置搜索好友对话框
	public void showDialog() {

		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("通过用户名查找好友");
		builder.setMessage("请输入用户名");
		edtfindUser = new EditText(getActivity());
		edtfindUser.setHint("请输入用户名");
		edtfindUser.setGravity(Gravity.CENTER);
		builder.setView(edtfindUser);
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("取消", null);
		// 创建显示对话框 （必须）
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {          
		case R.id.layout_addFriend:
			Dialog dialog=new Dialog(getActivity(), R.style.MyDialog);
			dialog.setContentView(R.layout.add_dialog);
			dialog.show();
			break;
		case R.id.tv_confirmBtn:
			getActivity().finish();
			break;
		}
	}

	private static String getSortKey(String sortKeyString) {
		String key = sortKeyString.substring(0, 1).toUpperCase();
		if (key.matches("[A-Z]")) {
			return key;
		}
		return "#";
	}

	Cursor c;

	public List<Map<String, Object>> getContact(Activity context) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor cursor = null;
		try {

			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			// 这里是获取联系人表的电话里的信息 包括：名字，名字拼音，联系人id,电话号码；
			// 然后在根据"sort-key"排序
			cursor = context.getContentResolver().query(
					uri,
					new String[] { "display_name", "sort_key", "contact_id",
							"data1" }, null, null, "sort_key");

			if (cursor.moveToFirst()) {
				do {
					String contact_phone = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					String name = cursor.getString(0);
					String sortKey = getSortKey(cursor.getString(1));
					int contact_id = cursor
							.getInt(cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("invite", false);
					map.put("ContactName", name);
					map.put("ContactNum", contact_phone);
					list.add(map);
				} while (cursor.moveToNext());
				c = cursor;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context = null;
		}
		return list;
	}

}
