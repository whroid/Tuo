package com.whroid.android.utility;

import android.content.Intent;

public class IntentHelper {

	
	public static Intent sendMaile(String to,String title,String content) {
		String[] reciver = new String[] { to };
		Intent myIntent = new Intent(Intent.ACTION_SEND);
		myIntent.setType("plain/text");
		myIntent.putExtra(Intent.EXTRA_EMAIL, reciver);
	        myIntent.putExtra(Intent.EXTRA_SUBJECT, "测试邮件");
	        myIntent.putExtra(Intent.EXTRA_TEXT, title+"\r\n"+content);
		return Intent.createChooser(myIntent, "请选择发送收邮件的应用");
		
	}
}
