package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.goOut.BusInfo;
import com.zero.goOut.BusLineAdapter;
import com.zero.goOut.BusResultBean;
import com.zero.goOut.CurrentLineBean;
import com.zero.goOut.GoOut;
import com.zero.goOut.GoOutBean;
import com.zero.goOut.LineBean;
import com.zero.goOut.LineHelp;
import com.zero.goOut.SubBusInfo;

public class BusResult extends Activity {

	private ListView listView240;
	private ListView listView704;
	private Button btn240;
	private Button btn704;
	private ProgressBar processBar;
	private ImageView refresh;
	private BusLineAdapter adapter240;
	private BusLineAdapter adapter704;
	private TextView startTime;
	private TextView endTime;
	private ImageView btnCutline;
	
	private GoOutBean busRequest; // 接收到的用户请求
	private GoOutBean catchDataGoOutBean;
	private LinearLayout bus_time;
	String[] station_240;
	List<String> list240;

	String[] station_704;
	List<String> list704;

	List<String> stations240;
	List<String> stations704;
	
	List<String> states240;
	List<String> states704;
	
	List<Integer> num240;
	List<Integer> num704;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_result);
		processBar = (ProgressBar) findViewById(R.id.bus_track_head_progress);
		refresh = (ImageView) findViewById(R.id.bus_track_refresh);
		listView240 = (ListView) findViewById(R.id.listView_240);
		listView704 = (ListView) findViewById(R.id.listView_704);

		startTime=(TextView) findViewById(R.id.BR_start_time_textView);
		endTime=(TextView) findViewById(R.id.BR_end_time_textView);
		bus_time=(LinearLayout) findViewById(R.id.bus_time);
		btnCutline=(ImageView) findViewById(R.id.image_button_cutline);
		
		btn240 = (Button) findViewById(R.id.btn_240);
		btn704 = (Button) findViewById(R.id.btn704);
		btn240.setEnabled(false);
		
		btn240.setOnClickListener(lineBtnClick(btn240));
		btn704.setOnClickListener(lineBtnClick(btn704));
		
		stations240 = new ArrayList<String>();
		stations704 = new ArrayList<String>();
		
		states240 = new ArrayList<String>();
		states704 = new ArrayList<String>();

		num240 = new ArrayList<Integer>();
		num704 = new ArrayList<Integer>();
		
		station_240 = getResources().getStringArray(R.array.stations_240);
		station_704 = getResources().getStringArray(R.array.stations_704);

		list240 = Arrays.asList(station_240);
		list704 = Arrays.asList(station_704);

		busRequest = (GoOutBean) getIntent().getSerializableExtra(
				"BUSTRACKINFO");
		List<String> listLine = new ArrayList<String>();
		listLine.add("240");
		listLine.add("704");
		catchDataGoOutBean = new GoOutBean(busRequest.getLocation(), listLine,
				busRequest.isDirection());
		
		// s=busRequest.getLocation()+" "+busRequest.getListLine()+" "+busRequest.isDirection();
		// Toast.makeText(this, s,Toast.LENGTH_LONG).show();
		
		new Thread(getDataRunable).start();
		
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processBar.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.GONE);
				new Thread(getDataRunable).start();
			}
		});
	}
	public void setTitle(){
		
		if(busRequest.getListLine().size()==1){
			
		}
		
	}
	public void setUI() {
		String curPosition=busRequest.getLocation();
		boolean dir=busRequest.isDirection();
		
		if (busRequest.isDirection()) {
			adapter240 = new BusLineAdapter(this,new BusResultBean(list240, stations240,states240,num240,curPosition,dir));
			adapter704 = new BusLineAdapter(this, new BusResultBean(list704, stations704,states704,num704,curPosition,dir));
		} else {
			Collections.reverse(list240);
			adapter240 = new BusLineAdapter(this, new BusResultBean(list240, stations240,states240,num240,curPosition,dir));
			Collections.reverse(list704);
			adapter704 = new BusLineAdapter(this, new BusResultBean(list704, stations704,states704,num704,curPosition,dir));
		}
		listView240.setAdapter(adapter240);
		listView704.setAdapter(adapter704);
		
		btn240.setVisibility(View.VISIBLE);
		btn704.setVisibility(View.VISIBLE);
		btnCutline.setVisibility(View.VISIBLE);
		
		if (busRequest.getListLine().size() == 1){
			
			if(busRequest.getListLine().get(0).equals("704")){
				startTime.setText("am7:00(pm16:30)");
				endTime.setText("am9:30(pm20:30)");
				
				btn240.setVisibility(View.GONE);
				btnCutline.setVisibility(View.GONE);
				
				listView704.setVisibility(View.VISIBLE);
				listView240.setVisibility(View.GONE);
				
			}else{
				btn704.setVisibility(View.GONE);
				btnCutline.setVisibility(View.GONE);
			}
		}
		bus_time.setVisibility(View.VISIBLE);
		
	}

	private Runnable getDataRunable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			getBusInfo(catchDataGoOutBean);
			handler.sendEmptyMessage(0);
		}
	};

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				setUI();
				processBar.setVisibility(View.GONE);
				refresh.setVisibility(View.VISIBLE);
				break;
			}
		};
	};

	public void getBusInfo(GoOutBean requset) {
		GoOut goOut = new GoOut();
		List<BusInfo> busList = goOut.getBusInfo(requset);
 		if (busList.size() > 0) {
			List<SubBusInfo> lineShort240 = busList.get(0).getListSubBusInfo();
			for (SubBusInfo s : lineShort240) {
				stations240.add(s.getStation());
				states240.add(s.getArrived());
				num240.add(s.getNumber());
			}
			if (busList.size() > 1) {
				List<SubBusInfo> lineLong240 = busList.get(1)
						.getListSubBusInfo();
				for (SubBusInfo s : lineLong240) {
					stations240.add(s.getStation());
					states240.add(s.getArrived());
					num240.add(s.getNumber());
				}
				if (busList.size() > 2) {
					List<SubBusInfo> line704 = busList.get(2)
							.getListSubBusInfo();
					for (SubBusInfo s : line704) {
						stations704.add(s.getStation());
						states704.add(s.getArrived());
						num704.add(s.getNumber());
					}
				}
			}
		}
	}
	private OnClickListener lineBtnClick(final Button btn) {
		return new OnClickListener() {
			public void onClick(View v) {
				if (btn == btn704) {
					btn704.setEnabled(false);
					btn240.setEnabled(true);
					startTime.setText("am7:00(pm16:30)");
					endTime.setText("am9:30(pm20:30)");
					listView704.setVisibility(View.VISIBLE);
					listView240.setVisibility(View.GONE);
				} else {
					btn704.setEnabled(true);
					btn240.setEnabled(false);
					startTime.setText("5:40");
					endTime.setText("19:40");
					listView704.setVisibility(View.GONE);
					listView240.setVisibility(View.VISIBLE);
				}
			}
		};
	}
}
