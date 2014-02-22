/**
 * 
 */
package com.zero.goOut;

import java.util.ArrayList;
import java.util.List;

import com.zero.jxauapp.R;
import com.zero.widget.MyListView;

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
    public List<BusInfo> busList = new ArrayList<BusInfo>();

    public BusTrackResultListAdapter(Context context,List<BusInfo> busList) {
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
	            holder.listView=(MyListView) convertView.findViewById(R.id.BR_listView);
	            
	            convertView.setTag(holder);
	            
	        } else {
	            holder = (Holder) convertView.getTag();
	        }
	        
	        LineHelp lineHelp = new LineHelp();
	        
	        LineBean lineBean = new LineBean();
			lineBean.setLine(busList.get(position).getCurrentLine());
			lineBean.setStatus(busList.get(position).isDire());
			
			List<CurrentLineBean> listCurrentLineBean = lineHelp
					.getRealNameAndFirstbusAndLastbusOfTime(lineBean);
			List<CurrentLineAndDireBean> listCurrentLineAndDireBeans = lineHelp
					.getFirstAndLastStation(lineBean);
			
            holder.line
                    .setText(listCurrentLineBean.get(0).getRealName());
            holder.startStation
                    .setText(listCurrentLineAndDireBeans.get(0).getFirstStation());
            holder.endStation
            		.setText(listCurrentLineAndDireBeans.get(0).getLastStation());
            holder.startTime
                    .setText(listCurrentLineBean.get(0).getFirstTime());
            holder.endTime
            		.setText(listCurrentLineBean.get(0).getLastTime());
            
            BusTrackInfoAdapter adapter=new BusTrackInfoAdapter(convertView.getContext(),
            		busList.get(position).getListSubBusInfo());
            
            holder.listView.setAdapter(adapter);
            BusUnit.setListViewHeightBasedOnChildren(holder.listView);
	    	return convertView;
	}
	
    class Holder {
        TextView line; // 项目1
        TextView startStation; // 参数1
        TextView endStation;
        TextView startTime;
        TextView endTime;
        MyListView listView;
    }
}
