package com.example.tacademy.abuband.SickReport;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.abuband.R;

public class SickReportAddActivity extends AppCompatActivity {

/*  =================================================

    레이아웃 맨 윗줄 비율 주는거 모르게썽 ㅠ

    똘미가 요일 안한대서, 내가 해야할듯...ㅠㅠ

    업페이지 버튼

    저장하기 버튼 만들기

  ================================================= */

    EditText edit_sickTitle, edit_sickMemo;
    TextView sickAdd_date, sickAdd_maxTemp, sickAdd_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_report_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sickAdd_date = (TextView) findViewById(R.id.sickAdd_date);
        sickAdd_maxTemp = (TextView) findViewById(R.id.sickAdd_maxTemp);
        edit_sickTitle = (EditText) findViewById(R.id.edit_sickTitle);


//        Intent intent = getIntent();



//        float f = intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP,5.5f);
//        Toast.makeText(SickReportAddActivity.this, "플로트"+f, Toast.LENGTH_SHORT).show();
//
//        sickAdd_date.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKDATE));
//        sickAdd_maxTemp.setText(intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP)+"");
//        edit_sickTitle.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKTITLE));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
