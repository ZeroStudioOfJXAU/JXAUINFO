package com.jxau.app;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class AppContext extends Application{
	
//	public static final String strKey = "lVHeadDQWp9Y18FdCmbORMZs";
//	/**
//	 * �ٶȵ�ͼȫ�ֱ���
//	 */
//	public BMapManager mBMapManager = null;
//	public MapView mMapView;// ��ͼView
//   	public MapController mMapController;//��MapController��ɵ�ͼ���� 
//   	public boolean isNetError;
//   	boolean m_bKeyRight = true;
//   	public MKMapStatus mMapStatus;
//   	public void initEngineManager(Context mContext) {
//        if (mBMapManager == null) {
//            mBMapManager = new BMapManager(mContext);
//        }
//        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
//        	ToastMessage(this, "BMapManager  ��ʼ������!");
//        }
//	}
//   	
//   	public class MyGeneralListener implements MKGeneralListener {
//        
//   		@Override
//        public void onGetNetworkState(int iError) {
//            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//            	isNetError = true;
//            	ToastMessage(AppContext.this, "���������������");
//            }
//            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//            	ToastMessage(AppContext.this, "������ȷ�ļ���������");
//            }
//        }
//        
//        @Override
//        public void onGetPermissionState(int iError) {
//            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
//                //��ȨKey����
//            	ToastMessage(AppContext.this, "���� AppContext.java�ļ�������ȷ����ȨKey��");
//                m_bKeyRight = false;
//            }
//        }
//    }
//   	
//   	public void ToastMessage(Context mContext, String msg) {
//		// TODO Auto-generated method stub
//		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//	}
   	
	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ��ApiClient
		// ��ʼ��BMapManager
//		initEngineManager(this);
		// ��ʼ��ImageLoader
		initImageLoader(this);
	}
	/**��ʼ��ͼƬ������������Ϣ**/
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
			.threadPriority(Thread.NORM_PRIORITY - 2)//����ͼƬ���߳���
			.denyCacheImageMultipleSizesInMemory() //����ͼ��Ĵ�ߴ罫���ڴ��л�����ǰ����ͼ���С�ߴ硣
			.discCacheFileNameGenerator(new Md5FileNameGenerator())//���ô��̻����ļ�����
			.tasksProcessingOrder(QueueProcessingType.LIFO)//���ü�����ʾͼƬ���н���
			.writeDebugLogs() // Remove for release app
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
