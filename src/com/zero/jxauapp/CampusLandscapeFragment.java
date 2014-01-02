/**
 * 
 */
package com.zero.jxauapp;

 /**   
 * Title: CampusLandscape
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-27
 */

import com.zero.campusLandscape.GalleryFlow;
import com.zero.campusLandscape.IConfig;
import com.zero.campusLandscape.ImageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

       
public class CampusLandscapeFragment extends Fragment{
	GalleryFlow gallery;
	ImageView imageView;  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.campus_landscape, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			ImageAdapter adapter = new ImageAdapter(getActivity());
			 gallery = (GalleryFlow)getActivity().findViewById(R.id.campus_gallery);
		     imageView=(ImageView)getActivity().findViewById(R.id.campus_imageView);
		     //…Ë÷√ª≠¿» ”Õº  ≈‰∆˜
		     gallery.setAdapter(adapter);
		     gallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
		            imageView.setImageResource(IConfig.IMGS[arg2]);
				}
		  });
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

