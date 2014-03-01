/**
 * 
 */
package com.jxau.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxau.app.adapter.CustomExpandableListAdapter;
import com.jxau.app.adapter.CustomListAdapter;
import com.jxau.app.bean.PhoneListViewHolder;
import com.jxau.app.bean.RadioListViewHolder;
import com.zero.jxauapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

 /**   
 * Title: PhoneNumberFragment
 * Description:用Fragment代替Activity显示电话信息，电话数据存放在valus/phone_number.xml中
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class PhoneNumberFragment extends Fragment{
	
	CustomExpandableListAdapter expandableListAdapter;
	ExpandableListView expandableListView;
	ListView commonListView;
	List<String> listDataHeader;
	Map<String, List<String>> listDataChild;
	List<String> commonNumberList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.phone, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		commonListView=(ListView) getActivity().findViewById(R.id.enum_listView);
		expandableListView = (ExpandableListView)getActivity().findViewById(R.id.phone_expandableListView);
		//add list data
		fillDataToList();
		CustomListAdapter listAdapter = new CustomListAdapter(getActivity(),commonNumberList);
		expandableListAdapter = new CustomExpandableListAdapter(getActivity(),
				listDataHeader, listDataChild);
		
		commonListView.setAdapter(listAdapter);
		expandableListView.setAdapter(expandableListAdapter);
		
		commonListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Hit me ！", Toast.LENGTH_SHORT).show();
				PhoneListViewHolder holder = (PhoneListViewHolder) arg1.getTag();
				String number=(String) holder.number.getText(); 
				Uri uri = Uri.parse("tel:" + number);
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
				
			}
			
		});
       
	}
	private void fillDataToList(){
		String[] common_number = getResources().getStringArray(R.array.common_number);
		String[] category = getResources().getStringArray(R.array.number_category);
		String[] organizationNumber = getResources().getStringArray(R.array.organization_number);
		String[] collegeNumber = getResources().getStringArray(R.array.college_number);
		
		listDataHeader=new ArrayList<String>();
		commonNumberList = new ArrayList<String>();
	    listDataHeader = Arrays.asList(category);
	    
	    List<String> orgNumber=new ArrayList<String>();
	    List<String> colNumber=new ArrayList<String>();
	    
	    commonNumberList=Arrays.asList(common_number);
	    orgNumber = Arrays.asList(organizationNumber);
	    colNumber = Arrays.asList(collegeNumber);
	    
	    listDataChild = new HashMap<String, List<String>>();
	    listDataChild.put(listDataHeader.get(0), orgNumber);
	    listDataChild.put(listDataHeader.get(1), colNumber);
	   
	    
	}
	 @Override
	public void onSaveInstanceState(Bundle outState) {
	    	super.onSaveInstanceState(outState);
	    }
}
