package com.anytalk.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

public class GridViewHeight {
	public static int setHeight(GridView gridView){
		ListAdapter adapter = gridView.getAdapter();
		View mView = adapter.getView(0, null, gridView);  
        mView.measure(  
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),  
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));   
        int height = mView.getMeasuredHeight()*3;  
        ViewGroup.LayoutParams params = gridView.getLayoutParams();  
        params.height = height;  
        gridView.setLayoutParams(params);  
        gridView.requestLayout();      
        return height;
	}
}
