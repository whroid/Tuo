package com.whroid.android.utility.image.pick;

import java.io.File;

import com.whroid.android.utility.LogUtil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

/**
 * 得到各种 intent（第三方的调用）
 * 
 * @author Administrator
 * 
 */
public class PickIntent {

	/**
	 * 调用手机拍招功能
	 * 
	 * @return
	 */
	public static Intent getPhoneCameraIntentNoPath() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		return intent;
	}

	/**
	 * 调用手机拍照功能，将拍照的图片放在filepath中
	 * 
	 * @param filepath
	 * @return
	 */
	public static Intent getPhoneCameraIntent(String path) {
		LogUtil.d("getPhoneCameraIntent", path);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(path);
		Uri originalUri = Uri.fromFile(file);// 这是个实例变量，方便下面获取图片的时候用
		intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
		return intent;
	}

	/**
	 * 得到图片选择 intent
	 * 
	 * @return
	 */
	public static Intent getImgSelectIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		// 是否剪辑
		intent.putExtra("return-data", true);
		return intent;

	}

	/**
	 * 得到图片选择 intent
	 * 
	 * @return
	 */
	public static Intent getImgSelectIntentN() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		return intent;

	}

	/**
	 * 调用浏览器 访问
	 * 
	 * @param url
	 * @return
	 */
	public static Intent getWebIntent(String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		return intent;

	}

	/*
	 * 剪切
	 */
	public static Intent getCropImageIntent(Bitmap data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");
		intent.putExtra("data", data);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 128);
		intent.putExtra("outputY", 128);
		intent.putExtra("return-data", true);
		return intent;
	}
	/**  
     * 裁剪图片方法实现  
     * @param uri  
     */ 
    public static Intent getCropImageIntent(Uri uri,int width) {  
        Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", width);  
        intent.putExtra("outputY", width);  
        intent.putExtra("return-data", true);  
        return intent;
    }  

	public static Intent sendMaile(String to) {
		String[] reciver = new String[] { to };
		Intent myIntent = new Intent(Intent.ACTION_SEND);
		myIntent.setType("plain/text");
		myIntent.putExtra(Intent.EXTRA_EMAIL, reciver);
		return Intent.createChooser(myIntent, "请选择发送收邮件的应用");
	}

}
