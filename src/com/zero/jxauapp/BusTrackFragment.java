/**
 * 
 */
package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.List;

import com.zero.goOut.BusRequestBean;
import com.zero.goOut.BusResultBean;
import com.zero.goOut.BusStationBean;
import com.zero.goOut.BusTrackInfo;
import com.zero.goOut.BusTrackResultListAdapter;
import com.zero.goOut.BusTimeBean;

import android.content.Intent;
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
	
	private BusRequestBean busRequest;//接收到的用户请求
	
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
		
		busRequest=(BusRequestBean) getArguments().getSerializable("BUSTRACKINFO");
		String s=busRequest.getCurrentLocation()+" "+busRequest.getLine()+" "+busRequest.isDirection();
		Toast.makeText(getActivity(), s,Toast.LENGTH_LONG).show();
		getBusInfo(busRequest);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processBar.setVisibility(View.VISIBLE);
				getBusInfo(busRequest);
				processBar.setVisibility(View.GONE);
			}
			
		});
	}

	public void getBusInfo(BusRequestBean requset) {
		List<BusResultBean> busList=getResult(busRequest);
		
		/*Accept the info from Internet,
		 * get a BusTrackInfo,put it to listView*/
		adapter = new BusTrackResultListAdapter(getActivity(),busList);
	    listView.setAdapter(adapter);
//	    processBar.setVisibility(View.GONE);
	}
	/*Test*/
	public List<BusResultBean> getResult(BusRequestBean busRequest){
		
		List<BusResultBean> list=new ArrayList<BusResultBean>();
		List<BusTrackInfo> resultList=new ArrayList<BusTrackInfo>();
		resultList.add(new BusTrackInfo("hell0",2,"skh",1));
		resultList.add(new BusTrackInfo("hell0",3,"skh",3));
		resultList.add(new BusTrackInfo("hell0",5,"skh",4));
		list.add(new BusResultBean(resultList,1,true));
		list.add(new BusResultBean(resultList,2,true));
		return list;
	}
	public static BusTimeBean getTimeAndLine(int surrentLine){
		BusTimeBean bb=new BusTimeBean("asdf","sdaf","fasdf");
		if(surrentLine==1){
			return bb;
		}
		BusTimeBean cc=new BusTimeBean("assg","sfgds","fadgdga");
		return cc;
	}
	public static BusStationBean getStation(int surrentLine,boolean dir){
		BusStationBean dd=new BusStationBean("财大","下落");
		return dd;
	}
}
