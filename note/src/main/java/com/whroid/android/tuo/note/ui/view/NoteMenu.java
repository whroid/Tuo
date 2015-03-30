package com.whroid.android.tuo.note.ui.view;

import java.util.ArrayList;
import java.util.List;

import com.whroid.android.tuo.note.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 笔记本菜单主页
 * @author whroid
 * @data   2014-6-27
 *
 */
public class NoteMenu extends LinearLayout{
	
	public static final String TAG = NoteMenu.class.getSimpleName();
	
	public static final int MENU_TYPE_LISTNEWS = 1;
	public static final int MENU_TYPE_ABOUT = 3;
	public static final int MENU_TYPE_NEW = 2;
	public static final int MENU_TYPE_FEEDBACK = 4;
	
	ListView mListView;
	Context mContext;
	OnNoteMenuItemClickListener mOnNoteMenuItemClickListener;
	
	List<NoteMenuItem> mMenuItemLists;
	
	public NoteMenu(Context context) {
		super(context);
		init(context);
	}
	public NoteMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.note_main_menu_ui, this);
		this.mContext = context;
		this.mListView = (ListView) findViewById(R.id.note_menu_listview);
		setData();
	}
	
	public NoteMenuItem getDefaultMenuItem()
	{
		return new NoteMenuItem("日记列表", MENU_TYPE_LISTNEWS);
	}
	private void setData()
	{
		mMenuItemLists = new ArrayList<NoteMenuItem>();
		mMenuItemLists.add(new NoteMenuItem("新建日记",MENU_TYPE_NEW));
		mMenuItemLists.add(new NoteMenuItem("日记列表", MENU_TYPE_LISTNEWS));
		mMenuItemLists.add(new NoteMenuItem("反馈",MENU_TYPE_FEEDBACK));
		mMenuItemLists.add(new NoteMenuItem("关于", MENU_TYPE_ABOUT));
	
		
		MenuAdapter adapter = new MenuAdapter(mContext, android.R.layout.simple_list_item_1, mMenuItemLists);
	
		this.mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(mOnNoteMenuItemClickListener != null)
				{
					mOnNoteMenuItemClickListener.onNoteMenuItem(arg2,(NoteMenuItem) arg0.getAdapter().getItem(arg2));
				}
			}
		});
		this.mListView.setAdapter(adapter);
	}
	
	public void setSeletedFromListView(int index)
	{
		Log.d(TAG, "setselectedFromListView:index="+index);
		View view = mListView.getChildAt(index);
		if(view != null)
		{
			view.setPressed(true);
		}
		mListView.setItemChecked(index, true);
	}
	public void setOnNoteMenuItemClickListener(OnNoteMenuItemClickListener mOnNoteMenuItemClickListener)
	{
		this.mOnNoteMenuItemClickListener = mOnNoteMenuItemClickListener;
	}
	public interface OnNoteMenuItemClickListener
	{
		public void onNoteMenuItem(int which, NoteMenuItem menuItem);
	}

	public class NoteMenuItem{
		public String text;
		public int ftag;
		int listview_position = -1;
		public NoteMenuItem(String text,int ftag)
		{
			this.text = text;
			this.ftag = ftag;
		}
		public int getListViewPosition()
		{
			if(listview_position>=0)
			{
				return listview_position;
			}
			int size = mMenuItemLists.size();
			for(int i=0;i<size;i++)
			{
				NoteMenuItem item = mMenuItemLists.get(i);
				if(ftag == item.ftag)
				{
					listview_position = i;
					break;
				}
			}
			return listview_position;
		}
	}
	
	class MenuAdapter extends ArrayAdapter<NoteMenuItem>
	{

		LayoutInflater inflater;
		public MenuAdapter(Context context, int resource,
				List<NoteMenuItem> objects) {
			super(context, resource, objects);
			this.inflater = LayoutInflater.from(context);
		}
		@Override
		public NoteMenuItem getItem(int position) {
			return super.getItem(position);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder =null;
			if(convertView == null)
			{
				convertView = inflater.inflate(R.layout.note_menu_item, null);
				holder = new ViewHolder();
				holder.imageview = (ImageView) convertView.findViewById(R.id.image);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			NoteMenuItem item = getItem(position);
			holder.text.setText(item.text);
			return convertView;
		}
		class ViewHolder{
			ImageView imageview;
			TextView text;
		}
		
	}
}
