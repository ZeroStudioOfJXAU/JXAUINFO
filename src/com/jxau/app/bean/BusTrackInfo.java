/**
 * 
 */
package com.jxau.app.bean;

public class BusTrackInfo {
	
	private String arrived;//��·
	private int number;//����
	private String station;//������վ��
	private int distance;//�뱾վ���ж�Զ
	
	
	public BusTrackInfo(String line,int number,String station,int distance){
		this.arrived=line;
		this.distance=distance;
		this.number=number;
		this.station=station;
	}


	/**
	 * @return the arrived
	 */
	public String getArrived() {
		return arrived;
	}


	/**
	 * @param arrived the arrived to set
	 */
	public void setArrived(String arrived) {
		this.arrived = arrived;
	}


	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}


	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}


	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}


	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}


	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}


	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	
}
