package com.whroid.android.tuo.note.db;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;

import com.whroid.android.utility.db.IDBManager;
import com.whroid.android.utility.db.ITable;


public class DNoteDBManager extends IDBManager {
	
	private String TAG = "DNoteDBManager";
	public static  String DB_NAME = "DNoteDBManager.db";
	public static final int DB_VERSION = 14;
	
	public DNoteDBManager(Context context)
	{
		super(context);
	}
	public static synchronized IDBManager getInstance(Context mContext)
    {
    	if(mDBManager == null)
    	{
    		 synchronized (IDBManager.class) {
                 if (mDBManager == null) {
                	 mDBManager = new DNoteDBManager(mContext);
                 }
             }
    	}
    	return mDBManager;
    }
  
	
	@Override
	public List<ITable> getAllTable() {
		List<ITable> lists = new ArrayList<ITable>();
		lists.add(new DNoteTable());
		return lists;
	}
	
	@Override
	public String getDatabaseName() {
		return DB_NAME;
	}

	@Override
	public int getDatabaseVersion() {
		return DB_VERSION;
	}
}
