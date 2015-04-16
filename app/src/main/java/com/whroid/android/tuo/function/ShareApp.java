package com.whroid.android.tuo.function;

import android.content.Context;
import android.content.Intent;

import com.whroid.android.share.MainActivity;

/**
 * Created by whroid on 2015/4/6.
 */
public class ShareApp extends Tapp {
    @Override
    public void open(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
