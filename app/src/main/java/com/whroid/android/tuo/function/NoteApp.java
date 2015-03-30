package com.whroid.android.tuo.function;

import android.content.Context;
import android.content.Intent;

import com.whroid.android.tuo.note.ui.NoteMainUI;

/**
 * Created by whroid on 2015/3/30.
 */
public class NoteApp extends Tapp {

    @Override
    public void open(Context context) {
        context.startActivity(new Intent(context,NoteMainUI.class));
    }
}
