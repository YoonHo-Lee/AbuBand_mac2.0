package com.example.tacademy.abuband.Baby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;

public class BabyUpdeleteActivity extends AppCompatActivity {

    EditText babyName, babyBirth, babyGender;
    String babyBirth_num, babyGender_num, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_updelete);
        setTitle(getString(R.string.title_baby_add));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        babyName = (EditText) findViewById(R.id.edit_babyName2);
        babyBirth = (EditText) findViewById(R.id.edit_babyBirth2);
        babyGender = (EditText) findViewById(R.id.edit_babyGender2);

        Intent intent = getIntent();
        _id = intent.getStringExtra(BabyListFragment.TAG__ID);
        babyBirth_num = intent.getStringExtra(BabyListFragment.TAG_BABYBIRTH);
        babyGender_num = intent.getStringExtra(BabyListFragment.TAG_BABYGENDER);

        StringBuffer sb = new StringBuffer();
        sb.append(babyBirth_num.substring(0, 4) + " / ");
        sb.append(babyBirth_num.substring(4, 6) + " / ");
        sb.append(babyBirth_num.substring(6, 8));


        String gender = null;
        if (Integer.parseInt(babyGender_num) == 0) {
            gender = "남아";
        } else if (Integer.parseInt(babyGender_num) == 1) {
            gender = "여아";
        }

        babyName.setText(intent.getStringExtra(BabyListFragment.TAG_BABYNAME));
        babyBirth.setText(sb.toString());
        babyGender.setText(gender);


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
                AlertDialog.Builder builder = new AlertDialog.Builder(BabyUpdeleteActivity.this);
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


        /***************** 아이 수정 *******************/
        Button btn = (Button) findViewById(R.id.btn_babyUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(babyName.getText().toString()) && !TextUtils.isEmpty(babyBirth.getText().toString()) && !TextUtils.isEmpty(babyGender.getText().toString())) {
                    //editText의 내용을 네트워크 쪽으로 보내기
                    updateBaby(_id, babyName.getText().toString(), babyBirth_num, babyGender_num);
                    finish();
                } else {
                    Toast.makeText(BabyUpdeleteActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*****************
     * 아이수정 네트워크 불러오기
     *******************/
    private void updateBaby(final String _id, final String name, final String birth, final String gender) {
        NetworkManager.getInstance().setBabyUpdate(BabyUpdeleteActivity.this, _id, name, birth, gender, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(BabyUpdeleteActivity.this, name + "의 정보가 수정 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(BabyUpdeleteActivity.this, "error : " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*****************
     * 아이삭제 네트워크 불러오기
     *******************/
    private void deleteBaby(final String _id) {
        NetworkManager.getInstance().setBabyDelete(BabyUpdeleteActivity.this, _id, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(BabyUpdeleteActivity.this, "정보가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(BabyUpdeleteActivity.this, "error : " + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /******************
     * 생년월일 다이얼로그
     *******************/
    /*커스텀 다이얼로그 불러오기*/
    /*년도 최대값을 올해로 설정하게, 캘린더로 현재 연도 가져오기.*/
    public void birthDialog() {
        final Dialog d = new Dialog(BabyUpdeleteActivity.this);
        d.setTitle("생년월일");
        d.setContentView(R.layout.dialog_baby_add_birth);
//                아마도 가로세로 크기 지정???????????????????????????????????
//        d.getWindow().setLayout(디멘으로 값지정);

        final NumberPicker npY, npM, npD;

        npY = (NumberPicker) d.findViewById(R.id.numberPickerY);
        npM = (NumberPicker) d.findViewById(R.id.numberPickerM);
        npD = (NumberPicker) d.findViewById(R.id.numberPickerD);

        npY.setMaxValue(2015);
        npM.setMaxValue(12);
        npD.setMaxValue(31);


        npY.setMinValue(1900);
        npM.setMinValue(1);
        npD.setMinValue(1);

        if (!TextUtils.isEmpty(babyBirth.getText().toString())) {
            npY.setValue(Integer.parseInt(babyBirth_num.substring(0, 4)));
            npM.setValue(Integer.parseInt(babyBirth_num.substring(4, 6)));
            npD.setValue(Integer.parseInt(babyBirth_num.substring(6, 8)));
        } else {
            npY.setValue(2000);
            npM.setValue(1);
            npD.setValue(1);
        }

        npY.setWrapSelectorWheel(false);
        npM.setWrapSelectorWheel(false);
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
                babyBirth.setText(Y + " / " + M + " / " + D);
//                babyBirth.setText(String.valueOf(npY.getValue()) + " / " + String.valueOf(npM.getValue()) + " / " + String.valueOf(npD.getValue()));
                d.dismiss();
            }
        });

        d.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_baby, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_baby_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("정보 삭제")
                        .setMessage("아이의 정보를 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //아이정보 삭제
                                deleteBaby(_id);
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