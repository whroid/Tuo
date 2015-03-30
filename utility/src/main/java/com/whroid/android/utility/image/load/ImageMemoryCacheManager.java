package com.whroid.android.utility.image.load;


import com.whroid.android.utility.LogUtil;

import android.graphics.Bitmap;

/**
 * 图片缓存回收 帮助类
 * 
 * @author Administrator
 * 
 */
public class ImageMemoryCacheManager {
	
	public static final String TAG = ImageMemoryCacheManager.class.getSimpleName();

	private static ImageMemoryCacheManager imgMemoryHelper;

	/**
	 * * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。 *
	 * 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
	 */
	private static final int SOFT_CACHE_SIZE = 30;

	private LruMemoryCache mLruMemoryCache;
	private static SoftMemeoryCache mSoftMemoryCache ;
	
	private static int MemorySize = 8;//用于图片缓存的内存大小。单位是(M)
	public static void init(int Memory)
	{
		if(Memory>16)
		{
			Memory = 16;
		}
	     MemorySize = Memory;
	}
	
	/**
	 * 用于图片缓存的内存大小。单位是(M)
	 * @param memorySize
	 */
	private ImageMemoryCacheManager() {
//		
		// 采用有效内存的1/8来存放缓存图片
		int memoryCacheSize = 1024 * 1024 * MemorySize;

		mLruMemoryCache = new LruMemoryCache(memoryCacheSize);
		mSoftMemoryCache = new SoftMemeoryCache(SOFT_CACHE_SIZE);
		mLruMemoryCache.addSoftCacheAware(mSoftMemoryCache);
	}

	public static ImageMemoryCacheManager getInstance() {
		if (imgMemoryHelper == null) {
			imgMemoryHelper = new ImageMemoryCacheManager();
		}
		return imgMemoryHelper;
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		mLruMemoryCache.put(key, bitmap);
		LogUtil.i(TAG, "内存缓存情况---：lru缓存：size:"+mLruMemoryCache.size() +" soft缓存：countSize:"+mSoftMemoryCache.keys().size());
	}

	public Bitmap getBitmapFromMemoryCache(String key) {
		if (key == null) {
			return null;
		}
		Bitmap bitmap = mLruMemoryCache.get(key);
		if(bitmap == null)
		{
			bitmap = mSoftMemoryCache.get(key);
			if(bitmap != null)
			{
				mLruMemoryCache.put(key, bitmap);
				mSoftMemoryCache.remove(key);
			}
		}
		LogUtil.i(TAG, "从内存管理中通过key获取bitmap:"+key+" bitmap:"+bitmap);
		return bitmap;
	}

	
	public void clearCache() {
		mLruMemoryCache.clear();
		mSoftMemoryCache.clear();
	}

}
