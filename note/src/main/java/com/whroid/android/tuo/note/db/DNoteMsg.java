package com.whroid.android.tuo.note.db;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;

public class DNoteMsg implements Serializable{

	public String msgid;
	public String title;
	public String content;
	public String summary;//简介
	public long createTime;
	public long updateTime;
	
	public DNoteMsg()
	{
		createTime = System.currentTimeMillis();
	}
	private String getSummrayFromContent()
	{
		if(summary==null)
		{
			summary = content.replaceAll("\r", " ");
			int length = summary.length();
			if(length > 100)
			{
				summary = summary.substring(0,100);
			}
		}
		return summary;
	}
	
	
	protected ContentValues getContentValues()
	{
		ContentValues values = new ContentValues();
		values.put(DNoteTable.MSGID, msgid);
		values.put(DNoteTable.TITLE, title);
		values.put(DNoteTable.CONTENT, content);
		values.put(DNoteTable.CREATETIME, createTime);
		values.put(DNoteTable.SUMMARY, getSummrayFromContent());
		if(updateTime <createTime)
		{
			updateTime = createTime;
		}
		values.put(DNoteTable.UPDATETIME, updateTime);
		return values;
	}
	protected ContentValues getUpdateContentValues()
	{
		ContentValues values = new ContentValues();
		values.put(DNoteTable.TITLE, title);
		values.put(DNoteTable.CONTENT, content);
		values.put(DNoteTable.SUMMARY, getSummrayFromContent());
		values.put(DNoteTable.UPDATETIME, System.currentTimeMillis());
		return values;
	}
	protected boolean parseCursor(Cursor cursor)
	{
		if(cursor == null||cursor.getCount()==0)
		{
			return false;
		}
		
		msgid = cursor.getString(cursor.getColumnIndex(DNoteTable.MSGID));
		title = cursor.getString(cursor.getColumnIndex(DNoteTable.TITLE));
		content = cursor.getString(cursor.getColumnIndex(DNoteTable.CONTENT));
		summary = cursor.getString(cursor.getColumnIndex(DNoteTable.SUMMARY));
		createTime = cursor.getLong(cursor.getColumnIndex(DNoteTable.CREATETIME));
		updateTime = cursor.getLong(cursor.getColumnIndex(DNoteTable.UPDATETIME));
		return true;
	}
	
	public boolean parseCursorNoContent(Cursor cursor)
	{
		if(cursor == null||cursor.getCount()==0)
		{
			return false;
		}
		msgid = cursor.getString(cursor.getColumnIndex(DNoteTable.MSGID));
		title = cursor.getString(cursor.getColumnIndex(DNoteTable.TITLE));
		summary = cursor.getString(cursor.getColumnIndex(DNoteTable.SUMMARY));
		createTime = cursor.getLong(cursor.getColumnIndex(DNoteTable.CREATETIME));
		updateTime = cursor.getLong(cursor.getColumnIndex(DNoteTable.UPDATETIME));
		return true;
	}
	
	public DNoteMsg(Cursor cursor)
	{
		parseCursor(cursor);
	}
	
	@Override
	public String toString() {
		return "DNoteMsg [msgid=" + msgid + ", title=" + title + ", content="
				+ content + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
	
}
