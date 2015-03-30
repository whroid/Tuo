package com.whroid.android.tuo.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author whroid
 * @data   2014-7-6
 *
 */
public abstract class TActivity extends ActionBarActivity implements
		TUI, OnClickListener {

	protected Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		init();
		initView();
		initData();
	}
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		init();
		initView();
		initData();
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return true;
	}

	protected void displayHomeAsUpDirctor() {
		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
	}
}
