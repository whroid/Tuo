package com.whroid.android.share.sina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.whroid.android.share.ShareFactory;

public class SinaShareFactory extends ShareFactory implements IWeiboHandler.Response{
	private static final int THUMB_SIZE = 150;
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI  mWeiboShareAPI = null;

    
    Activity  context;
   public SinaShareFactory(Activity context)
   {
	   this.context = context;
       
       // 创建微博分享接口实例
       mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, SinaConstants.APP_KEY);
       
       // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
       // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
       // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
       mWeiboShareAPI.registerApp();
       
		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
       // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
       // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
       // 失败返回 false，不调用上述回调
   }
   
   public void handleWeiboResponse(Intent intent)
   {
	   mWeiboShareAPI.handleWeiboResponse(intent, this);
   }
   public void handleWeiboResponse(Intent intent,Response response)
   {
	   mWeiboShareAPI.handleWeiboResponse(intent, response);
   }
	    
	@Override
	public void shareText(String text, boolean isShare2Cycle) {
		
		
		 TextObject textObject = new TextObject();
	        textObject.text = text;
	        
		  WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
	            weiboMessage.textObject = textObject;
	        
		shareMutilMessage(weiboMessage);
	}

	@Override
	public void shareImage(String url, boolean isShare2Cycle) {
		
	}

	@Override
	public void shareImage(Bitmap bitmap, boolean isShare2Cycle) {
		
	}

	@Override
	public void shareHtml(String url, String title, String description,
			Bitmap bitmap, boolean isShare2Cycle) {
		
		
		        WebpageObject mediaObject = new WebpageObject();
		        mediaObject.identify = Utility.generateGUID();
		        mediaObject.title = title;
		        mediaObject.description = description;
		        
		        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE,
						THUMB_SIZE, true);
				bitmap.recycle();
		        // 设置 Bitmap 类型的图片到视频对象里
		        mediaObject.setThumbImage(thumbBmp);
		        mediaObject.actionUrl = url;
		        mediaObject.defaultText = title;
		        
		        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		        weiboMessage.mediaObject = mediaObject;
		        shareMutilMessage(weiboMessage);
		        
	}

	@Override
	public void sharevideo(String url, String title, String description,
			Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sharemusic(String musicurl, String musicdataurl, String title,
			String description, Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		
	}

	
	private void shareMutilMessage( WeiboMultiMessage weiboMessage)
	{
		 // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        
		 AuthInfo authInfo = new AuthInfo(context, SinaConstants.APP_KEY, SinaConstants.REDIRECT_URL, SinaConstants.SCOPE);
         Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context.getApplicationContext());
         String token = "";
         if (accessToken != null) {
             token = accessToken.getToken();
         }
         mWeiboShareAPI.sendRequest(context, request, authInfo, token, new WeiboAuthListener() {
             
             @Override
             public void onWeiboException( WeiboException arg0 ) {
             }
             
             @Override
             public void onComplete( Bundle bundle ) {
                 // TODO Auto-generated method stub
                 Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                 AccessTokenKeeper.writeAccessToken(context.getApplicationContext(), newToken);
             //    Toast.makeText(context.getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
             }
             
             @Override
             public void onCancel() {
             }
         });
	}

	//这个需要定义到调用的Activity中，在这里不会进入
	@Override
	public void onResponse(BaseResponse baseResp) {
		 switch (baseResp.errCode) {
	        case WBConstants.ErrorCode.ERR_OK:
	            Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
	            break;
	        case WBConstants.ErrorCode.ERR_CANCEL:
	            Toast.makeText(context, "分享取消", Toast.LENGTH_LONG).show();
	            break;
	        case WBConstants.ErrorCode.ERR_FAIL:
	            Toast.makeText(context, 
	                    "分享失败" + "Error Message: " + baseResp.errMsg, 
	                    Toast.LENGTH_LONG).show();
	            break;
	        }
	}
}
