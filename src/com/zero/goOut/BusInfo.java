package com.zero.goOut;

/* 车辆信息  */
/* station 表示前往或者到达的站点名称*/
/* number 表示前往或者到达的公交数量 */
/* flag—— true 表示到达 ， false 表示前往*/
/* dire  表示方向， true表示正方向， false 表示反方向*/
/* distance 表示离当前位置的距离*/
public class BusInfo {
	private String station;
	private int number;
	// 前往 到达
	private boolean arrived;
	private int distance;
	
	private String currentLine;
	private boolean dire;
	
//	public static final int NOCARARRIVEDSIGN = -1;
//	private static BusInfo noCarArrivedBusInfo;
//	public static BusInfo getTerminatorBusInfo(){
//		if(noCarArrivedBusInfo == null){
//			noCarArrivedBusInfo = new BusInfo();
//			noCarArrivedBusInfo.setDistance(NOCARARRIVEDSIGN);
//		}
//    		return noCarArrivedBusInfo;
//	}
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isFlag() {
		return arrived;
	}

	public void setFlag(boolean flag) {
		this.arrived = flag;
	}

	public boolean isDire() {
		return dire;
	}

	public void setDire(boolean dire) {
		this.dire = dire;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}

