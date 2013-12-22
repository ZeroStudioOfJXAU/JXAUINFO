package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

 /**   
 * Title: CommonPhoneAct
 * Description:���õ绰ҳ��ʾ�������Ľ���
 * @author DaiS
 * @version 1.0
 * @date 2013-12-15
 */   
public class CommonPhoneAct extends FragmentActivity{
	
	private ListView listView;
    //private List<String> data = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         
        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        setContentView(listView);
    }
     
    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("У԰110 : 110");
        data.add("����ʳ��   : 189");
        data.add("����  : 122");
        data.add("��ͨ��� : 121");
        return data;
    }
}
