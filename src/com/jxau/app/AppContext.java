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
//	 * 百度地图全局变量
//	 */
//	public BMapManager mBMapManager = null;
//	public MapView mMapView;// 地图View
//   	public MapController mMapController;//用MapController完成地图控制 
//   	public boolean isNetError;
//   	boolean m_bKeyRight = true;
//   	public MKMapStatus mMapStatus;
//   	public void initEngineManager(Context mContext) {
//        if (mBMapManager == null) {
//            mBMapManager = new BMapManager(mContext);
//        }
//        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
//        	ToastMessage(this, "BMapManager  初始化错误!");
//        }
//	}
//   	
//   	public class MyGeneralListener implements MKGeneralListener {
//        
//   		@Override
//        public void onGetNetworkState(int iError) {
//            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//            	isNetError = true;
//            	ToastMessage(AppContext.this, "您的网络出错啦！");
//            }
//            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//            	ToastMessage(AppContext.this, "输入正确的检索条件！");
//            }
//        }
//        
//        @Override
//        public void onGetPermissionState(int iError) {
//            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
//                //授权Key错误：
//            	ToastMessage(AppContext.this, "请在 AppContext.java文件输入正确的授权Key！");
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
		// 初始化ApiClient
		// 初始化BMapManager
//		initEngineManager(this);
		// 初始化ImageLoader
		initImageLoader(this);
	}
	/**初始化图片加载类配置信息**/
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
			.threadPriority(Thread.NORM_PRIORITY - 2)//加载图片的线程数
			.denyCacheImageMultipleSizesInMemory() //解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
			.discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
			.tasksProcessingOrder(QueueProcessingType.LIFO)//设置加载显示图片队列进程
			.writeDebugLogs() // Remove for release app
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
