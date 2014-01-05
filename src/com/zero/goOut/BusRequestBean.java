/**
 * 
 */
package com.zero.goOut;

import java.io.Serializable;
import java.util.ArrayList;

 /**   
 * Title: BusRequestBean
 * Description:用户选择的出行信息
 * @author DaiS
 * @date 2013-12-20
 */

public class BusRequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String currentLocation;//当前位置
	private ArrayList<String> line;//公交线路
	private boolean isDirection;   //方向，true表示出发，false表示到达
	
	public BusRequestBean(String cL,ArrayList<String> line,boolean dir){
		this.currentLocation=cL;
		this.line=line;
		this.isDirection=dir;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the line
	 */
	public ArrayList<String> getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(ArrayList<String> line) {
		this.line = line;
	}

	/**
	 * @return the isDirection
	 */
	public boolean isDirection() {
		return isDirection;
	}

	/**
	 * @param isDirection the isDirection to set
	 */
	public void setDirection(boolean isDirection) {
		this.isDirection = isDirection;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
