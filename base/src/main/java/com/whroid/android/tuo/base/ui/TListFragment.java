package com.whroid.android.tuo.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 
 * @author whroid
 * @data   2014-6-16
 *
 */
public abstract class TListFragment extends ListFragment implements TUI,
		 OnClickListener {
	public String BASE = getClass().getSimpleName();

	protected View view;
	protected int layoutId;
	protected Activity context;
	private String fragmentTag;
	
	protected abstract void setLayout(Bundle savedInstanceState);

	public void setFragmentTag(String fragmentTag)
	{
		this.fragmentTag = fragmentTag;
	}
	public String getFragmentTag()
	{
		return this.fragmentTag;
	}
	public TListFragment(){};
	public TListFragment(String fragmentTag){this.fragmentTag = fragmentTag;};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = getActivity();
		setLayout(savedInstanceState);
		if (layoutId == 0) {
			throw new IllegalArgumentException("layoutId不能为空");
		}

		if (view != null) {
			return view;
		} else {
			return inflater.inflate(layoutId, container, false);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		view = getView();
		init();
		initView();
		initData();
	}

	protected View findViewById(int rid)
	{
		return view.findViewById(rid);
	}
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	protected void setLayoutId(int layoutid) {
		this.layoutId = layoutid;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
