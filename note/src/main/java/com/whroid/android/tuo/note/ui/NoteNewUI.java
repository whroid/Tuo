package com.whroid.android.tuo.note.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


import com.whroid.android.tuo.base.TAlert;
import com.whroid.android.tuo.base.ui.TActivity;
import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.NoteContants;
import com.whroid.android.tuo.note.db.DB;
import com.whroid.android.tuo.note.model.MNote;
import com.whroid.android.utility.ToastUtils;

/**
 * 记事本编辑界面
 * @author whroid
 * @data   2014-6-30
 *
 */
public class NoteNewUI extends TActivity{
	
	public static String TAG = NoteNewUI.class.getSimpleName();
	
	public static final int TYPE_EDIT = 1; //编辑
	public static final int TYPE_NEW = 2;  //新添加的
	
	EditText mEditorView;
	EditText mTitleEdit;
	Button mSaveBtn;
	int currentType = TYPE_NEW;
	MNote mNote;
	String noteId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_new);
	}
	@Override
	public void init() {
      Intent intent = getIntent();
      mNote = new MNote();
      if(intent != null)
      {
    	  currentType = intent.getIntExtra(NoteContants.INTENT_TUO_TYPE, TYPE_NEW);
    	  noteId = intent.getStringExtra(NoteContants.INTENT_TUO_NOTE_ID);
      }
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mSaveBtn = (Button) findViewById(R.id.new_save);
		mEditorView = (EditText) findViewById(R.id.new_edit);
		mTitleEdit = (EditText) findViewById(R.id.title);
		mSaveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveNote();
			}
		});
	
		switch(currentType)
		{
		case TYPE_NEW:
			setTitle("新建日记");
			break;
		case TYPE_EDIT:
			setTitle("编辑日记");
			break;
		}
	}

	@Override
	public void initData() {
		if(!TextUtils.isEmpty(noteId))
	       new Thread(new ObtainRunable()).start();
	}

	private void saveNote()
	{
		String content = mEditorView.getText().toString();
		String title = mTitleEdit.getText().toString().trim();
		if(title.equals(""))
		{
			title = "笔记";
		}
		long index =0;
		if(currentType == TYPE_NEW)
		{
			mNote = new MNote(title, content);
			index  = DB.insertNote(context, mNote.getDNoteMsg());
		}else if(currentType == TYPE_EDIT)
		{
			mNote.setTitle(title);
			mNote.setContent(content);
			index = DB.update(context, mNote.getDNoteMsg());
		}
		
		if(index >0)
		{
			ToastUtils.show(context, "成功");
			finish();
		}else{
			ToastUtils.show(context, "失败");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		noSaveTip();
	}
	
	private void noSaveTip()
	{
		TAlert.showAlert(context, "放弃？？", "数据未保存，确定是否要放弃它了，想好咯", "放手", "取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ToastUtils.show(context, "放手也是一种态度");
				finish();
			}
		}, null);
	}
	private void setView()
	{
		mTitleEdit.setText(mNote.getTitle());
		mEditorView.setText(mNote.getContent());
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
			if(TextUtils.isEmpty(noteId))
			{
				return;
			}
			mNote = DB.obtainNote(context, noteId);
			handler.sendEmptyMessage(1);
		}
		
	}
}
