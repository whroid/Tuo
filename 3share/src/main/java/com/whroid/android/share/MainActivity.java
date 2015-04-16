package com.whroid.android.share;

import com.whroid.android.share.sina.SinaShareFactory;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    Button sendtext;
    Button sendImage, sendwebpage;
    SinaShareFactory sinaShareFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        sendtext = (Button) findViewById(R.id.sendtext);
        sendImage = (Button) findViewById(R.id.sendimage);
        sendwebpage = (Button) findViewById(R.id.sendwebpage);
        sendtext.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                sinaShareFactory.shareText("分享易信成功", false);
            }
        });
        sendImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Drawable drawable = getResources().getDrawable(R.drawable.test);
                sinaShareFactory.shareImage(((BitmapDrawable) drawable).getBitmap(), false);
            }
        });
        sendwebpage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Drawable drawable = getResources().getDrawable(R.drawable.test);
                String url = "http://3g.163.com/ntes/special/0034073A/wechat_article.html?docid=978FP00H00014AED";
                String title = "好的大幅度发斯度好的大幅度发斯蒂芬速度幅度";
                String descriptions = "幅度发斯蒂芬速度好的大幅度发斯蒂芬速度幅度发斯蒂芬速度好的大幅度发斯蒂";
                sinaShareFactory.shareHtml(url, title, descriptions, ((BitmapDrawable) drawable).getBitmap(), true);
            }
        });

        sinaShareFactory = ShareManager.getSinaShareFactory(this);
        sinaShareFactory.handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        sinaShareFactory.handleIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
