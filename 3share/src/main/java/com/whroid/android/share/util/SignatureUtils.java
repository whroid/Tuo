package com.whroid.android.share.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

public class SignatureUtils {
	
	public static final String TAG = Signature.class.getSimpleName();
	 private static Signature[] getRawSignature(Context paramContext, String packageName)
	  {
	    Signature[] arrayOfSignature = null;
	    if ((packageName == null) || (packageName.length() == 0))
	    {
	    	Log.e(TAG, "getSignature, packageName is null");
	    	return arrayOfSignature;
	    }
	    
	      PackageManager localPackageManager = paramContext.getPackageManager();
	      PackageInfo localPackageInfo;
	      try
	      {
	        localPackageInfo = localPackageManager.getPackageInfo(packageName, 64);
	        if (localPackageInfo != null)
	        {
	        	 arrayOfSignature = localPackageInfo.signatures;
	        }else
	        Log.e(TAG, "info is null, packageName = " + packageName);
	      }
	      catch (PackageManager.NameNotFoundException localNameNotFoundException)
	      {
	        Log.e(TAG, "NameNotFoundException");
	      }
	      return arrayOfSignature;
	  }

	  public  static String getSign(Context context,String pakcageName)
	  {
	    Signature[] arrayOfSignature = getRawSignature(context, pakcageName);
	    if ((arrayOfSignature == null) || (arrayOfSignature.length == 0))
	    {
	      Log.e(TAG, "signs is null");
	      return  null;
	    }
	    int i = arrayOfSignature.length;
	    String sign = "";
	    for (int j = 0; j<i; ++j)
	    {
	    	if(j>=i) break;
	    	sign +=MD5.getMessageDigest(arrayOfSignature[j].toByteArray());
	    }
	    return sign;
	  }

}
