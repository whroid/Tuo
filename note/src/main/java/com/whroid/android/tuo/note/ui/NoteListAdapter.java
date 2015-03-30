package com.whroid.android.tuo.note.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whroid.android.tuo.note.R;
import com.whroid.android.tuo.note.model.MNote;

public class NoteListAdapter extends BaseAdapter{

	List<MNote> notes;
	Context context;
	LayoutInflater mInflater;
	public NoteListAdapter(Context context,List<MNote>  notes)
	{
		this.context = context;
		this.notes = notes;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return notes==null?0:notes.size();
	}

	@Override
	public Object getItem(int position) {
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.note_list_item, null);
			holder = new ViewHolder();
			holder.contentTv = (TextView) convertView.findViewById(R.id.content);
			holder.timeTv = (TextView) convertView.findViewById(R.id.time);
			holder.titleTv = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		MNote note = notes.get(position);
		holder.contentTv.setText(note.summary);
		holder.timeTv.setText(note.getTime());
		holder.titleTv.setText(note.title);
		return convertView;
	}
	class ViewHolder{
		TextView titleTv;
		TextView timeTv;
		TextView contentTv;
	}

}
