package com.whroid.android.utility.db;

import java.util.List;

import com.whroid.android.utility.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @文件描述 
 * @author whroid
 * @create 2014-3-26
 */
public class MSQLiteOpenHelper {

	String TAG = MSQLiteOpenHelper.class.getSimpleName();
	List<ITable> tables;
	Context context;
	String name;
	int version;
	public MSQLiteOpenHelper(Context context, String name, int version,List<ITable> tables) {
		this.tables = tables;
		this.context = context;
		this.name = name;
		this.version = version;
	}
	public MTSQLiteOpenHelper getSQLiteOpenHelper()
	{
		return new MTSQLiteOpenHelper(context, name, null,version);
	}
	class MTSQLiteOpenHelper extends SQLiteOpenHelper{
		

	public MTSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(tables != null)
		{
			for(ITable table:tables)
			{
				db.execSQL(table.getCreateTableSQL());
			}
		}else{
			LogUtil.e(TAG, "数据库无法创建表，由于没有导入表信息");
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(tables != null)
		{
			for(ITable table:tables)
			{
				db.execSQL(table.getUpgradeTableSQL());
			}
		}else{
			LogUtil.e(TAG, "数据库无法更新表，由于没有导入表信息");
		}
		onCreate(db);
	}
	
	}

}
