package com.zero.goOut;

/* ������Ϣ  */
/* station ��ʾǰ�����ߵ����վ������*/
/* number ��ʾǰ�����ߵ���Ĺ������� */
/* flag���� true ��ʾ���� �� false ��ʾǰ��*/
/* dire  ��ʾ���� true��ʾ������ false ��ʾ������*/
public class BusInfo {
	public String station;
	public int number;
	public boolean flag;
	public boolean dire;
	public int distance;
	public boolean getDirection(){
		return dire;
	}
	public BusInfo(String s, int n, boolean f){
		station  =s;
		number = n;
		flag = f;
	}
}
