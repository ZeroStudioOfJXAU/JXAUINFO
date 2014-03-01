package com.jxau.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.mapapi.map.MKMapStatusChangeListener;
import com.jxau.app.adapter.ArrayAdapter;
import com.jxau.app.bean.CustomItem;
import com.jxau.app.common.MapUtil;
import com.jxau.app.common.XmlReader;
import com.jxau.app.widget.CustomIconOverlay;
import com.jxau.app.widget.CustomTextOverlay;
import com.zero.jxauapp.R;

/**
 * Title: jxauMapAct Description:��ͼҳ��
 * 
 * @author DaiS
 * @version 1.0
 * @date 2013-12-14
 */
public class MapActivity extends FragmentActivity implements OnClickListener {

	Button routeBtn; // ��·ѡ��
	BMapManager mBMapManager = null; // �ٶȵ�ͼ������
	MapView mMapView = null;// ��ͼ��ͼ
	MKSearch mSearch = null;// ����ģ��

	// ��·�滮���
	LinearLayout linear; // �Ի���
	AutoCompleteTextView editStart;// ��������Զ���ȫ��
	AutoCompleteTextView editEnd;// �յ������Զ���ȫ��
	AutoCompleteTextView searchACTV;
	Button searchBtn;
	Button mBtnDrive = null; // �ݳ�����
	Button mBtnTransit = null; // ��������
	Button mBtnWalk = null; // ��������
	Button mbtnLoc = null; // �Զ���λ
	LocationClient locationClient = null; // ��λ
	Button mBtnHit = null; // ��Ļȡ��

	GeoPoint routeEndPoint; // ��·�滮���յ�����
	GeoPoint routeStartPoint;// ��·�滮���������

	RouteOverlay routeOverlay = null; // ��·ͼ��
	TransitOverlay transitOverlay = null;// ����ͼ��

	MyLocationOverlay myLocationOverlay = null;// ��λͼ��
	LocationData locData = null;// λ������
	MapController mMapController ;
	ArrayAdapter<String> adapter;// �Զ���ȫ������
	Map<String, GeoPoint> map = new HashMap<String, GeoPoint>();// ��Ŵ�xml�ļ�����ӵĲ�ȫ����
	// ��ʾ������״̬
	private final String STATU_END = "��ͼ�ϵĵ�";
	private final String STATU_START = "��ǰλ��";

	// ���з�ʽ��0��ʾ���У�1��ʾ������2��ʾ����
	private final int VEHICLE_WALK = 0;
	private final int VEHICLE_TRANSIT = 1;
	private final int VEHICLE_DRIVE = 2;

	private static final int UPDATE_TIME = 100000;// ��ʱ��λ��ʱ��������λ����
	// �Ƿ���Ӧ��Ļ���
	private boolean enClickAble = false;
	// ���·�߽ڵ����
	private View viewCache = null;
	private TextView popupText = null;// ����view
	private TextView  popleftText = null;
	private TextView  poprightText = null;
	int searchType = -1;// ��¼���������ͣ����ּݳ�/���к͹���
	int nodeIndex = -2;// �ڵ�����,������ڵ�ʱʹ��
	MKRoute route = null;// ����ݳ�/����·�����ݵı�����������ڵ�ʱʹ��
	Button mBtnPre = null;// ��һ���ڵ�
	Button mBtnNext = null;// ��һ���ڵ�
	private PopupOverlay pop = null;// ��������ͼ�㣬����ڵ�ʱʹ��
	// �ٶȵ�ͼ��key
	private final String baiDuMapKey = "lVHeadDQWp9Y18FdCmbORMZs";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mBMapManager = new BMapManager(getApplication());
		mBMapManager.init(baiDuMapKey, null);

