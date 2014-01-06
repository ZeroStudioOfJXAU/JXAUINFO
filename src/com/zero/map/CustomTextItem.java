package com.zero.map;

import android.R.color;
import android.graphics.Color;

import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.TextItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

 /**   
 * Title: CustomTextItem
 * Description:�Զ���TextItem,ͳһ���ֵĴ�С����ɫ���Լ�������ɫ
 * @author DaiS
 * @version 1.0
 * @date 2013-12-17
 */   
public class CustomTextItem extends TextItem{
	private int latOffset=100;//γ��ƫ����100,ʹ���ֲ���ͼ���·�
	//���ִ�С����ɫ���Լ�������ɫ����������
	private  int FONTSIZE=26;
//	private final Symbol.Color FONTCOLOR = new Symbol().new Color(255,0,255,0); 
//	private final Symbol.Color BGCOLOR = new Symbol().new Color(150,80,80,80);
	private final Symbol.Color FONTCOLOR = new Symbol().new Color(Color.BLACK);
	private final Symbol.Color BGCOLOR = new Symbol().new Color(color.holo_orange_light);
	
	public CustomTextItem(int latitude,int longtitude,String name){
		super();
		this.fontSize=FONTSIZE;
		this.bgColor=BGCOLOR;
		this.fontColor=FONTCOLOR;
		this.pt=new GeoPoint(latitude-latOffset,longtitude);
		this.text=name;
	}
	
	public int getFontSize(){
		return FONTSIZE;
	}
	
	public void setFontSize(int font){
		this.FONTSIZE=font;
	}

	/**
	 * @return the fONTSIZE
	 */
	public int getFONTSIZE() {
		return FONTSIZE;
	}

	/**
	 * @param fONTSIZE the fONTSIZE to set
	 */
	public void setFONTSIZE(int fONTSIZE) {
		FONTSIZE = fONTSIZE;
	}

	/**
	 * @return the bGCOLOR
	 */
	public Symbol.Color getBGCOLOR() {
		return BGCOLOR;
	}
}
