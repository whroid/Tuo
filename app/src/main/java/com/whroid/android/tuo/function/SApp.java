package com.whroid.android.tuo.function;

/**
 * Created by whroid on 2015/3/30.
 */
public  enum SApp {
 NOTE("笔记本"),TWEB("内嵌浏览器"),SAMPLE("内部示例"),SHARE("分享示例"),FOUNDATION("程序基础");
    final String name;

     private SApp(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
       return name;
    }
}