		setContentView(R.layout.map);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		// �����������õ����ſؼ�
		mMapView.setBuiltInZoomControls(true);
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		mMapController= mMapView.getController();
		mMapController.setCompassMargin(20,110);
		// �ø����ľ�γ�ȹ���һ�����ĵ�
		GeoPoint point = new GeoPoint((int) (28.768217 * 1E6),
				(int) (115.839311 * 1E6));
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(18);// ���õ�ͼzoom����
		mMapController.enableClick(false);
		// ��Ⱦ���ǵ�͸�����
		mMapView.setSatellite(true);
		mMapView.setTraffic(true);
		getCustomInfoFromAssetsXML();
//		getGroundInfoFromAssetsXML();
		// ��ʼ��mSearch����Ӽ�����
		mSearch = new MKSearch();
		mSearch.init(mBMapManager, myMKSearchListener);
		// ΪMapView��ӵ���¼�����
		mMapView.regMapTouchListner(MymapTouchListener);
		linear = (LinearLayout) findViewById(R.id.map_linear_layout);
		mBtnDrive = (Button) findViewById(R.id.drive_btn);
		mBtnTransit = (Button) findViewById(R.id.transit_btn);
		mBtnWalk = (Button) findViewById(R.id.walk_btn);
		searchBtn = (Button) findViewById(R.id.search_btn);
		editStart = (AutoCompleteTextView) findViewById(R.id.start_ACTextView);
		editEnd = (AutoCompleteTextView) findViewById(R.id.end_ACTextView);
		searchACTV = (AutoCompleteTextView) findViewById(R.id.search_ACTextView);
		mbtnLoc = (Button) findViewById(R.id.auto_loc_button);
		mBtnHit = (Button) findViewById(R.id.screen_hit_btn);
		routeBtn = (Button) findViewById(R.id.map_route_btn);
		
		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		
	    popleftText = (TextView) viewCache.findViewById(R.id.popleft);
		poprightText = (TextView) viewCache.findViewById(R.id.popright);
		
