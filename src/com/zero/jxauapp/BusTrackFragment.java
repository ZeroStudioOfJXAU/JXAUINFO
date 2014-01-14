/**
 * 
 */
package com.zero.jxauapp;

import java.util.List;

import com.zero.goOut.BusInfo;
import com.zero.goOut.BusTrackResultListAdapter;
import com.zero.goOut.GoOut;
import com.zero.goOut.GoOutBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

 /**
 * Title: BusTrackFragment
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class BusTrackFragment extends Fragment{
	
	private ListView listView;
	private ProgressBar processBar;
	private ImageView refresh;
	private BusTrackResultListAdapter adapter;
	
	private GoOutBean busRequest;//接收到的用户请求
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bus_track_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView=(ListView)getActivity().findViewById(R.id.bus_track_ListView);
		processBar=(ProgressBar) getActivity().findViewById(R.id.bus_track_head_progress);
		refresh=(ImageView) getActivity().findViewById(R.id.bus_track_refresh);
		
		busRequest=(GoOutBean) getArguments().getSerializable("BUSTRACKINFO");
		
		String s=busRequest.getLocation()+" "+busRequest.getListLine()+" "+busRequest.isDirection();
		Toast.makeText(getActivity(), s,Toast.LENGTH_LONG).show();
		getBusInfo(busRequest);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processBar.setVisibility(View.VISIBLE);
				getBusInfo(busRequest);
			}
		});
	}

	public void getBusInfo(GoOutBean requset) {
		GoOut goOut = new GoOut();
		System.out.println(requset.getLocation()+" "
				+requset.getListLine().size() + " "
				+requset.isDirection());
		List<BusInfo> busList=goOut.getBusInfo(requset);
		System.out.println(busList.size());
		Toast.makeText(getActivity(), ""+busList.size(),Toast.LENGTH_LONG).show();
		adapter = new BusTrackResultListAdapter(getActivity(),busList);
	    listView.setAdapter(adapter);
	    processBar.setVisibility(View.GONE);
	}
}
