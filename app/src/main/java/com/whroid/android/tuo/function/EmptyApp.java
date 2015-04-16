package com.whroid.android.tuo.function;

import android.content.Context;

import com.whroid.android.utility.ToastUtils;

/**
 * Created by whroid on 2015/3/30.
 */
public class EmptyApp extends Tapp {
    @Override
    public void open(Context context) {
        ToastUtils.show(context,"空应用，不支持打开");
    }
}
