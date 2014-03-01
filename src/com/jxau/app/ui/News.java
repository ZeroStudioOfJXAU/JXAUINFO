package com.jxau.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.jxau.app.adapter.NewsListAdapter;
import com.jxau.app.bean.NewsBean;
import com.jxau.app.common.InternetDataCatcher;
import com.jxau.app.widget.PullToRefreshListView;
import com.zero.jxauapp.R;

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
import android.widget.ProgressBar;
import android.widget.Toast;

public class News extends Activity{
	
	private Button framebtn_School_News;
	private Button framebtn_School_Notice;
	
	private ProgressBar proBar;
	
	private PullToRefreshListView lvNews;
	private PullToRefreshListView lvNoticle;
	
	private NewsListAdapter adapter1;
	private NewsListAdapter adapter2;
	
	private List<NewsBean> newsResultList=new ArrayList<NewsBean>();
	private List<NewsBean> noticleResultList=new ArrayList<NewsBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		init();
	}
	
	private Runnable getDataRunable = new Runnable() {
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		InternetDataCatcher.getNewsListDate();
            newsResultList=InternetDataCatcher.getNewsList();
            noticleResultList=InternetDataCatcher.getNoticleList();
            handler.sendEmptyMessage(0);
    	}
    };
    
	public void init(){
		framebtn_School_News = (Button) findViewById(R.id.frame_btn_news_lastest);
		framebtn_School_Notice = (Button) findViewById(R.id.frame_btn_news_blog);
		
		framebtn_School_News.setEnabled(false);
		framebtn_School_News.setOnClickListener(frameNewsBtnClick(framebtn_School_News));
		framebtn_School_Notice.setOnClickListener(frameNewsBtnClick(framebtn_School_Notice));
		
		proBar=(ProgressBar) findViewById(R.id.main_head_progress);
		
		lvNews= (PullToRefreshListView) findViewById(R.id.frame_listview_news);
		lvNoticle=(PullToRefreshListView) findViewById(R.id.frame_listview_noticle);
		new Thread(getDataRunable).start();
		setListItemOnClick();
	}
    private Handler handler = new Handler() {  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 0:
            	proBar.setVisibility(View.GONE);
            	if(noticleResultList.size()==0 || newsResultList.size()==0){
            		Toast.makeText(getApplicationContext(), "农大官网数据出错！", Toast.LENGTH_LONG).show();
                }
                adapter1=new NewsListAdapter(News.this,newsResultList);
                adapter2=new NewsListAdapter(News.this,noticleResultList);
                
                lvNews.setAdapter(adapter1);
                lvNoticle.setAdapter(adapter2);
                lvNews.onRefreshComplete();
        		lvNoticle.onRefreshComplete();
                break;  
            }  
        };  
    };  
	
	public void setListItemOnClick(){
		lvNews.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				new Thread(getDataRunable).start();
			}
		});
		lvNoticle.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				new Thread(getDataRunable).start();
			}
		});
		lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				  int position, long id) {
				
				  ListView lv = (ListView)parent;  
			      NewsBean news = (NewsBean) lv.getItemAtPosition(position); 
			      Intent intent=new Intent(News.this,NewsDetails.class);
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
			      Intent intent=new Intent(News.this,NewsDetails.class);
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
