package com.anytalk.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/*
 * 头像工具类
 */
public class UserPic {

	/*
	 * 拍照
	 */
	public static void takePicture(Activity activity, Uri uri) {
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		activity.startActivityForResult(imageCaptureIntent, 1);
	}

	/*
	 * 从手机相册选择
	 */
	public static  void fromPicList(Activity activity, Uri uri) {
		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
				"image/*");
		intent.putExtra("output", uri);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 180);// 输出图片大小
		intent.putExtra("outputY", 180);
		activity.startActivityForResult(intent, 2);
	}

	/*
	 * 截图方法
	 */
	public static void cropPicture(Activity activity, Uri uri) {
		Intent intent1 = new Intent("com.android.camera.action.CROP");
		intent1.setDataAndType(uri, "image/*");
		intent1.putExtra("crop", "true");
		intent1.putExtra("aspectX", 1);// 裁剪框比例
		intent1.putExtra("aspectY", 1);
		intent1.putExtra("outputX", 180);// 输出图片大小
		intent1.putExtra("outputY", 180);
		intent1.putExtra("return-data", true);
		activity.startActivityForResult(intent1, 3);
	}
}
