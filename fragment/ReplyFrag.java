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
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anytalk.listener.SurfacOnTochListener;
import com.anytalk.utils.CameraUtil;
import com.anytalk.utils.ScreenHeight;
import com.anytalk.utils.SoundUtil;
import com.anytalk.widget.LazyFragment;
import com.example.anytalk.R;

/**
 * 
 * @author 黄松炎
 * @edit ShaornMasack
 *         回复fragment
 */
public class ReplyFrag extends LazyFragment implements OnClickListener,
		SurfaceHolder.Callback, OnLongClickListener {

	// 名字待定
	private ImageView iv_back;

	// 好友名字
	private TextView tv_friendName;

	// 匿名
	private ImageView iv_anonymity;

	// 回复音频信息
	private ImageView iv_soundMsg;

	// 回复图片信息
	private RelativeLayout rl_picMsg;

	// 回复纯文本信息
	private EditText ed_textMsg;

	// 当前页码
	private int page;

	// 是否慢加载参数
	private boolean isPrepared;

	// 回复界面控件
	private SurfaceView sv_replyView;

	// 显示回复界面控件的抽象接口
	private SurfaceHolder msurfaceHolder;

	// 相机
	private static Camera mCamera;

	// 相机参数
	private Camera.Parameters mParameters;

	// 长按图片添加文字
	private EditText ed_textMsgonImg;

	/*
	 * 单例
	 */
	public static ReplyFrag newInstance(int page) {
		ReplyFrag newFragment = new ReplyFrag(page);

		return newFragment;

	}

	/*
	 * 构造方法
	 */
	public ReplyFrag(int page) {
		this.page = page;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		isPrepared = true;
		return inflater.inflate(R.layout.frag_reply, null);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		lazyLoad();
	}

	/*
	 * 数据慢（懒）加载方法 
	 * 可以阻止进入Fragment时不预先加载其他界面的生命周期 
	 * 加快界面初始化速度 (non-Javadoc)
	 * 
	 * @see com.anytalk.widget.LazyFragment#lazyLoad()
	 */
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		if (!isPrepared || !isVisible) {
			return;
		}

		// 相机
		sv_replyView = (SurfaceView) getView().findViewById(R.id.relpyView);
		msurfaceHolder = sv_replyView.getHolder();
		msurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		msurfaceHolder.setKeepScreenOn(true);
		sv_replyView.setFocusable(true);
		sv_replyView
				.setBackgroundColor(ComponentCallbacks2.TRIM_MEMORY_BACKGROUND);
		sv_replyView.setOnClickListener(this);
		sv_replyView.setOnLongClickListener(this);

		msurfaceHolder.addCallback(this);
		ed_textMsgonImg = (EditText) getView().findViewById(R.id.msg);

		rl_picMsg = (RelativeLayout) getView().findViewById(R.id.picMsg);
		// replyView.setOnTouchListener(new
		// SurfacOnTochListener(getActivity(),picMsg));

		System.out.println("page--->" + page);
		iv_soundMsg = (ImageView) getView().findViewById(R.id.soundMsg);

		ed_textMsg = (EditText) getView().findViewById(R.id.textMsg);
		iv_soundMsg.setOnClickListener(this);
		// picMsg.setOnClickListener(this);

		switch (page) {
		case 0:
			iv_soundMsg.setVisibility(View.VISIBLE);
			rl_picMsg.setVisibility(View.GONE);
			ed_textMsg.setVisibility(View.GONE);
			break;
		case 1:
			iv_soundMsg.setVisibility(View.GONE);
			rl_picMsg.setVisibility(View.VISIBLE);
			ed_textMsg.setVisibility(View.GONE);
			break;
		case 2:
			iv_soundMsg.setVisibility(View.GONE);
			rl_picMsg.setVisibility(View.GONE);
			ed_textMsg.setVisibility(View.VISIBLE);
			break;
		}
		isPrepared = false;

	}

	/*
	 * 按钮监听(non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.soundMsg:

			break;
		case R.id.picMsg:
			// CameraUtil.letCamera(getActivity());
			break;
		case R.id.relpyView:
			onfocus();
			break;
		default:
			break;
		}
	}

	/*
	 * 页面切换方法(non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	/*
	 * 绘图界面surface(non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if (null == mCamera) {
			mCamera = Camera.open();
		}
		try {
			mCamera.setPreviewDisplay(msurfaceHolder);
			initCamera();
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 销毁surface(non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
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
			Log.e("Came_e", "图像出错");
		}
	}

	/*
	 * 相机参数的初始化设置
	 */
	private void initCamera() {
		mParameters = mCamera.getParameters();
		mParameters.setPictureFormat(PixelFormat.JPEG);
		// parameters.setPictureSize(surfaceView.getWidth(),
		// surfaceView.getHeight()); // 部分定制手机，无法正常识别该方法。
		// parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		int width = ScreenHeight.getScreenWidth(getActivity());
		// parameters.setPictureSize(1280, 960);
		List<Size> pictureSizes = mParameters.getSupportedPictureSizes();
		List<Size> previewSizes = mParameters.getSupportedPreviewSizes();
		;

		for (int i = 0; i < pictureSizes.size(); i++) {
			Size psize = pictureSizes.get(i);

		}
		Size tempSzie = previewSizes.get(0);
		for (int i = 0; i < previewSizes.size(); i++) {
			Size psize = previewSizes.get(i);
			System.out.println("width-->" + psize.width + "height-->"
					+ psize.height);
			System.out.println("asdasd--->" + psize.width
					/ (float) psize.height);
			if (psize.height / (float) psize.width == 0.75) {

				if (psize.height > tempSzie.height) {
					tempSzie = psize;
				}
			}
		}
		System.out.println("height--->" + tempSzie.height);
		System.out.println("width--->" + tempSzie.width);
		// parameters.setPreviewSize(tempSzie.width, tempSzie.height);
		setDispaly(mParameters, mCamera);
		mCamera.setParameters(mParameters);
		mCamera.startPreview();
	}

	/*
	 * 相机自动对焦方法
	 */
	public void onfocus() {
		mCamera.autoFocus(new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean arg0, Camera arg1) {
				// camera.takePicture(shutterCallback, rawCallback,
				// jpegCallback);
			}
		});
	}

	/*
	 * 相机拍照方法
	 */
	public void takePicture() {
		if (mCamera != null) {
			onfocus();
			mCamera.takePicture(null, rawCallback, jpegCallback);
			mCamera.startPreview();
		} else {
			System.out.println("null-----");
		}
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			// Log.d(TAG, "onShutter'd");
		}
	};
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// Log.d(TAG, "onPictureTaken - raw");
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
			}
		}
	};

	/*
	 * 实现长按加入文字(non-Javadoc)
	 * 
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		ed_textMsgonImg.setVisibility(View.VISIBLE);
		System.out.println("long");
		return false;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// lazyLoad();
		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.SendPic");
		BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				System.out.println("recice");
			}
		};
		broadcastManager.registerReceiver(mItemViewListClickReceiver,
				intentFilter);
	}

}
