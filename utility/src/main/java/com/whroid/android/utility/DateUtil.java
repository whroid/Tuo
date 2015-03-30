package com.whroid.android.utility;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateUtil {
	
	static SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm");
	static SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyy年MM月dd日");
	public static String getHourMinutes(long time){
		Date d = new Date(time);
		return formatter3.format(d);
	}
	
	public static String getYMD(long time){
		Date d = new Date(time);
		return formatterYMD.format(d);
	}
	public static String getYMD(String time){
		try{
		long date = Long.valueOf(time);
		return getYMD(date);
		}catch(Exception e)
		{
			e.printStackTrace();
			return time;
		}
	}
}
