/**
 * 
 */
package com.zero.goOut;

 /**   
 * Title: BusTrackInfo
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class BusTrackInfo {
	
	private String line; //��·
	private String number;//����
	private String station;//������վ��
	private String distance;//�뱾վ���ж�Զ
	
	public BusTrackInfo(String line,String number,String station,String distance){
		this.line=line;
		this.distance=distance;
		this.number=number;
		this.station=station;
	}
	public String getLine(){
		return line;
	}
	public void setLine(String line){
		this.line=line;
	}
	
	public String getNumber(){
		return number;
	}
	public void setNumber(String number){
		this.number=number;
	}
	public String getStation(){
		return station;
	}
	public void setStation(String station){
		this.station=station;
	}
	public String getDistance(){
		return distance;
	}
	public void setDistance(String distance){
		this.distance=distance;
	}
	
}
