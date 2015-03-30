package com.whroid.android.tuo.note.ui;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.whroid.android.tuo.base.ui.TListFragment;
import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.db.DB;
import com.whroid.android.tuo.note.model.MNote;

public class NoteListFragment extends TListFragment{

	
	ListView mListView;
	List<MNote> mNotes;
	NoteListAdapter mListAdapter;
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
	}

	@Override
	public void initData() {
		
		mNotes = DB.obtainNotes(context);
		mListAdapter = new NoteListAdapter(context, mNotes);
		mListView.setAdapter(mListAdapter);
		
	}

	

}
