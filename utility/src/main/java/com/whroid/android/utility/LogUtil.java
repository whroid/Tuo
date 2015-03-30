package com.whroid.android.utility;

/**
 * 
 * @description    日志工具类
 * @project        myctu
 * @author         wuhongren
 * @create         2013-5-30下午5:27:52
 * 
 */
public class LogUtil {
	
	public static boolean isDebug = true;
	public static void v(String tag, String msg) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable t) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.v(tag, msg, t);
	}

	public static void d(String tag, String msg) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.d(tag, msg);
	}
	public static void d(String tag, Object obj) {
		if(obj == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.d(tag, obj.toString());
	}
	public static void d(String tag, String msg, Throwable t) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.d(tag, msg, t);
	}

	public static void i(String tag, String msg) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable t) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.i(tag, msg, t);
	}

	public static void w(String tag, String msg) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable t) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.w(tag, msg, t);
	}

	public static void e(String tag, String msg) {
		if(msg == null)
		{
			return;
		}
		if (isDebug)
			android.util.Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable t) {
		if(msg == null)
		{
			return;
		}
		if (isDebug){
			android.util.Log.e(tag, msg, t);
			if(t != null)
			t.printStackTrace();
		}
			
	}
	public static void e(String tag,String where,Exception e){
		if(where == null)
		{
			return;
		}
		if(isDebug){
			e(tag,where);
			if(e != null)
			e.printStackTrace();
		}
	}
}
