/**
 * 
 */
package com.zero.map;

import com.baidu.platform.comapi.basestruct.GeoPoint;

 /**   
 * Title: CustomGround
 * Description:�Զ����Ground�࣬��������ȡ��Ÿ������xml�����ݵ����������ڳ�ʼ�������档
 * @author DaiS
 * @version 1.0
 * @date 2013-12-18
 */

public class CustomGround {
	
	private GeoPoint LBPoint;
	private GeoPoint RTPoint;
	private int bitMapId;
	
	public CustomGround(int lbLa,int lbLo,int rtLa,int rtLo,int id ){
		LBPoint=new GeoPoint(lbLa,lbLo);
		RTPoint=new GeoPoint(rtLa,rtLo);
		this.bitMapId=id;
	}
	public GeoPoint getLBPoint(){
		return LBPoint;
	}
	
	public void setLBPoint(GeoPoint LBPoint){
		this.LBPoint=LBPoint;
	}
	public GeoPoint getRTPoint(){
		return RTPoint;
	}
	public void setRTPoint(GeoPoint RTPoint){
		this.RTPoint=RTPoint;
	}
	
	public int getbitMapId(){
		return bitMapId;
	}
	public void setbitMapId(int bitMapId){
		this.bitMapId=bitMapId;
	}
}
