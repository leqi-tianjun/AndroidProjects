package cn.zj.one.hanhan;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class Win8StyleActivity extends Activity {
    /** Called when the activity is first created. */
	private ViewPager myViewPager;
	private Context context=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initalView();
    }
	private void initalView() {
		myViewPager=(ViewPager)findViewById(R.id.viewPagerContent);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view1 = myViewPager.inflate(context, R.layout.view1, null);
		final ArrayList<View> listView = new ArrayList<View>();
		listView.add(view1);
		
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
				 ((ViewPager)container).addView(listView.get(position));

				return   listView.get(position);

			 }
		};
		myViewPager.setAdapter(adapter);

	}
}