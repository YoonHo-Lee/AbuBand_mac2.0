package com.example.tacademy.abuband.Baby;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.abuband.R;


/**
 * Created by Tacademy on 2015-11-05.
 */
public class BabyItemView extends RelativeLayout {
    public BabyItemView(Context context) {
        super(context);
        init();
    }


    BabyItemData mData;

    ImageView babyList_iconView;
    TextView babyList_nameView, babyList_birthView, babyList_genderView;

    private void init() {
        inflate(getContext(), R.layout.view_baby_item, this);
        babyList_iconView = (ImageView) findViewById(R.id.babyList_icon);
        babyList_nameView = (TextView) findViewById(R.id.babyList_name);
        babyList_birthView = (TextView) findViewById(R.id.babyList_birth);
        babyList_genderView = (TextView) findViewById(R.id.babyList_gender);

    }

    public void setItemData(BabyItemData data)  {
        mData = data;

        if (data.babyList_icon != null) {
            //서버랑 연동시 이미지로더를 이용해서 이미지 불러오기
//            babyList_iconView.setImageDrawable();
        }
        babyList_nameView.setText(data.babyList_Name);
        babyList_birthView.setText(data.babyList_birth);
        babyList_genderView.setText(data.babyList_gender);
    }
}
