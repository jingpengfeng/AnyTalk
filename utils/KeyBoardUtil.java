package com.anytalk.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtil {
	
	public static void keyUp(final EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		Timer timer = new Timer();
 	     timer.schedule(new TimerTask()
 	     {
 	         public void run() 
         {
             InputMethodManager inputManager =(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
             inputManager.showSoftInput(editText, 0);
         }
     }, 998);
	}
}
