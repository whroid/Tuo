package com.whroid.android.share.util;

import java.security.MessageDigest;

public final class MD5
{
  public static final String getMessageDigest(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[16];
    arrayOfChar1[0] = 48;
    arrayOfChar1[1] = 49;
    arrayOfChar1[2] = 50;
    arrayOfChar1[3] = 51;
    arrayOfChar1[4] = 52;
    arrayOfChar1[5] = 53;
    arrayOfChar1[6] = 54;
    arrayOfChar1[7] = 55;
    arrayOfChar1[8] = 56;
    arrayOfChar1[9] = 57;
    arrayOfChar1[10] = 97;
    arrayOfChar1[11] = 98;
    arrayOfChar1[12] = 99;
    arrayOfChar1[13] = 100;
    arrayOfChar1[14] = 101;
    arrayOfChar1[15] = 102;
    String str = null;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      int i = arrayOfByte.length;
      char[] arrayOfChar2 = new char[i * 2];
      int j = 0;
      int k = 0;
      if (j >= i)
      {
        str = new String(arrayOfChar2);
        return str;
      }
      int l = arrayOfByte[j];
      int i1 = k + 1;
      arrayOfChar2[k] = arrayOfChar1[(0xF & l >>> 4)];
      k = i1 + 1;
      arrayOfChar2[i1] = arrayOfChar1[(l & 0xF)];
      ++j;
    }
    catch (Exception localException)
    {
      str = null;
    }
     return str;
  }

  public static final byte[] getRawDigest(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      arrayOfByte1 = arrayOfByte2;
      return arrayOfByte1;
    }
    catch (Exception localException)
    {
      arrayOfByte1 = null;
    }
    return arrayOfByte1;
  }
}