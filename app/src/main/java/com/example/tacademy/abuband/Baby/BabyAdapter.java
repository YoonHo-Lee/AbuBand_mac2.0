package com.example.tacademy.abuband.Baby;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-05.
 */
public class BabyAdapter extends BaseAdapter {
    List<BabyItemData> babyItems = new ArrayList<BabyItemData>();

    @Override
    public int getCount() {
        return babyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return babyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BabyItemView babyView;
        if (convertView == null) {
            babyView =  new BabyItemView(parent.getContext());
        } else {
            babyView = (BabyItemView) convertView;
        }
//        view.setOnImageClickListener(mImageClickListener);
        babyView.setItemData(babyItems.get(position));
        return babyView;
    }

    public void add(BabyItemData item) {
        babyItems.add(item);
        notifyDataSetChanged();
    }
}
