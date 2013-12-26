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
import android.widget.TextView;

 /**   
 * Title: BusTrackInfoAdapter
 * Description:自定义适配器，用于把公交的到站信息填充到ListView中
 * @author DaiS
 * @version 1.0
 * @date 2013-12-26
 */
public class BusTrackInfoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public Context context;
    public List<BusTrackInfo> busList = new ArrayList<BusTrackInfo>();

    public BusTrackInfoAdapter(Context context,List<BusTrackInfo> busList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.busList=busList;
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
            convertView = mInflater.inflate(R.layout.bus_track_unit,
                    null);
            holder = new Holder();
            holder.detail_project1_tv = (TextView) convertView
                    .findViewById(R.id.detail_project1_tv);
            holder.detail_params1_tv = (TextView) convertView
                    .findViewById(R.id.detail_params1_tv);
            holder.detail_project2_tv = (TextView) convertView
                    .findViewById(R.id.detail_project2_tv);
            holder.detail_params2_tv = (TextView) convertView
                    .findViewById(R.id.detail_params2_tv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //换行进行横向数据填充
            holder.detail_project1_tv
                    .setText(busList.get(position).getLine());
            holder.detail_params1_tv
                    .setText(busList.get(position).getNumber());
            holder.detail_project1_tv.setVisibility(View.VISIBLE);
            holder.detail_params1_tv.setVisibility(View.VISIBLE);
            //填充一行中后两个元素
            holder.detail_project2_tv.setText(busList
                    .get(position).getStation());
            holder.detail_params2_tv
                    .setText(busList.get(position).getDistance());
            holder.detail_project2_tv.setVisibility(View.VISIBLE);
            holder.detail_params2_tv.setVisibility(View.VISIBLE);
        return convertView;
    }

    class Holder {
        TextView detail_project1_tv; // 项目1
        TextView detail_params1_tv; // 参数1
        TextView detail_project2_tv;
        TextView detail_params2_tv;
    }
    
    // 退出
    public void exit() {
        busList = null;
        notifyDataSetChanged();
        mInflater = null;
        context = null;
    }
}
