package com.anytalk.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.ImageView;

public class CameraUtil {
	public static String strImgPath;
	public static void letCamera(Activity activity) {
		// TODO Auto-generated method stub
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		strImgPath = Environment.getExternalStorageDirectory().toString()
				+ "/dlion/";// �����Ƭ���ļ���
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()) + ".jpg";// ��Ƭ����
		File out = new File(strImgPath);
		if (!out.exists()) {
			out.mkdirs();
		}
		out = new File(strImgPath, fileName);
		strImgPath = strImgPath + fileName;// ����Ƭ�ľ���·��

		Uri uri = Uri.fromFile(out);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 2);
		activity.startActivityForResult(imageCaptureIntent, 1);
	}
	
	public static  Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}
