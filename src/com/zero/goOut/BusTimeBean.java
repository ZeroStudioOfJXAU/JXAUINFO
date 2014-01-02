/**
 * 
 */
package com.zero.goOut;

 /**   
 * Title: busTimeBean
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2014-1-1
 */

public class BusTimeBean {
	private String startTime;
	private String endTime;
	private String line;
	
	public BusTimeBean(String startTime,String endTime,String line){
		this.endTime=endTime;
		this.startTime=startTime;
		this.line=line;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
}
