package com.zero.goOut;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 根据当前位置，选择的车辆，方向拿到信息
 * 返回信息组成List<BusInfo>
 * 信息携带者Handler
 * @author king
 * @data  13/12/27
 * @hind 最下面有模拟运行提示
 */
public class getDataAsyncTask extends AsyncTask<Void, Void, List<BusInfo>> {
	private String line = "";
	private int dire = 0;
	private String location="";
	Handler handler=null;

	public getDataAsyncTask(String location, String line,int dire, Handler handler) {
		
		this.location=location;
		this.line = line;
		this.dire=dire;
		this.handler = handler;
	}

	@Override
	protected List<BusInfo> doInBackground(Void... params) {
		int realDire;
		if(dire==1) realDire=2;
		else realDire=1;
		// deal data in here
		List<BusInfo> list = new ArrayList<BusInfo>();
		String url3 = "http://mybus.jx139.com/LineDetailQuery?lineId="+
				line+"&direction="+realDire+"&";
		String info3 = HtmlDeal.GetContentFromUrl(url3);
		if(info3!=""){ 
			String mainInfo = HtmlDeal.getDivContentByJsoup(info3);
			if(mainInfo!="")
			{
				List<BusInfo> list3 = getBusInfo(mainInfo);
				list.addAll(list3);
			}
		}
		String url = "http://mybus.jx139.com/LineDetailQuery?lineId=1"+
				line+"&direction="+realDire+"&";
		String info = HtmlDeal.GetContentFromUrl(url);
		if(info!=""){ 
			String mainInfo = HtmlDeal.getDivContentByJsoup(info);
			if(mainInfo!="")
			{
				List<BusInfo> list3 = getBusInfo(mainInfo);
				list.addAll(list3);
			}
		}
		String url2 = "http://mybus.jx139.com/LineDetailQuery?lineId=2"+
				line+"&direction="+realDire+"&";
		String info2 = HtmlDeal.GetContentFromUrl(url2);
		if(info2!=""){ 
			String mainInfo = HtmlDeal.getDivContentByJsoup(info2);
			if(mainInfo!="")
			{
				List<BusInfo> list3 = getBusInfo(mainInfo);
				list.addAll(list3);
			}
		}
		BusInfo first = new BusInfo(null, 0, false);
		if(dire==1)
		{
			first.dire = true;
		}else first.dire = false;
		
		list.add(0, first);
		return list;
	}
	
	protected List<BusInfo> getBusInfo(String mainInfo){		
				
		List<BusInfo> list = HtmlDeal.SolveCase(mainInfo, location, dire);
		return list;
	}
	@Override
	protected void onPostExecute(List<BusInfo> result) {
		super.onPostExecute(result);
		// deal message
		Message msg = new Message();
		msg.obj = result;
		handler.sendMessage(msg);
	}
}

// 模拟运行此模块的功能
/*
 * public class MainActivity extends Activity {
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text =  (TextView) findViewById(R.id.text);
		text.setText("");
		new getDataAsyncTask("下罗站", "240", 2, handler).execute();
		new getDataAsyncTask("下罗站", "704", 2, handler).execute();
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			List<BusInfo> list = (List<BusInfo>) msg.obj;		
			if(list.get(0).getDirection())
				text.append("正方向"+'\n');
			else text.append("反方向"+'\n');
			for(int i=1; i < list.size(); i++)
			{
				String info = list.get(i).station+list.get(i).number+list.get(i).flag+"\n";	
				text.append(info);
			}
		}			
	};
}
 */