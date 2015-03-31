package com.whroid.android.tuo;

import android.app.Application;

import com.whroid.android.utility.monitor.AppCrashHandler;

/**
 * Created by whroid on 2015/3/31.
 */
public class TApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init(this).setStorageDir(TFileManager.CRASH_DIR);
    }
}
