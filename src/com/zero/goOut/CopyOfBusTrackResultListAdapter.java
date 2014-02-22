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


public class CopyOfBusTrackResultListAdapter extends BaseAdapter{
	
	
	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	private LayoutInflater mInflater;
    public Context context;
    public List<BusInfo> busList = new ArrayList<BusInfo>();

    public CopyOfBusTrackResultListAdapter(Context context,List<BusInfo> busList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.busList=busList;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return busList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return busList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 Holder holder = null;
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.bus_result_list_item,
	                    null);
	            holder = new Holder();
	            holder.listView=(MyListView) convertView.findViewById(R.id.BR_listView);
	            convertView.setTag(holder);
	        } else {
	            holder = (Holder) convertView.getTag();
	        }
			BusTrackInfoAdapter adapter=new BusTrackInfoAdapter(convertView.getContext(),
            		busList.get(position).getListSubBusInfo());
            holder.listView.setAdapter(adapter);
            BusUnit.setListViewHeightBasedOnChildren(holder.listView);
	    	return convertView;
	}
	
    class Holder {
        MyListView listView;
    }
}
