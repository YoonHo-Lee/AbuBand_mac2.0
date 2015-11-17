package com.example.tacademy.abuband.Band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tacademy.abuband.Baby.BabyAddActivity;
import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;
import com.example.tacademy.abuband.Setting.SettingFragment;

public class BandAddActivity extends AppCompatActivity {

    Button btn_send, btn_add;
    EditText edit_bandSerial;

    public static final int RESULT_CODE_0 = 0;
    public static final int RESULT_CODE_1 = 1;
    public static final int RESULT_CODE_2 = 2;
    public static final int RESULT_CODE_3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_add);
        setTitle(getString(R.string.title_band_serial));

        btn_send = (Button) findViewById(R.id.btn_serialSend);
        btn_add = (Button) findViewById(R.id.btn_serialAdd);
        edit_bandSerial = (EditText) findViewById(R.id.edit_bandSerial);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버로 밴드의 시리얼을 보내서 확인 받는 과정
                //아래는 true값 리턴 받았을때를 가정
                if (!TextUtils.isEmpty(edit_bandSerial.getText().toString())) {
                    BandSeralCheck(edit_bandSerial.getText().toString());
                } else {
                    Toast.makeText(BandAddActivity.this, "시리얼 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Intent i = getIntent();
        final int flag = i.getIntExtra(SettingFragment.SETTING_FLAG,999);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flag값 받아서 설정에서 왔을때를 구분
                if(flag == 777) {   // 설정에서 넘어온 경우
                    finish();
                }else   {   // 처음 왔을때는 아이 등록화면으로
                    Intent intent = new Intent(BandAddActivity.this, BabyAddActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void BandSeralCheck(String serial) {
        NetworkManager.getInstance().getBandSerial(this, serial, new NetworkManager.OnResultListener<BandItemData>() {
            @Override
            public void onSuccess(BandItemData result) {
                    switch (result.code)    {
                        case RESULT_CODE_0:
                            Toast.makeText(BandAddActivity.this, "통신환경을 확인해 주세요.\ncode:3 ", Toast.LENGTH_SHORT).show();
                            break;

                        case RESULT_CODE_1:
                            Toast.makeText(BandAddActivity.this, "시리얼 확인", Toast.LENGTH_SHORT).show();
                            ImageView iv = (ImageView) findViewById(R.id.image_serialCheck);
                            iv.setVisibility(View.VISIBLE);

                            btn_send.setVisibility(View.INVISIBLE);
                            btn_add.setVisibility(View.VISIBLE);
                            break;

                        case RESULT_CODE_3:
                            Toast.makeText(BandAddActivity.this, "없는 시리얼 번호 입니다.\ncode:3 ", Toast.LENGTH_SHORT).show();
                            break;
                    }

            }

            @Override
            public void onFail(int code) {

            }
        });
    }
}
