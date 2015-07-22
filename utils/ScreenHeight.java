package com.anytalk.utils;

import com.example.anytalk.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

public class ScreenHeight {
	public static int rvHeight;
	public static int statusBarHeight;
	public static int getHeight(Activity activity){
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//灞忓箷瀹藉害
		int height = wm.getDefaultDisplay().getHeight();//灞忓箷楂樺害
		Rect rect= new Rect();  
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
		int statusBarHeight = rect.top; //鐘舵�佹爮楂樺害	
		return height-statusBarHeight-rvHeight;
	}
	public static void getstatusBarHeight(Activity activity){
		Rect rect= new Rect();  
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
		statusBarHeight = rect.top;
	}
	
	public static int getScreenWidth(Activity activity){
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//灞忓箷瀹藉害
		return width;
	}
	
	
	public static int getPageHeight(Activity activity){
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//灞忓箷瀹藉害
		int height = wm.getDefaultDisplay().getHeight();//灞忓箷楂樺害
		Rect rect= new Rect();  
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
		int statusBarHeight = rect.top; //鐘舵�佹爮楂樺害	
		return height-statusBarHeight;
	}
}
