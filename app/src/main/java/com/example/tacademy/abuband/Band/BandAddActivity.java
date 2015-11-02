package com.example.tacademy.abuband.Band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tacademy.abuband.Baby.BabyAddActivity;
import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.R;

public class BandAddActivity extends AppCompatActivity {

    boolean isChecked = false;
    Button btn_send, btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_add);

        btn_send = (Button) findViewById(R.id.btn_serialSend);
        btn_add = (Button) findViewById(R.id.btn_serialAdd);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버로 밴드의 시리얼을 보내서 확인 받는 과정
                //아래는 true값 리턴 받았을때를 가정
                isChecked = true;
                ImageView iv = (ImageView) findViewById(R.id.image_serialCheck);
                iv.setVisibility(View.VISIBLE);

                btn_send.setVisibility(View.INVISIBLE);
                btn_add.setVisibility(View.VISIBLE);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BandAddActivity.this, BabyAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
