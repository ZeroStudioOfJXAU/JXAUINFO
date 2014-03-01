package com.jxau.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxau.app.adapter.CustomExpandableListAdapter;
import com.jxau.app.adapter.CustomListAdapter;
import com.jxau.app.bean.PhoneListViewHolder;
import com.zero.jxauapp.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhoneNumber extends Activity{
	CustomExpandableListAdapter expandableListAdapter;
	ExpandableListView expandableListView;
	ListView commonListView;
	List<String> listDataHeader;
	Map<String, List<String>> listDataChild;
	List<String> commonNumberList;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone);
		commonListView=(ListView)findViewById(R.id.enum_listView);
		expandableListView = (ExpandableListView)findViewById(R.id.phone_expandableListView);
		//add list data
		fillDataToList();
		CustomListAdapter listAdapter = new CustomListAdapter(this,commonNumberList);
		expandableListAdapter = new CustomExpandableListAdapter(this,
				listDataHeader, listDataChild);
		
		commonListView.setAdapter(listAdapter);
		expandableListView.setAdapter(expandableListAdapter);
		
		commonListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "Hit me £¡", Toast.LENGTH_SHORT).show();
				PhoneListViewHolder holder = (PhoneListViewHolder) arg1.getTag();
				String number=(String) holder.number.getText(); 
				Uri uri = Uri.parse("tel:" + number);
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
				
			}
		});
		expandableListView.setOnChildClickListener(new OnChildClickListener(){

//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
////				Toast.makeText(getApplicationContext(), "Hit me £¡", Toast.LENGTH_SHORT).show();
//				PhoneListViewHolder holder = (PhoneListViewHolder) arg1.getTag();
//				String number=(String) holder.number.getText(); 
//				Uri uri = Uri.parse("tel:" + number);
//				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//				startActivity(intent);
//				
//			}; ]

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				// TODO Auto-generated method stub
				PhoneListViewHolder holder = (PhoneListViewHolder) arg1.getTag();
				String number=(String) holder.number.getText(); 
				Uri uri = Uri.parse("tel:" + number);
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
				return true;
			}
		});
       
	}
	private void fillDataToList(){
		String[] common_number = getResources().getStringArray(R.array.common_number);
		String[] category = getResources().getStringArray(R.array.number_category);
		String[] organizationNumber = getResources().getStringArray(R.array.organization_number);
		String[] collegeNumber = getResources().getStringArray(R.array.college_number);
		String[] fastFoodNumber = getResources().getStringArray(R.array.fastFood_number);
		
		listDataHeader=new ArrayList<String>();
		commonNumberList = new ArrayList<String>();
	    listDataHeader = Arrays.asList(category);
	    
	    List<String> orgNumber=new ArrayList<String>();
	    List<String> colNumber=new ArrayList<String>();
	    List<String> foodNumber=new ArrayList<String>();
	    commonNumberList=Arrays.asList(common_number);
	    orgNumber = Arrays.asList(organizationNumber);
	    colNumber = Arrays.asList(collegeNumber);
	    foodNumber= Arrays.asList(fastFoodNumber);
	    listDataChild = new HashMap<String, List<String>>();
	    listDataChild.put(listDataHeader.get(0), orgNumber);
	    listDataChild.put(listDataHeader.get(1), colNumber);
	    listDataChild.put(listDataHeader.get(2), foodNumber);
	   
	    
	}
	 @Override
	public void onSaveInstanceState(Bundle outState) {
	    	super.onSaveInstanceState(outState);
	    }
	
}
