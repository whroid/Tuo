package com.whroid.android.utility.image.browse;

import com.whroid.android.utility.LogUtil;
import com.whroid.android.utility.image.ImageObserver;
import com.whroid.android.utility.image.load.AsyncImageLoaderManager;
import com.whroid.android.utility.image.load.ImageImpl;
import com.whroid.android.utility.image.load.ImageLoadCallBack;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowseFragment extends Fragment {
	public static final String TAG = BrowseFragment.class.getSimpleName();

	public static final String INTENT_KEY_IMAGE = "imageobserver";

	ZoomableImageView imageview = null;
	AsyncImageLoaderManager mImageLoaderManager;

	public static BrowseFragment newInstance(ImageImpl observer) {
		BrowseFragment fragment = new BrowseFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(INTENT_KEY_IMAGE, observer);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mImageLoaderManager = new AsyncImageLoaderManager(this.getActivity());
		imageview = new ZoomableImageView(getActivity());

		ImageImpl observer = (ImageImpl) getArguments()
				.getSerializable(INTENT_KEY_IMAGE);
		LogUtil.d(TAG, observer.toString());
		Bitmap bitmap = mImageLoaderManager.loadImageWithFile(new ImageObserver(observer), new ImageLoadCallBack() {
					@Override
					public void imageLoaded(String url, Bitmap bitmap) {
						if (bitmap != null)
							imageview.setImageBitmap(bitmap);
					}
				});
		if (bitmap != null) {
			imageview.setImageBitmap(bitmap);
		}else{
		}
		
		imageview.setTag(observer.getTag());

		return imageview;
	}

}
