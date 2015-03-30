package com.whroid.android.utility;


import android.os.Process;
import android.util.Log;

/**
 * 
 * @文件描述 打印log日志，包括详细线程的信息
 * @author whroid
 * @create 2014-3-25
 */
public class InmovationLogger {
	
	private final static String tagBase = "[inmovation]";
	public static int logLevel = Log.VERBOSE;
	private static boolean logFlag = true;
	String tag = tagBase;

	public static InmovationLogger getLogger(String tag)
	{
		return new InmovationLogger(tag);
	}
	private InmovationLogger(String tag) {
		this.tag = tagBase+tag;
	}

	private String getFunctionName() {

		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		
		for (StackTraceElement st : sts) {

			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return "[ process id:"+Process.myPid() + " ThreadName"+ Thread.currentThread().getName()+ " : " + st.getFileName() + ":" + st.getLineNumber() + " ]";
		}
		return null;
	}

	public void i(Object str) {
		if (!logFlag)
			return;
		if (logLevel <= Log.INFO) {
			String name = getFunctionName();
			if (name != null) {
				Log.i(tag, name + " - " + str);
			} else {
				Log.i(tag, str.toString());
			}
		}
	}

	public void v(Object str) {
		if (!logFlag)
			return;
		if (logLevel <= Log.VERBOSE) {
			String name = getFunctionName();
			if (name != null) {
				Log.v(tag, name + " - " + str);
			} else {
				Log.v(tag, str.toString());
			}
		}
	}

	public void w(Object str) {
		if (!logFlag)
			return;
		if (logLevel <= Log.WARN) {
			String name = getFunctionName();
			if (name != null) {
				Log.w(tag, name + " - " + str);
			} else {
				Log.w(tag, str.toString());
			}
		}
	}

	public void e(Object str) {
		if (!logFlag)
			return;
		if (logLevel <= Log.ERROR) {
			String name = getFunctionName();
			if (name != null) {
				Log.e(tag, name + " - " + str);
			} else {
				Log.e(tag, str.toString());
			}
		}
	}

	public void e(Exception ex) {
		if (!logFlag)
			return;
		if (logLevel <= Log.ERROR) {
			Log.e(tag, "error", ex);
		}
	}
	public  void e(String msg, Throwable t) {

		if (!logFlag)
			return;
		if (logLevel <= Log.ERROR) {
			Log.e(tag, msg, t);
		}
	}

	public void d(Object str) {
		if (!logFlag)
			return;
		if (logLevel <= Log.DEBUG) {
			String name = getFunctionName();
			if (name != null) {
				Log.d(tag, name + " - " + str);
			} else {
				Log.d(tag, str.toString());
			}
		}
	}

}
