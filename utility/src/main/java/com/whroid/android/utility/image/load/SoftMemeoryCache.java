package com.whroid.android.utility.image.load;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;

public class SoftMemeoryCache implements MemoryCacheAware<String, Bitmap>{

	LinkedHashMap< String , SoftReference<Bitmap>> cacheMap;
	float  loadFactor = 0.75f;
	int memoyCacheSize = 20;
	public SoftMemeoryCache(int Size)
	{
		if(Size != 0)
		{
			this.memoyCacheSize = Size;
		}
		cacheMap = new LinkedHashMap<String, SoftReference<Bitmap>>(
				memoyCacheSize, loadFactor, true) {
			private static final long serialVersionUID = 6040103833179403725L;

			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				if (size() > memoyCacheSize) {
					return true;
				}
				return false;
			}
		};
	}
	@Override
	public boolean put(String key, Bitmap value) {
		if(value == null)
		{
			return false;
		}
		cacheMap.put(key, new SoftReference<Bitmap>(value));
		return  true;
	}

	@Override
	public Bitmap get(String key) {
		// TODO Auto-generated method stub
		synchronized (cacheMap) {
			SoftReference<Bitmap> softRef = cacheMap.get(key);
			if(softRef != null)
			{
				return softRef.get();
			}
		}
		
		return null;
	}

	@Override
	public void remove(String key) {
		cacheMap.remove(key);
	}

	@Override
	public Collection<String> keys() {
		// TODO Auto-generated method stub
		return cacheMap.keySet();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		cacheMap.clear();
	}

}
