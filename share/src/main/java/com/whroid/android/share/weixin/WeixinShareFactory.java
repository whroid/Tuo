package com.whroid.android.share.weixin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.whroid.android.share.ShareFactory;
import com.whroid.android.share.util.WeixinUtil;

public class WeixinShareFactory extends ShareFactory {
	private static final int THUMB_SIZE = 150;
	private IWXAPI api;
	Context context;

	public WeixinShareFactory(Context context) {
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(context, WeixinConstant.APPID, false);
		api.registerApp(WeixinConstant.APPID);
	}
	public void handleIntent(Intent intent,IWXAPIEventHandler handler)
	{
		api.handleIntent(intent, handler);
	}

	@Override
	public void shareText(String text, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = text;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;

		// 调用api接口发送数据到微信
		api.sendReq(req);
	}

	@Override
	public void shareImage(String url, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		try {
			WXImageObject imgObj = new WXImageObject();
			Bitmap bmp = null;
			if (URLUtil.isNetworkUrl(url)) {
				imgObj.imageUrl = url;

				bmp = BitmapFactory.decodeStream(new URL(url).openStream());

			} else {
				File file = new File(url);
				if (!file.exists()) {
					String tip = "图片路径不存在";
					Toast.makeText(context, tip + " path = " + url,
							Toast.LENGTH_LONG).show();
					return;
				}
				imgObj.setImagePath(url);
				bmp = BitmapFactory.decodeFile(url);
			}

			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;

			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
					THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = WeixinUtil.bmpToByteArray(thumbBmp, true);

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("img");
			req.message = msg;
			req.scene = isShare2Cycle ? SendMessageToWX.Req.WXSceneTimeline
					: SendMessageToWX.Req.WXSceneSession;
			api.sendReq(req);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * note：bitmap 会被释放掉
	 */
	@Override
	public void shareImage(Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		WXImageObject imgObj = new WXImageObject(bitmap);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE,
				THUMB_SIZE, true);
		bitmap.recycle();
		msg.thumbData = WeixinUtil.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	@Override
	public void shareHtml(String url, String title, String description,
			Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = description;
		Bitmap thumb = bitmap;
		msg.thumbData = WeixinUtil.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);

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

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
	
	
	//分享返回回调方法 ，需要在activity中填写
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
        String result = "";
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "发送被拒绝";
			break;
		default:
			result = "发送返回";
			break;
		}
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}
}
