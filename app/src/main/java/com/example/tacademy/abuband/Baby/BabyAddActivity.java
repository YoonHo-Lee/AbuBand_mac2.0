package com.example.tacademy.abuband.Baby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.R;

public class BabyAddActivity extends AppCompatActivity {

    EditText babyName, babyBirth, babyGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_add);

        babyName = (EditText) findViewById(R.id.edit_babyName);
        babyBirth = (EditText) findViewById(R.id.edit_babyBirth);
        babyGender = (EditText) findViewById(R.id.edit_babyGender);

        babyBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDialog();
            }
        });

        babyGender.setOnClickListener(new View.OnClickListener() {
            int selectPosition;
            @Override
            public void onClick(View v) {
                selectPosition = 0;
                final String[] gender = {"남아", "여아"};
                AlertDialog.Builder builder = new AlertDialog.Builder(BabyAddActivity.this);
                builder.setTitle("성별");
                builder.setSingleChoiceItems(gender, selectPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectPosition = which;
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        babyGender.setText(gender[selectPosition]);
                    }
                });
                builder.create().show();
            }
        });


        Button btn = (Button) findViewById(R.id.btn_babyAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabyAddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /*커스텀 다이얼로그 불러오기*/
    /*년도 최대값을 올해로 설정하게, 캘린더로 현재 연도 가져오기.*/
    public void birthDialog()   {
        final Dialog d = new Dialog(BabyAddActivity.this);
        d.setTitle("생년월일");
        d.setContentView(R.layout.dialog_baby_add_birth);
//                아마도 가로세로 크기 지정???????????????????????????????????
//        d.getWindow().setLayout(디멘으로 값지정);

        final NumberPicker npY, npM, npD;

        npY = (NumberPicker) d.findViewById(R.id.numberPickerY);
        npY.setMaxValue(2015);
        npY.setMinValue(1900);
        npY.setValue(2000);
        npY.setWrapSelectorWheel(false);

        npM = (NumberPicker) d.findViewById(R.id.numberPickerM);
        npM.setMaxValue(12);
        npM.setMinValue(1);
        npM.setWrapSelectorWheel(false);

        npD = (NumberPicker) d.findViewById(R.id.numberPickerD);
        npD.setMaxValue(31);
        npD.setMinValue(1);
        npD.setWrapSelectorWheel(false);

        Button btn_ok;
        btn_ok = (Button) d.findViewById(R.id.btn_birth_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyBirth.setText(String.valueOf(npY.getValue())+ " / " + String.valueOf(npM.getValue()) + " / " + String.valueOf(npD.getValue()));
                d.dismiss();
            }
        });

        d.show();
    }
}
