package com.anytalk.utils;

import com.example.anytalk.R;

import android.widget.ImageView;

public class SoundUtil {
	public static void soundNum(ImageView imageView,int value){
		if(value<7){
			imageView.setImageResource(R.drawable.record_animate_01);
		}else if(value>=7&&value<14){
			imageView.setImageResource(R.drawable.record_animate_02);
		}else if(value>=14&&value<21){
			imageView.setImageResource(R.drawable.record_animate_03);
		}else if(value>=21&&value<28){
			imageView.setImageResource(R.drawable.record_animate_04);
		}else if(value>=28&&value<35){
			imageView.setImageResource(R.drawable.record_animate_05);
		}else if(value>=35&&value<42){
			imageView.setImageResource(R.drawable.record_animate_06);
		}else if(value>=42&&value<49){
			imageView.setImageResource(R.drawable.record_animate_07);
		}else if(value>=49&&value<56){
			imageView.setImageResource(R.drawable.record_animate_08);
		}else if(value>=56&&value<63){
			imageView.setImageResource(R.drawable.record_animate_09);
		}else if(value>=63&&value<70){
			imageView.setImageResource(R.drawable.record_animate_10);
		}else if(value>=70&&value<77){
			imageView.setImageResource(R.drawable.record_animate_11);
		}else if(value>=77&&value<84){
			imageView.setImageResource(R.drawable.record_animate_12);
		}else if(value>=84&&value<91){
			imageView.setImageResource(R.drawable.record_animate_13);
		}else if(value>=91){
			imageView.setImageResource(R.drawable.record_animate_14);
		}
	}
}
