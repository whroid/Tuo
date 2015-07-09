package com.whroid.android.tuo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.whroid.android.tuo.R;
import com.whroid.android.tuo.base.ui.TFragment;

public class MessageFrUI extends TFragment{

    TextView tv_message;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		tv_message = (TextView) findViewById(R.id.message);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

        new Handler().post(new Runnable() {
            @Override
            public void run() {

              //  String str = UrlConnectHttpUtil.test();
               // tv_message.setText(str);
            }
        });

	}

	@Override
	protected void setLayout(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setLayoutId(R.layout.message_ui);
	}

}
