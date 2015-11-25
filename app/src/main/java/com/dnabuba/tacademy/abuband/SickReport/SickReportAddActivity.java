package com.dnabuba.tacademy.abuband.SickReport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.R;

public class SickReportAddActivity extends AppCompatActivity {

/*  =================================================

    레이아웃 맨 윗줄 비율 주는거 모르게썽 ㅠ

    똘미가 요일 안한대서, 내가 해야할듯...ㅠㅠ

    업페이지 버튼

    저장하기 버튼 만들기

  ================================================= */

    EditText edit_sickTitle, edit_sickMemo;
    TextView sickAdd_date, sickAdd_maxTemp, sickAdd_babyName;
    String image;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sickreport_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sickreport_add:
                sickReportUpdate(edit_sickTitle.getText().toString(), edit_sickMemo.getText().toString(), image);
                Toast.makeText(SickReportAddActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sickReportUpdate(String title, String memo, String image) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(memo)) {
            NetworkManager.getInstance().setSickReportUpdate(this, title, memo, image, new NetworkManager.OnResultListener<NetworkCodeResult>() {


                @Override
                public void onSuccess(NetworkCodeResult result) {

                }

                @Override
                public void onFail(int code) {

                }
            });
        } else {
            Toast.makeText(SickReportAddActivity.this, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
        }
    }


    //타이틀바의 뒤로가기 제어
    @Override
    public boolean onSupportNavigateUp() {
        onUpBack();
        return super.onSupportNavigateUp();
    }

    //소프트키의 뒤로가기 제어
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onUpBack();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onUpBack()  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제")
                .setMessage("저장하지 않고\n이전 화면으로 이동하시겠습니까?\n저장하지 않은 데이터는 반영되지 않습니다.")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //아픔일지 수정화면 닫기
                        finish();
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
    }
}
