package com.myandroid.msgblocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private Context context=this;
	private ViewPager myViewPager;
	private FrameLayout mSettingFrameLayout;
	private FrameLayout mMainFrameLayout;

	private boolean mSlided = false;
	private ImageView mSettingBtn;

	private final static int TRANSLATE_ANIMATION_WIDTH = 140;
	private final static int ANIMATION_DURATION_FAST = 450;
	private final static int ANIMATION_DURATION_SLOW = 350;
	private final static int MOVE_DISTANCE = 50;
	
	// 屏幕宽度
	private int mWidth;
	private int mHeigth;
	
	private float mPositionX;
	private FrameLayout mMainFrameMaskLayout;
	private ImageView mSettingNegativeBtn;
	private PagerAdapter adapter;
	private PackageManager mPackageManager;
	private List<Map<String, Object>> mActList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initalView();
    }
	private void initalView() {

		mPackageManager = getPackageManager();
		
		mWidth = getResources().getDisplayMetrics().widthPixels;
		mHeigth = getResources().getDisplayMetrics().heightPixels;
		
		myViewPager=(ViewPager)findViewById(R.id.viewPagerContent);
//		LinearLayout lin = (LinearLayout)findViewById(R.id.pagerLinear);
//		LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
//		lp.gravity=Gravity.LEFT;
//		lp.leftMargin=20;
//		lin.setLayoutParams(lp);
//		LayoutParams param = myViewPager.getLayoutParams();
//	
//		myViewPager.setLayoutParams(lp);

		mActList = getListData();
		final ArrayList<View> listView = new ArrayList<View>();

    	List<Map<String, Object>> newList = null;
		do{
			View childView = View.inflate(context, R.layout.main_page_layout, null);
			GridView mGrid = (GridView) childView.findViewById(R.id.main_page_layout);
			 
		    if(mActList.size()>=8){
		    	newList = copyList(mActList.subList(0, 8));
		    	mGrid.setAdapter(new AppsAdapter(newList));	
		    	for(int i = 0; i < 8; i++){
		    		mActList.remove(0);
		    	}
		    }else{
		    	newList = copyList(mActList.subList(0, mActList.size()));
		    	mGrid.setAdapter(new AppsAdapter(newList));	
		    	int size = mActList.size();
		    	for(int i = 0; i < size; i++){
		    		mActList.remove(0);
		    	}
		    }
			listView.add(childView);
			if(mActList.size() == 0){
				break;
			}
		}while(true);
		
		adapter = new PagerAdapter(){

			@Override
			public int getCount() {
				return listView.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0==arg1;
			}
			 public void destroyItem(View container, int position, Object object) {
				 ((ViewPager)container).removeView(listView.get(position));
				 
			 }
			 public Object instantiateItem(View container, int position) {
//				 LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
//					lp.gravity=Gravity.LEFT;
//					lp.leftMargin=30;
//				 container.setLayoutParams(lp);
				 ((ViewPager)container).addView(listView.get(position));

				return   listView.get(position);

			 }
		};	
		myViewPager.setAdapter(adapter);
		final TextView pageNoTv = (TextView) findViewById(R.id.pageNo);
		myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  
            @Override  
            public void onPageSelected(int arg0) {  
            	if(arg0==0){
            		pageNoTv.setText("1 / 2");
            	}else{
            		pageNoTv.setText("2 / 2");
            	}
   
            }  @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
  
            }  
  
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
  
            }  
        });  
            
            
		mSettingFrameLayout = (FrameLayout) findViewById(R.id.setting);
		
		mMainFrameLayout = (FrameLayout) findViewById(R.id.main);
		mMainFrameMaskLayout = (FrameLayout) findViewById(R.id.main_page_mask);
