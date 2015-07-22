package com.anytalk.listener;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anytalk.utils.WidgetUtil;
import com.example.anytalk.R;

public class SurfacOnTochListener implements OnTouchListener{
	private Activity activity;
	private RelativeLayout layout;
	public SurfacOnTochListener(Activity activity,RelativeLayout layout){
		this.activity = activity;
		this.layout = layout;
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();
			System.out.println("x-->"+x);
			System.out.println("y-->"+y);
			ImageView imageView = new ImageView(activity);
			//imageView.setImageResource(R.drawable.i1);
		//	layout.addView(imageView);
			//WidgetUtil.setLayout(imageView, x, y);
			
			
//			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//			params2.setMargins(lastX, lastY, 0, 0);
//			imageView.scrollTo(-20, 20);
//			layout.addView(imageView,params2);
			
//			Animation scaleAnimation = new ScaleAnimation(0.1f, 1.0f,0.1f,1.0f);
//			//初始化 Alpha动画  
//			Animation  alphaAnimation = new AlphaAnimation(1f, 0.0f);  
//			  
//			//动画集  
//			AnimationSet set = new AnimationSet(true);  
//			set.addAnimation(scaleAnimation);  
//			set.addAnimation(alphaAnimation);  
//			set.setFillAfter(true);
//			//设置动画时间 (作用到每个动画) 
//			set.setDuration(1000); 
//			//layout.removeView(imageView);
//			
//			imageView.startAnimation(set);  
			break;
		}
		return false;
	}
}
