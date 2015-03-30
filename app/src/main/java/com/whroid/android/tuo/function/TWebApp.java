package com.whroid.android.tuo.function;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.whroid.android.twebbrowser.TwebBrowserUI;

/**
 * Created by whroid on 2015/3/30.
 */
public class TWebApp extends Tapp {
    @Override
    public void open(Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        intent.setClass(context, TwebBrowserUI.class);
        intent.addCategory(Intent. CATEGORY_BROWSABLE);
        intent.addCategory(Intent. CATEGORY_DEFAULT);
        context.startActivity(intent);
    }
}
