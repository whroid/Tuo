package com.whroid.android.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

/**
 * 
 * @文件描述 
 * @author whroid
 * @create 2014-3-25
 */
public class MobileUtils {

	/**
	 * 判定wifi是否可用
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context inContext) {
		WifiManager mWifiManager = (WifiManager) inContext
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
			// System.out.println("**** WIFI is on");
			return true;
		} else {
			// System.out.println("**** WIFI is off");
			return false;
		}
	}

	/**
	 * 判断是否有网络
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isHasNetwork(Context inContext) {
		if (isWiFiActive(inContext) || isNetworkAvailable(inContext)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否有可用的3G网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				info.getSubtype();
				if (info.isAvailable()) {
					return true;
				}

			}
		}
		System.out.println("**** newwork is off");
		return false;
	}
	public static String getVersionName(Context context)
	{
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			String verName = info.versionName;
			return verName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

    /**
     * 是否有内存卡
     * @return
     */
    public static boolean isHasSdcard()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
