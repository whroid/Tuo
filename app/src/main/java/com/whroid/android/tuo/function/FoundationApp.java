package com.whroid.android.tuo.function;

import android.content.Context;
import android.content.Intent;

import com.whroid.android.foundation.ItemListActivity;

/**
 * Created by whroid on 2015/4/6.
 */
public class FoundationApp extends  Tapp {
    @Override
    public void open(Context context) {
        context.startActivity(new Intent(context, ItemListActivity.class));
    }
}
