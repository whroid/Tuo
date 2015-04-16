package com.whroid.android.share.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void make(Context context,String text)
	{
	    Toast.makeText(context, text, 1000).show();
	}
}
