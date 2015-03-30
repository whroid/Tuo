package com.whroid.android.utility.image.load;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.whroid.android.utility.InmovationLogger;
import com.whroid.android.utility.LogUtil;
import com.whroid.android.utility.StringUtil;
import com.whroid.android.utility.image.ImageObserver;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

/**
 * 异步获取图片类
 * 
 */
public class AsyncImageLoaderManager {

	public static final String TAG = "AsyncImageLoaderManagercall";
	InmovationLogger mLogger = InmovationLogger.getLogger(TAG);
	ExecutorService mExecutorService;
	static FileCache mFileCache;

	public AsyncImageLoaderManager(Context context) {

		int nThreads = Runtime.getRuntime().availableProcessors();
		mExecutorService = Executors.newFixedThreadPool(nThreads);
		mFileCache = new FileCache(context);
	}

	public AsyncImageLoaderManager() {
		int nThreads = Runtime.getRuntime().availableProcessors();
		mExecutorService = Executors.newFixedThreadPool(nThreads);
	}

	public static void init(Context context) {
		// 得到应用的实际可用内存大小
		int Memory = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		ImageMemoryCacheManager.init(Memory / 8);
		mFileCache = new FileCache(context);
	}

	/**
	 * imageurl是图片的直接路径
	 * 
	 * @param imageUrl
	 * @param imageCallback
	 * @return
	 */
	public Bitmap loadImage(final String imgurl,
			final ImageLoadCallBack imageCallback) {
		ImageObserver mImageObserver = new ImageObserver(imgurl);
		Bitmap bitmap = ImageMemoryCacheManager.getInstance()
				.getBitmapFromMemoryCache(mImageObserver.getMemoryCacheKey());

		if (bitmap != null) {
			return bitmap;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((ImageObserver) message.obj);
			}
		};

