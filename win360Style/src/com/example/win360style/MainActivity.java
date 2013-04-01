package com.example.win360style;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private Context context=this;
	private ViewPager myViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 initalView();
    }
	private void initalView() {
		myViewPager=(ViewPager)findViewById(R.id.viewPagerContent);
//		LinearLayout lin = (LinearLayout)findViewById(R.id.pagerLinear);
//		LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
//		lp.gravity=Gravity.LEFT;
//		lp.leftMargin=20;
//		lin.setLayoutParams(lp);
//		LayoutParams param = myViewPager.getLayoutParams();
//	
//		myViewPager.setLayoutParams(lp);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view1 = myViewPager.inflate(context, R.layout.view1, null);
		View view2 = myViewPager.inflate(context, R.layout.view2, null);
		final ArrayList<View> listView = new ArrayList<View>();
		listView.add(view1);
		listView.add(view2);
		PagerAdapter adapter = new PagerAdapter(){

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
	}
}
