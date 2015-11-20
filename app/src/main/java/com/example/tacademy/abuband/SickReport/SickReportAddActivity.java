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
    TextView sickAdd_date, sickAdd_maxTemp, sickAdd_babyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_report_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sickAdd_date = (TextView) findViewById(R.id.sickAdd_date);
        sickAdd_maxTemp = (TextView) findViewById(R.id.sickAdd_maxTemp);
        sickAdd_babyName = (TextView) findViewById(R.id.sickAdd_babyName);
        edit_sickTitle = (EditText) findViewById(R.id.edit_sickTitle);
        edit_sickMemo = (EditText) findViewById(R.id.edit_sickMemo);


        Intent intent = getIntent();


        sickAdd_date.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_DATE));
        sickAdd_babyName.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_NAME));
        edit_sickTitle.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_TITLE));
        edit_sickMemo.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_MEMO));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
