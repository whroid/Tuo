package com.whroid.android.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * 
 * @文件描述 
 * @author whroid
 * @create 2014-3-25
 */
public class ActivityUtils {

	/**
	 * 得到屏幕 宽（像素）
	 * 
	 * @param activity
	 * @return
	 */
	public static int getPhoneWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	/**
	 * 隐藏软键盘
	 * 
	 * @param et
	 *            软键盘对应的输入框
	 * @param context
	 */
	public static void hideSoft(TextView et, Context context) {
		// 隐藏软键盘
		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		} catch (Exception e) {
			Log.e("通讯录找好友，隐藏软键盘错误", e.getMessage());
		}
	}
	/**
	 * 显示软键盘
	 */
	public static void showSoft(TextView et,Context context){
		//显示软键盘,控件ID可以是EditText,TextView   
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, 0);  
	}

}
