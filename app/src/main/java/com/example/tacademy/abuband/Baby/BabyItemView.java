package com.example.tacademy.abuband.Baby;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.abuband.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * Created by Tacademy on 2015-11-05.
 */
public class BabyItemView extends RelativeLayout {
    public BabyItemView(Context context) {
        super(context);
        init();
    }


    ImageView babyList_iconView;
    TextView babyList_nameView, babyList_birthView, babyList_genderView;

    DisplayImageOptions options;

    private void init() {
//        inflate(getContext(), R.layout.view_baby_item, this);
        babyList_iconView = (ImageView) findViewById(R.id.babyList_icon);
        babyList_nameView = (TextView) findViewById(R.id.babyList_name);
        babyList_birthView = (TextView) findViewById(R.id.babyList_birth);
        babyList_genderView = (TextView) findViewById(R.id.babyList_gender);

        inflate(getContext(), R.layout.view_baby_item, this);
        babyList_iconView = (ImageView)findViewById(R.id.babyList_icon);
        babyList_nameView = (TextView)findViewById(R.id.babyList_name);
        babyList_birthView = (TextView)findViewById(R.id.babyList_birth);
        babyList_genderView = (TextView)findViewById(R.id.babyList_gender);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(50))
                .build();

    }

    public void setItemData(BabyItemData data)  {

        babyList_nameView.setText(data.name);
        babyList_birthView.setText(data.birth+"");

        String textGender = "null";
        switch (data.gender)    {
            case 0:
                textGender = "남아";
                break;
            case 1:
                textGender = "여아";
                break;
        }
        babyList_genderView.setText(textGender);


        ImageLoader.getInstance().displayImage(data.image, babyList_iconView, options);
    }
}
