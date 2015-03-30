package com.whroid.android.utility.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @文件描述 
 * @author whroid
 * @create 2014-3-26
 */
public  class IDBManager {
	
    private SQLiteOpenHelper    dbHelper;
    private SQLiteDatabase      mReadDB;
    private SQLiteDatabase      mWriteDB;
    protected static IDBManager    mDBManager;
    Context mContext;
    
    
    public IDBManager(Context mContext)
    {
       this.mContext = mContext;
    	dbHelper = new MSQLiteOpenHelper(mContext, getDatabaseName(), getDatabaseVersion(),getAllTable()).getSQLiteOpenHelper();
    }
    
    public IDBManager(){}
    public synchronized SQLiteDatabase getReadSQLiteDatabase()
    {
    	if(mReadDB == null)
    	{
    		mReadDB = dbHelper.getReadableDatabase();
    	}
    	return mReadDB;
    }
    public synchronized SQLiteDatabase getWritableDatabase()
    {
    	if(mWriteDB == null)
    	{
    		mWriteDB = dbHelper.getWritableDatabase();
    	}
    	return mWriteDB;
    }
    
    public synchronized void close()
    {
    	if(mWriteDB != null)
    	{
    		mWriteDB.close();
    	}
    	if(mReadDB != null)
    	{
    		mReadDB.close();
    	}
    }

    public   List<ITable> getAllTable(){
    	return null;
    }
    public String getDatabaseName()
    {
    	return mContext.getPackageName();
    }
    public int getDatabaseVersion()
    {
    	return 1;
    }
}
