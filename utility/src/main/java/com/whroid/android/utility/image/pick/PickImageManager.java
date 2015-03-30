package com.whroid.android.utility.image.pick;

import java.io.File;

import com.whroid.android.utility.BitmapUtils;
import com.whroid.android.utility.LogUtil;
import com.whroid.android.utility.ToastUtils;
import com.whroid.android.utility.image.ImageObserver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

/**
 * 从本地获取图片，或者拍照方式
 * 
 * @author whroid
 * 
 */
public class PickImageManager {

	public static final String TAG = PickImageManager.class.getSimpleName();
	public static final int IMAGE_PHOTO = 0x112;// 从相册选取
	public static final int IMAGE_CAMERA = 0x113;// 拍照选取
	public static final int IMAGE_CROP = 0x114;// 切图

	private static String cameraPath = Environment
			.getExternalStorageDirectory() + "/camera";
	private static String systemRootPath = "";

	private static int mObtainImageMaxWidth = 720;// 获取图片后，返回的bitmap的最大宽度

	private static boolean isCopyPickImage = true;// 返回的是否是拷贝后的图片地址

	public static void setObtainImageMaxWidth(int maxWidth) {
		mObtainImageMaxWidth = maxWidth;
	}

	public static void setIsCopyPickImage(boolean isCopy) {
		isCopyPickImage = isCopy;
	}

	/**
	 * 请确保文件可以创建，也就是目录存在
	 * 
	 * @param initcamera
	 */
	public static void initCameraImage(String initcamera) {
		if (!TextUtils.isEmpty(initcamera))
			cameraPath = initcamera;
	}

	public void setCameraPhotoName(String cameraName) {
		if (!TextUtils.isEmpty(cameraName)) {
			cameraPath = systemRootPath + File.separator + "c" + cameraName;
		}
	}

	Fragment mFragment;
	Activity mActivity;

	public PickImageManager(Fragment fragment) {
		this.mFragment = fragment;
		this.mActivity = mFragment.getActivity();
		systemRootPath = mActivity.getExternalCacheDir().getAbsolutePath();
		cameraPath = mActivity.getExternalCacheDir() + File.separator + "c"
				+ System.currentTimeMillis();
	}

	public PickImageManager(Activity activity) {
		this.mActivity = activity;
		cameraPath = mActivity.getExternalCacheDir() + File.separator + "c"
				+ System.currentTimeMillis();
		
		if(mActivity == null)
		{
			LogUtil.e(TAG, "pickImageManger activity is null");
		
		}
		LogUtil.e(TAG,  "dir:"+mActivity.getExternalCacheDir());
		systemRootPath = mActivity.getExternalCacheDir().getAbsolutePath();
	}

	public void pickImageFromPhoto() {

		Intent inImgPick = PickIntent.getImgSelectIntentN();
		if (mFragment != null) {
			mFragment.startActivityForResult(inImgPick,
					PickImageManager.IMAGE_PHOTO);
		} else {
			mActivity.startActivityForResult(inImgPick,
					PickImageManager.IMAGE_PHOTO);
		}
	}

	public void pickCropImageFromPhoto(Uri uri, int width) {
		Intent inImgPick = PickIntent.getCropImageIntent(uri, width);
		if (mFragment != null) {
			mFragment.startActivityForResult(inImgPick,
					PickImageManager.IMAGE_CROP);
		} else {
			mActivity.startActivityForResult(inImgPick,
					PickImageManager.IMAGE_CROP);
		}
	}

