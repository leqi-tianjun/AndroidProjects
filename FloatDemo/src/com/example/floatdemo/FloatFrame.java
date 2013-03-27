package com.example.floatdemo;

import java.util.ArrayList;  
import java.util.List;  
  
import android.app.ActivityManager;  
import android.app.ActivityManager.RunningTaskInfo;  
import android.content.ComponentName;  
import android.content.Context;  
import android.content.Intent;  
import android.content.pm.PackageManager;  
import android.content.pm.ResolveInfo;  
import android.os.Handler;  
import android.os.Message;  
import android.text.TextUtils;  
import android.util.Log;  
import android.view.GestureDetector;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.WindowManager;  
import android.widget.Button;  
import android.widget.LinearLayout;  

public class FloatFrame extends LinearLayout {

	    private float mTouchRawX;  
	    private float mTouchRawY;  
	  
	    private WindowManager wm = (WindowManager) getContext()  
	            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);  
	  
	    // 此wmParams为获取的全局变量，用以保存悬浮窗口的属性  
	    private WindowManager.LayoutParams wmParams = ((FloatApplication) getContext().getApplicationContext()).getWmParams();  
	    private Context mContext;  
	    View viewExtFrame;  
	    Button btnExt;  
	    
	    public FloatFrame(Context context) {  
	        super(context);  
	        mContext = context;  
	        gestureDetector = new GestureDetector(context, gestureListener);  
	  
	  
	        View view = View.inflate(context, R.layout.float_layout_main, null);  
	        viewExtFrame = view.findViewById(R.id.id_float_ext_frame);  
	        btnExt = (Button)view.findViewById(R.id.id_btn_ext);  
	        int ids[] = { R.id.id_btn_ext, R.id.id_btn_exit };  
	        for (int id : ids) {  
	            view.findViewById(id).setOnClickListener(onClick);  
	  
	        }  
	        view.findViewById(R.id.id_btn_exit).setOnTouchListener(frameOnTouchListener);  
	        this.addView(view);  
	          
	  
	    }  
	  
	    private void clickView(int id) {  
	        switch (id) {  
	  
	        case R.id.id_btn_ext:  
	            int visible = viewExtFrame.getVisibility();  
	            if (visible == View.VISIBLE) {  
	                viewExtFrame.setVisibility(View.INVISIBLE);  
	                btnExt.setText("<<");  
	            } else {  
	                viewExtFrame.setVisibility(View.VISIBLE);  
	                btnExt.setText(">>");  
	            }  
	              
	            break;  
	        case R.id.id_btn_exit:  
	            wm.removeView(FloatFrame.this);// 点击退出，销毁悬浮窗口  
	            Log.e("float view", "exit");  
	            break;  
	        default:  
	  
	        }  
	    }  
	// 响应悬浮窗口中的Button点击  
	    View.OnClickListener onClick = new View.OnClickListener() {  
	  
	        @Override  
	        public void onClick(View v) {  
	            // TODO Auto-generated method stub  
	            clickView(v.getId());  
	        }  
	    };  
	    final static int MESSAGE_MOVE = 0x1001;  
	    final static int MESSAGE_DOWN = 0x1002;  
	    final static int MESSAGE_UP = 0x1003;  
	    Handler mHandler = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case MESSAGE_MOVE:  
	                updateViewPosition();  
	                break;  
	            case MESSAGE_DOWN:  
	                break;  
	            case MESSAGE_UP:  
	                break;  
	            default:  
	            }  
	        }  
	    };  
	// 点击Listener  
	    OnTouchListener frameOnTouchListener = new OnTouchListener() {  
	  
	        public boolean onTouch(View v, MotionEvent event) {  
	            if (null == gestureDetector || null == event) {  
	                return false;  
	            }  
	            return gestureDetector.onTouchEvent(event);  
	        }  
	  
	    };  
	// 手势识别  
	    GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {  
	  
	        @Override  
	        public boolean onDown(MotionEvent e) {  
	            // TODO Auto-generated method stub  
	            Log.d("OnGestureListener", "onDown");  
	            mTouchRawX = e.getX();  
	            mTouchRawY = e.getY();  
	  
	            return false;  
	        }  
	  
	        @Override  
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	                float velocityY) {  
	  
	            return true;  
	        }  
	  
	        @Override  
	        public void onLongPress(MotionEvent e) {  
	            // TODO Auto-generated method stub  
	            // Log.d("OnGestureListener", "onLongPress");  
	  
	        }  
	  
	        @Override  
	        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
	                float distanceX, float distanceY) {  
	            mTouchRawX = e2.getRawX();  
	            mTouchRawY = e2.getRawY();  
	            mHandler.sendEmptyMessage(MESSAGE_MOVE);  
	  
	            return true;  
	        }  
	  
	        @Override  
	        public void onShowPress(MotionEvent e) {  
//	          Log.d("OnGestureListener", "onShowPress");  
	        }  
	  
	        @Override  
	        public boolean onSingleTapUp(MotionEvent e) {  
//	          Log.d("OnGestureListener", "onSingleTapUp");  
	  
	            return false;  
	        }  
	  
	    };  
	  
	    private GestureDetector gestureDetector;  
	  
	    private void updateViewPosition() {  
	        // 更新浮动窗口位置参数  
	  
	        wmParams.x = (int) mTouchRawX;  
	        wmParams.y = (int) mTouchRawY;  
	        wm.updateViewLayout(this, wmParams);  
	  
	    }  

}
