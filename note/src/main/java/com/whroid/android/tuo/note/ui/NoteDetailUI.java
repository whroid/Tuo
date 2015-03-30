package com.whroid.android.tuo.note.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.TextView;


import com.whroid.android.tuo.base.ui.TActivity;
import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.NoteContants;
import com.whroid.android.tuo.note.db.DB;
import com.whroid.android.tuo.note.model.MNote;
import com.whroid.android.utility.IntentHelper;
import com.whroid.android.utility.LogUtil;

public class NoteDetailUI extends TActivity {

	public static final String  TAG = NoteDetailUI.class.getSimpleName();
	TextView mContentTv;
	MNote mNote;
	String noteId;
	public static final int REQUEST_EDIT = 11;

	Button mEditButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_detail_ui);
	}

	@Override
	public void init() {
		noteId = getIntent().getStringExtra(NoteContants.INTENT_TUO_NOTE_ID);
	}

	@Override
	public void initView() {
		mContentTv = (TextView) findViewById(R.id.content);
		mEditButton = (Button) findViewById(R.id.edit);
		mEditButton.setOnClickListener(this);
	}

	@Override
	public void initData() {
		displayHomeAsUpDirctor();
		setTitle("详情");
		new Thread(new ObtainRunable()).start();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, 1, 0, "分享");
		MenuItemCompat.setShowAsAction(item, MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			String title = "<H1>" + mNote.title + "</H1>";
			Intent intent = IntentHelper.sendMaile("whroid@gmail.com", title,
                    mNote.content);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.edit) {
			Intent intent = NoteUIManager.toNoteEdit(context, mNote.getNoteId());
			startActivityForResult(intent, REQUEST_EDIT);
		}
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0== REQUEST_EDIT)
		{
			new Thread(new ObtainRunable()).start();
		}
	}
	
	private void setView()
	{
		LogUtil.d(TAG, "详情中得到数据：" + mNote);
		mContentTv.setText(mNote.content);
		setTitle(mNote.title);
	}
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			 setView();
		};
	};
	class ObtainRunable implements Runnable
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mNote = DB.obtainNote(context, noteId);
			handler.sendEmptyMessage(1);
		}
		
	}
}
