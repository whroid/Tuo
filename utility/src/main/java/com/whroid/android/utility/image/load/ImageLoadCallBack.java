package com.whroid.android.utility.image.load;

import com.whroid.android.utility.LogUtil;
import com.whroid.android.utility.image.ImageObserver;

import android.graphics.Bitmap;

public   class ImageLoadCallBack {

	public static final String TAG = "ImageLoadCallBack";
	public Object obj;
	public ImageLoadCallBack(){}
	public ImageLoadCallBack(Object obj)
	{
		this.obj = obj;
	}
	public  void imageLoaded(ImageObserver observer)
	{
		LogUtil.d(TAG, "observer:"+observer);
		if(observer != null)
		imageLoaded(observer.getUrl(),observer.getBitmap());
	}
	public void imageLoaded(String url,Bitmap bitmap)
	{
		
	}
}
