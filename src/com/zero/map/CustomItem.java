package com.zero.map;

import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.TextItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * Title: CustomItem
 * Description:�Զ����ͼ���ǵ㣬����һ�����֣�CustomTextItem����ͼƬ������OverlayItem��
 * 
 * @author DaiS
 * @date 2013-12-15
 */
public class CustomItem {
	//���ǵ����������
	private CustomTextItem tItem;
	//���ǵ��ͼ���ʾ
	private OverlayItem oItem;
	//ͼƬID��
	private int id;

	/**
	 * ����OverlayItem�����Լ��趨ͼ�꣬Ҳ����ʹ�������ϲ��Ĭ��ͼ�꣬������������������
	 * ��id�Ĺ�������ͼ����Ϣ��id�ű�ʾ
	 * @param mLat
	 * @param mLon
	 * @param title
	 * @param id
	 */
	public CustomItem(int mLat, int mLon, String title, int id) {
		oItem = new OverlayItem(new GeoPoint(mLat, mLon), title, title);
		this.id = id;
		tItem = new CustomTextItem(mLat, mLon, title);
	}

	/**
	 * ����ͼ��Ĺ�����
	 * @param mLat
	 * @param mLon
	 * @param title
	 */
	public CustomItem(int mLat, int mLon, String title) {
		oItem = new OverlayItem(new GeoPoint(mLat, mLon), title, title);
		tItem = new CustomTextItem(mLat, mLon, title);
	}

	public TextItem getTextItem() {
		return tItem;
	}
	public void setTextItem(CustomTextItem tItem) {
		this.tItem=tItem;
	}
	public OverlayItem getOverlayItem() {
		return oItem;
	}
	public void setTextItem(OverlayItem oItem) {
		this.oItem=oItem;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
}