//		mMainFrameLayout.setOnTouchListener(mOnTouchListener);
		mSettingFrameLayout.setVisibility(View.GONE);
		TranslateAnimation translate = new TranslateAnimation(0, TRANSLATE_ANIMATION_WIDTH, 0, 0);
		translate.setDuration(ANIMATION_DURATION_FAST);
		// 动画完成时停在结束位置
		translate.setFillAfter(true);
		mSettingFrameLayout.startAnimation(translate);
		mSettingFrameLayout.getAnimation().setAnimationListener(
				new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation anim) {						 
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation anima) {
						mSettingFrameLayout.setVisibility(View.VISIBLE);
						
					}
				});
		
		mSettingBtn = (ImageView) findViewById(R.id.setting_btn);
		mSettingBtn.setOnClickListener(mOnClickListener);
		

		mSettingNegativeBtn = (ImageView) findViewById(R.id.setting_negative_btn);
		mSettingNegativeBtn.setOnClickListener(mOnClickListener);
		
		
		TextView tv = (TextView) findViewById(R.id.menu_net_udpate);
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	// 点击按钮
		private OnClickListener mOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.setting_btn :
						if (mSlided) {
							slideIn();
						} else {
							slideOut();
						}
						break;
					case R.id.setting_negative_btn :
						if (mSlided) {
							slideIn();
						} else {
							slideOut();
						}
						break;
				}
			}
		};
		
		// 滑动
		private OnTouchListener mOnTouchListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (v.getId() == R.id.main_page_mask) {
					int action = event.getAction();
					switch (action) {
						case MotionEvent.ACTION_DOWN :
							mPositionX = event.getX();
							break;
						case MotionEvent.ACTION_MOVE :
							final float currentX = event.getX();
							// 向左边滑动
							if (currentX - mPositionX <= -MOVE_DISTANCE && !mSlided) {
								slideOut();
							} else if (currentX - mPositionX >= MOVE_DISTANCE && mSlided) {
								slideIn();
							}
							break;
					}
					return true;
				} 
				return false;
			}
		};
		
		

		/**
		 * 滑出侧边栏
		 */
		private void slideOut() {
			TranslateAnimation animation = new TranslateAnimation(
					0, -TRANSLATE_ANIMATION_WIDTH, 0, 0);
			animation.setDuration(ANIMATION_DURATION_FAST);
			animation.setFillAfter(true);
			mMainFrameLayout.startAnimation(animation);			
			mMainFrameLayout.getAnimation().setAnimationListener(
					new Animation.AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation anim) {
							TranslateAnimation translate = new TranslateAnimation(TRANSLATE_ANIMATION_WIDTH,
									0, 0, 0);
							translate.setDuration(ANIMATION_DURATION_SLOW);
							translate.setFillAfter(true);
							mSettingFrameLayout.startAnimation(translate);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation anima) {
							mSlided = true;
//							RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
//							lp.rightMargin = TRANSLATE_ANIMATION_WIDTH; 
//							//lp.topMargin = top;
//							int[] pos={mSettingBtn.getLeft(),mSettingBtn.getTop(),mSettingBtn.getRight(),mSettingBtn.getBottom()};
//							mSettingBtn.setLayoutParams(lp);
							/*final int left = mSettingBtn.getLeft();
				            final int top = mSettingBtn.getTop();
				            final int right = mSettingBtn.getRight();
				            final int bottom = mSettingBtn.getBottom();
				            mSettingBtn.layout(left, top , right, bottom);
				            System.out.println("left: "+(left)+";top:"+ top+";right:"+ (right)+";bottom:"+  bottom);*/
							
							mMainFrameMaskLayout.setVisibility(View.VISIBLE);
							mMainFrameMaskLayout.setOnTouchListener(mOnTouchListener);
							mSettingBtn.setVisibility(View.GONE);
						}
					});
		}

		/**
		 * 滑进侧边栏
		 */
		private void slideIn() {
			TranslateAnimation translate = new TranslateAnimation(0, TRANSLATE_ANIMATION_WIDTH, 0, 0);
			translate.setDuration(ANIMATION_DURATION_FAST);
			// 动画完成时停在结束位置
			translate.setFillAfter(true);
			mSettingFrameLayout.startAnimation(translate);
			mSettingFrameLayout.getAnimation().setAnimationListener(
					new Animation.AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							mSettingFrameLayout.setVisibility(View.GONE);
							TranslateAnimation mainAnimation = new TranslateAnimation(
									-TRANSLATE_ANIMATION_WIDTH, 0, 0, 0);
							mainAnimation.setDuration(ANIMATION_DURATION_SLOW);
							mainAnimation.setFillAfter(true);
							mMainFrameLayout.startAnimation(mainAnimation);
							
						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							mSlided = false;

							mMainFrameMaskLayout.setVisibility(View.GONE);
							mMainFrameMaskLayout.setOnTouchListener(null);
							mSettingBtn.setVisibility(View.VISIBLE);
							myViewPager.setFocusable(true);
						}
					});

		}
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && mSlided) {
				slideIn();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		
		public class AppsAdapter extends BaseAdapter {
			private List<Map<String, Object>> list;
	        public AppsAdapter(List<Map<String, Object>> list) {
	        	this.list = list;
	        }

	        public View getView(int position, View convertView, ViewGroup parent) {
	            TextView i;

	            if (convertView == null) {
	                i = new TextView(context);
	                i.setLayoutParams(new GridView.LayoutParams((mWidth-150-20)/2-5, (mHeigth-160)/4-15));
	            } else {
	                i = (TextView) convertView;
	            }

	            final Map<String, Object> map = (Map<String, Object>) list.get(position);
	            
	            i.setBackgroundColor(getResources().getColor(R.color.transparent_bg));
	            i.setText((String) map.get("title"));
	            i.setGravity(Gravity.CENTER);
	            i.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = (Intent) map.get("intent");
						startActivity(intent);
					}
				});
				
	            return i;
	        }


	        public final int getCount() {
	            return list.size();
	        }

	        public final Object getItem(int position) {
	            return list.get(position);
	        }

	        public final long getItemId(int position) {
	            return position;
	        }
	    }
		
		private List<Map<String, Object>> getListData() {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			Intent mainIntent = new Intent(Intent.ACTION_MAIN);
			mainIntent.addCategory("android.intent.category.FUNCTION");
			List<ResolveInfo> list = mPackageManager.queryIntentActivities(mainIntent, 0);
			final int n = list.size();
			for (int i = 0; i < n; i++) {
				ResolveInfo info = list.get(i);
				CharSequence labelSeq = info.loadLabel(mPackageManager);
				String label = TextUtils.isEmpty(labelSeq) ? info.activityInfo.name
						: labelSeq.toString();
				addItem(data, activityIntent(info.activityInfo.applicationInfo.packageName, info.activityInfo.name), label);
			}
			return data;
		}

		public Intent activityIntent(String pkg, String componentName) {
			Intent intent = new Intent();
			intent.setClassName(pkg, componentName);
			return intent;
		}

		public void addItem(List<Map<String, Object>> data, Intent intent,
				String name) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", name);
			map.put("intent", intent);
			data.add(map);
		}
		
		public List copyList(List source){
			List resultList = new ArrayList();
			for(Object o: source){
				resultList.add(o);
			}
			return resultList;
		}
}
