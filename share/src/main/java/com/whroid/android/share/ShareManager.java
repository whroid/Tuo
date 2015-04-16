package com.whroid.android.share;

import android.app.Activity;
import android.content.Context;

import com.whroid.android.share.sina.SinaShareFactory;
import com.whroid.android.share.weixin.WeixinShareFactory;
import com.whroid.android.share.yixin.YixinShareFactory;

public class ShareManager {
	
    public static ShareFactory getShareFactory(Context context,Share share)
    {
    	ShareFactory factory = null;
    	switch(share)
    	{
    	case WEIXIN:
    		factory = new WeixinShareFactory(context);
    		break;
    	case YIXIN:
    		factory = new YixinShareFactory(context);
    		break;
    	}
    	return factory;
    }
    
    public static SinaShareFactory getSinaShareFactory(Activity context)
    {
    	return new SinaShareFactory(context);
    }
    
    public static WeixinShareFactory getWeixinShareFactory(Activity context)
    {
    	return new WeixinShareFactory(context);
    }

}
