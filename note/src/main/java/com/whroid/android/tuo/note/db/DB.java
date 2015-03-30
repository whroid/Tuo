package com.whroid.android.tuo.note.db;

import java.util.ArrayList;
import java.util.List;

import com.whroid.android.tuo.note.model.MNote;

import android.content.Context;

public class DB {

	
	public static long insertNote(Context context,DNoteMsg note)
	{
		DNoteTable noteTable = new DNoteTable();
		return noteTable.insert(context, note);
	}
	public static List<MNote> obtainNotes(Context context)
	{
		
		List<MNote> mNotes = new ArrayList<MNote>();
		DNoteTable noteTable = new DNoteTable();
		List<DNoteMsg>  mDNotes =  noteTable.obtainLists(context);
		if(mDNotes == null)
		{
			return mNotes;
		}
		for(DNoteMsg note:mDNotes)
		{
			mNotes.add( new MNote(note));
		}
		return mNotes;
	}
	
	public static int delete(Context context, String noteId)
	{
		DNoteTable table = new DNoteTable();
		return table.delete(context, noteId);
	}
	public static long update(Context context,DNoteMsg note)
	{
		DNoteTable table = new DNoteTable();
		return table.update(context, note);
	}
	public static MNote obtainNote(Context context,String noteid)
	{
		DNoteTable table = new DNoteTable();
		DNoteMsg note = table.obtainOne(context, noteid);
		MNote mnote = null;
		if(note != null)
		{
			mnote = new MNote(note);
		}
		return mnote;
	}
}
