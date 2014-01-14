package com.zero.jxauapp;

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
 * Description:�໬�˵�
 * @author DaiS
 * @version 1.0
 * @date 2013-12-20
 */   
public class SideManuFragment extends ListFragment {
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
			getActivity().startActivity(new Intent().setClass(getActivity(), AdminSystemAct.class));
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}
	
//	public void switchContent(Fragment to) {
//        FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
//                android.R.anim.fade_in, R.anim.slide_out);
//        if (!to.isAdded()) {    //���ж��Ƿ�add��
//            transaction.hide(from).add(R.id.content_frame, to).commit(); // ���ص�ǰ��fragment��add��һ����Activity��
//        } else {
//            transaction.hide(from).show(to).commit(); // ���ص�ǰ��fragment����ʾ��һ��
//        }
//    }

	// �л�Fragment��ͼ��ring
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			//fca.switchContent(fragment);
		} 
	}
}