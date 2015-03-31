package com.whroid.android.tuo.function;
import android.content.Context;
import android.content.Intent;

import com.whroid.android.sample.SampleActivity;

/**
 * Created by whroid on 2015/3/31.
 */
public class SampleApp extends Tapp {
    @Override
    public void open(Context context) {
        context.startActivity(new Intent(context, SampleActivity.class));
    }
}