		searchBtn.setOnClickListener(this);
		// ��·��ť����Ӽ�����������ʾ��·�滮�Ի���
		routeBtn.setOnClickListener(this);
		// Ϊ��λ��ť��Ӽ���
		locData = new LocationData();
		mbtnLoc.setOnClickListener(this);
		mBtnHit.setOnClickListener(this);
		// Ϊ������ť��Ӽ�����
		mBtnDrive.setOnClickListener(this);
		mBtnTransit.setOnClickListener(this);
		mBtnWalk.setOnClickListener(this);
		// ��ʼ����λ����
		initLocationService();
		InfoFromAssetsXML();
		initAutoCompleteTextView();
		initNode();
		mMapView.regMapStatusChangeListener(listener);
	}

	boolean isClear = false;
	int fontSize = 26;

	public int fun(int a) {
		return 2 * a - 8;
	}
	boolean flag;
	MKMapStatusChangeListener listener = new MKMapStatusChangeListener() {
		public void onMapStatusChange(MKMapStatus mapStatus) {
			//��������ͼ���ű仯
//			if (mapStatus.zoom >= 17) {
//
//				if (fun((int) mapStatus.zoom) != fontSize) {
//					fontSize = fun((int) mapStatus.zoom);
//					BMapUtil.removeCustomTextOverlay(mMapView);
//					getCustomInfoFromAssetsXML();
//					isClear = false;
//					mMapView.refresh();
//				} else if (fun((int) mapStatus.zoom) == fontSize && isClear) {
//					getCustomInfoFromAssetsXML();
//					isClear = false;
//					mMapView.refresh();
//				}
//			} else {
//				mMapView.getOverlays().clear();
//				mMapView.refresh();
//				isClear = true;
//			}
			 if(mapStatus.zoom<17){
				 MapUtil.removeCustomTextOverlay(mMapView);
			 //mMapView.getOverlays().clear();
			 flag=true;
			 }
			 if(flag && mapStatus.zoom>=17){
			 flag=false;
			 	getCustomInfoFromAssetsXML();
			 }
		}
	};
	MKMapTouchListener MymapTouchListener = new MKMapTouchListener() {
		@Override
		public void onMapClick(GeoPoint point) {
			// �ڴ˴����ͼ����¼�
			if (pop != null) {
				pop.hidePop();
			}
			if (enClickAble) {
				editEnd.setText("��ͼ�ϵĵ�");
				routeEndPoint = point;
				Toast.makeText(getApplicationContext(), point.toString(),
						Toast.LENGTH_LONG).show();
				mMapView.getController().enableClick(false);
				enClickAble = false;
			}
		}

		@Override
		public void onMapDoubleClick(GeoPoint point) {

		}

		@Override
		public void onMapLongClick(GeoPoint point) {

		}
	};
	/**
	 * AutoCompleteTextView������
	 */
	TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			adapter.clear(); // ���adapter������
			if (s.length() > 0) {
				for (String key : map.keySet()) {
					if (key.indexOf(s.toString().trim()) != -1) {
						adapter.add(key);
					}
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	/**
	 * ��ʼ�����Զ���ȫ��Ĺ���
	 */
	public void initAutoCompleteTextView() {

		String[] autoStrings = new String[] { "����ʳ��", "����ʳ��" };
		ArrayList<String> lst = new ArrayList<String>();
		// ����ArrayAdapter�������趨�Ե��������б���չʾ���ڶ��������趨����
		lst.addAll(Arrays.asList(autoStrings));
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lst);
		// ����AutoCompleteTextView��Adapter
		editStart.setAdapter(adapter);
		editEnd.setAdapter(adapter);
		searchACTV.setAdapter(adapter);
		//getCustomInfoFromAssetsXML();
		editStart.addTextChangedListener(watcher);
		editEnd.addTextChangedListener(watcher);
		searchACTV.addTextChangedListener(watcher);
	}

	/**
	 * ��ʼ����λ����
	 */
	public void initLocationService() {

		locationClient = new LocationClient(this);
		// ���ö�λ����
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // �Ƿ��GPS
		option.setCoorType("bd09ll"); // ���÷���ֵ���������͡�
		option.setPriority(LocationClientOption.NetWorkFirst); // ���ö�λ���ȼ�
		option.setProdName("com.zero.jaxuapp"); // ���ò�Ʒ������
		option.setScanSpan(UPDATE_TIME); // ���ö�ʱ��λ��ʱ��������λ����
		locationClient.setLocOption(option);

		// ��λͼ���ʼ��
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// ���ö�λ����
		myLocationOverlay.setData(locData);
		// ��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// �޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
		// ע��λ�ü�����

		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					return;
				}
				locData.latitude = location.getLatitude();
				locData.longitude = location.getLongitude();
				// �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
				locData.accuracy = location.getRadius();
				// �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
				locData.direction = location.getDerect();
				// ���¶�λ����
				myLocationOverlay.setData(locData);
				mMapView.getController().animateTo(
						new GeoPoint((int) (locData.latitude * 1e6),
								(int) (locData.longitude * 1e6)));
				// ����ͼ������ִ��ˢ�º���Ч
				mMapView.refresh();
				routeStartPoint = new GeoPoint(
						(int) (location.getLatitude() * 1E6), (int) (location
								.getLongitude() * 1E6));
				editStart.setText("��ǰλ��");
				//
				locationClient.stop();
				Toast.makeText(MapActivity.this, "��λ�ɹ�", Toast.LENGTH_SHORT)
						.show();
				
			}

			@Override
			public void onReceivePoi(BDLocation location) {
			}
		});
	}

	/**
	 * ��ʾ���ѡ��Ի���
	 * 
	 * @param way
	 * @param startArray
	 * @param endArray
	 */
	public void showStartDialog(final int way, final String[] startArray,
			final String[] endArray) {
		new AlertDialog.Builder(MapActivity.this)
				.setTitle("��ѡ�����")
				.setSingleChoiceItems(startArray, -1,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// flagΪtrue��ʾѡ����㣬false��ʾѡ���յ�
								editStart.setText(startArray[which]);
							}
						})
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (!editEnd.getText().toString().equals(STATU_END)) {
							showEndDialog(way, endArray);
						}
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
	}

	/**
	 * ��ʾ�յ�ѡ��Ի���
	 * 
	 * @param way
	 * @param endArray
	 */
	public void showEndDialog(final int way, final String[] endArray) {
		new AlertDialog.Builder(MapActivity.this)
				.setTitle("��ѡ���յ�")
				.setSingleChoiceItems(endArray, -1,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								editEnd.setText(endArray[which]);
							}
						})
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						searchRoute(way);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
	}

	/**
	 * ����·�߹滮����
	 * 
	 * @param way��ʾ��ѯ�ķ�ʽ
	 */
	public void searchRoute(int way) {
		route = null;
		routeOverlay = null;
		transitOverlay = null;
//		mBtnPre.setVisibility(View.INVISIBLE);
//		mBtnNext.setVisibility(View.INVISIBLE);
		// ��������
		String start = editStart.getText().toString().trim();
		MKPlanNode stNode = new MKPlanNode();
		if (start != null && !start.equals("��ǰλ��")) {
			if (map.containsKey(start))
				stNode.pt = map.get(start);
			else
				stNode.name = start;
		} else if (start.equals("��ǰλ��")) {
			stNode.pt = routeStartPoint;
		} else {
			Toast.makeText(MapActivity.this, "���������", Toast.LENGTH_SHORT).show();
			return;
		}

		String end = editEnd.getText().toString().trim();
		MKPlanNode enNode = new MKPlanNode();

		if (end != null && !end.equals("��ͼ�ϵĵ�")) {
			if (map.containsKey(end))
				enNode.pt = map.get(end);
			else
				enNode.name = end;

		} else if (end.equals("��ͼ�ϵĵ�")) {
			enNode.pt = routeEndPoint;
		} else {
			Toast.makeText(MapActivity.this, "�������յ���ڵ�ͼ��ѡ��", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (way == VEHICLE_DRIVE) {
			mSearch.drivingSearch("�ϲ�", stNode, "�ϲ�", enNode);
		} else if (way == VEHICLE_TRANSIT) {
			mSearch.transitSearch("�ϲ�", stNode, enNode);
		} else {
			mSearch.walkingSearch("�ϲ�", stNode, "�ϲ�", enNode);
		}
	}

	/**
	 * ��������ʱ�����յ㲻Ψһ������£����ݵ�ɸѡ
	 * 
	 * @param stPois
	 * @param enPois
	 * @param way
	 */
	public void dealWithMKSearchData(ArrayList<MKPoiInfo> stPois,
			ArrayList<MKPoiInfo> enPois, int way) {
		String[] start = new String[stPois.size()];
		for (int i = 0; i < stPois.size(); i++) {
			start[i] = stPois.get(i).name;
		}
		String[] end = new String[enPois.size()];
		for (int i = 0; i < enPois.size(); i++) {
			end[i] = enPois.get(i).name;
		}

		if (!editStart.getText().toString().equals(STATU_START)) {
			showStartDialog(way, start, end);
		} else if (!editEnd.getText().toString().equals(STATU_END)) {
			showEndDialog(way, end);
		} else {
			Toast.makeText(MapActivity.this, "�޷��ҵ��õ�ַ!", Toast.LENGTH_LONG)
					.show();
		}
	}
	
	/**
	 * ��·�滮������POI������
	 */
	MKSearchListener myMKSearchListener = new MKSearchListener() {

		public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
			if (error == MKEvent.ERROR_ROUTE_ADDR) {
				ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
				ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
				dealWithMKSearchData(stPois, enPois, VEHICLE_DRIVE);
				return;
			}
			// ����ſɲο�MKEvent�еĶ���
			if (error != 0 || res == null) {
				Toast.makeText(MapActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			searchType = 0;
			routeOverlay = new RouteOverlay(MapActivity.this, mMapView);
			routeOverlay.setData(res.getPlan(0).getRoute(0));
			// �������ͼ��
			MapUtil.removeNotCustomOverlay(mMapView);
			// ���·��ͼ��
			mMapView.getOverlays().add(routeOverlay);
			// ִ��ˢ��ʹ��Ч
			mMapView.refresh();
			// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mMapView.getController().animateTo(res.getStart().pt);
			route = res.getPlan(0).getRoute(0);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
		}

		public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
			// �����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
			if (error == MKEvent.ERROR_ROUTE_ADDR) {
				ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
				ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
				dealWithMKSearchData(stPois, enPois, VEHICLE_TRANSIT);
			}
			if (error != 0 || res == null) {
				Toast.makeText(MapActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			searchType = 1;
			transitOverlay = new TransitOverlay(MapActivity.this, mMapView);
			// �˴���չʾһ��������Ϊʾ��
			transitOverlay.setData(res.getPlan(0));
			// �������ͼ��
			MapUtil.removeNotCustomOverlay(mMapView);
			// ���·��ͼ��
			mMapView.getOverlays().add(transitOverlay);
			// ִ��ˢ��ʹ��Ч
			mMapView.refresh();
			// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(),
					transitOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mMapView.getController().animateTo(res.getStart().pt);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = 0;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);

		}

		public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			// �����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
			if (error == MKEvent.ERROR_ROUTE_ADDR) {
				// �������е�ַ
				ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
				ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
				dealWithMKSearchData(stPois, enPois, VEHICLE_WALK);
				return;
			}
			if (error != 0 || res == null) {
				Toast.makeText(MapActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			searchType = 2;
			routeOverlay = new RouteOverlay(MapActivity.this, mMapView);
			// �˴���չʾһ��������Ϊʾ��
			routeOverlay.setData(res.getPlan(0).getRoute(0));
			// �������ͼ��
			MapUtil.removeNotCustomOverlay(mMapView);
			// ���·��ͼ��
			mMapView.getOverlays().add(routeOverlay);
			// ִ��ˢ��ʹ��Ч
			mMapView.refresh();
			// ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			// �ƶ���ͼ�����
			mMapView.getController().animateTo(res.getStart().pt);
			// ��·�����ݱ����ȫ�ֱ���
			route = res.getPlan(0).getRoute(0);
			// ����·�߽ڵ��������ڵ����ʱʹ��
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
		}

		public void onGetAddrResult(MKAddrInfo res, int error) {

		}

		public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
		}

		public void onGetBusDetailResult(MKBusLineResult result, int iError) {
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
		}

		@Override
		public void onGetPoiDetailSearchResult(int type, int iError) {
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult result, int type,
				int error) {
		}
	};
	OnClickListener nodeClickListener = new OnClickListener() {
		public void onClick(View v) {
			// ���·�߽ڵ�
			nodeClick(v);
		}
	};

	/**
	 * �ڵ����
	 * 
	 * @param v
	 */

	public void initNode() {
		mBtnPre = (Button) findViewById(R.id.pre);
		mBtnNext = (Button) findViewById(R.id.next);
		mBtnPre.setOnClickListener(nodeClickListener);
		mBtnNext.setOnClickListener(nodeClickListener);
		createPaopao();
	}

	/**
	 * ������������ͼ��
	 */
	public void createPaopao() {

		// ���ݵ����Ӧ�ص�
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				if(index == 0 || index == 2){
					if (routeBtn.getText().equals("��·")) {
						linear.setVisibility(View.VISIBLE);
						
						editStart.setText("");
						editEnd.setText("");
						
						routeBtn.setText("����");
					} else if (routeBtn.getText().equals("����")) {
						linear.setVisibility(View.GONE);
						routeBtn.setText("��·");
						// �����·��
						MapUtil.removeNotCustomOverlay(mMapView);
					}
				}
				if(index == 0){
					editStart.setText(popupText.getText());
				}else if(index == 2){
					editEnd.setText(popupText.getText());
				}
			}
		};
		pop = new PopupOverlay(mMapView, popListener);
	}

	public void nodeClick(View v) {
		if (searchType == 0 || searchType == 2) {
			// �ݳ�������ʹ�õ����ݽṹ��ͬ���������Ϊ�ݳ����У��ڵ����������ͬ
			if (nodeIndex < -1 || route == null
					|| nodeIndex >= route.getNumSteps())
				return;

			// ��һ���ڵ�
			if (mBtnPre.equals(v) && nodeIndex > 0) {
				// ������
				nodeIndex--;
				// �ƶ���ָ������������
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// ��������
				popupText.setBackgroundResource(R.drawable.map_popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(MapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
			// ��һ���ڵ�
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps() - 1)) {
				// ������
				nodeIndex++;
				// �ƶ���ָ������������
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// ��������
				popupText.setBackgroundResource(R.drawable.map_popup);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(MapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
		}
		if (searchType == 1) {
			// ��������ʹ�õ����ݽṹ��������ͬ����˵�������ڵ����
			if (nodeIndex < -1 || transitOverlay == null
					|| nodeIndex >= transitOverlay.getAllItem().size())
				return;

			// ��һ���ڵ�
			if (mBtnPre.equals(v) && nodeIndex > 1) {
				// ������
				nodeIndex--;
				// �ƶ���ָ������������
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// ��������
				popupText.setBackgroundResource(R.drawable.map_popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(MapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
			// ��һ���ڵ�
			if (mBtnNext.equals(v)
					&& nodeIndex < (transitOverlay.getAllItem().size() - 2)) {
				// ������
				nodeIndex++;
				// �ƶ���ָ������������
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// ��������
				popupText.setBackgroundResource(R.drawable.map_popup);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(MapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
		}
	}

	/**
	 * ��ȡxml�е��ļ�������ģ��ƥ���û�����
	 */
	public void InfoFromAssetsXML() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(getResources().getAssets().open("point_db.xml"), null);
			map = XmlReader.getPointDB(xpp);
		} catch (Exception e) {
		}

	}

	/**
	 * ��ȡXML�ļ��еĸ��ǵ���Ϣ����Ⱦ��ͼ
	 */
	public void getCustomInfoFromAssetsXML() {
		ArrayList<CustomItem> items = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(getResources().getAssets().open("point_db.xml"), null);
			items = XmlReader.getCustomItemInfo(xpp);
		} catch (Exception e) {
		}
		Drawable mark = getResources().getDrawable(R.drawable.baidumap_ico);
		// ����IteminizedOverlay
		CustomIconOverlay itemOverlay = new CustomIconOverlay(mark, mMapView);
		CustomTextOverlay textOverlay = new CustomTextOverlay(mMapView);
		// mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.getOverlays().add(textOverlay);
		// ��OverlayItem׼��Overlay����
		for (CustomItem c : items) {
			c.getTextItem().fontSize = fontSize;
			textOverlay.addText(c.getTextItem());
			itemOverlay.addItem(c.getOverlayItem());
		}
	}

	/**
	 * ��ȡXML�еľ���������Ϣ����ͼƬ����
	 */
//	public void getGroundInfoFromAssetsXML() {
//		ArrayList<CustomGround> grounds = null;
//		try {
//			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//			factory.setNamespaceAware(true);
//			XmlPullParser xpp = factory.newPullParser();
//			xpp.setInput(getResources().getAssets().open("groundInfo.xml"),
//					null);
//			grounds = XmlReader.getGroundInfo(xpp);
//		} catch (Exception e) {
//		}
//		CustomGroundOverlay mGroundOverlay = new CustomGroundOverlay(mMapView);
//		Drawable d = getResources().getDrawable(R.drawable.stadium);
//		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
//		mMapView.getOverlays().add(mGroundOverlay);
//		for (CustomGround cg : grounds) {
////			Drawable d = getResources().getDrawable(cg.getbitMapId());
////			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
//			Ground mGround = new Ground(bitmap, cg.getLBPoint(),
//					cg.getRTPoint());
//			mGroundOverlay.addGround(mGround);
//		}
//		mMapView.refresh();
//	}

	@Override
	public void onClick(View v) {
		int ItemId = v.getId();// ��ȡ�����idֵ
		switch (ItemId) {
		case R.id.map_route_btn:
			if (routeBtn.getText().equals("��·")) {
				linear.setVisibility(View.VISIBLE);
				routeBtn.setText("����");
			} else if (routeBtn.getText().equals("����")) {
				linear.setVisibility(View.GONE);
				routeBtn.setText("��·");
				mBtnPre.setVisibility(View.GONE);
				mBtnNext.setVisibility(View.GONE);
				
				editEnd.setText("");
				editStart.setText("");
				// �����·��
				MapUtil.removeNotCustomOverlay(mMapView);
			}
			break;
		case R.id.auto_loc_button:
			if (locationClient == null) {
				return;
			}
			locationClient.start();
			locationClient.requestLocation();
			
//			if (locationClient.isStarted()) {
//				mbtnLoc.setText("�Զ���λ");
//				locationClient.stop();
//			} else {
//				mbtnLoc.setText("ֹͣ��λ");
//				
//			}
			break;
		case R.id.screen_hit_btn:
			enClickAble = true;
			mMapView.getController().enableClick(true);
			break;
		case R.id.drive_btn:
			searchRoute(VEHICLE_DRIVE);
			break;
		case R.id.transit_btn:
			searchRoute(VEHICLE_TRANSIT);
			break;
		case R.id.walk_btn:
			if(editStart.getText().toString().trim()==null || 
				editEnd.getText().toString().trim()==null){
				Toast.makeText(getApplicationContext(), "������ʼ�յ�",
						Toast.LENGTH_SHORT).show();
				break;
			}else{
				searchRoute(VEHICLE_WALK);
			}
			break;
		case R.id.search_btn:
			String place=searchACTV.getText().toString().trim();
			if(place == null){
				Toast.makeText(getApplicationContext(), "�����������ص�",
						Toast.LENGTH_SHORT).show();
			}else{
				mMapController.setCenter(map.get(place));
				popleftText.setBackgroundResource(R.drawable.map_popup_side);
				popleftText.setText("�������");
				poprightText.setBackgroundResource(R.drawable.map_popup_side);
				poprightText.setText("������");
				popupText.setBackgroundResource(R.drawable.map_popup);
				popupText.setText(place);
				Bitmap[] b = new Bitmap[3];
				b[0]=MapUtil.getBitmapFromView(popleftText);
				b[1]=MapUtil.getBitmapFromView(popupText);
				b[2]=MapUtil.getBitmapFromView(poprightText);
				pop.showPopup(b,map.get(place), 5);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		mSearch.destory();
		if (locationClient != null)
			locationClient.stop();
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapManager != null) {
			mBMapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapManager != null) {
			mBMapManager.start();
		}
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

}