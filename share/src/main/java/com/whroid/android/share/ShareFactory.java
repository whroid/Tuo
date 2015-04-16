package com.whroid.android.share;

import android.graphics.Bitmap;

public abstract class ShareFactory {
	
	public abstract void shareText(String text,boolean isShare2Cycle);
	public abstract void shareImage(String url,boolean isShare2Cycle);
	public abstract void shareImage(Bitmap bitmap,boolean isShare2Cycle);
	public abstract void shareHtml(String url,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
	public abstract void sharevideo(String url,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
	public abstract  void sharemusic(String musicurl,String musicdataurl,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
}
