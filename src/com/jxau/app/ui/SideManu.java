package com.jxau.app.ui;

import com.zero.jxauapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

 /**   
 * Title: SideManuFragment
 * Description:≤‡ª¨≤Àµ•
 * @author DaiS
 * @version 1.0
 * @date 2013-12-20
 */   
public class SideManu extends ListFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] sideManuArray = getResources().getStringArray(R.array.side_menu_item_array);
		ArrayAdapter<String> SideManuAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, sideManuArray);
		setListAdapter(SideManuAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			getActivity().startActivity(new Intent().setClass(getActivity(), AdminSystem.class));
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// «–ªªFragment ”Õºƒ⁄ring
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof Main) {
			Main fca = (Main) getActivity();
			//fca.switchContent(fragment);
		} 
	}
}