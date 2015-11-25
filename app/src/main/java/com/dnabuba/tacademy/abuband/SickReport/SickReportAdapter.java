package com.dnabuba.tacademy.abuband.SickReport;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class SickReportAdapter extends BaseAdapter {
    List<SickReportItemData> sickItems = new ArrayList<>();
    private String email;

    @Override
    public int getCount() {
        return sickItems.size();
    }

    @Override
    public Object getItem(int position) {
        return sickItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SickReportItemView sickView;
        if(convertView == null) {
            sickView = new SickReportItemView(parent.getContext());
        }else   {
            sickView = (SickReportItemView) convertView;
        }
        sickView.setItemData(sickItems.get(position));
        return sickView;
    }

    public void add(SickReportItemData item) {
        sickItems.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        sickItems.clear();
        notifyDataSetChanged();
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
