package com.dnabuba.tacademy.abuband.Baby;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;


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
//                .displayer(new RoundedBitmapDisplayer(100)) //곡률 50:모서리곡선인정사각형, 100원형
                //위 소스의 이미지 늘어나는 버그를 잡고, 정 가운데를 크롭
                .displayer(new BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
                        Bitmap centerCroppedBitmap;
                        if (bitmap.getWidth() >= bitmap.getHeight()){ //in case of width larger than height
                            centerCroppedBitmap = Bitmap.createBitmap(
                                    bitmap,
                                    bitmap.getWidth()/2 - bitmap.getHeight()/2,
                                    0,
                                    bitmap.getHeight(),
                                    bitmap.getHeight()
                            );
                        }else{ //in case of height larger than width
                            centerCroppedBitmap = Bitmap.createBitmap(
                                    bitmap,
                                    0,
                                    bitmap.getHeight()/2 - bitmap.getWidth()/2,
                                    bitmap.getWidth(),
                                    bitmap.getWidth()
                            );
                        }
                        //** then apply bitmap rounded **//
                        RoundedBitmapDrawable circledDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), centerCroppedBitmap);
                        circledDrawable.setCircular(true);
                        circledDrawable.setAntiAlias(true);
                        imageAware.setImageDrawable(circledDrawable);
                    }
                })
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
            //배경 : 녹색
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
