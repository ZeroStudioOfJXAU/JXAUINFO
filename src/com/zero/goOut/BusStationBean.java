/**
 * 
 */
package com.zero.goOut;

/**
 * Title: BusStationBean Description:
 * 
 * @author DaiS
 * @version 1.0
 * @date 2014-1-1
 */

public class BusStationBean {
	private String startStation;
	private String endStation;

	public BusStationBean(String startStation, String endStation) {
		this.startStation = startStation;
		this.endStation = endStation;
	}

	/**
	 * @return the startStation
	 */
	public String getStartStation() {
		return startStation;
	}

	/**
	 * @param startStation the startStation to set
	 */
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	/**
	 * @return the endStation
	 */
	public String getEndStation() {
		return endStation;
	}

	/**
	 * @param endStation the endStation to set
	 */
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	
}
