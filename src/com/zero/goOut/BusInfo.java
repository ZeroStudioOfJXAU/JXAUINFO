package com.zero.goOut;

/* ������Ϣ  */
/* station ��ʾǰ�����ߵ����վ������*/
/* number ��ʾǰ�����ߵ���Ĺ������� */
/* flag���� true ��ʾ���� �� false ��ʾǰ��*/
/* dire  ��ʾ���� true��ʾ������ false ��ʾ������*/
/* distance ��ʾ�뵱ǰλ�õľ���*/
public class BusInfo {
	private String station;
	private int number;
	// ǰ�� ����
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

