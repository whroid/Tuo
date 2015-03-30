package com.whroid.android.twebbrowser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 *
 */
public class TwebBrowserUI extends ActionBarActivity {

    WebView webview;
    ProgressBar progressbar;
   Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweb_browser_ui);
        init();
        initView();
        initData();
    }
     private void init() {

     }

     private void initView() {
     webview = (WebView) findViewById(R.id.webview);
     progressbar = (ProgressBar) findViewById(R.id.progressbar);
         initWebView();
     }

     private void initData() {
         Uri data = getIntent().getData();
         String str = data.getPath();
         if(data != null)
             webview.loadUrl(data.toString());
     }
    private void initWebView()
    {
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setDomStorageEnabled(true);

        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(false);//支持缩放
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);//适应屏幕

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);

                if (newProgress == 100) {
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                        }
                    }, 10);

                } else if (newProgress == 0) {
                    progressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(view.getTitle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweb_browser_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
