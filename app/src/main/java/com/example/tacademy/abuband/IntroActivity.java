package com.example.tacademy.abuband;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tacademy.abuband.Member.MemberActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageView intro = (ImageView) findViewById(R.id.image_intro);

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MemberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView skip = (ImageView) findViewById(R.id.skipbtn);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //임시 메일 정보 저장
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("email", "test02@naver.com");
                editor.commit();

                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
