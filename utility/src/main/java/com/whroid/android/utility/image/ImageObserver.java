package com.whroid.android.utility.image;

import java.io.File;
import java.io.Serializable;

import com.whroid.android.utility.BitmapUtils;
import com.whroid.android.utility.StringUtil;
import com.whroid.android.utility.image.load.ImageImpl;

import android.graphics.Bitmap;
import android.webkit.URLUtil;

public class ImageObserver extends ImageImpl implements Serializable {

	public static boolean isCanObtainBitmap(ImageObserver image) {
		if (image == null || image.bitmap == null) {
			return false;
		}
		return true;
	}

	/**
	 * 图片路径是否和合法的本地路径，通过判断路径存在和文件大小
	 * 主要用于拍照后，返回时再onActivityresult中判断获取的图片是否成功。
	 * @return
	 */
	public boolean isValidSdPath() {
		if (StringUtil.isEmpty(url)) {
			return false;
		}
		try {
			File file = new File(url);
			if (file.exists() && file.length() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * scheme is http,
	 * 
	 * @return
	 */
	public boolean isValidUrl() {
		return URLUtil.isValidUrl(getUrl());
	}

	Bitmap bitmap;

	public ImageObserver(String url, Bitmap bitmap) {
		super(url);
		this.bitmap = bitmap;
	}

	public ImageObserver(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public ImageObserver(String id, String url) {
		super(id, url);
	}

	public ImageObserver(ImageImpl image) {
		this.id = image.getId();
		this.tag = image.getTag();
		this.type = image.type;
		this.url = image.getUrl();
	}

	public ImageObserver(String url) {
		super(url);
	}

	public ImageObserver() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463678220207486207L;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap value) {
		this.bitmap = value;
	}

	/**
	 * recycle bitmap if unused
	 */
	public void recycle() {
		BitmapUtils.recycleBitmap(bitmap);
		bitmap = null;
	}
}
