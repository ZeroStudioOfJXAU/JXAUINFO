/**
 * 
 */
package com.zero.jxauapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

 /**   
 * Title: PhoneNumberFragment
 * Description:��Fragment����Activity��ʾ�绰��Ϣ���绰���ݴ����valus/phone_number.xml��
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class PhoneNumberFragment extends ListFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.phone_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		String[] sideManuArray = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> SideManuAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, sideManuArray);
	   setListAdapter(SideManuAdapter);
	}
	
	 @Override
	public void onSaveInstanceState(Bundle outState) {
	    	super.onSaveInstanceState(outState);
	    }
}