		Runnable loadTask;
		if (!URLUtil.isValidUrl(imgurl)) {
			loadTask = new LoaderImageSdTask(mImageObserver, handler);
		} else {
			loadTask = new LoaderImageTask(mImageObserver, handler);
		}
		mExecutorService.execute(loadTask);
		return null;
	}

	/**
	 * @param mImageObserver
	 * @param imageCallback
	 * @return
	 */
	public Bitmap loadImage(ImageObserver mImageObserver,
			final ImageLoadCallBack imageCallback) {

		Bitmap bitmap = ImageMemoryCacheManager.getInstance()
				.getBitmapFromMemoryCache(mImageObserver.getMemoryCacheKey());
		if (bitmap != null) {
			return bitmap;
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((ImageObserver) message.obj);
			}
		};

		Runnable loadTask;
		if (!mImageObserver.isValidUrl()) {
			loadTask = new LoaderImageSdTask(mImageObserver, handler);
		} else {
			loadTask = new LoaderImageTask(mImageObserver, handler);
		}
		mExecutorService.execute(loadTask);
		return null;
	}

	public void loadImage(final String imgurl, final ImageView imageview) {
		ImageObserver mImageObserver = new ImageObserver(imgurl);
		Bitmap bitmap = ImageMemoryCacheManager.getInstance()
				.getBitmapFromMemoryCache(mImageObserver.getMemoryCacheKey());

		if (bitmap != null) {
			imageview.setImageBitmap(bitmap);
			return;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				ImageObserver observer = (ImageObserver) message.obj;
				if (imageview != null && observer.getBitmap() != null) {
					imageview.setImageBitmap(observer.getBitmap());
				}
			}
		};

		Runnable loadTask;
		if (!mImageObserver.isValidUrl()) {
			loadTask = new LoaderImageSdTask(mImageObserver, handler);
		} else {
			loadTask = new LoaderImageTask(mImageObserver, handler);
		}
		mExecutorService.execute(loadTask);
		return;
	}

	public void loadImage(String url, ImageView imageview, final View parentView) {
		ImageObserver observer = new ImageObserver(url);
		imageview.setTag(observer.getTag());

		Bitmap bitmap = loadImage(observer, new ImageLoadCallBack() {
			@Override
			public void imageLoaded(ImageObserver observer) {
				super.imageLoaded(observer);
				ImageView image = (ImageView) parentView
						.findViewWithTag(observer.getTag());
				if (image != null && observer.getBitmap() != null) {
					image.setImageBitmap(observer.getBitmap());
				}
			}
		});
		if (bitmap != null)
			imageview.setImageBitmap(bitmap);

	}

	public void loadImage(ImageObserver observer, ImageView imageview,
			final View parentView) {
		imageview.setTag(observer.getTag());
		Bitmap bitmap = loadImage(observer, new ImageLoadCallBack() {
			@Override
			public void imageLoaded(ImageObserver observer) {
				super.imageLoaded(observer);
				ImageView image = (ImageView) parentView
						.findViewWithTag(observer.getTag());
				if (image != null && observer.getBitmap() != null) {
					image.setImageBitmap(observer.getBitmap());
				}
			}
		});
		if (bitmap != null)
			imageview.setImageBitmap(bitmap);

	}

	/**
	 * 包含文件缓存的，缓存机制
	 * 
	 * @param mImageObserver
	 *            需要确定图片的id，和
	 * @param imageCallback
	 * @return
	 */
	public Bitmap loadImageWithFile(ImageObserver mImageObserver,
			final ImageLoadCallBack imageCallback) {

		if (mImageObserver.getType() <= 0)
			mImageObserver.setType(ImageObserver.TYPE_IMAGE);
		Bitmap bitmap = ImageMemoryCacheManager.getInstance()
				.getBitmapFromMemoryCache(mImageObserver.getMemoryCacheKey());
		if (bitmap != null) {
			mImageObserver.setBitmap(bitmap);
			return bitmap;
		}
		// 判断文件中是否存在
		if (mFileCache != null) {
			String path = mFileCache.obtainCachePath(mImageObserver);
			if (!StringUtil.isEmpty(path)) {
				LogUtil.i(TAG + "cache",
						"本地缓存存在：" + mImageObserver.getMemoryCacheKey()
								+ " path:" + path);
				mImageObserver.setUrl(path);
			} else {
				LogUtil.i(TAG + "cache",
						"本地没有缓存网络获取：" + mImageObserver.getMemoryCacheKey());
			}
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((ImageObserver) message.obj);
			}
		};

		Runnable loadTask;
		if (!mImageObserver.isValidUrl()) {
			loadTask = new LoaderImageSdTask(mImageObserver, handler);
		} else {
			loadTask = new LoaderImageTask(mImageObserver, handler);
		}
		mExecutorService.execute(loadTask);
		return null;
	}

	/**
	 * 头像的加载方法，加载后的图片会保存在文件中
	 * 
	 * @param observer
	 *            需要传入图片保存的名称id，和图片的加载路径
	 * @param imageview
	 * @param parentView
	 *            imageview 所属的父控件
	 */
	public void loadImageHeadWithFile(ImageObserver observer,
			ImageView imageview, final View parentView) {
		observer.setType(ImageObserver.TYPE_AVATAR);
		loadImageWithFile(observer, imageview, parentView);

	}

	public Bitmap loadImageHeadWithFile(String id, String url,
			final ImageLoadCallBack imageCallback) {
		ImageObserver observer = new ImageObserver(id, url);
		observer.setType(ImageObserver.TYPE_AVATAR);
		return loadImageWithFile(observer, imageCallback);

	}

	/**
	 * 普通图片的加载方法，加载后的图片保存在文件中
	 * 
	 * @param observer
	 *            需要传入图片保存的名称id，和图片的加载路径
	 * @param imageview
	 * @param parentView
	 *            imageview 所属的父控件
	 */
	public void loadImageWithFile(ImageObserver observer, ImageView imageview,
			final View parentView) {
		if (mFileCache == null && imageview != null) {
			mFileCache = new FileCache(imageview.getContext());
		}
		imageview.setTag(observer.getTag());
		observer.setType(ImageObserver.TYPE_IMAGE);
		Bitmap bitmap = loadImageWithFile(observer, new ImageLoadCallBack() {
			@Override
			public void imageLoaded(ImageObserver observer) {
				super.imageLoaded(observer);
				ImageView image = (ImageView) parentView
						.findViewWithTag(observer.getTag());
				if (image != null && observer.getBitmap() != null) {
					image.setImageBitmap(observer.getBitmap());
				}
			}
		});
		if (bitmap != null)
			imageview.setImageBitmap(bitmap);

	}

	public void loadImageHeadWithFile(String id, String url,
			ImageView imageview, final View parentView) {
		loadImageHeadWithFile(new ImageObserver(id, url), imageview, parentView);
	}

	public void loadImageWithFile(String id, String url, ImageView imageview,
			final View parentView) {
		loadImageWithFile(new ImageObserver(id, url), imageview, parentView);
	}

	public Bitmap loadImageWithFile(String id, String url,
			final ImageLoadCallBack imageCallback) {
		ImageObserver observer = new ImageObserver(id, url);
		observer.setType(ImageObserver.TYPE_IMAGE);
		return loadImageWithFile(observer, imageCallback);
	}

	/**
	 * 
	 * @param filename
	 *            新的图片名称
	 * @param srcPath
	 *            本地图片地址
	 */
	public void saveBitmapToFileCache(String filename, String srcPath) {
		// 判断文件中是否存在
		if (mFileCache != null) {
			mFileCache.saveImage(filename, srcPath);
		}
	}

	class LoaderImageTask implements Runnable {

		Handler handler;
		ImageObserver mImageObserver;

		public LoaderImageTask(ImageObserver mImageObserver, Handler handler) {
			this.handler = handler;
			this.mImageObserver = mImageObserver;
		}

		@Override
		public void run() {
			Bitmap bitmap = ImageMemoryCacheManager.getInstance()
					.getBitmapFromMemoryCache(
							mImageObserver.getMemoryCacheKey());
			LogUtil.i(
					TAG,
					"LoaderImageTask 线程开始获取网络图片:"
							+ mImageObserver.getMemoryCacheKey());

			if (bitmap == null) {
				bitmap = ImageLoader.loadImageFromUrl(mImageObserver.getUrl());
			} else {
				LogUtil.i(
						TAG,
						"网络线程中有缓存图片取消下载： :"
								+ mImageObserver.getMemoryCacheKey());
			}
			if (mImageObserver.getMemoryCacheKey() != null && bitmap != null) {
				LogUtil.i(
						TAG,
						"LoaderImageTask mImageObserver.getMemoryCacheKey():"
								+ mImageObserver.getMemoryCacheKey());
				ImageMemoryCacheManager.getInstance().addBitmapToMemoryCache(
						mImageObserver.getMemoryCacheKey(), bitmap);
			}
			mImageObserver.setBitmap(bitmap);
			Message message = handler.obtainMessage(0, mImageObserver);
			handler.sendMessage(message);

			if (bitmap != null && mFileCache != null)
				mFileCache.saveBitmap(mImageObserver, bitmap);
		}

	}

	class LoaderImageSdTask implements Runnable {
		Handler handler;

		ImageObserver mImageObserver;

		public LoaderImageSdTask(ImageObserver mImageObserver, Handler handler) {
			this.handler = handler;
			this.mImageObserver = mImageObserver;
		}

		@Override
		public void run() {

			Bitmap bitmap = ImageMemoryCacheManager.getInstance()
					.getBitmapFromMemoryCache(
							mImageObserver.getMemoryCacheKey());
			LogUtil.i(
					TAG,
					"LoaderImageSdTask 线程开始获取本地图片："
							+ mImageObserver.getMemoryCacheKey() + " path:"
							+ mImageObserver.getUrl());
			if (bitmap == null) {
				try {
					bitmap = ImageLoader.loadImageFromSDCard(mImageObserver
							.getUrl());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.e(TAG, "图片路径未找到：" + mImageObserver.getUrl());
				} catch (OutOfMemoryError error) {
					Log.e(TAG, "图片内存溢出：" + mImageObserver.getUrl());
					System.gc();
				}
			} else {
				LogUtil.i(
						TAG,
						"本地线程--缓存中有图片取消sd卡下载："
								+ mImageObserver.getMemoryCacheKey());
			}
			if (mImageObserver.getMemoryCacheKey() != null && bitmap != null) {
				ImageMemoryCacheManager.getInstance().addBitmapToMemoryCache(
						mImageObserver.getMemoryCacheKey(), bitmap);
			}
			mImageObserver.setBitmap(bitmap);
			mLogger.d("从sd:" + mImageObserver);
			Message message = handler.obtainMessage(0, mImageObserver);
			handler.sendMessage(message);
		}
	}
}
