package com.dnabuba.tacademy.abuband.Setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.Member.MemberActivity;
import com.dnabuba.tacademy.abuband.Menual.MenualActivity;
import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

public class AccountActivity extends AppCompatActivity {

    TextView text_email, text_serial, text_baby, text_newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        text_email = (TextView) findViewById(R.id.account_email);
        text_serial = (TextView) findViewById(R.id.account_band_serial);
        text_baby = (TextView) findViewById(R.id.account_baby);
        text_newPassword = (TextView) findViewById(R.id.account_password);

        text_email.setText(PropertyManager.getInstance().getPrefEmail().toString());
        text_serial.setText(PropertyManager.getInstance().getPrefSerial().toString());
        text_baby.setText(PropertyManager.getInstance().getPrefBaby().toString());

        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("로그아웃")
                        .setMessage("로그아웃하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //로그아웃
                                setLogout();
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
        });

        text_newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLogout() {
        NetworkManager.getInstance().setLogout(AccountActivity.this, new NetworkManager.OnResultListener<NetworkCodeResult>() {

            @Override
            public void onSuccess(NetworkCodeResult result) {
                if (result.code == 1) {
                    Toast.makeText(AccountActivity.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
                    PropertyManager.getInstance().setAllClear();
                    Intent intent = new Intent(AccountActivity.this, MemberActivity.class);
                    startActivity(intent);
                    finish();
                    //TODO:메인액티비티 죽이기
                    MainActivity a = new MainActivity();
                    a.finish();         //이따구로 구현하니 느린듯
                }

            }

            @Override
            public void onFail(int code) {
                Toast.makeText(AccountActivity.this, "Logout Err"+code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
