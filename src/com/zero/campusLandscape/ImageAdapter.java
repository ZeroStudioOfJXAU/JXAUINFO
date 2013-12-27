package com.zero.campusLandscape;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class ImageAdapter extends BaseAdapter implements ListAdapter {
	private Context mContext = null;

	public ImageAdapter(Context c) {
		this.mContext = c;
	}

	@Override
	public int getCount() {
		return (IConfig.IMGS.length);
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public int getResId(int position) {
		return (IConfig.IMGS[position]);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = null;
		if (convertView == null) {
			iv = new ImageView(mContext);
			iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new Gallery.LayoutParams(300, 300));
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setPadding(8, 8, 8, 8);
		} else {
			iv = (ImageView) convertView;
		}
		// 设置图片视图的资源
		iv.setImageResource(IConfig.IMGS[position]);
		return (iv);
	}
};
