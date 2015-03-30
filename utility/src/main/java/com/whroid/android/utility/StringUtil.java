package com.whroid.android.utility;

import android.webkit.URLUtil;

public class StringUtil {

	
	public static boolean isEmpty(String str)
	{
		if(str == null || str.equals(""))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是有效的路径
	 * @param path
	 * @return true 如果是可以访问的网络地址
	 */
	public static boolean isValidURL(String path)
	{
		if(isEmpty(path)) return false;
		
		return URLUtil.isHttpUrl(path);
	}
	
	/**
	 * 判断路径是否是本地sdcard路径
	 * @param path
	 * @return
	 */
	public static boolean isSdcardFile(String path)
	{
		if(isEmpty(path)) return false;
		
		int index = path.indexOf("sdcard");
		return path.startsWith("/")&&index>=0?true:false;
	}
}
