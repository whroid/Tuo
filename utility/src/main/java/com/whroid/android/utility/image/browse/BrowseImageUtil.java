package com.whroid.android.utility.image.browse;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.whroid.android.utility.image.ImageObserver;
import com.whroid.android.utility.image.load.ImageImpl;

public class BrowseImageUtil {

	
	public static void browseImage(Context context,ArrayList<ImageImpl> images,int index)
	{
		Intent intent = new Intent(context,BrowseImageUI.class);
		intent.putExtra(BrowseImageUI.KEY_IMAGES, images);
		intent.putExtra(BrowseImageUI.KEY_INDEX, index);
		context.startActivity(intent);
	}
	public static void browseImage(Context context,ImageImpl image)
	{
		ArrayList<ImageImpl> images = new ArrayList<ImageImpl>();
		images.add(image);
		Intent intent = new Intent(context,BrowseImageUI.class);
		intent.putExtra(BrowseImageUI.KEY_IMAGES, images);
		intent.putExtra(BrowseImageUI.KEY_INDEX, 0);
		context.startActivity(intent);
	}
	public static <T extends BrowseImageUI> void browseImage(Context context,ArrayList<ImageImpl> images,int index,Class<T> cls)
	{
		Intent intent = new Intent(context,cls);
		intent.putExtra(BrowseImageUI.KEY_IMAGES, images);
		intent.putExtra(BrowseImageUI.KEY_INDEX, index);
		context.startActivity(intent);
	}
}
