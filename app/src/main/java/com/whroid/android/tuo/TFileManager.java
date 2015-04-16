package com.whroid.android.tuo;

import android.os.Environment;

/**
 * Created by whroid on 2015/3/31.
 * 文件管理
 */
public class TFileManager {

    /**
     * 根目录
     */
    public final static String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/tuo/";
    /**
     * 奔溃日志目录
     */
    public final static String CRASH_DIR = ROOT_DIR+"crash";
}
