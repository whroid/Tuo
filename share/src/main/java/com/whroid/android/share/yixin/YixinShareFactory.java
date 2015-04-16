package com.whroid.android.share.yixin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.whroid.android.share.ShareConfig;
import com.whroid.android.share.ShareFactory;
import com.whroid.android.share.util.StringUtil;
import com.whroid.android.share.util.ToastUtil;

import im.yixin.sdk.api.IYXAPI;
import im.yixin.sdk.api.SendMessageToYX;
import im.yixin.sdk.api.YXAPIFactory;
import im.yixin.sdk.api.YXImageMessageData;
import im.yixin.sdk.api.YXMessage;
import im.yixin.sdk.api.YXMusicMessageData;
import im.yixin.sdk.api.YXTextMessageData;
import im.yixin.sdk.api.YXVideoMessageData;
import im.yixin.sdk.api.YXWebPageMessageData;
import im.yixin.sdk.util.BitmapUtil;

public class YixinShareFactory extends ShareFactory {

	private IYXAPI api;

	Context context;

	public YixinShareFactory(Context context) {
		api = YXAPIFactory.createYXAPI(context, ShareConfig.YIXIN_APPID);
		api.registerApp();
	}

	public void shareText(String text, boolean isShare2Cycle) {
		// 初始化一个YXTextObject对象
		YXTextMessageData textObj = new YXTextMessageData();
		textObj.text = text;

		// 用YXTextObject对象初始化一个YXMessage对象
		YXMessage msg = new YXMessage();
		msg.messageData = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "title is ignored";
		msg.description = text;
		// 构造一个Req对象
		SendMessageToYX.Req req = new SendMessageToYX.Req();
		// transaction字段用于唯一标识一个请求
		req.transaction = buildTransaction("text");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		// 调用api接口发送数据到易信
		api.sendRequest(req);
	}

	@Override
	public void shareImage(String url, boolean isShare2Cycle) {
		if (StringUtil.isEmpty(url)) {
			ToastUtil.make(context, "分享图片路径为空，无法分享");
			return;
		}

		Bitmap bmp = null;
		Bitmap thumbBmp = null;
		int THUMB_SIZE = 80;
		if (StringUtil.isValid(url)) {
			try {
				bmp = BitmapFactory.decodeStream(new URL(url).openStream());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			THUMB_SIZE = 200;
		} else {
			File file = new File(url);
			if (!file.exists()) {
				ToastUtil.make(context, "图片路径不存在：" + url);
				return;
			}
			bmp = BitmapFactory.decodeFile(url);
		}

		thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		if (bmp != null)
			bmp.recycle();

		YXImageMessageData imgObj = new YXImageMessageData();
		imgObj.imagePath = url;

		YXMessage msg = new YXMessage();
		msg.messageData = imgObj;

		msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

		SendMessageToYX.Req req = new SendMessageToYX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		api.sendRequest(req);
	}

	@Override
	public void shareImage(Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		if (bitmap == null) {
			ToastUtil.make(context, "bitmap 为空，取消分享");
			return;
		}
		YXImageMessageData imgObj = new YXImageMessageData(bitmap);
		YXMessage msg = new YXMessage();
		msg.messageData = imgObj;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
		bitmap.recycle();
		msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true); // 设置缩略图

		SendMessageToYX.Req req = new SendMessageToYX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		api.sendRequest(req);

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	@Override
	public void shareHtml(String url,String title,String descriptions,Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		YXWebPageMessageData webpage = new YXWebPageMessageData();
		webpage.webPageUrl = url;
		YXMessage msg = new YXMessage(webpage);
		// msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		msg.title = title;
		// msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		msg.description = descriptions;
		Bitmap thumb =bitmap;
		msg.thumbData = BitmapUtil.bmpToByteArray(thumb, false);
		SendMessageToYX.Req req = new SendMessageToYX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		api.sendRequest(req);
	}

	@Override
	public void sharevideo(String url, String title, String description,
			Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		YXVideoMessageData video = new YXVideoMessageData();
		video.videoUrl =  url;
		YXMessage msg = new YXMessage(video);
		msg.title = title;
		msg.description = description;
		Bitmap thumb = bitmap;
		msg.thumbData = BitmapUtil.bmpToByteArray(thumb, true);
		SendMessageToYX.Req req = new SendMessageToYX.Req();
		req.transaction = buildTransaction("video");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		api.sendRequest(req);

		// finish();
	}

	@Override
	public void sharemusic(String musicurl, String musicdataurl, String title,
			String description, Bitmap bitmap, boolean isShare2Cycle) {
		// TODO Auto-generated method stub
		YXMusicMessageData music = new YXMusicMessageData();
		music.musicUrl = musicurl;
		music.musicDataUrl = musicdataurl;
		
		// 低宽带s
		//music.musicLowBandUrl = "http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=978FP00H00014AED";
		//music.musicLowBandDataUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
		
		YXMessage msg = new YXMessage();
		msg.messageData = music;
		// msg.title = "Music Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		msg.title = title;
		// msg.description = "Music Album Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		msg.description =description;
		Bitmap thumb = bitmap;
		msg.thumbData = BitmapUtil.bmpToByteArray(thumb, true);

		SendMessageToYX.Req req = new SendMessageToYX.Req();
		req.transaction = buildTransaction("music");
		req.message = msg;
		req.scene = isShare2Cycle ? SendMessageToYX.Req.YXSceneTimeline
				: SendMessageToYX.Req.YXSceneSession;
		api.sendRequest(req);

		// finish();
	}

}
