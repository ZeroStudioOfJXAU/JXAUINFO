package com.zero.goOut;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class GoOut {
	private String line = "";
	private int dire = 0;
	private String location = "";

	public List<BusInfo> getBusInfo(GoOutBean goOutInfo) {
		// 值的传递
		line = goOutInfo.getLine();
		if (goOutInfo.isDirection()) dire = 1;			
		else dire = 2;
		location = goOutInfo.getLocation();

		// deal data in here
		List<BusInfo> list = new ArrayList<BusInfo>();
		// 无长短班区别
		if (!getClass(list)) {
			getShortClass(list);// 长班
			getLongClass(list);// 短班
		}
		return list;
	}

	private void getLongClass(List<BusInfo> list) {
		// 在实际的公交公司中，将南昌市作为中心，从南昌市发出则为正，反之
		// 而这里以农大为中心，出去为正，反之。恰好以南昌公交公司相反
		int realDire;
		if (dire == 1) realDire = 2;
		else realDire = 1;
		
		String url2 = "http://mybus.jx139.com/LineDetailQuery?lineId=2" + line
				+ "&direction=" + realDire + "&";
		System.out.println("长班的地址为：" + url2);
		try {
			String info2 = HtmlDeal.GetContentFromUrl(url2);
			if (info2 != "") {
				String mainInfo = HtmlDeal.getDivContentByJsoup(info2);
				if (mainInfo != "") {
					List<BusInfo> list3 = getBusInfo(mainInfo);
					if (list.size() != 0)
						list.addAll(list3);
				}
			}
		} catch (NullPointerException e) {

		}
	}

	private void getShortClass(List<BusInfo> list) {
		// TODO Auto-generated method stub
		int realDire;
		if (dire == 1)
			realDire = 2;
		else
			realDire = 1;
		String url = "http://mybus.jx139.com/LineDetailQuery?lineId=1" + line
				+ "&direction=" + realDire + "&";
		System.out.println("短班的地址为：" + url);
		try {
			String info = HtmlDeal.GetContentFromUrl(url);
			if (info != "") {
				String mainInfo = HtmlDeal.getDivContentByJsoup(info);
				if (mainInfo != "") {
					List<BusInfo> list3 = getBusInfo(mainInfo);
					if (list3.size() != 0)
						list.addAll(list3);
				}
			}
		} catch (NullPointerException e) {

		}
	}

	private boolean getClass(List<BusInfo> list) {
		// TODO Auto-generated method stub
		int realDire;
		if (dire == 1)
			realDire = 2;
		else
			realDire = 1;
		String url3 = "http://mybus.jx139.com/LineDetailQuery?lineId=" + line
				+ "&direction=" + realDire + "&";
		System.out.println("无长短班地址为：" + url3);
		try{
			String info3 = HtmlDeal.GetContentFromUrl(url3);
			if (info3 != "") {
				String mainInfo = HtmlDeal.getDivContentByJsoup(info3);
				if (mainInfo != "") {
					List<BusInfo> list3 = getBusInfo(mainInfo);
					if (list3.size() != 0)
					{
						list.addAll(list3);
						return true;
					}
				}
			}
		}catch(NullPointerException e){
			
		}
		return false;
	}

	private List<BusInfo> getBusInfo(String mainInfo) {
		int realDire;
		if (dire == 1)
			realDire = 2;
		else
			realDire = 1;
		List<BusInfo> list = HtmlDeal.SolveCase(mainInfo, location, realDire);
		return list;
	}
}
