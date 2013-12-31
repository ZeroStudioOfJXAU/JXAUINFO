/**
 * 
 */
package com.zero.news;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zero.app.AppConfigue;

 /**   
 * Title: InternetHelper
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2013-12-28
 */   
public class InternetDataCatcher {
	
	private static List<String> dateList;
	/**
	 * ��ȡ�����б�
	 */
	public static List<NewsBean> getNewsList(){
//		getNewsListDate();
		List<NewsBean> newsList=new ArrayList<NewsBean>();
		String abs = AppConfigue.HOST_ABS;
		String url = AppConfigue.NEWS_LIST_URL;
		
		String content = "";
		try {
			Document doc = Jsoup.connect(url).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			content = doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Document doc = Jsoup.parse(content);
		Elements divs = doc.getElementsByClass("columnStyle");
		String divContent = divs.toString();
		
		Document doc2 = Jsoup.parse(divContent, abs);
		Elements linkStrs = doc2.getElementsByTag("a");
		for (Element linkStr : linkStrs) {
			String title = linkStr.getElementsByTag("a").text();
			String news_url = linkStr.getElementsByTag("a").attr("href");
			newsList.add(new NewsBean(title,"ũ�����","֮ǰ",abs+news_url));
		}
		if(dateList.size()!=0){
			for(int i=0;i<8;i++){
				newsList.get(i).setPubDate(dateList.get(i));
			}
		}
		return newsList;
	}
	
	public static void getNewsListDate(){
		List<String> dateList2=new ArrayList<String>();
		String url =AppConfigue.HOST_URL;
		String content = "";
		try {
			Document doc = Jsoup.connect(url).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			content = doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Document doc = Jsoup.parse(content);
		Elements divs=doc.getElementsByTag("div");
		String divContent = divs.toString();
		Document doc2 = Jsoup.parse(divContent);
		 Elements linkStrs=doc2.getElementsByAttributeValue("style",
	        		"white-space:nowrap");
		for (Element linkStr : linkStrs) {
		    String date=linkStr.text();
		    dateList2.add(date);
		}
		dateList=dateList2;
		
	}
	
	/**
	 * ��ȡ������ϸ����
	 */
	public static NewsDetailsBean getNewDetails(String url) {
		String title="";
		String content = "";
		String context="";
		try {
			Document doc = Jsoup.connect(url).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			content = doc.toString();
			title=doc.title();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(content);
		Elements linkStrs = doc.getElementsByTag("p");
		for (Element linkStr : linkStrs) {
			context += "       "+linkStr.text().replace("?", " ")+"\r\n"+'\n';
		}
		Elements dateStrs = doc.getElementsByTag("div");
		String date = dateStrs.text().substring(5,15);
		int index1=-1,index2=-1;
		for(int i=context.length()-1;i>=0;i--){
			if(context.charAt(i)=='��'||context.charAt(i)==')'){
				index1=i;
			}
			if(context.charAt(i)=='��'||context.charAt(i)=='('){
				index2=i;
				break;
			}
		}
		int index=context.lastIndexOf("��");
		String origin=null;
		if(index2>index){
			origin=context.substring(index2+1, index1);
			context=context.substring(0, index2);
		}
		
		return new NewsDetailsBean(title,context,date,origin);
	}
	
	/**
	 * ��ȡ֪ͨ�б�
	 */
	public static List<NewsBean> getNoticleList(){
		List<NewsBean> newsList=new ArrayList<NewsBean>();
		String abs = AppConfigue.HOST_ABS;
		String url = AppConfigue.NOTICLE_LIST_URL;
		
		String content = "";
		try {
			Document doc = Jsoup.connect(url).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			content = doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Document doc = Jsoup.parse(content);
		Elements divs = doc.getElementsByTag("div");
		Document doc2 = Jsoup.parse(divs.toString());
		Elements table=doc2.getElementsByAttributeValue("class", "columnStyle");
		Document doc3 = Jsoup.parse(table.toString());
		Elements linkStrs = doc3.getElementsByTag("a");
		for (Element linkStr : linkStrs) {
			String title = linkStr.getElementsByTag("a").text();
			String news_url = linkStr.getElementsByTag("a").attr("href");
			int indexEnd1=-1,indexEnd2=-1,index=-1;
			
			indexEnd1=title.indexOf(":");
			indexEnd2=title.indexOf("��");
			if(indexEnd1!=-1)
				index=indexEnd1;
			if(indexEnd2!=-1)
				index=indexEnd2;
			if(index!=-1){
				String origin=title.substring(0, index);
				String subTitle=title.substring(index+1);
				newsList.add(new NewsBean(subTitle,origin,"֮ǰ",abs+news_url));
			}else{
				newsList.add(new NewsBean(title,"ũ�����","֮ǰ",abs+news_url));
			}
		}
		
		for(int i=8;i<dateList.size();i++){
			newsList.get(i-8).setPubDate(dateList.get(i));
		}
		return newsList;
	}    
	/**
	 * ��ȡ֪ͨ��ϸ����
	 */
	public static String getNoticleDetails(String url) {
		String content = "";
		String context="";
		try {
			Document doc = Jsoup.connect(url).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			content = doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(content);
		Elements linkStrs = doc.getElementsByTag("p");
		for (Element linkStr : linkStrs) {
			context += "       "+linkStr.text().replace("?", " ")+"\r\n"+'\n';
		}
		return context;
	}
}
