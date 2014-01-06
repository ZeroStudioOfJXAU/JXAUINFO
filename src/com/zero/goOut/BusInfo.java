package com.zero.goOut;

/* 车辆信息 */
import java.util.List;

/* 车辆信息  */
/* station 表示前往或者到达的站点名称*/
/* number 表示前往或者到达的公交数量 */
/* flag—— true 表示到达 ， false 表示前往*/
public class BusInfo {
	
	private List<SubBusInfo> listSubBusInfo;
	private String currentLine;
	private String dire;
	
	public List<SubBusInfo> getListSubBusInfo() {
		return listSubBusInfo;
	}
	public void setListSubBusInfo(List<SubBusInfo> listSubBusInfo) {
		this.listSubBusInfo = listSubBusInfo;
	}
	public String getCurrentLine() {
		return currentLine;
	}
	public void setCurrentLine(String currentLine) {
		this.currentLine = currentLine;
	}
	public String getDire() {
		return dire;
	}
	public void setDire(String dire) {
		this.dire = dire;
	}
	
}

