package com.zero.goOut;

import java.util.List;

/* ������Ϣ  */
/* station ��ʾǰ�����ߵ����վ������*/
/* number ��ʾǰ�����ߵ���Ĺ������� */
/* flag���� true ��ʾ���� �� false ��ʾǰ��*/
/* dire  ��ʾ���� true��ʾ������ false ��ʾ������*/
/* distance ��ʾ�뵱ǰλ�õľ���*/
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

