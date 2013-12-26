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
		busList.add(new BusTrackInfo("��·","����","����","�뱾վ"));//��ͷ
		
		dealWithDataFromPopWindow();//���������һ�����������ѯ�� ArrayList<BusTrackInfo>
		
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

		if (info.getCurrentLocation().endsWith("ũ��")) {
			location = "ũ��������վ";
		} else if (info.getCurrentLocation().endsWith("����")) {
			location = "����վ";
		} else {
			location = "�ƴ�վ";
		}
		
		//����ʾ��
		busList.add(new BusTrackInfo(info.getLine(),"2",info.getCurrentLocation(),"2"));
		// new getDataAsyncTask(location,line,dire, handler).execute();
		// 1��ʾ���� 2��ʾ������
		
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
