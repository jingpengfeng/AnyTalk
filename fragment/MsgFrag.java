package com.anytalk.fragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.anytalk.utils.CameraUtil;
import com.anytalk.utils.ScreenHeight;
import com.anytalk.utils.SoundUtil;
import com.example.anytalk.R;

public class MsgFrag extends Fragment implements OnClickListener, Callback {

	// 录音页面
	private ImageView iv_soundMsg;

	// 拍照页面
	private SurfaceView sv_picMsg;

	// 文字页面
	private EditText et_textMsg;

	// 用surfaceholder来装载相机
	private SurfaceHolder holder;

	// 相机
	private static Camera camera;
	// 相机参数
	private Parameters parameters;
	// 相机位置前置后置
	private int cameraPosition = Camera.CameraInfo.CAMERA_FACING_FRONT;

	// 当前页数
	private int currentIndex;

	// 广播管理
	private static LocalBroadcastManager broadcastManager;

	/*
	 * 单例
	 */
	public static MsgFrag newInstance(int index) {
		MsgFrag newFragment = new MsgFrag(index);

		return newFragment;

	}

	/*
	 * 构造方法
	 */
	public MsgFrag(int index) {
		currentIndex = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_msg, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initHolder();
		bindListener();
		receiveBroadCast();
		setMsgType(currentIndex);
	}

	public void initView() {
		iv_soundMsg = (ImageView) getView().findViewById(R.id.iv_soundMsg);
		sv_picMsg = (SurfaceView) getView().findViewById(R.id.sv_picMsg);
		et_textMsg = (EditText) getView().findViewById(R.id.et_textMsg);

		// 这两个功能待查
		sv_picMsg.setFocusable(true);
		sv_picMsg
				.setBackgroundColor(ComponentCallbacks2.TRIM_MEMORY_BACKGROUND);

	}

	public void initHolder() {
		holder = sv_picMsg.getHolder();
		// 设置缓存，待查
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 功能不清，待查
		holder.setKeepScreenOn(true);
		// 添加回调
		holder.addCallback(this);
	}

	public void bindListener() {
		sv_picMsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sv_picMsg:
			onfocus();
			break;
		}
	}

	/*
	 * 切换聊天方式
	 */
	public void setMsgType(int index) {
		switch (index) {
		case 0:
			iv_soundMsg.setVisibility(View.VISIBLE);
			sv_picMsg.setVisibility(View.GONE);
			et_textMsg.setVisibility(View.GONE);
			break;
		case 1:
			iv_soundMsg.setVisibility(View.GONE);
			sv_picMsg.setVisibility(View.VISIBLE);
			et_textMsg.setVisibility(View.GONE);
			break;
		case 2:
			iv_soundMsg.setVisibility(View.GONE);
			sv_picMsg.setVisibility(View.GONE);
			et_textMsg.setVisibility(View.VISIBLE);
			break;
		}
	}

	/*
	 * 广播接收
	 */
	public void receiveBroadCast() {
		if (broadcastManager == null) {

			broadcastManager = LocalBroadcastManager.getInstance(getActivity());
			IntentFilter filter = new IntentFilter();
			// 声音过滤
			filter.addAction("android.intent.action.Sound");
			// 图片
			filter.addAction("android.intent.action.Pic");
			// 文本
			filter.addAction("android.intent.action.Text");
			//抬起
			filter.addAction("android.intent.action.Up");
			// 注册广播

			BroadcastReceiver receiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					if (action.equals("android.intent.action.Sound")) {
						SoundUtil.soundNum(iv_soundMsg, intent.getIntExtra("sound", 0));
					} else if (action.equals("android.intent.action.Pic")) {
						System.out.println("pic");
						takePicture();
					} else if (action.equals("android.intent.action.Text")) {
						System.out.println("text");
					}else if(action.equals("android.intent.action.Up")){
						iv_soundMsg.setImageResource(R.drawable.record_animate_01);
					}
				}
			};
			broadcastManager.registerReceiver(receiver, filter);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		OpenCamera();
	}

	/*
	 * surface销毁回调，关闭相机
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}

	}

	/*
	 * 控制图像的正确显示方向
	 */
	private void setDispaly(Camera.Parameters parameters, Camera camera) {
		if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
			setDisplayOrientation(camera, 90);
		} else {
			parameters.setRotation(90);
		}

	}

	/*
	 * 实现的图像的正确显示
	 */
	private void setDisplayOrientation(Camera camera, int i) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod(
					"setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null) {
				downPolymorphic.invoke(camera, new Object[] { i });
			}
		} catch (Exception e) {
		}
	}

	/*
	 * 暂停预览
	 */
	@Override
	public void onPause() {
		if (camera != null) {
			camera.stopPreview();
		}
		super.onPause();
	}

	/*
	 * 回复预览
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (camera != null) {
			System.out.println("resumpic");
			camera.startPreview();
		}
	}

	// 相机参数的初始化设置
	private void initCamera() {
		parameters = camera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		setDispaly(parameters, camera);
		camera.setParameters(parameters);
		camera.startPreview();
	}

	/*
	 * 对焦
	 */
	public void onfocus() {
		camera.autoFocus(new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean arg0, Camera arg1) {

			}
		});
	}

	public void takePicture() {
		onfocus();
		camera.takePicture(null, rawCallback, jpegCallback);
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Toast.makeText(getActivity(), "no sdcard!", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			FileOutputStream outStream = null;
			try {
				// Write to SD Card
				String fileName = String.format(Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ "/1234.jpg");
				outStream = new FileOutputStream(fileName);
				outStream.write(data);
				outStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// camera.stopPreview();
				camera.startPreview();
			}
		}
	};

	public void OpenCamera() {
		CameraInfo cameraInfo = new CameraInfo();
		int cameraCount = Camera.getNumberOfCameras();
		for (int i = 0; i < cameraCount; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			releaseCamera();
			if (cameraPosition == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
			} else {
				cameraPosition = Camera.CameraInfo.CAMERA_FACING_FRONT;
			}
			try {
				camera = Camera.open(cameraPosition);
				camera.setPreviewDisplay(holder);
			} catch (Exception e) {

			}
			try {
				initCamera();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/*
	 * 释放相机
	 */
	public void releaseCamera() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

}
