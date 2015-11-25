package com.dnabuba.tacademy.abuband.Baby;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * Created by Tacademy on 2015-11-05.
 */
public class BabyItemView extends RelativeLayout {
    private final Context mContext;

    public BabyItemView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    RelativeLayout babyList_itemView;
    ImageView babyList_iconView, babyList_Update;
    TextView babyList_nameView, babyList_birthView, babyList_genderView;
    DisplayImageOptions options;
    BabyItemData mData;

    /************************* 이미지 클릭 리스너 *********************************/
    public interface OnImageClickListener {
        public void onImageClick(BabyItemView view, BabyItemData data);
    }

    OnImageClickListener mListener;
    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    /************************* End of 이미지 클릭 리스너 *********************************/

    private void init() {
//        inflate(getContext(), R.layout.view_baby_item, this);
//        babyList_iconView = (ImageView) findViewById(R.id.babyList_icon);
//        babyList_Update = (ImageView) findViewById(R.id.babyList_update_btn);
//        babyList_nameView = (TextView) findViewById(R.id.babyList_name);
//        babyList_birthView = (TextView) findViewById(R.id.babyList_birth);
//        babyList_genderView = (TextView) findViewById(R.id.babyList_gender);

        inflate(getContext(), R.layout.view_baby_item, this);

        babyList_itemView = (RelativeLayout) findViewById(R.id.babyList_itemView);
        babyList_iconView = (ImageView)findViewById(R.id.babyList_icon);
        babyList_Update = (ImageView) findViewById(R.id.babyList_update_btn);
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

        babyList_Update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onImageClick(BabyItemView.this, mData);
                }
            }
        });

    }

    public void setItemData(BabyItemData data)  {
        mData = data;
        babyList_nameView.setText(data.name);
        babyList_birthView.setText(data.birth+"");

        String textGender = "null";


        //선택된 아이인지 아닌지 판별
        if(data._id.equals(PropertyManager.getInstance().getPrefBaby()))    {
            //선택된아이
            //배경 : 검정, 녹색?
            //글씨 : 흰색
            babyList_itemView.setBackgroundResource(R.drawable.selector_babylist_item_select);
            babyList_nameView.setTextColor(Color.WHITE);
            babyList_birthView.setTextColor(Color.WHITE);
            babyList_genderView.setTextColor(Color.WHITE);
        }else {
            //선택되지 않은 아이
            //배경 : 흰색
            //글씨 : 검정
            babyList_itemView.setBackgroundResource(R.drawable.selector_babylist_item_unselect);
            babyList_nameView.setTextColor(Color.BLACK);
            babyList_birthView.setTextColor(Color.BLACK);
            babyList_genderView.setTextColor(Color.BLACK);
        }

        switch (data.gender)    {
            case 0:
                textGender = "남아";
                babyList_Update.setBackgroundColor(getResources().getColor(R.color.gender_boy));
                break;
            case 1:
                textGender = "여아";
                babyList_Update.setBackgroundColor(getResources().getColor(R.color.gender_girl));
                break;
        }
        babyList_genderView.setText(textGender);



        ImageLoader.getInstance().displayImage(data.image, babyList_iconView, options);
    }
}
