package com.whroid.android.utility.image.load;

import java.util.Collection;

import com.whroid.android.utility.LogUtil;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

public class LruMemoryCache implements MemoryCacheAware<String, Bitmap>{

	public static final String TAG = LruMemoryCache.class.getSimpleName();
	private LruCache<String, Bitmap> lruCache;
	
	MemoryCacheAware<String, Bitmap> softMemoryCache;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public LruMemoryCache(int memoryCacheSize)
	{
		LogUtil.i(TAG, "lruMemoryCache:memoryCacheSize:"+memoryCacheSize);
		lruCache = new LruCache<String, Bitmap>(memoryCacheSize){
			
			protected int sizeOf(String key, Bitmap value) {
				int verson = Build.VERSION.SDK_INT;
				if (verson >= Build.VERSION_CODES.HONEYCOMB_MR1) {
					return value.getByteCount();
				} else {
					return super.sizeOf(key, value);
				}
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				 LogUtil.i(TAG, "LruMemoryCache:从lru缓存移除:"+key+" oldValue:"+oldValue+" newValue:"+newValue);
				// 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
				if(softMemoryCache != null)
				{
					softMemoryCache.put(key, oldValue);
				}
			}
		};
	}
	public void addSoftCacheAware(MemoryCacheAware<String, Bitmap> cache)
	{
		softMemoryCache = cache;
	}
	
	@Override
	public boolean put(String key, Bitmap value) {
		
		  synchronized (lruCache) {
			     if (lruCache.get(key) == null && value != null) {
			    	 LogUtil.i(TAG, "LruMemoryCache:放入lru缓存中："+key+" putSize:"+lruCache.putCount()+" bitmap:"+value);
			    	 lruCache.put(key, value);
			   }}
			return true;
	}

	@Override
	public Bitmap get(String key) {
		
		Bitmap bitmap;
		synchronized (lruCache) {
			bitmap = lruCache.get(key);
			if (bitmap != null) {
				// 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
				lruCache.remove(key);
				lruCache.put(key, bitmap);
			}
		}
		LogUtil.d(TAG, "得到bitmap:"+key+" bitmap："+bitmap);
		return bitmap;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		lruCache.remove(key);
	}

	@Override
	public Collection<String> keys() {
		return null;
	}
	public int size()
	{
		return lruCache.size();
	}
	public int maxSize()
	{
		return lruCache.maxSize();
	}

	@Override
	public void clear() {
		lruCache.evictAll();
	}

}
