package com.whroid.android.tuo.note.util;

import java.util.List;

import com.whroid.android.tuo.note.model.MNote;

public class DataHandleUtil {

	public static void removeNoteFromNoteList(List<MNote>  notes,String noteId)
	{
		
		if(notes == null||notes.size()==0||noteId == null)
		{
			return;
		}
		for(MNote note:notes)
		{
			if(note.getNoteId().equals(noteId))
			{
				notes.remove(note);
				return;
			}
		}
	}
}
