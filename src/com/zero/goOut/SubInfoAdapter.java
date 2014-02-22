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
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Title: BusTrackInfoAdapter Description:自定义适配器，用于把公交的到站信息填充到ListView中
 * 
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */

public class SubInfoAdapter extends ArrayAdapter {

	private LayoutInflater mInflater;
	public Context context;
	public List<SubBusInfo> busList = new ArrayList<SubBusInfo>();

	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public SubInfoAdapter(Context context, List<SubBusInfo> objects) {
		super(context, R.layout.bus_track_unit, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mInflater = LayoutInflater.from(this.context);
		this.busList = objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (busList != null)
			return busList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (busList != null)
			return busList.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (busList != null)
			return position;
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.bus_track_unit, parent,
					false);
			holder = new Holder();
			holder.carNumber = (TextView) convertView
					.findViewById(R.id.car_number_textView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.carNumber.setText(String.valueOf(busList.get(position)
				.getNumber()));

		return convertView;
	}

	class Holder {
		TextView carNumber; // 项目1
	}

	// 退出
	public void exit() {
		busList = null;
		notifyDataSetChanged();
		mInflater = null;
		context = null;
	}
}
