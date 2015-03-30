package com.whroid.android.tuo.note.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.whroid.android.tuo.note.db.DNoteMsg;

public class MNote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6905651295416862948L;
	public String title;
	public String content;
	public String summary;
	public long time;
	public String noteId;
	public long createTime;
	public long updateTime;
	
	public MNote()
	{
		noteId = UUID.randomUUID().toString();
		
	}
	public MNote(String title,String content)
	{
		this.title = title;
		this.content = content;
		createCreateTime();
		this.updateTime = System.currentTimeMillis();
		this.noteId = UUID.randomUUID().toString();
	}
	public MNote(DNoteMsg note)
	{
		title = note.title;
		content = note.content;
		time = note.updateTime;
		noteId = note.msgid;
		createTime = note.createTime;
		updateTime = note.updateTime;
		summary = note.summary;
	}
	public DNoteMsg getDNoteMsg()
	{
		DNoteMsg msg = new DNoteMsg();
		msg.msgid = noteId;
		msg.title = title;
		msg.content = content;
		msg.createTime = createTime;
		msg.updateTime = System.currentTimeMillis();
		return msg;
	}
	public void createCreateTime()
	{
		createTime = System.currentTimeMillis();
	}
	public String getTime()
	{
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		return format.format(new Date(time));
	}
	public String getNoteId()
	{
		return noteId;
	}
	public String getTitle()
	{
		return title;
	}
	public String getContent()
	{
		return content;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MNote [title=" + title + ", content=" + content + ", summary="
				+ summary + ", time=" + time + ", noteId=" + noteId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
	
	
	
}
