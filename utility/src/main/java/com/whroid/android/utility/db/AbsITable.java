package com.whroid.android.utility.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public abstract class AbsITable implements ITable{
	

	List<KeyItem> list = new ArrayList<KeyItem>();
	
	protected void addKeyItem(String key,String type)
	{
		list.add(new KeyItem(key, type));
	}
	protected void clear()
	{
		list.clear();
		list.add(new KeyItem(ID, true));
	}
	
	protected String getCreateSql(String tableName)
	{
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "
				+ tableName + " (");
		int size = list.size();
		int index = 0;
		for(KeyItem item:list)
		{
			sb.append(item.toSQL());
			if(index++<size -1)
			{
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	protected String getDropSql(String tableName)
	{
		return "DROP TABLE IF EXISTS "+ tableName;
	}
	protected void closeCursor(Cursor cursor)
	{
		try{
		if(cursor != null &&!cursor.isClosed())
		{
			cursor.close();
		}
		cursor = null;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
