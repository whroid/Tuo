package com.whroid.android.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.whroid.android.utility.WLogger;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * 图片bitmap工具类，主要负责处理bitmap相关操作
 * @author whroid
 *
 */
public class BitmapUtils {

	public static final String TAG = "BitmapUtils";
	static WLogger WLogger = WLogger.getLogger(TAG);

	/**
	 * 获取图片字节数组。通过本地图片路径获取图片，并且现在图片的最大宽度，将其转成字节数组
	 * @param context
	 * @param filepath   本地图片路径
	 * @param maxWidth   最大的图片宽度
	 * @return   图片的字节数组
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static byte[] getByteArrayFromMobile(Context context,
			String filepath, int maxWidth) throws FileNotFoundException,
			IOException {
		Uri uri = Uri.fromFile(new File(filepath));
		Bitmap bitmap = getBitmapFromMobile(context, uri, maxWidth);
		byte[] by = convertBitmapToBytes(bitmap);
		recycleBitmap(bitmap);

		return by;
	}

	/**
	 * 获取图片的字节数组，从给定的图片路径中获取图片，并将其转成字节数组
	 * @param context
	 * @param filepath
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static byte[] getByteArrayFromMobile(Context context, String filepath)
			throws FileNotFoundException, IOException {
		Uri uri = Uri.fromFile(new File(filepath));
		Bitmap bitmap = getBitmapFromMobile(context, uri, 700);
		byte[] by = convertBitmapToBytes(bitmap);
				recycleBitmap(bitmap);
		return by;
	}

	/**
	 * 通过uri 获取手机图片
	 * 
	 * @param context
	 * @param uri
	 * @return
	 * @throws java.io.FileNotFoundException
	 */
	public static Bitmap getBitmapFromMobile(Context context, Uri uri,
			int maxWidth) throws FileNotFoundException, IOException {
		Bitmap bitmap = null;
		try {
			ContentResolver cr = context.getContentResolver();
			InputStream is = cr.openInputStream(uri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			is.close();
			options.inPurgeable = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			int size = options.outWidth / maxWidth * 2;
			if (size <= 1) {
				size = 1;
			}
			options.inSampleSize = size;
			options.inJustDecodeBounds = false;
			is = cr.openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(is, null, options);
			is.close();
			return bitmap;
		} catch (OutOfMemoryError ex) {
			WLogger.e("获取图片内存溢出", ex);
		} catch (Exception e) {
			e.printStackTrace();
			WLogger.e("获取图片内存溢出", e);
		}
		return null;
	}

	/**
	 * 获取图片，等比缩放保证图片不大于maxwidth最大宽度
	 * 
	 * @param context
	 * @param uri
	 * @param maxWidth
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static Bitmap getBitmapFromMobileWithMaxWidth(Context context,
			Uri uri, int maxWidth) throws FileNotFoundException, IOException {
		try {
			ContentResolver cr = context.getContentResolver();
			InputStream is = cr.openInputStream(uri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			int size = options.outWidth / maxWidth;
			if (size < 1) {
				size = 1;
			}
			is.close();
			options.inPurgeable = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inSampleSize = size;
			LogUtil.d(TAG, "getBitmapFromMobileWithMaxWidth:" + uri);
			options.inJustDecodeBounds = false;
			is = cr.openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
			bitmap = getBitmapByWH(bitmap, maxWidth);
			is.close();
			return bitmap;
		} catch (OutOfMemoryError ex) {
			WLogger.e("获取图片内存溢出", ex);
		} catch (Exception e) {
			WLogger.e("获取图片错误", e);
		}
		return null;
	}

	/**
	 * 获取图片，等比缩放保证图片不大于maxwidth最大宽度
	 * 
	 * @param path
	 * @param maxWidth
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */

	public static Bitmap getBitmapFromMobileWithMaxWidth(String path,
			int maxWidth) throws FileNotFoundException, IOException ,Exception{
		try {
			File file = new File(path);
			if(!file.exists())
			{
				return null;
			}
			FileInputStream fis = new FileInputStream(path);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(fis, null, options);
			fis.close();
			options.inJustDecodeBounds = false;
			options.inPurgeable = true;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			
			//处理图片是否会倒转
			int degree = BitmapUtils.readPictureDegree(path);
           LogUtil.d(TAG, "图片旋转多少度："+degree+" path:"+path);
			int size = 1;
			if(degree%180==0)
			{
				size = (int) (options.outWidth *1.2/ maxWidth);
			}else{
				size = (int) (options.outHeight *1.2/ maxWidth);
			}
			if(size <=1)
			{
				size = 1;
			}
			options.inSampleSize = size;
			fis = new FileInputStream(path);
			Bitmap b = BitmapFactory.decodeStream(fis, null, options);
			b = BitmapUtils.rotaingImageView(degree, b);
			b = getBitmapByWH(b, maxWidth);
			fis.close();
			return b;
		} catch (OutOfMemoryError ex) {
			WLogger.e("获取图片内存溢出", ex);
			//throw new Exception("获取图片内存溢出", ex);
			System.gc();
		} 
		return null;
	}

	/**
	 * 等比缩放到宽度为width,如果宽度更小，则不做处理直接返回
	 * 
	 * @param bitmap
	 * @param width
	 * @return
	 */
	public static Bitmap getBitmapByWH(Bitmap bitmap, int width) {
		if (bitmap == null) {
			Log.e("bitmap帮助类", "图片怎么为空呢，");
			return null;
		}
		int twidth = bitmap.getWidth();
		int theight = bitmap.getHeight();
		float scaleWidth = 1.0f;

		if (twidth > width) {
			scaleWidth = (float) width / twidth;
		}
		else{
			return bitmap;
		}
		
		if (scaleWidth < 0.00001) {
			scaleWidth = 1;
		}
		
		Matrix matrixA = new Matrix();
		matrixA.postScale(scaleWidth, scaleWidth);
		Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, twidth, theight,
				matrixA, true);
		recycleBitmap(bitmap);
		return bit;
	}

