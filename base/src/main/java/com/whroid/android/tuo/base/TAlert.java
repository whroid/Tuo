package com.whroid.android.tuo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

public class TAlert {

	public interface OnAlertSelectId {
		void onClick(int whichButton);
	}
	
	/**
	 * dialog 自带的list
	 * 
	 * @param context
	 * @param title
	 * @param items
	 *            list数组
	 * @param okListner
	 * @return
	 */
	public static AlertDialog showAlert(Context context, String title,
			String[] items, final OnAlertSelectId okListner) {

		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		if (items == null) {
			return null;
		}

		CharSequence[] itemc = new CharSequence[items.length];
		int i = 0;
		for (String item : items) {
			itemc[i++] = item;

		}

		final Builder builder = new AlertDialog.Builder(context);
		if(!TextUtils.isEmpty(title))
		builder.setTitle(title);
		builder.setItems(itemc, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (okListner != null) {
					okListner.onClick(which);
				}
				dialog.dismiss();
			}
		});
		final AlertDialog alert = builder.create();
		alert.setTitle(title);
		alert.show();
		return alert;
	}
	/**
	 * 显示提示对话框
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param OkText
	 *            确定按钮上的文字
	 * @param cancelText
	 *            取消按钮上的文字
	 * @param listener
	 *            确定键的监听
	 * @return
	 */
	public static AlertDialog showAlert(Context context, String title,
			String message, String OkText, String cancelText,
			DialogInterface.OnClickListener listener,
			DialogInterface.OnClickListener cancellistener) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(OkText, listener);
		builder.setNegativeButton(cancelText, cancellistener);// 取消
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

}
