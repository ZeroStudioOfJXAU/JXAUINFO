/**
 * 
 */
package com.zero.jxauapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zero.goOut.BusInfo;
import com.zero.goOut.BusTrackResultListAdapter;
import com.zero.goOut.GoOut;
import com.zero.goOut.GoOutBean;

 /**   
 * Title: BusTrckAct
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2014-1-9
 */

public class BusTrackAct extends Activity{

	private ListView listView;
	private ProgressBar processBar;
	private ImageView refresh;
	private BusTrackResultListAdapter adapter;
	
	private GoOutBean busRequest;//接收到的用户请求
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_track_layout);
		listView=(ListView)findViewById(R.id.bus_track_ListView);
		processBar=(ProgressBar) findViewById(R.id.bus_track_head_progress);
		refresh=(ImageView) findViewById(R.id.bus_track_refresh);
		
		busRequest=(GoOutBean)getIntent().getSerializableExtra("BUSTRACKINFO");
		
		String s=busRequest.getLocation()+" "+busRequest.getListLine()+" "+busRequest.isDirection();
		Toast.makeText(this, s,Toast.LENGTH_LONG).show();
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
		Toast.makeText(this, ""+busList.size(),Toast.LENGTH_LONG).show();
		adapter = new BusTrackResultListAdapter(this,busList);
	    listView.setAdapter(adapter);
	    processBar.setVisibility(View.GONE);
	}

}
