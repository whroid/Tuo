package com.whroid.android.tuo.ui;



import com.whroid.android.tuo.R;
import com.whroid.android.tuo.base.ui.TFragment;
import com.whroid.android.tuo.note.ui.NoteMainUI;
import com.whroid.android.utility.StringUtil;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class AppFrUI extends TFragment {

	ListView listview;
	@Override
	public void init() {

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.listview);
     boolean a =    StringUtil.isEmpty("str" +
             "");
	}

	@Override
	public void initData() {
		String[] items = new String[]{"笔记本"};
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, items);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getActivity(),NoteMainUI.class));
			}
		});
	}

	@Override
	protected void setLayout(Bundle savedInstanceState) {
		setLayoutId(R.layout.app_ui);
	}

}
