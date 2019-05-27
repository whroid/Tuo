package com.whroid.android.utility.image.load;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.whroid.android.utility.HttpUtils;
import com.whroid.android.utility.WLogger;
import com.whroid.android.utility.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

public class ImageLoader {

	public static final String TAG = "ImageLoader";

	static WLogger mLogger = WLogger.getLogger(TAG);
	private static final int REQUEST_TIMEOUT = 30 * 1000;
	private static final int SO_TIMEOUT = 30 * 1000;

	/**
	 * 从网络获取图片
	 * 
	 * @param url
	 *            图片的url
	 * @return 获取的图片
	 */
	public static Bitmap loadImageFromUrl(String url) {

		if(TextUtils.isEmpty(url))
		{
			return null;
		}
		Bitmap bitmap = null;
		HttpGet httpRequest = new HttpGet(url);
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);

		try {
			HttpResponse response = client.execute(httpRequest);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				bitmap = loadImageFromUrl(response.getEntity().getContent(),
						700);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError error) {
			mLogger.d("获取图片内存溢出：" + url);
			System.gc();
		} catch (Exception e) {
			mLogger.e("获取图片异常", e);
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
				client = null;
			}
		}
		return bitmap;
	}

	// //加载手机图片
	public static Bitmap loadImageFromUrl(InputStream input, int width)
			throws FileNotFoundException {

		byte[] datas;
		ByteArrayInputStream bis = null, biss = null;
		try {
			datas = HttpUtils.readStream(input);
			bis = new ByteArrayInputStream(datas);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true; // 获取这个图片的宽和高
			BitmapFactory.decodeStream(bis, null, options);
			options.inJustDecodeBounds = false;
			// 先确定缩放比例
			int outWidth = options.outWidth;
		
			int scale = outWidth / width;
			if (scale < 1) {
				scale = 1;
			}
			LogUtil.d(TAG, "图片的原始高宽：" + options.outWidth + " height:"
					+ options.outHeight+"scale:"+scale);
			biss = new ByteArrayInputStream(datas);
			options.inJustDecodeBounds = false; // 获取这个图片的宽和高
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inSampleSize = scale;
			return BitmapFactory.decodeStream(biss, null, options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (biss != null) {
					biss.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// //加载手机图片
	public static Bitmap loadImageFromSDCard(String path, int width)
			throws FileNotFoundException {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 获取这个图片的宽和高
		BitmapFactory.decodeFile(path, options);// 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 先确定缩放比例
		int outWidth = options.outWidth;
		
		int scale = outWidth / width;
		if (scale < 1) {
			scale = 1;
		}
		options.inJustDecodeBounds = false; // 获取这个图片的宽和高
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = scale;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap loadImageFromSDCard(String path)
			throws FileNotFoundException {
		if(TextUtils.isEmpty(path))
		{
			return null;
		}
		return loadImageFromSDCard(path, 700);
	}

}
