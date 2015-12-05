package com.dnabuba.tacademy.abuband;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dnabuba.tacademy.abuband.GCM.RegistrationIntentService;
import com.dnabuba.tacademy.abuband.Member.LoginItemData;
import com.dnabuba.tacademy.abuband.Member.MemberActivity;
import com.dnabuba.tacademy.abuband.Menual.MenualActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class IntroActivity extends AppCompatActivity {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if(!TextUtils.isEmpty(PropertyManager.getInstance().getPrefEmail()) && !TextUtils.isEmpty(PropertyManager.getInstance().getPrefPassword()))    {
//            Log.e("intro - onCreate", PropertyManager.getInstance().getPrefEmail() + " / " + PropertyManager.getInstance().getPrefPassword());
        }

        ImageView intro = (ImageView) findViewById(R.id.image_intro);

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MemberActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        // activity start...
        //onCreate에서 코드 작성 하지말고,
        //실제로 로딩 끝나거나 자동로그인등으로 화면을 자동으로 넘길때, 이 곳에서 코드 작성해서 사용한다.
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(PropertyManager.getInstance().getPrefEmail()) && !TextUtils.isEmpty(PropertyManager.getInstance().getPrefPassword()))    {
                    Log.e("intro - doRealStart", PropertyManager.getInstance().getPrefEmail() + " / " + PropertyManager.getInstance().getPrefPassword());
                    setLogin(PropertyManager.getInstance().getPrefEmail(), PropertyManager.getInstance().getPrefPassword(), PropertyManager.getInstance().getRegistrationToken());
                }else{
                    Intent intent = new Intent(IntroActivity.this, MemberActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 100);


    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void setLogin(String prefEmail, String prefPassword, String registrationToken) {
        NetworkManager.getInstance().setLogin(this, prefEmail, prefPassword, registrationToken, new NetworkManager.OnResultListener<LoginItemData>() {
            @Override
            public void onSuccess(LoginItemData result) {
                Intent intent;
                switch (result.code)    {
                    case 1: // 등록된 아이가 있는 경우
                        Log.e("IntroActivity", "onSuccess code1");
                        intent = new Intent(IntroActivity.this, MainActivity.class);
//                        intent.putExtra(LoginFragment.TAG_BABY_IMAGE,result.image);
//                        intent.putExtra(LoginFragment.TAG_BABY_NAME,result.name);
//                        intent.putExtra(LoginFragment.TAG_BABY_BIRTH,result.birth);
                        PropertyManager.getInstance().setPrefBaby_Image(result.image);
                        PropertyManager.getInstance().setPrefBaby_Name(result.name);
                        PropertyManager.getInstance().setPrefBaby_Birth(result.birth);
                        PropertyManager.getInstance().setPrefBaby_id(result.id);
//                        startActivity(intent);
                        startActivityForResult(intent, 0);
                        finish();
                        break;
                    case 3: // 등록된 아이가 없는 경우
                        Log.e("IntroActivity", "onSuccess code3");
                        intent = new Intent(IntroActivity.this, MenualActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                }
            }

            @Override
            public void onFail(int code) {
                Log.e("Intro-AutoLogin", "onFail"+code);
            }
        });
    }

}
