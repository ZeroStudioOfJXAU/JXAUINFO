/**
 * 
 */
package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zero.phone.CustomExpandableListAdapter;
import com.zero.phone.CustomListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

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
		return inflater.inflate(R.layout.phone_layout, null);
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
