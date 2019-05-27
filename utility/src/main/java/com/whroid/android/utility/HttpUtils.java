package com.whroid.android.utility;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.UnknownHostException;


/**
 * 
 * @文件描述
 * @author whroid
 * @create 2014-5-15
 */
public class HttpUtils {

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);

		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public  static void getIp() {
		java.net.InetAddress x;
		try {
			x = java.net.InetAddress.getByName("www.baidu.com");
			String ip = x.getHostAddress();// 得到字符串形式的ip地址
			System.out.println(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