	public void pickImageFromCamera() {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			ToastUtils.show(mActivity, "无法识别SD卡，无法进行拍照");
			return;
		}
		LogUtil.i("pickImageFromCamera", "pickImageFromCamera:" + cameraPath);
		Intent inCamera = PickIntent.getPhoneCameraIntent(cameraPath);
		if (mFragment != null) {
			mFragment.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		} else {
			mActivity.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		}
	}

	public void pickImageFromCamera(File file) {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			ToastUtils.show(mActivity, "无法识别SD卡，无法进行拍照");
			return;
		}
		cameraPath = file.getAbsolutePath();
		LogUtil.i("pickImageFromCamera", "pickImageFromCamera:" + cameraPath);
		Intent inCamera = PickIntent.getPhoneCameraIntent(cameraPath);
		if (mFragment != null) {
			mFragment.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		} else {
			mActivity.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		}
	}

	public void pickImageFromCamera(String filename) {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			ToastUtils.show(mActivity, "无法识别SD卡，无法进行拍照");
			return;
		}
		setCameraPhotoName(filename);
		LogUtil.i(TAG, "pickImageFromCamera:" + cameraPath);
		Intent inCamera = PickIntent.getPhoneCameraIntent(cameraPath);
		if (mFragment != null) {
			mFragment.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		} else {
			mActivity.startActivityForResult(inCamera,
					PickImageManager.IMAGE_CAMERA);
		}
	}

	public ImageObserver obtainImageFromPhotoIntent(Intent data) {
		ImageObserver observer = new ImageObserver();
		if (data != null) {
			Uri originalUri = null;
			try {
				originalUri = data.getData(); // 获得图片的uri
				LogUtil.d(TAG, "obtainImageFromPhotoIntent:" + UriUtil.getPath(mActivity,
						originalUri));
				String path = UriUtil.getPath(mActivity,
						originalUri);
				observer = new ImageObserver(path,
						BitmapUtils.getBitmapFromMobileWithMaxWidth(path, mObtainImageMaxWidth));
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String path = UriUtil.getPath(mActivity,
							originalUri);
					observer = new ImageObserver(path,
							BitmapUtils.getBitmapFromMobileWithMaxWidth(path, mObtainImageMaxWidth));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		if (observer != null) {
			LogUtil.d(TAG, "图片路径：" + observer.getUrl());
		}
		return observer;
	}

	public ImageObserver obtainImageFromCameraIntent(Intent data) {
		LogUtil.d(TAG, "obtainImageFromCameraIntent:" + cameraPath);
		ImageObserver observer = new ImageObserver(cameraPath);
		try {
			Bitmap bitmap = BitmapUtils.getBitmapFromMobileWithMaxWidth(
					cameraPath, mObtainImageMaxWidth);
			// LogUtil.d("开始获取图片", bitmap==null?"加载图片时为空":"加载图片不为空");
			observer.setBitmap(bitmap); // 显得到bitmap图片
		} catch (Exception e) {
			e.printStackTrace();
		}
		return observer;
	}

	private ImageObserver copyPickImage(ImageObserver image, boolean isCamera) {
		if (image == null) {
			return image;
		}
		String newPath = image.getUrl();
		if (!isCamera) {
			newPath = mActivity.getCacheDir() + File.separator
					+ System.currentTimeMillis();
		}
		if(image.getBitmap() == null)
		{
			try {
				image.setBitmap(BitmapUtils.getBitmapFromMobileWithMaxWidth(
						image.getUrl(), mObtainImageMaxWidth));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean is = BitmapUtils.copyFile(newPath, image.getBitmap(), 90);
        LogUtil.d(TAG, "copy选择的图片是否成功："+is + "iscamera:"+isCamera+" path:"+newPath);
		image.setUrl(newPath);
		return image;
	}

	public ImageObserver onActivityResult(int requestCode, int resultCode,
			Intent data) {
		LogUtil.d(TAG, "onActivityResult:" + resultCode + " data:" + data);
		if (resultCode != Activity.RESULT_OK) {
			return null;
		}
		ImageObserver observer = null;

		try {
			switch (requestCode) {
			case IMAGE_CAMERA:
				observer = obtainImageFromCameraIntent(data);

				break;
			case IMAGE_PHOTO:
				observer = obtainImageFromPhotoIntent(data);
				break;
			}

			if (isCopyPickImage) {
				observer = copyPickImage(observer,
						requestCode == IMAGE_CAMERA ? true : false);
			}

		} catch (OutOfMemoryError e) {
			LogUtil.e(TAG, "获取图片时，内存溢出", e);
			ToastUtils.show(mActivity, "图片获取失败");
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtils.show(mActivity, "图片获取失败");
		}
		return observer;
	}

	public ImageObserver onActivityResultWithCrop(int requestCode,
			int resultCode, Intent data, int width) {
		if (resultCode != Activity.RESULT_OK) {
			return null;
		}
		ImageObserver observer = null;
		Log.d("onActivityResultWithCrop", "onActivityResultWith" + requestCode);
		try {
			switch (requestCode) {
			case IMAGE_CAMERA:
				Uri uri = Uri.fromFile(new File(cameraPath));
				pickCropImageFromPhoto(uri, width);
				return null;
			case IMAGE_PHOTO:
				if (data != null) {
					Uri originalUri = data.getData();
					pickCropImageFromPhoto(originalUri, width);
					return null;
				}
				break;
			case IMAGE_CROP:
				observer = new ImageObserver((Bitmap) data.getExtras().get(
						"data"));
				Log.d("onActivityResultWithCrop", "onActivityResultWithCrop："
						+ observer.getBitmap());
				break;
			}
		} catch (OutOfMemoryError e) {
			LogUtil.e(TAG, "获取图片时，内存溢出", e);
			ToastUtils.show(mActivity, "图片获取失败");
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtils.show(mActivity, "图片获取失败");
		}
		return observer;
	}

}
