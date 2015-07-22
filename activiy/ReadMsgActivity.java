package com.anytalk.activiy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anytalk.fragment.ReadMsgFrag;
import com.anytalk.utils.ScreenHeight;
import com.example.anytalk.R;


/**
 * 
 * @author 黄松炎
 * 读信息
 */
public class ReadMsgActivity extends FragmentActivity {
	
	//返回信息箱
	private ImageView iv_backToBox;
	//信息数量
	private ImageView iv_msgCount;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			execute();
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.act_readmsg);
		initView();
		
		final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative);
		ViewTreeObserver vto2 = relativeLayout.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				relativeLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				ScreenHeight.rvHeight = relativeLayout.getHeight();
				System.out.println("rv--->" + ScreenHeight.rvHeight);
				handler.sendEmptyMessage(0);
			}
		});
	}
	
	public void initView(){
		iv_backToBox = (ImageView) findViewById(R.id.iv_backToBox);
		iv_msgCount = (ImageView) findViewById(R.id.iv_msgCount);
	}

	public void execute() {

		getSupportFragmentManager().beginTransaction()
				.add(R.id.msgFram, new ReadMsgFrag()).commit();

	}

}
