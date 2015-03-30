package com.whroid.android.tuo.note.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.whroid.android.tuo.base.TAlert;
import com.whroid.android.tuo.base.TAlert.OnAlertSelectId;
import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.db.DB;
import com.whroid.android.tuo.note.model.MNote;
import com.whroid.android.tuo.note.util.DataHandleUtil;

/**
 * 日志列表
 * @author whroid
 * @data   2014-7-27
 *
 */
public class NoteListFUI extends NoteFragment{

	public static final int REQUEST_EDIT = 101;
	public static final int MENU_REFRESH_ITEMID = 124;
	
	ListView mListView;
	List<MNote> mNotes;
	NoteListAdapter mListAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	protected void setLayout(Bundle savedInstanceState) {
		
		setLayoutId(R.layout.note_lists);
	}
	@Override
	public void init() {
		
	}

	@Override
	public void initView() {
		
		mListView = (ListView) findViewById(R.id.note_listview);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MNote note = (MNote) arg0.getAdapter().getItem(arg2);
				Intent intent = NoteUIManager.toNoteDetail(NoteListFUI.this, note.noteId);
				startActivityForResult(intent, REQUEST_EDIT);
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final MNote note = (MNote) arg0.getAdapter().getItem(arg2);
				String[] items = new String[]{"编辑","标记","分享","删除"};
				TAlert.showAlert(context, null, items, new OnAlertSelectId() {
					
					@Override
					public void onClick(int whichButton) {
						switch(whichButton)
						{
						case 0:
							Intent  intent = NoteUIManager.toNoteEdit(context, note.noteId);
							startActivityForResult(intent, REQUEST_EDIT);
							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							DataHandleUtil.removeNoteFromNoteList(mNotes, note.getNoteId());
							DB.delete(context, note.getNoteId());
							mListAdapter.notifyDataSetChanged();
							break;
						}
					}
				});
				return false;
			}
		});
	}

	
	@Override
	public void initData() {
		new Thread(new ObtainRunable()).start();		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		MenuItem qrcode = menu.add(0, MENU_REFRESH_ITEMID, 0, "刷新");
		MenuItemCompat
				.setShowAsAction(qrcode, MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_REFRESH_ITEMID:
			new Thread(new ObtainRunable()).start();	
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_EDIT)
		{
			new Thread(new ObtainRunable()).start();	
		}
	}
	private void setView()
	{
		if(mListAdapter == null)
		{
		mListAdapter = new NoteListAdapter(context, mNotes);
		mListView.setAdapter(mListAdapter);
		}else{
			mListAdapter.notifyDataSetChanged();
		}
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
			List<MNote> list = DB.obtainNotes(context);
			if(mNotes == null)
			{
				mNotes = new ArrayList<MNote>();
			}
			mNotes.clear();
			mNotes.addAll(list);
			handler.sendEmptyMessage(1);
		}
		
	}
}
