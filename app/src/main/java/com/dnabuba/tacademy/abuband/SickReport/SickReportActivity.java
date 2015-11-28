package com.dnabuba.tacademy.abuband.SickReport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SickReportActivity extends AppCompatActivity {

    TextView sick_date, sick_day, sick_maxTemp,  sick_title, sick_memo, sick_babyName;
    String _id, day;
    LinearLayout sickreport_titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

/*
        INTENT로 데이터 받는 부분 다 지우고,
                리스트의 값을 통해 서버에서 데이터 받아오고,
                    그 데이터를 SickReportAddActivity로 intent.putExtras로 보내줘야 할 것 같다.
*/


        sick_date = (TextView) findViewById(R.id.sickreport_date);
        sick_day = (TextView) findViewById(R.id.sickreport_dayOfWeek);
        sick_maxTemp = (TextView) findViewById(R.id.sickreport_maxTemp);
        sick_babyName = (TextView) findViewById(R.id.sickreport_babyName);
        sick_title = (TextView) findViewById(R.id.text_sickTitle);
        sick_memo = (TextView) findViewById(R.id.text_sickMemo);
        sickreport_titleBar = (LinearLayout) findViewById(R.id.sickreport_titleBar);

        Intent intent = getIntent();
        sick_date.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_DATE).substring(0,10));
        sick_maxTemp.setText(intent.getFloatExtra(SickReportListFragment.TAG_SR_MAXTEMP, 0.0f)+"");
        sick_babyName.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_NAME));
        sick_title.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_TITLE));
        sick_memo.setText(intent.getStringExtra(SickReportListFragment.TAG_SR_MEMO));
        _id = intent.getStringExtra(SickReportListFragment.TAG_SR__ID);

        getDateDay(sick_date.getText().toString(), "yyyy-MM-dd");
        sick_day.setText(day);

        float maxTemp = intent.getFloatExtra(SickReportListFragment.TAG_SR_MAXTEMP, 0.0f);
        Log.e("SickReport","maxTemp: "+maxTemp);

        if(maxTemp > 37.9f)   {
            sickreport_titleBar.setBackgroundColor(getResources().getColor(R.color.temp_high));
        }else if(maxTemp > 37.4f)   {
            sickreport_titleBar.setBackgroundColor(getResources().getColor(R.color.temp_mild));
        }else if(maxTemp > 35.9f)   {
            sickreport_titleBar.setBackgroundColor(getResources().getColor(R.color.temp_normal));
        }else if(maxTemp < 35.9f)    {
            sickreport_titleBar.setBackgroundColor(getResources().getColor(R.color.temp_low));
        }


//        float f = intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP,5.5f);
//        Toast.makeText(SickReportAddActivity.this, "플로트"+f, Toast.LENGTH_SHORT).show();

//        sick_date.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKDATE));
//        sick_maxTemp.setText(intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP) + "");
//        sick_title.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKTITLE));
//        sick_memo.setText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sickreport,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())   {
            case R.id.menu_sickreport_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //아픔일지 삭제
                                sickReportDelete(_id);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.menu_sickreport_update:
                Toast.makeText(this, "수정 코드 작성", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SickReportActivity.this, SickReportAddActivity.class);
                intent.putExtra(SickReportListFragment.TAG_SR_DATE,sick_date.getText().toString());
                intent.putExtra(SickReportListFragment.TAG_SR_MAXTEMP, sick_maxTemp.getText().toString());
                intent.putExtra(SickReportListFragment.TAG_SR_NAME,sick_babyName.getText().toString());
                intent.putExtra(SickReportListFragment.TAG_SR_TITLE,sick_title.getText().toString());
                intent.putExtra(SickReportListFragment.TAG_SR_MEMO, sick_memo.getText().toString());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sickReportDelete(String _id) {
        if (!TextUtils.isEmpty(_id)) {
            NetworkManager.getInstance().setSickReportDelete(this, _id, new NetworkManager.OnResultListener<NetworkCodeResult>() {
                @Override
                public void onSuccess(NetworkCodeResult result) {
                    finish();
                }

                @Override
                public void onFail(int code) {

                }
            });
        }else {
            Toast.makeText(SickReportActivity.this, "네트워크 오류 입니다.\n다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }


    //년-월-일 받아서 요일로 변경
    public void getDateDay(String date, String dateType)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = null;
        try {
            nDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1:
                day = "일요일";
                break ;
            case 2:
                day = "월요일";
                break ;
            case 3:
                day = "화요일";
                break ;
            case 4:
                day = "수요일";
                break ;
            case 5:
                day = "목요일";
                break ;
            case 6:
                day = "금요일";
                break ;
            case 7:
                day = "토요일";
                break ;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
