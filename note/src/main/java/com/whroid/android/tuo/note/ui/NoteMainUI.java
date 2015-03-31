package com.whroid.android.tuo.note.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.whroid.android.tuo.base.ui.TActivity;
import com.whroid.android.tuo.base.ui.TFragment;
import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.ui.view.NoteMenu;
import com.whroid.android.tuo.note.ui.view.NoteMenu.NoteMenuItem;
import com.whroid.android.tuo.note.ui.view.NoteMenu.OnNoteMenuItemClickListener;

/**
 * 记事本主界面
 * 
 * @author whroid
 * @data 2014-6-30
 * 
 */
public class NoteMainUI extends TActivity {

	public static final String TAG = NoteMainUI.class.getSimpleName();
	public static final int MENU_NEW_ITEMID = 123;
	public static final int MENU_REFRESH_ITEMID = 124;

	public int NOTE_NEW_REQUESTCODE = 100;

	DrawerLayout mDrawerlayout;
	NoteMenu mNoteMenuView;
	List<TFragment> mFragments;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private ActionBarHelper mActionBarHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_main_ui);
	}

	@Override
	public void init() {

	}

	@Override
	public void initView() {
		mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerlayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mNoteMenuView = (NoteMenu) findViewById(R.id.notemenuview);
		  mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout,
	                R.drawable.ic_drawer, R.string.note_drawer_open, R.string.note_drawer_close);
		  mDrawerlayout.setDrawerListener(new NoteDrawerListener());
		  mActionBarHelper = new ActionBarHelperICS();
		  mActionBarHelper.init();
		setNoteMenuView();
		setFragment();
		showFragment(mNoteMenuView.getDefaultMenuItem());
	}

	private void setNoteMenuView() {
		mNoteMenuView
				.setOnNoteMenuItemClickListener(new OnNoteMenuItemClickListener() {

					@Override
					public void onNoteMenuItem(int which, NoteMenuItem menuItem) {
						switch (menuItem.ftag) {
						case NoteMenu.MENU_TYPE_ABOUT:
							NoteUIManager.toAboutUI(context);
							break;
						case NoteMenu.MENU_TYPE_NEW:
							NoteUIManager.toNoteNew(NoteMainUI.this,
									NOTE_NEW_REQUESTCODE);
							break;
						case NoteMenu.MENU_TYPE_LISTNEWS:
							showFragment(menuItem);
							break;
						case NoteMenu.MENU_TYPE_FEEDBACK:
							break;
						default:
							break;
						}
						mDrawerlayout.closeDrawer(Gravity.LEFT);
					}
				});
	}

	private void setFragment() {
		mFragments = new ArrayList<TFragment>();
		NoteListFUI item2 = new NoteListFUI();
		item2.setFragmentTag(NoteMenu.MENU_TYPE_LISTNEWS);
		mFragments.add(item2);
	}

	private TFragment getBaseFragment(int tag) {
		for (TFragment fragment : mFragments) {
			if (fragment.getFragmentTag() == tag) {
				return fragment;
			}
		}
		return null;
	}

	private void showFragment(NoteMenuItem item)
	{
		showFragment(item.ftag);
		mNoteMenuView.setSeletedFromListView(item.getListViewPosition());
		mActionBarHelper.setTitle(item.text);
	}
	private void showFragment(int tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction franscation = fragmentManager.beginTransaction();
		TFragment baseFragment = getBaseFragment(tag);
		franscation.replace(R.id.content_frame, baseFragment);
		franscation.commitAllowingStateLoss();
	}

	@Override
	public void initData() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem qrcode = menu.add(0, MENU_NEW_ITEMID, 0, "新建");
		MenuItemCompat
				.setShowAsAction(qrcode, MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_NEW_ITEMID:
			NoteUIManager.toNoteNew(NoteMainUI.this,
					NOTE_NEW_REQUESTCODE);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {

	}
	@Override
	public boolean onSupportNavigateUp() {
		return true;
	}
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
  

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	 private class NoteDrawerListener implements DrawerLayout.DrawerListener {
	        @Override
	        public void onDrawerOpened(View drawerView) {
	            mDrawerToggle.onDrawerOpened(drawerView);
	            mActionBarHelper.onDrawerOpened();
	        }

	        @Override
	        public void onDrawerClosed(View drawerView) {
	            mDrawerToggle.onDrawerClosed(drawerView);
	            mActionBarHelper.onDrawerClosed();
	        }

	        @Override
	        public void onDrawerSlide(View drawerView, float slideOffset) {
	            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
	        }

	        @Override
	        public void onDrawerStateChanged(int newState) {
	            mDrawerToggle.onDrawerStateChanged(newState);
	        }
	    }

	    /**
	     * Stub action bar helper; this does nothing.
	     */
	    private class ActionBarHelper {
	        public void init() {}
	        public void onDrawerClosed() {}
	        public void onDrawerOpened() {}
	        public void setTitle(CharSequence title) {}
	    }

	    /**
	     * Action bar helper for use on ICS and newer devices.
	     */
	    private class ActionBarHelperICS extends ActionBarHelper {
	        private final ActionBar mActionBar;
	        private CharSequence mDrawerTitle;
	        private CharSequence mTitle;

	        ActionBarHelperICS() {
	            mActionBar = getSupportActionBar();
	        }

	        @Override
	        public void init() {
	            mActionBar.setDisplayHomeAsUpEnabled(true);
	          //  mActionBar.setHomeButtonEnabled(true);
	            mActionBar.setDisplayHomeAsUpEnabled(true);
	            mTitle = mDrawerTitle = getTitle();
	        }

	        /**
	         * When the drawer is closed we restore the action bar state reflecting
	         * the specific contents in view.
	         */
	        @Override
	        public void onDrawerClosed() {
	            super.onDrawerClosed();
	            mActionBar.setTitle(mTitle);
	        }

	        /**
	         * When the drawer is open we set the action bar to a generic title.
	         * The action bar should only contain data relevant at the top level of
	         * the nav hierarchy represented by the drawer, as the rest of your content
	         * will be dimmed down and non-interactive.
	         */
	        @Override
	        public void onDrawerOpened() {
	            super.onDrawerOpened();
	            mActionBar.setTitle(mDrawerTitle);
	        }

	        @Override
	        public void setTitle(CharSequence title) {
	            mTitle = title;
	        }
	    }
}
