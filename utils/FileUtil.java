package com.anytalk.utils;

import java.io.File;

import android.os.Environment;

public class FileUtil {
	public static  String sdcardPath = Environment.getExternalStorageDirectory().toString();
	public static  String recordingPath = sdcardPath + "/wave_soundtouch/";
	public static  String picgPath = sdcardPath + "/pic/";
	public static void createFile(){
		File recordFile = new File(recordingPath);
		if(!recordFile.exists()){
			recordFile.mkdir();
		}
		File picFile = new File(picgPath);
		if(!picFile.exists()){
			recordFile.mkdir();
		}
	}
}
