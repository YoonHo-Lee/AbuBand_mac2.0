package com.dnabuba.tacademy.abuband.Baby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

public class BabyAddActivity extends AppCompatActivity {

    EditText babyName, babyBirth, babyGender;
    String babyBirth_num;
    String babyGender_num;
    BabyAdapter babyAdapter;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_add);
        setTitle(getString(R.string.title_baby_add));

        Intent intent = getIntent();
        flag = intent.getIntExtra(BabyListFragment.TAG_BABYFLAG, 999);

        if(flag == 777) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        babyName = (EditText) findViewById(R.id.edit_babyName);
        babyBirth = (EditText) findViewById(R.id.edit_babyBirth);
        babyGender = (EditText) findViewById(R.id.edit_babyGender);



        /***************** 생년월일 설정 *******************/
        babyBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDialog();
            }
        });

        /***************** 성별 설정 *******************/
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
                        babyGender_num = selectPosition + "";
                    }
                });
                builder.create().show();
            }
        });


        /***************** 아이 추가 *******************/
        Button btn = (Button) findViewById(R.id.btn_babyAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(babyName.getText().toString()) && !TextUtils.isEmpty(babyBirth.getText().toString()) && !TextUtils.isEmpty(babyGender.getText().toString())) {
                    //editText의 내용을 네트워크 쪽으로 보내기
                    addBaby(babyName.getText().toString(), babyBirth_num, babyGender_num);
                } else {
                    Toast.makeText(BabyAddActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*****************
     * 아이추가 네트워크 불러오기
     *******************/
    private void addBaby(final String name, final String birth, final String gender) {
        NetworkManager.getInstance().setBabyAdd(BabyAddActivity.this, name, birth, gender, new NetworkManager.OnResultListener<BabyaddCodeResult>() {
            @Override
            public void onSuccess(BabyaddCodeResult result) {
                switch (result.code)    {
                    case 1:
                        PropertyManager.getInstance().setPrefBaby(result.id); // 선택된 아이 아이디 저장
                        Intent intent = new Intent(BabyAddActivity.this, MainActivity.class);
                        intent.putExtra("name", result.name);
                        intent.putExtra("birth", result.birth);
                        startActivity(intent);
                        finish();
                        break;
                }
                Toast.makeText(BabyAddActivity.this, name + birth + gender + "등록", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code) {
//                Toast.makeText(BabyAddActivity.this, "error : " + code, Toast.LENGTH_SHORT).show();
                Log.e("BabyAddActivity", "addBaby Fail" + code);
            }
        });
    }

    /*****************
     * 생년월일 다이얼로그
     *******************/
    /*커스텀 다이얼로그 불러오기*/
    /*년도 최대값을 올해로 설정하게, 캘린더로 현재 연도 가져오기.*/
    public void birthDialog() {
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
                //한자리수 월 일 앞에 0 붙이기
                StringBuilder sb = new StringBuilder();
                String Y, M, D;
                Y = npY.getValue() + "";
                M = npM.getValue() + "";
                D = npD.getValue() + "";
                if (String.valueOf(npM.getValue()).length() == 1) {
                    M = "0" + npM.getValue();
                }
                if (String.valueOf(npD.getValue()).length() == 1) {
                    D = "0" + npD.getValue();
                }
                sb.append(Y);
                sb.append(M);
                sb.append(D);
                babyBirth_num = sb.toString(); //서버로 보내는 값
                babyBirth.setText(String.valueOf(npY.getValue()) + " / " + String.valueOf(npM.getValue()) + " / " + String.valueOf(npD.getValue()));
                d.dismiss();
            }
        });

        d.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}