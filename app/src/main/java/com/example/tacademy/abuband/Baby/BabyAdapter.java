package com.example.tacademy.abuband.Baby;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-05.
 */
public class BabyAdapter extends BaseAdapter implements BabyItemView.OnImageClickListener {
    List<BabyItemData> babyItems = new ArrayList<BabyItemData>();

    String email;

    /**************************************이미지 클릭 리스너*********************************************/

    public interface OnAdapterImageListener {
        public void onAdapterImageClick(BabyAdapter adapter, BabyItemView view, BabyItemData data);
    }

    OnAdapterImageListener mListener;

    public void setOnAdapterImageListener(OnAdapterImageListener listener) {
        mListener = listener;
    }

    @Override
    public void onImageClick(BabyItemView view, BabyItemData data) {
        if (mListener != null) {
            mListener.onAdapterImageClick(this, view, data);
        }
    }

    /**************************************End of 이미지 클릭 리스너*********************************************/

    public void setEmail(String email) {
        this.email = email;
    }

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
        babyView.setOnImageClickListener(this);
        babyView.setItemData(babyItems.get(position));
        return babyView;
    }

    public void add(BabyItemData item) {
        babyItems.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        babyItems.clear();
        notifyDataSetChanged();
    }
}
