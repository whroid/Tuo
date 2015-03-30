package com.whroid.android.utility.db;

public abstract interface ITable {

	public final static String ID = "_id";
	public abstract String getCreateTableSQL();
	public abstract String getUpgradeTableSQL();
	public class KeyItem
	{
		public String key;
		public String type;
		public boolean isPrimarykey;
		public KeyItem(String key,String type)
		{
			this.key = key;
			this.type = type;
			this.isPrimarykey = false;
		}
		public KeyItem(String key,Boolean isPrimaryKey)
		{
			this.key = key;
			this.isPrimarykey = isPrimaryKey;
		}
		
		public String toSQL()
		{
			if(isPrimarykey)
			{
				return key + " INTEGER PRIMARY KEY AUTOINCREMENT";
			}else {
				return key + " "+type;
			}
		}
	}
}
