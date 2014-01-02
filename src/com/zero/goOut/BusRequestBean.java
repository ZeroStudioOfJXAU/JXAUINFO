/**
 * 
 */
package com.zero.goOut;

import java.io.Serializable;
import java.util.ArrayList;

 /**   
 * Title: BusRequestBean
 * Description:�û�ѡ��ĳ�����Ϣ
 * @author DaiS
 * @date 2013-12-20
 */

public class BusRequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String currentLocation;//��ǰλ��
	private ArrayList<String> line;//������·
	private boolean isDirection;   //����true��ʾ������false��ʾ����
	
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
