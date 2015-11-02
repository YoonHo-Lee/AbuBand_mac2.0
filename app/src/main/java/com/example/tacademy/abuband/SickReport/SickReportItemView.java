package com.example.tacademy.abuband.SickReport;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.abuband.R;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class SickReportItemView extends RelativeLayout {
    public SickReportItemView(Context context) {
        super(context);
        init();
    }

    TextView sickDate, sickTemp, sickTitle;
    SickReportItemData mData;

    private void init() {
        inflate(getContext(), R.layout.view_sick_report_item, this);
        sickDate = (TextView)findViewById(R.id.sickList_date);
        sickTemp = (TextView)findViewById(R.id.sickList_temp);
        sickTitle = (TextView)findViewById(R.id.sickList_title);
    }

    public void setItemData(SickReportItemData data) {
        mData = data;
        sickDate.setText(data.sickDate);
        sickTemp.setText(data.sickMaxTemp+"");
        sickTitle.setText(data.sickTitle);
    }
}
