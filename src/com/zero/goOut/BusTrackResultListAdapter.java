/**
 * 
 */
package com.zero.goOut;

import java.util.ArrayList;
import java.util.List;

import com.zero.jxauapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

 /**   
 * Title: BusTrackResultListAdapter
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2014-1-1
 */

public class BusTrackResultListAdapter extends BaseAdapter{
	
	
	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	private LayoutInflater mInflater;
    public Context context;
    public List<BusResultBean> busList = new ArrayList<BusResultBean>();

    public BusTrackResultListAdapter(Context context,List<BusResultBean> busList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.busList=busList;
    }
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return busList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return busList.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	/** 

     * 设置Listview的高度 

     */  

    public void setListViewHeight(ListView listView) {   

        ListAdapter listAdapter = listView.getAdapter();    
        if (listAdapter == null) {   
            return;   
        }   
        int totalHeight = 0;   
        for (int i = 0; i < listAdapter.getCount(); i++) {   
            View listItem = listAdapter.getView(i, null, listView);   
            listItem.measure(0, 0);   
            totalHeight += listItem.getMeasuredHeight();   
        }   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        listView.setLayoutParams(params);  
    }  
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 Holder holder = null;
	        if (convertView == null) {
	        	
	            convertView = mInflater.inflate(R.layout.bus_result_list_item,
	                    null);
	            holder = new Holder();
	            holder.line = (TextView) convertView
	                    .findViewById(R.id.BR_line_textView);
	            holder.startStation = (TextView) convertView
	                    .findViewById(R.id.BR_start_station_textView);
	            holder.endStation = (TextView) convertView
	                    .findViewById(R.id.BR_end_station_textView);
	            holder.startTime = (TextView) convertView
	                    .findViewById(R.id.BR_start_time_textView);
	            holder.endTime = (TextView) convertView
	                    .findViewById(R.id.BR_end_time_textView);
	            holder.listView=(ListView) convertView.findViewById(R.id.BR_listView);
	            
	            convertView.setTag(holder);
	            
	        } else {
	            holder = (Holder) convertView.getTag();
	        }
	        
	        BusTimeBean timeBean=new BusTimeBean("6:00","18:00","240");
	        BusStationBean stationBean=new BusStationBean("财大","下落");
	    		
            holder.line
                    .setText(timeBean.getLine());
            holder.startStation
                    .setText(stationBean.getStartStation());
            holder.endStation
            		.setText(stationBean.getEndStation());
            holder.startTime
                    .setText(timeBean.getStartTime());
            holder.endTime
            		.setText(timeBean.getEndTime());
            
            BusTrackInfoAdapter adapter=new BusTrackInfoAdapter(convertView.getContext(),
            		busList.get(position).getResultList());
            
            holder.listView.setAdapter(adapter);
            setListViewHeight(holder.listView);
	    	return convertView;
	}
	
    class Holder {
        TextView line; // 项目1
        TextView startStation; // 参数1
        TextView endStation;
        TextView startTime;
        TextView endTime;
        ListView listView;
    }
}
