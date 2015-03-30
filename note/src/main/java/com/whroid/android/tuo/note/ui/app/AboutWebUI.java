package com.whroid.android.tuo.note.ui.app;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whroid.android.tuo.base.ui.TActivity;

/**
 * 关于界面
 * @author whroid
 * @data   2014-7-27
 *
 */
public class AboutWebUI extends TActivity{

	public static final String TAG = AboutWebUI.class.getSimpleName();
	
	WebView webview;
	String url = "file:///android_asset/about.html";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webview = new WebView(this);
		setContentView(webview);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBlockNetworkImage(true);
		webview.loadUrl(url);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				setTitle(view.getTitle());
			}
			
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler,
					SslError error) {
				// 不管证书是否有效
				handler.proceed();
			}

		});
		displayHomeAsUpDirctor();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
