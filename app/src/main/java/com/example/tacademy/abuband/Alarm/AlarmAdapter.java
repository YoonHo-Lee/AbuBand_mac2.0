package com.example.tacademy.abuband.Alarm;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class AlarmAdapter extends BaseAdapter  {

    List<AlarmItemData> alarmItems = new ArrayList<AlarmItemData>();

    @Override
    public int getCount() {
        return alarmItems.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmItemView alarmView;
        if(convertView == null) {
            alarmView = new AlarmItemView(parent.getContext());
        }else  {
            alarmView = (AlarmItemView)convertView;
        }
        alarmView.setItemData(alarmItems.get(position));
        return alarmView;
    }

    public void add(AlarmItemData item) {
        alarmItems.add(item);
        notifyDataSetChanged();
    }
}
