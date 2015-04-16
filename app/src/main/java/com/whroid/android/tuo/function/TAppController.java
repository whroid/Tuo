package com.whroid.android.tuo.function;

/**
 * Created by whroid on 2015/3/30.
 */
public class TAppController {

    public static Tapp createTApp(SApp sapp)
    {
        Tapp tapp = null;
        if(sapp == SApp.NOTE)
        {
            tapp = new NoteApp();
        }else if(sapp == SApp.TWEB)
        {
            tapp = new TWebApp();
        }else if(sapp == SApp.SAMPLE) {
            tapp = new SampleApp();
        }else if(sapp == SApp.SHARE){
            tapp = new ShareApp();
        }else if(sapp == SApp.FOUNDATION){
            tapp = new FoundationApp();
        }
        else {
            tapp = new EmptyApp();
        }
        return tapp;
    }
}
