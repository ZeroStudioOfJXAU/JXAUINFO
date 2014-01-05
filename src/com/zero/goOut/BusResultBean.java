/**
 * 
 */
package com.zero.goOut;

import java.util.List;

 /**   
 * Title: resultBean
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2014-1-1
 */

public class BusResultBean {
	
	private List<BusTrackInfo> resultList;
	private int currentLine;
	private boolean dire;
	
	public BusResultBean(List<BusTrackInfo> resultList,int currentLine,boolean dire){
		this.resultList=resultList;
		this.currentLine=currentLine;
		this.dire=dire;
	}

	/**
	 * @return the resultList
	 */
	public List<BusTrackInfo> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<BusTrackInfo> resultList) {
		this.resultList = resultList;
	}

	/**
	 * @return the currentLine
	 */
	public int getCurrentLine() {
		return currentLine;
	}

	/**
	 * @param currentLine the currentLine to set
	 */
	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}

	/**
	 * @return the dire
	 */
	public boolean isDire() {
		return dire;
	}

	/**
	 * @param dire the dire to set
	 */
	public void setDire(boolean dire) {
		this.dire = dire;
	}
}
