package com.whroid.android.tuo.note.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.whroid.android.tuo.note.NoteContants;
import com.whroid.android.tuo.note.db.DNoteMsg;
import com.whroid.android.tuo.note.model.MNote;
import com.whroid.android.tuo.note.ui.app.AboutWebUI;

public class NoteUIManager {

	public static Intent toNoteDetail(Fragment context,String noteId)
	{
		Intent intent = new Intent(context.getActivity(),NoteDetailUI.class);
		intent.putExtra(NoteContants.INTENT_TUO_NOTE_ID, noteId);
		return intent;
	}
	public static void toNoteNew(Activity context,int requestCode)
	{
		Intent intent = new Intent(context,NoteNewUI.class);
		context.startActivityForResult(intent, requestCode);
	}
	
	public static void toAboutUI(Context context)
	{
		Intent intent = new Intent(context,AboutWebUI.class);
		context.startActivity(intent);
	}
	
	public static Intent toNoteEdit(Context context,String noteId)
	{
		Intent intent = new Intent(context,NoteNewUI.class);
		intent.putExtra(NoteContants.INTENT_TUO_NOTE_ID, noteId);
		intent.putExtra(NoteContants.INTENT_TUO_TYPE, NoteNewUI.TYPE_EDIT);
		return intent;
	}
}
