package com.anytalk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int VERSION=1;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		// TODO Auto-generated constructor stub
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context,String name){
		this(context, name,VERSION);
	}
	
	public DatabaseHelper(Context context,String name,int version){
		this(context, name, null, version);
	}
	
	//�������ݿ�ʱ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("�������ݿ�");
		db.execSQL("create table user(_id INTEGER PRIMARY KEY,name varchar(20),pic INTEGER,msgCount INTEGER )");
	}
	
	//�������ݿ�ʱ����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("�������ݿ�");
	}
	
}
