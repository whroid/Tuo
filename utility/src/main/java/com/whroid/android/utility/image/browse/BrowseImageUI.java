package com.whroid.android.utility.image.browse;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.whroid.android.utility.image.ImageObserver;
import com.whroid.android.utility.image.load.ImageImpl;

public class BrowseImageUI extends FragmentActivity {

	public static final String TAG = "BrowseImageUI";
	
	public static final String KEY_IMAGES = "key_images";
	public static final String KEY_INDEX = "key_index";
	protected ViewPager viewPager;

	private int lastPositon;
	ArrayList<ImageImpl> images;
	int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentViewWithinitView();
		initData();
	}
	/**
	 * 设置界面显示的内容并且初始化viewpager
	 */
	protected void setContentViewWithinitView()
	{
		viewPager = new ViewPager(this);
		viewPager.setId(android.R.id.button1);
		setContentView(viewPager);
	}
	private void initData()
	{
		initIntent();
		initViewPager();
	}
	private void initIntent()
	{
		images = (ArrayList<ImageImpl>) getIntent().getSerializableExtra(KEY_IMAGES);
		currentIndex = getIntent().getIntExtra(KEY_INDEX, 0);
	}
	
	private void initViewPager()
	{
		viewPager.setAdapter(fragmentPagerAdp);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {

				if (index != lastPositon) {
					ImageImpl image = images.get(index);
					ZoomableImageView img = ((ZoomableImageView) viewPager
							.findViewWithTag(image.getTag()));
					if(img != null)
					img.resetImage();
					lastPositon = index;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	
		viewPager.setCurrentItem(currentIndex);
	}

	private FragmentStatePagerAdapter fragmentPagerAdp = new FragmentStatePagerAdapter(
			getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return images==null?0:images.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return BrowseFragment.newInstance(images.get(arg0));
		}
	};

}
