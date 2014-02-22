/**
 * 
 */
package com.zero.jxauapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zero.app.AppConfigue;
import com.zero.goOut.BusUnit;
import com.zero.news.InternetDataCatcher;
import com.zero.news.ListHeaderView;
import com.zero.news.NewsBean;
import com.zero.news.NewsDetailsBean;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

 /**   
 * Title: TestNewDetailsAct
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-28
 */

public class NewsDetailsAct extends AbsListViewBaseActivity{
	
	DisplayImageOptions options; //����ͼƬ���ؼ���ʾѡ��
	
	private ImageView mHome;
	private ProgressBar mProgressbar;
	
	private TextView mTitle;
	private TextView mAuthor;
	private TextView mPubDate;
	private TextView mContext;
	private ImageView picture;
	private String newsUrl;
	private NewsDetailsBean newsInfo;
	private String noticleContext;
	private boolean isNews=true;
	private NewsBean noticle;
	
	private ScrollView scrollView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		//����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder()
		.resetViewBeforeLoading(true)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		
		.showStubImage(R.drawable.ic_stub)    //��ImageView���ع�������ʾͼƬ
		.showImageForEmptyUri(R.drawable.ic_empty)  //image���ӵ�ַΪ��ʱ
		.showImageOnFail(R.drawable.ic_error)  //image����ʧ��
		.cacheInMemory(true)  //����ͼƬʱ�����ڴ��м��ػ���
		.cacheOnDisc(true)   //����ͼƬʱ���ڴ����м��ػ���
		//.displayer(new RoundedBitmapDisplayer(100))  //�����û�����ͼƬtask(������Բ��ͼƬ��ʾ)
		.build();

		listView = (ListView) findViewById(android.R.id.list);
		//��������
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				startImagePagerActivity(position);
//			}
//		});
		newsUrl = getIntent().getStringExtra("NEWS_URL");
		noticle= (NewsBean) getIntent().getSerializableExtra("NOTICLE_NEWS");  
		if(newsUrl==null){
			newsUrl=noticle.getUrl();
			isNews=false;
		}
		this.initView();
		new Thread(){
	          @Override  
	          public void run() {  
	              // TODO Auto-generated method stub  
	              super.run(); 
	              if(isNews){
	            	  try {
						newsInfo=InternetDataCatcher.getNewDetails(newsUrl);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	              }else{
	            	  noticleContext=InternetDataCatcher.getNoticleDetails(newsUrl);
	              }
	             
	              handler.sendEmptyMessage(0); 
	          }  
	      }.start(); 
	}
	private Handler handler = new Handler() {  
		  
		public void handleMessage(Message msg) { 
	      	  
	          switch (msg.what) {  
	          case 0:  
	        	  if(isNews){
		        	mTitle.setText(newsInfo.getTitle());
		        	mPubDate.setText(newsInfo.getDate());
		        	mAuthor.setText(newsInfo.getAuthor());
		        	if(newsInfo.getPictures().size()>0){
		        		listView.setAdapter(new ItemAdapter(newsInfo.getPictures()));
		        	}else{
		        		listView.setVisibility(View.GONE);
		        		scrollView.setVisibility(View.VISIBLE);
		        		mContext.setText(newsInfo.getContext());
		        	}
	        	  }else{
	        		  mTitle.setText(noticle.getTitle());
			          mPubDate.setText(noticle.getPubDate());
			          mAuthor.setText(noticle.getAuthor());
	        		  listView.setVisibility(View.GONE);
	        		  scrollView.setVisibility(View.VISIBLE);
	        		  mContext.setText(noticleContext);
	        	  }
	        	mProgressbar.setVisibility(View.GONE);
	        	break;  
	          }
	      };  
	  }; 
	  public static Bitmap returnBitMap(String path) {  
	        URL url = null;  
	        Bitmap bitmap = null;  
	        try {  
	            url = new URL(path);  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//����HttpURLConnection����,���ǿ��Դ������л�ȡ��ҳ����.  
	            conn.setDoInput(true);  
	            conn.connect();  
	            InputStream is = conn.getInputStream(); //�õ����緵�ص�������  
	            bitmap = BitmapFactory.decodeStream(is);  
	            is.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return bitmap;  
	    }  
	private void initView() {
		
		mHome = (ImageView) findViewById(R.id.news_detail_home);
		mProgressbar = (ProgressBar) findViewById(R.id.news_detail_head_progress);

		mTitle = (TextView) findViewById(R.id.news_detail_title);
		mAuthor = (TextView) findViewById(R.id.news_detail_author);
		mPubDate = (TextView) findViewById(R.id.news_detail_date);
		mContext=(TextView) findViewById(R.id.news_detail_context_2);
		picture=(ImageView) findViewById(R.id.imageView1);
		scrollView = (ScrollView) findViewById(R.id.news_detail_scrollview);
		mHome.setOnClickListener(homeClickListener);
	}

	private View.OnClickListener homeClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(NewsDetailsAct.this, NewsAct.class);
			startActivity(intent);
			finish();
		}
	};
	private void startImagePagerActivity(int position) {
//		Intent intent = new Intent(this, ImagePagerActivity.class);
//		intent.putExtra(Extra.IMAGES, imageUrls);
//		intent.putExtra(Extra.IMAGE_POSITION, position);
//		startActivity(intent);
	}

	/**�Զ���ͼƬ������**/
	class ItemAdapter extends BaseAdapter {
		
		public List<String> list;
		public ItemAdapter(List<String> list){
			this.list=list;
		}
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		private class ViewHolder {
			public TextView context;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			convertView = null;//���û������
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_list_image, parent, false);
				holder = new ViewHolder();
				holder.context=(TextView) view.findViewById(R.id.news_detail_context);
				holder.image = (ImageView) view.findViewById(R.id.imageView1);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
			if(position==0){
				holder.context.setText(newsInfo.getContext());
			}else{
				holder.context.setText("");
				holder.context.setVisibility(View.GONE);
			}
			//Adds display image task to execution pool. Image will be set to ImageView when it's turn.
			imageLoader.init(ImageLoaderConfiguration.createDefault(NewsDetailsAct.this));
			imageLoader.displayImage(list.get(position), holder.image, options, animateFirstListener);
			
			return view;
		}
	}

	/**ͼƬ���ؼ����¼�**/
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500); //����image���ض���500ms
					displayedImages.add(imageUri); //��ͼƬuri��ӵ�������
				}
			}
		}
	}
	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
}
