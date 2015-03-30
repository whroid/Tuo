package com.whroid.android.tuo.note.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.whroid.android.utility.db.AbsITable;


public class DNoteTable extends AbsITable {
	
	public static final String TAG = "DNoteTable";

	private static final String TBNAME = "DNoteTable";

	public static final String MSGID = "noteid"; // 消息id
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String SUMMARY = "summary";//简介
	public static final String CREATETIME = "createTime"; // 消息title
	public static final String UPDATETIME = "updateTime"; // 消息内容
	
	static class Sqls{
		
		public static String update = MSGID+" = '%s'";
	}
	
	@Override
	public String getCreateTableSQL() {
		clear();
		addKeyItem(MSGID, "TEXT");
		addKeyItem(TITLE, "TEXT");
		addKeyItem(CONTENT, "TEXT");
		addKeyItem(SUMMARY,"TEXT");
		addKeyItem(CREATETIME, "TEXT");
		addKeyItem(UPDATETIME, "TEXT");
		
		return getCreateSql(TBNAME);
	}

	@Override
	public String getUpgradeTableSQL() {
		// TODO Auto-generated method stub
		return getDropSql(TBNAME);
	}

	public long insert(Context context,DNoteMsg note)
	{
		SQLiteDatabase db =  DNoteDBManager.getInstance(context).getWritableDatabase();
		return db.insert(TBNAME, null, note.getContentValues());
	}
	public long update(Context context,DNoteMsg note)
	{
		SQLiteDatabase db =  DNoteDBManager.getInstance(context).getWritableDatabase();
		String updatesql = String.format(Sqls.update, note.msgid);
		return db.update(TBNAME, note.getUpdateContentValues(), updatesql,null);
	}
	
	public List<DNoteMsg> obtainLists(Context context)
	{
		List<DNoteMsg> notes = new ArrayList<DNoteMsg>();
		SQLiteDatabase db =  DNoteDBManager.getInstance(context).getWritableDatabase();
		Cursor cursor = db.query(TBNAME, null, null, null, null, null, UPDATETIME+" desc");
		if(!cursor.moveToFirst())
		{
			closeCursor(cursor);
			return notes;
		}
		do{
			DNoteMsg msg = new DNoteMsg();
			msg.parseCursorNoContent(cursor);
			notes.add(msg);
		}while(cursor.moveToNext());
		closeCursor(cursor);
		return notes;
	}
	
	
	public int delete(Context context,String noteId)
	{
		SQLiteDatabase db =  DNoteDBManager.getInstance(context).getWritableDatabase();
		return db.delete(TBNAME, MSGID+"=?", new String[]{noteId});
	}
	
	public DNoteMsg obtainOne(Context context,String noteId){
		DNoteMsg note = null;
		SQLiteDatabase db =  DNoteDBManager.getInstance(context).getWritableDatabase();
		Cursor cursor = db.query(TBNAME, null, MSGID+"=?", new String[]{noteId}, null, null, null);
		cursor.moveToFirst();
		note = new DNoteMsg(cursor);
		closeCursor(cursor);
		return note;
	}
}
