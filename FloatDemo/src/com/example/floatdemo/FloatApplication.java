package com.example.floatdemo;

import android.app.Application;
import android.view.WindowManager;  

public class FloatApplication extends Application {
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	public WindowManager.LayoutParams getWmParams(){
		return wmParams;
	}
	
}
 