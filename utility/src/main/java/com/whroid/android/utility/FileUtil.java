package com.whroid.android.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * 
 * @文件描述 文件相关帮助类，支持对sd的操作
 * @author whroid
 * @create 2014-3-25
 */
public class FileUtil {
	static InmovationLogger logger = InmovationLogger.getLogger(FileUtil.class
			.getSimpleName());
	public static String TAG = FileUtil.class.getSimpleName();

	/**
	 * 将位置的数据保存到文件中
	 */

	public static File saveDataToSDcard(String filepath, String data,
			boolean isAppend) {

		File file = new File(filepath);
		FileOutputStream fos = null;
		// 判断文件是否存在 如果不存在就创建
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file, isAppend);
			fos.write(data.getBytes());

		} catch (IOException e) {
			logger.e("创建文件失败", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.e("关闭文件流失败", e);
			}
		}
		return file;

	}

	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				logger.e("文件为空");
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String readDataFromSDcard(String filepath) {
		StringBuilder data = new StringBuilder();
		File file = new File(filepath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] by = new byte[1024];
			int index = 0;
			while ((index = fis.read(by)) != -1) {
				data.append(new String(by, 0, index));
			}
		} catch (Exception e) {
			try {
				fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return data.toString();
	}

	/**
	 * 保存bitmap到指定的路径中，同时可以设置保存图片时的质量
	 * 
	 * @param filepath
	 * @param bitmap
	 * @param quatity
	 */
	public static boolean saveBitmap(String filepath, Bitmap bitmap, int quatity) {
		if(bitmap == null)
		{
			return false;
		}
		
		FileOutputStream out = null;
		try {
			File file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}
			
			file.createNewFile();
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, quatity, out)) {
				out.flush();
			}
			LogUtil.d(TAG,"File savebitmap Success：" + filepath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(TAG,"savebitmap error：" + e.getMessage() + " filepath:"
					+ filepath);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件
	 * @param filepath
	 * @return
	 */
	public static boolean deleteFile(String filepath) {
		if (filepath == null || filepath.equals("")) {
			LogUtil.e(TAG, "无法删除文件由于路径：为空");
			return false;
		}
		try {
			File file = new File(filepath);
			if (file.exists()) {
				return file.delete();
			}else{
				LogUtil.e(TAG, "无法删除文件由于路径：不存在:"+filepath);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(TAG, "无法删除文件由于路径：异常");
			return false;
		}

	}

}
