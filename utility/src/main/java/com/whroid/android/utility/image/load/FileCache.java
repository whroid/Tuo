package com.whroid.android.utility.image.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.MappedByteBuffer;

import com.whroid.android.utility.BitmapUtils;
import com.whroid.android.utility.FileUtil;
import com.whroid.android.utility.LogUtil;
import com.whroid.android.utility.StringUtil;
import com.whroid.android.utility.image.ImageObserver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.GetChars;
import android.util.Log;

/**
 * 
 * @文件描述 文件缓存类
 * @author whroid
 * @create 2014-2-17
 */
public class FileCache {

	static String TAG = "FileCache";

	static int IMAGE_MAX_WIDTH = 700; // 图片最大的宽度，如果为0 则表示不限制最大值

	String cache_path = null; // 缓存根目录
	String cache_image_path = null; // 普通图片缓存地址
	String cache_avatar_path = null; // 头像缓存地址

	public static final int CACHE_TYPE_IMAGE = 0x10;
	public static final int CACHE_TYPE_AUDIO = 0x11;
	public static final int CACHE_TYPE_HEAD = 0x12;

	public FileCache(Context context) {
		cache_path = context.getExternalCacheDir().getAbsolutePath().toString();
		cache_image_path = cache_path + File.separator + "image";
		cache_avatar_path = cache_path + File.separator + "avatar";
		initDir(cache_image_path);
		initDir(cache_avatar_path);
	}

	private void initDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 保存普通图片
	 * 
	 * @param filename
	 * @param bitmap
	 */
	public void saveImage(String filename, Bitmap bitmap) {
		if (StringUtil.isEmpty(filename)) {
			Log.e(TAG, "saveImage: filename is null");
			return;
		}
		if (bitmap == null) {
			Log.e(TAG, "saveImage: bitmap is null");
			return;
		}
		String filepath = getCacheImagePath(filename);
		FileUtil.saveBitmap(filepath, bitmap, 90);
	}

	public boolean saveImage(String filename, String srcPath) {
		if (StringUtil.isEmpty(filename)) {
			Log.e(TAG, "saveImage: filename is null");
			return false;
		}
		if (srcPath == null) {
			Log.e(TAG, "saveImage: srcPath is null");
			return false;
		}
		Bitmap bitmap = null;
		try {
			bitmap = obtainBitmap(srcPath);
			String filepath = getCacheImagePath(filename);
			LogUtil.d(TAG, "缓存文件路径：" + filepath);
			boolean bo = FileUtil.saveBitmap(filepath, bitmap, 90);
			BitmapUtils.recycleBitmap(bitmap);
			return bo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 保存头像图片
	 * 
	 * @param filename
	 * @param bitmap
	 */
	public void saveAvater(String filename, Bitmap bitmap) {
		if (StringUtil.isEmpty(filename)) {
			Log.e(TAG, "saveAvater: filename is null");
			return;
		}
		if (bitmap == null) {
			Log.e(TAG, "saveAvater: bitmap is null");
			return;
		}
		String filepath = getCacheAvaterPath(filename);
		FileUtil.saveBitmap(filepath, bitmap, 90);
	}

	private String getCacheImagePath(String filename) {
		return cache_image_path + File.separator + filename;
	}

	private String getCacheAvaterPath(String filename) {
		return cache_avatar_path + File.separator + filename;
	}

	/**
	 * 获取普通图片
	 * 
	 * @param filename
	 * @return
	 */
	public Bitmap obtainImage(String filename) {

		try {
			return obtainBitmap(getCacheImagePath(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError error) {
			Log.e(TAG, "获取图片内存溢出：" + getCacheImagePath(filename));
			return null;
		}
	}

	/**
	 * 获取头像图片
	 * 
	 * @param filename
	 * @return
	 */
	public Bitmap obtainAvatar(String filename) {
		try {
			return obtainBitmap(getCacheAvaterPath(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError error) {
			Log.e(TAG, "获取图片内存溢出：" + getCacheAvaterPath(filename));
			return null;
		}
	}

	public Bitmap obtainBitmap(ImageImpl cache) {
		switch (cache.getType()) {
		case ImageImpl.TYPE_AVATAR:
			return obtainAvatar(cache.getFileCacheName());
		case ImageImpl.TYPE_IMAGE:
			return obtainImage(cache.getFileCacheName());
		}
		return null;
	}

	public String obtainCachePath(ImageImpl cache) {
		String cachePath = "";
		switch (cache.getType()) {
		case ImageImpl.TYPE_AVATAR:
			cachePath = getCacheAvaterPath(cache.getFileCacheName());
		case ImageImpl.TYPE_IMAGE:
			cachePath = getCacheImagePath(cache.getFileCacheName());
		}
		try {
			File file = new File(cachePath);
			if (file != null && file.exists()) {
				return cachePath;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void saveBitmap(ImageImpl cache, Bitmap bitmap) {
		
		if(StringUtil.isEmpty(cache.getFileCacheName()))
		{
			return;
		}
		Log.i(TAG, "将bitmap缓存加入到本地文件中：" + cache.getFileCacheName());
		switch (cache.getType()) {
		case ImageImpl.TYPE_AVATAR:
			saveAvater(cache.getFileCacheName(), bitmap);
			break;
		case ImageImpl.TYPE_IMAGE:
			saveImage(cache.getFileCacheName(), bitmap);
			break;
		}
	}

	/**
	 * /** 从filepath获取bitmap。由于是保存缓存图片，所以认为图片都是合适的。直接读取
	 * 
	 * @param filepath
	 *            图片的路径
	 * @return 获取到的bitmap
	 * @throws java.io.FileNotFoundException
	 *             filepath 不存在时
	 */
	private Bitmap obtainBitmap(String filepath) throws FileNotFoundException {
		if (StringUtil.isEmpty(filepath)) {
			return null;
		}
		File file = new File(filepath);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		FileInputStream fis = new FileInputStream(file);
		options.inJustDecodeBounds = false; // 获取这个图片的宽和高
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;

		return BitmapFactory.decodeStream(fis, null, options);
	}
}
