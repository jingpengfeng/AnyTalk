package com.anytalk.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class SqliteDatabase {
	private Context context;
	private DatabaseHelper dbHelper;

	public SqliteDatabase(Context context) {
		this.context = context;
		CreateDatabaseHelper();
	}

	public void CreateDatabaseHelper() {
		dbHelper = new DatabaseHelper(context, "contacts.db");
	}

	public void Insert(int pic, String name, int i) {
		ContentValues values = new ContentValues();
		values.put("pic", pic);
		values.put("name", name);
		values.put("msgCount", i);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.insert("user", null, values);
	}

	/*
	 * //===========================��ѯ�������===================================
	 * public List<Map<String, Object>> Query(String name){ List<Map<String,
	 * Object>> list = new ArrayList<Map<String, Object>>(); DatabaseHelper
	 * dbHelper = new DatabaseHelper(context,"test_mars_db"); SQLiteDatabase db
	 * = dbHelper.getReadableDatabase(); Cursor cursor = db.query("user", new
	 * String[]{"name","phone","qq","mail","address"}, "name=?", new
	 * String[]{name}, null, null, null); while(cursor.moveToNext()){ String
	 * name1 = cursor.getString(cursor.getColumnIndex("name")); String name2 =
	 * cursor.getString(cursor.getColumnIndex("phone")); String name3 =
	 * cursor.getString(cursor.getColumnIndex("qq")); String name4 =
	 * cursor.getString(cursor.getColumnIndex("mail")); String name5 =
	 * cursor.getString(cursor.getColumnIndex("address")); while
	 * (cursor.moveToNext()) { Map<String, Object> map = new HashMap<String,
	 * Object>(); map.put("name",
	 * cursor.getString(cursor.getColumnIndex("name"))); map.put("phone",
	 * cursor.getString(cursor.getColumnIndex("phone"))); map.put("qq",
	 * cursor.getString(cursor.getColumnIndex("qq"))); map.put("mail",
	 * cursor.getString(cursor.getColumnIndex("mail")));
	 * map.put("address",cursor.getString(cursor.getColumnIndex("address")));
	 * list.add(map); } return list; System.out.println("query--->" + name1);
	 * System.out.println("query--->" + name2); System.out.println("query--->" +
	 * name3); System.out.println("query--->" + name4);
	 * System.out.println("query--->" + name5);
	 * System.out.println("======================================================"
	 * ); } }
	 */

	public List<Map<String, Object>> Query() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, null, null, null, null,
				"_id desc");
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", cursor.getString(cursor.getColumnIndex("pic")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("msgCount",
					cursor.getString(cursor.getColumnIndex("msgCount")));
			list.add(map);
		}
		return list;
	}

	// ģ���ѯ
	public List<Map<String, Object>> fuzzyQuery(String str) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String condition = null;
		String[] args = null;
		if (str != null && !"".equals(str.trim())) {
			// ���֡��������
			condition = "name" + " like ? ";
			args = new String[] { "%" + str + "%" };
		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, condition, args, null, null,
				"_id desc");
		while (cursor.moveToNext()) {
			// values.put("pic", pic);
			// values.put("name", name);
			// values.put("msgCount", msgCount);
			Map<String, Object> map = new HashMap<String, Object>();
			int pic = cursor.getInt(cursor.getColumnIndex("pic"));
			System.out.println("pic---" + pic);
			map.put("pic", pic);
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("msgCount",
					cursor.getString(cursor.getColumnIndex("msgCount")));
			list.add(map);
		}
		return list;
	}

	public boolean Query(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("user", null, "name=?", new String[] { name },
				null, null, null);
		if (cursor.moveToNext()) {
			System.out.println("true");
			return true;
		}
		return false;
	}

	// ===========================���£��޸ģ����===================================
	public ArrayList modifiy(int pic, String name, String msgCount) {
		ContentValues values = new ContentValues();
		values.put("pic", pic);
		values.put("name", name);
		values.put("msgCount", msgCount);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// System.out.println("************�޸���ϵ��֮ǰ  : " +
		// Address_Book.LIST.get(Address_Book.CLICK_ID).toString());
		db.update("user", values, "name = ?", new String[] { name });

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Cursor cursor = db.query("user", new String[] { "name", "msgCount" },
				null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("pic", pic);
			map.put("name", name);
			map.put("msgCount", msgCount);
		}
		return list; // ����ȫ�����list�����б���ʾ
	}

	public void deleteItem(String name) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete("user", "name=?", new String[] { name });
	}

	public void deleteItem() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete("user", null, null);
	}

}
