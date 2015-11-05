package com.example.tacademy.abuband.SickReport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.abuband.R;

public class SickReportActivity extends AppCompatActivity {

    TextView sick_date, sick_maxTemp,  sick_title, sick_memo;

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
        sick_maxTemp = (TextView) findViewById(R.id.sickreport_maxTemp);
        sick_title = (TextView) findViewById(R.id.text_sickTitle);
        sick_memo = (TextView) findViewById(R.id.text_sickMemo);


        Intent intent = getIntent();

//        float f = intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP,5.5f);
//        Toast.makeText(SickReportAddActivity.this, "플로트"+f, Toast.LENGTH_SHORT).show();

        sick_date.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKDATE));
        sick_maxTemp.setText(intent.getExtras().getFloat(SickReportListFragment.TAG_SICKMAXTEMP) + "");
        sick_title.setText(intent.getExtras().getString(SickReportListFragment.TAG_SICKTITLE));
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
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
