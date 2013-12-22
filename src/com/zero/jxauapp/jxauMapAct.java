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
 * Description:地图页面
 * @author DaiS
 * @version 1.0
 * @date 2013-12-14
 */   
public class jxauMapAct extends FragmentActivity {
	
	//百度地图管理器
	BMapManager mBMapMan = null;
	//地图视图
	MapView mMapView = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("lVHeadDQWp9Y18FdCmbORMZs", null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.jxau_map_layout);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (28.768217 * 1E6),
				(int) (115.839311 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(17);// 设置地图zoom级别
		// addCoverItem();
		getCustomInfoFromAssetsXML();
		getGroundInfoFromAssetsXML();
	}

	/**
	 * 读取XML文件中的覆盖点信息，渲染地图
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
		// 创建IteminizedOverlay
		CustomOverlay itemOverlay = new CustomOverlay(mark, mMapView);
		TextOverlay textOverlay = new TextOverlay(mMapView);
		// mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.getOverlays().add(textOverlay);
		// 用OverlayItem准备Overlay数据
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