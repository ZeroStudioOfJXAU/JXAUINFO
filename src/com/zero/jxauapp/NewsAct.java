package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.List;

import com.zero.news.InternetDataCatcher;
import com.zero.news.NewsBean;
import com.zero.news.NewsListAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class NewsAct extends Activity{
	
	private Button framebtn_School_News;
	private Button framebtn_School_Notice;
	
	private ListView lvNews;
	private ListView lvNoticle;
	
	private NewsListAdapter adapter1;
	private NewsListAdapter adapter2;
	
	private List<NewsBean> list1=new ArrayList<NewsBean>();
	private List<NewsBean> list2=new ArrayList<NewsBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_news);
		init();
	}
	
	public void init(){
		framebtn_School_News = (Button) findViewById(R.id.frame_btn_news_lastest);
		framebtn_School_Notice = (Button) findViewById(R.id.frame_btn_news_blog);
		
		framebtn_School_News.setEnabled(false);
		framebtn_School_News.setOnClickListener(frameNewsBtnClick(framebtn_School_News));
		framebtn_School_Notice.setOnClickListener(frameNewsBtnClick(framebtn_School_Notice));
		
		lvNews= (ListView) findViewById(R.id.listView1);
		lvNoticle=(ListView) findViewById(R.id.listView2);

		new Thread(){  
	          @Override  
	          public void run() {  
	              // TODO Auto-generated method stub  
	              super.run();  
	              InternetDataCatcher.getNewsListDate();
	              list1=InternetDataCatcher.getNewsList();
	              list2=InternetDataCatcher.getNoticleList();
	              handler.sendEmptyMessage(0);  
	          }  
	      }.start(); 
		
      setListItemOnClick();
      
	}
    private Handler handler = new Handler() {  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 0:  
                adapter1=new NewsListAdapter(NewsAct.this,list1);
                adapter2=new NewsListAdapter(NewsAct.this,list2);
                lvNews.setAdapter(adapter1);
                lvNoticle.setAdapter(adapter2);
                break;  
            }  
        };  
    };  
	
	public void setListItemOnClick(){
		
		lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				  int position, long id) {
				
				  ListView lv = (ListView)parent;  
			      NewsBean news = (NewsBean) lv.getItemAtPosition(position); 
			      Intent intent=new Intent(NewsAct.this,NewsDetailsAct.class);
			      intent.putExtra("NEWS_URL",news.getUrl());
			      startActivity(intent);
			}
		});
		lvNoticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				  int position, long id) {
				
				  ListView lv = (ListView)parent;  
			      NewsBean news = (NewsBean) lv.getItemAtPosition(position); 
			      Intent intent=new Intent(NewsAct.this,NewsDetailsAct.class);
			      Bundle bundle = new Bundle();  
			      bundle.putSerializable("NOTICLE_NEWS", news);  
			      intent.putExtras(bundle);  
			      startActivity(intent);
			}
		});
	}
	
	private OnClickListener frameNewsBtnClick(final Button btn) {
		return new OnClickListener() {
			public void onClick(View v) {
				if (btn == framebtn_School_News) {
					framebtn_School_News.setEnabled(false);
				} else {
					framebtn_School_News.setEnabled(true);
				}
				if (btn == framebtn_School_Notice) {
					framebtn_School_Notice.setEnabled(false);
				} else {
					framebtn_School_Notice.setEnabled(true);
				}
				
				if (btn == framebtn_School_News) {
					lvNews.setVisibility(View.VISIBLE);
					lvNoticle.setVisibility(View.GONE);

				} else {
					lvNews.setVisibility(View.GONE);
					lvNoticle.setVisibility(View.VISIBLE);
				}
			}
		};
	}
}
