package com.whroid.android.share;

import android.content.Intent;
import android.graphics.Bitmap;

public abstract class ShareFactory {

    /**
     * 主要负责处理，分享成功返回的处理事件。主要是在oncreate和newIntent中调用
     * @param intent
     */
    public abstract void handleIntent(Intent intent);
	public abstract void shareText(String text,boolean isShare2Cycle);
	public abstract void shareImage(String url,boolean isShare2Cycle);
	public abstract void shareImage(Bitmap bitmap,boolean isShare2Cycle);
	public abstract void shareHtml(String url,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
	public abstract void sharevideo(String url,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
	public abstract void sharemusic(String musicurl,String musicdataurl,String title,String description,Bitmap bitmap,boolean isShare2Cycle);
}
