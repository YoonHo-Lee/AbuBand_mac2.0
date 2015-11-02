package com.example.tacademy.abuband.Baby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.R;

public class BabyAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_add);

        Button btn = (Button) findViewById(R.id.btn_babyAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabyAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
