package com.dnabuba.tacademy.abuband.Setting;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editPassword = (EditText) findViewById(R.id.edit_forgotPassword);

        if(PropertyManager.getInstance().getPrefEmail() != null) {
            editPassword.setText(PropertyManager.getInstance().getPrefEmail());
        }

        Button btn = (Button) findViewById(R.id.forgotPassword_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(editPassword.getText().toString());
            }
        });
    }

    private void sendEmail(String email) {
        NetworkManager.getInstance().setNewPassword(this, email, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("ForgotPasswordActivity", "비번찾기 성공");
                Toast.makeText(ForgotPasswordActivity.this, "이메일 발송!!!, 이메일의 링크로 비밀번호를 바꿔주세요.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFail(int code) {
                Log.e("ForgotPasswordActivity", "비번찾기 실패"+code);
                Toast.makeText(ForgotPasswordActivity.this, "네트워크 오류, 잠시후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
