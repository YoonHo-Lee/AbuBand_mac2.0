package com.example.tacademy.abuband.Alarm;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.abuband.R;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class AlarmItemView extends RelativeLayout {

    AlarmItemData mData;
    TextView alarm_temp;

    public AlarmItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_alarm_item, this);
        alarm_temp = (TextView)findViewById(R.id.text_alarm_temp);
    }

    public void setItemData(AlarmItemData data) {
        mData = data;
        alarm_temp.setText(data.alarm_temp+"");
    }
}
