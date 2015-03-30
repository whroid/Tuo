package com.whroid.android.tuo.ui;


import com.whroid.android.tuo.R;
import com.whroid.android.tuo.base.ui.TActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends TActivity {

	private FragmentTabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_ui);
		

	}


	@Override
	public void init() {

	}

	@Override
	public void initView() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(
				mTabHost.newTabSpec("simple").setIndicator(
						createIndicator("消息",
								R.drawable.main_btn_tab_message)),
				MessageFrUI.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("contacts").setIndicator(
						createIndicator("圈子",
								R.drawable.main_btn_tab_circle)),
				AppFrUI.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("custom").setIndicator(
						createIndicator("我的",
								R.drawable.main_btn_tab_mine)),
				MineFrUI.class, null);
	}

	private View createIndicator(String name, int tabImage) {
		View v = getLayoutInflater().inflate(R.layout.tabhost_main_item, null);
		TextView tv = (TextView) v.findViewById(R.id.tab_txt);
		ImageView img = (ImageView) v.findViewById(R.id.tab_img);
		tv.setText(name);
		img.setImageResource(tabImage);
		return v;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