	/**
	 * 现在最大的宽高，使用等比缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapByWH(Bitmap bitmap, int width, int height) {
		if (bitmap == null) {
			Log.e("bitmap帮助类", "图片怎么为空呢，");
			return null;
		}
		int twidth = bitmap.getWidth();
		int theight = bitmap.getHeight();
		float scaleWidth = 1.0f;

		if (twidth > width) {
			scaleWidth = (float) width / twidth;
		}
		float scaleHeight = 1.0f;
		if (theight > height) {
			scaleHeight = (float) height / theight;
		}
		float scale = Math.min(scaleWidth, scaleHeight);
		if (twidth <= width && theight < height) {
			scale = 1;
		}
		if (scale < 0.00001) {
			scale = 1;
		}
		Matrix matrixA = new Matrix();
		matrixA.postScale(scale, scale);
		Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, twidth, theight,
				matrixA, true);
		return bit;
	}

	/**
	 * 将bitmap 转成byte[]
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] convertBitmapToBytes(Bitmap image) {

		if (image == null) {
			return null;
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 90, baos);
			byte[] value = baos.toByteArray();
			baos.close();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将视图View转成bitmap
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 拷贝图片文件到另外的文件中，同时限制最大宽度和图片的质量
	 * 
	 * @param desPath
	 *            copy的目的地址
	 * @param srcPath
	 *            copy的原地址
	 * @param maxWidth
	 *            图片的最大宽度
	 * @param quary
	 *            copy图片的质量
	 * @return
	 * @throws java.io.IOException
	 * @throws java.io.FileNotFoundException
	 */
	public static boolean copyFile(String desPath, String srcPath,
			int maxWidth, int quatity) {
		try {
			Bitmap bitmap = getBitmapFromMobileWithMaxWidth(srcPath, maxWidth);
			boolean is = FileUtil.saveBitmap(desPath, bitmap, quatity);
			recycleBitmap(bitmap);
			return is;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean copyFile(String desPath, Bitmap bitmap, int quatity) {
		try {
			boolean is = FileUtil.saveBitmap(desPath, bitmap, quatity);
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

     /*
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
		Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        		bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	/**
	 * 释放图片资源
	 * 
	 * @param bitmap
	 */
	public static void recycleBitmap(Bitmap bitmap) {
		try {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			bitmap = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.gc();
		}
	}
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
}
