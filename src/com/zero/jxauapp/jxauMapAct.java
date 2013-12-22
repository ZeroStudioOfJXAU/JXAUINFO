package com.zero.jxauapp;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Ground;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.TextOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.zero.map.CustomGround;
import com.zero.map.CustomItem;
import com.zero.map.CustomOverlay;
import com.zero.map.readXml.XmlReader;

 /**   
 * Title: jxauMapAct
 * Description:��ͼҳ��
 * @author DaiS
 * @version 1.0
 * @date 2013-12-14
 */   
public class jxauMapAct extends FragmentActivity {
	
	//�ٶȵ�ͼ������
	BMapManager mBMapMan = null;
	//��ͼ��ͼ
	MapView mMapView = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("lVHeadDQWp9Y18FdCmbORMZs", null);
		// ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
		setContentView(R.layout.jxau_map_layout);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// �����������õ����ſؼ�
		MapController mMapController = mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		GeoPoint point = new GeoPoint((int) (28.768217 * 1E6),
				(int) (115.839311 * 1E6));
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(17);// ���õ�ͼzoom����
		// addCoverItem();
		getCustomInfoFromAssetsXML();
		getGroundInfoFromAssetsXML();
	}

	/**
	 * ��ȡXML�ļ��еĸ��ǵ���Ϣ����Ⱦ��ͼ
	 */
	private void getCustomInfoFromAssetsXML() {
		ArrayList<CustomItem> items = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(getResources().getAssets().open("pointInfo.xml"), null);
			items = XmlReader.getCustomItemInfo(xpp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Drawable mark = getResources().getDrawable(R.drawable.baidumap_ico);
		// ����IteminizedOverlay
		CustomOverlay itemOverlay = new CustomOverlay(mark, mMapView);
		TextOverlay textOverlay = new TextOverlay(mMapView);
		// mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.getOverlays().add(textOverlay);
		// ��OverlayItem׼��Overlay����
		for (CustomItem c : items) {
			textOverlay.addText(c.getTextItem());
			itemOverlay.addItem(c.getOverlayItem());
		}
	}

	public void getGroundInfoFromAssetsXML() {
		ArrayList<CustomGround> grounds = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(getResources().getAssets().open("groundInfo.xml"),
					null);
			grounds=XmlReader.getGroundInfo(xpp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		GroundOverlay mGroundOverlay = new GroundOverlay(mMapView);
		mMapView.getOverlays().add(mGroundOverlay);
		for (CustomGround cg : grounds) {
			Drawable d = getResources().getDrawable(cg.getbitMapId());
			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
			Ground mGround = new Ground(bitmap, cg.getLBPoint(),
					cg.getRTPoint());
			mGroundOverlay.addGround(mGround);
		}
		mMapView.refresh();
	}
	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}