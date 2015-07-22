package com.anytalk.widget;

import java.io.IOException;
import java.lang.reflect.Method;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Cameraview extends SurfaceView implements Callback{

	private SurfaceHolder holder;
	private Camera camera;
	private Camera.Parameters parameters; 
	public Cameraview(Context context) {
		super(context);
		holder = getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.setKeepScreenOn(true);  
		setFocusable(true);  
		setBackgroundColor(ComponentCallbacks2.TRIM_MEMORY_BACKGROUND);
		holder.addCallback(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		 camera.autoFocus(new AutoFocusCallback() {  
             @Override  
             public void onAutoFocus(boolean success, Camera camera) {  
                 if(success){  
                     initCamera();//实现相机的参数初始化  
                    camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。  
                 }  
             }  

         }); 
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		if(null==camera){ 
			camera=Camera.open();
		}
		try {  
            camera.setPreviewDisplay(holder);  
            initCamera();  
            camera.startPreview();  
      } catch (IOException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
      }  
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		 camera.stopPreview();  
         camera.release();  
         camera=null;  
	}
	
    //相机参数的初始化设置  
    private void initCamera()  
      {  
          parameters=camera.getParameters();  
          parameters.setPictureFormat(PixelFormat.JPEG);  

         // parameters.setPictureSize(surfaceView.getWidth(), surfaceView.getHeight());  // 部分定制手机，无法正常识别该方法。  
        //  parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);     
          parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦  
          parameters.setJpegQuality(100);
          setDispaly(parameters,camera);  
          camera.setParameters(parameters);  
          camera.startPreview();  
          camera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上  
            
      } 
    
    
    //控制图像的正确显示方向  
    private void setDispaly(Camera.Parameters parameters,Camera camera)  
    {  
        if (Integer.parseInt(Build.VERSION.SDK) >= 8){  
              setDisplayOrientation(camera,90);  
          }  
      else{  
              parameters.setRotation(90);  
          }  
          
    }       
    
    //实现的图像的正确显示  
    private void setDisplayOrientation(Camera camera, int i) {  
        Method downPolymorphic;  
       try{  
              downPolymorphic=camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});  
              if(downPolymorphic!=null) {  
                  downPolymorphic.invoke(camera, new Object[]{i});  
              }  
          }  
          catch(Exception e){  
              Log.e("Came_e", "图像出错");  
          }  
    }  
    
    public interface ShowCamera{
    	public void showCamera(int page);
    }

}
