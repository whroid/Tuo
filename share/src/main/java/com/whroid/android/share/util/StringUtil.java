package com.whroid.android.share.util;

import android.webkit.URLUtil;

public class StringUtil {

	
	public static boolean isEmpty(String str)
	{
		if(str != null && !str.equals(""))
		{
			return false;
		}
		return true;
	}
	
	public static boolean isValid(String url)
	{
		if(isEmpty(url))
		{
			return false;
		}
		return URLUtil.isValidUrl(url);
	}
}
