/**
 * 
 */
package com.zero.jxauapp;

import com.zero.news.InternetDataCatcher;
import com.zero.news.NewsBean;
import com.zero.news.NewsDetailsBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

 /**   
 * Title: TestNewDetailsAct
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-28
 */

public class NewsDetailsAct extends Activity{
	
	private ImageView mHome;
	private ProgressBar mProgressbar;
	
	private TextView mTitle;
	private TextView mAuthor;
	private TextView mPubDate;
	private TextView article;

	private String newsUrl;
	private NewsDetailsBean newsInfo;
	private String noticleContext;
	private boolean isNews=true;
	private NewsBean noticle;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		
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
	            	  newsInfo=InternetDataCatcher.getNewDetails(newsUrl);
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
		        	article.setText(newsInfo.getContext());
		        	mPubDate.setText(newsInfo.getDate());
		        	mAuthor.setText(newsInfo.getAuthor());
	        	  }else{
	        		  mTitle.setText(noticle.getTitle());
	        		  article.setText(noticleContext);
			          mPubDate.setText(noticle.getPubDate());
			          mAuthor.setText(noticle.getAuthor());
	        	  }
	        	mProgressbar.setVisibility(View.GONE);
	        	break;  
	          }
	      };  
	  }; 
	private void initView() {
		
		mHome = (ImageView) findViewById(R.id.news_detail_home);
		mProgressbar = (ProgressBar) findViewById(R.id.news_detail_head_progress);

		mTitle = (TextView) findViewById(R.id.news_detail_title);
		mAuthor = (TextView) findViewById(R.id.news_detail_author);
		mPubDate = (TextView) findViewById(R.id.news_detail_date);
		article=(TextView) findViewById(R.id.textView1);

		mHome.setOnClickListener(homeClickListener);
	}

	private View.OnClickListener homeClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(NewsDetailsAct.this, NewsAct.class);
			startActivity(intent);
			finish();
		}
	};
}
