package com.whroid.android.utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * 
 * @description 工具类
 * @project myctu
 * @author wuhongren
 * @create 2013-5-30下午6:13:43
 * 
 */
public class Util {
	
	/**
	 * 截取最后一个"/"获取id
	 * 
	 * @param uri
	 * @return
	 */
	public static String getIdFromString(String uri) {
		if (uri == null) {
			return "";
		}
		int index = uri.lastIndexOf("/");
		if (index < 0) {
			return uri;
		}
		try {
			return uri.substring(index + 1);
		} catch (Exception e) {
			return "";
		}
	}
	

	/**
	 * 将map类型转成basicNameValuePair
	 * 
	 * @param map
	 * @return
	 */
	public static List<NameValuePair> mapToNameValuPair(Map<String, String> map) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map == null) {
			return list;
		}
		for (String key : map.keySet()) {
			list.add(new BasicNameValuePair(key, map.get(key)));
		}
		return list;
	}



	/**
	 * double 类型取后面N位小数 N自定义.
	 * 
	 * @param nodesTemp
	 * @return
	 */
	public static String getNumDouble(double dou, int num) {
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(num);
		retValue = df.format(dou);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}
	/**
	 * 传入字节数，得到文件大小
	 * @param size
	 * @return
	 */
	public static String getFileSize(long size)
	{
		String[] unit = new String[]{"K","M","G","T","P"};
		
		long fileSize = size;
		for(int i=0;i<unit.length;i++)
		{
			long B = fileSize/1024;
			if(B<1024)
			{
				int _B = (int) (fileSize%1024/100);
				return B+"."+_B+unit[i];
			}
			fileSize = B;
		}
		return "文件过大";
		
	}

	/**
	 * // 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * // 字符串转二进制
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 讲set转成list
	 * 
	 * @param set
	 * @return
	 */
	public static <T extends Object> List<T> convertSetToList(Set<T> set) {
		List<T> list = new ArrayList<T>();
		if (set != null) {
			for (T t : set) {
				list.add(t);
			}
		}
		return list;
	}
	/**
	 * 将list复制到另个list中
	 * @param dscList  目标list
	 * @param srcList  源list
	 */
	public static <T extends Object> void copyListToList(List<T> dscList,List<T> srcList)
	{
		if(dscList == null)
		{
			dscList = new ArrayList<T>();
		}
		if(srcList == null || srcList.size()==0)
		{
			return;
		}
		for(T t:srcList)
		{
			dscList.add(t);
		}
	}

	/**
	 * 数组转list
	 * 
	 * @param items
	 * @return
	 */
	public static <T extends Object> List<T> arrayToList(T[] items) {
		List<T> list = new ArrayList<T>();

		if (items == null) {
			return list;
		}
		for (T str : items) {
			list.add(str);
		}
		return list;
	}

	public static String[] convertSetToArray(Set<String> set) {
		if (set == null) {
			return null;
		}
		int size = set.size();
		String[] t1 = new String[size];
		int i = 0;
		for (String t : set) {
			t1[i++] = t;
		}
		return t1;
	}
	public static String[] convertListToArray(List<String> list) {
		if (list == null) {
			return null;
		}
		int size = list.size();
		String[] t1 = new String[size];
		int i = 0;
		for (String t : list) {
			t1[i++] = t;
		}
		return t1;
	}
}
