/**
 * 
 */
package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.List;

import com.zero.goOut.BusInfo;
import com.zero.goOut.BusTrackInfo;
import com.zero.goOut.BusTrackInfoAdapter;
import com.zero.goOut.GoOutInfo;
import com.zero.goOut.getDataAsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

 /**
 * Title: BusTrackFragment
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class BusTrackFragment extends Fragment{
	
	private List<BusTrackInfo> busList = new ArrayList<BusTrackInfo>();
	private ListView listView;
	private BusTrackInfoAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bus_track_layout, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView=(ListView)getActivity().findViewById(R.id.bus_track_ListView);
		busList.add(new BusTrackInfo("线路","车数","开往","离本站"));//表头
		
		dealWithDataFromPopWindow();//在这里接受一个来自网络查询的 ArrayList<BusTrackInfo>
		
		adapter = new BusTrackInfoAdapter(getActivity(),busList);
	    listView.setAdapter(adapter);
	}
	
	
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			List<BusInfo> list = (List<BusInfo>) msg.obj;
			
		};
	};

	public void dealWithDataFromPopWindow() {
		GoOutInfo info=(GoOutInfo) getArguments().getSerializable("BUSTRACKINFO");
		String location;

		if (info.getCurrentLocation().endsWith("农大")) {
			location = "农大生活区站";
		} else if (info.getCurrentLocation().endsWith("下罗")) {
			location = "下罗站";
		} else {
			location = "财大站";
		}
		
		//接收示例
		busList.add(new BusTrackInfo(info.getLine(),"2",info.getCurrentLocation(),"2"));
		// new getDataAsyncTask(location,line,dire, handler).execute();
		// 1表示正向 2表示反方向
		
		/*BUG here,Wait for fixing!*/
//		if (info.getDirection()) {
//			if (info.getLine().equals("240 704")) {
//				new getDataAsyncTask(location, "240", 1, handler).execute();
//				new getDataAsyncTask(location, "704", 1, handler).execute();
//			} else {
//				new getDataAsyncTask(location, info.getLine(), 1, handler)
//						.execute();
//			}
//		} else {
//			if (info.getLine().equals("240 704")) {
//				new getDataAsyncTask(location, "240", 0, handler).execute();
//				new getDataAsyncTask(location, "704", 0, handler).execute();
//			} else {
//				new getDataAsyncTask(location, info.getLine(), 0, handler)
//						.execute();
//			}
//		}
	}
}
