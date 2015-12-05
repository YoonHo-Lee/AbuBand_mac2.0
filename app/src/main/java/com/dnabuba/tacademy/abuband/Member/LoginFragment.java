package com.dnabuba.tacademy.abuband.Member;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.Menual.MenualActivity;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;
import com.dnabuba.tacademy.abuband.Setting.ForgotPasswordActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText edit_eMail, edit_password;
    CheckBox sampleCheckFlag;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static final int RESULT_CODE_0 = 0;
    public static final int RESULT_CODE_1 = 1;
    public static final int RESULT_CODE_2 = 2;
    public static final int RESULT_CODE_3 = 3;

    public static final String TAG_BABY_IMAGE = "image";
    public static final String TAG_BABY_NAME = "name";
    public static final String TAG_BABY_BIRTH = "birth";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_login));

        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        edit_eMail = (EditText) rootView.findViewById(R.id.edit_eMail);
        edit_password = (EditText) rootView.findViewById(R.id.edit_password);

        //로그인 버튼
        final Button btn_login = (Button) rootView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogin(edit_eMail.getText().toString(), edit_password.getText().toString(), PropertyManager.getInstance().getRegistrationToken());
//                Log.e("qazwsx", "토큰 " + PropertyManager.getInstance().getRegistrationToken());
            }
        });


        //회원가입 버튼
        TextView tv = (TextView) rootView.findViewById(R.id.text_memberAdd);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.memberContainer, new MemberAddFragment(), null).addToBackStack(null).commit();

            }
        });

        //비밀번호 찾기 버튼
        TextView tv2 = (TextView) rootView.findViewById(R.id.text_forgotPassword);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void setLogin(final String email, final String password, final String token) {
        NetworkManager.getInstance().setLogin(getContext(), email, password, token, new NetworkManager.OnResultListener<LoginItemData>() {
            @Override
            public void onSuccess(LoginItemData result) {
                Log.e("LoginFragment", "onSuccess, token : "+token);
                String serial = result.result;
                //이메일, 비번, 밴드시리얼 저장
                PropertyManager.getInstance().setPrefEmail(email);
                PropertyManager.getInstance().setPrefPassword(password);
                PropertyManager.getInstance().setPrefSerial(serial);

                Intent intent;
                switch (result.code)    {
                    case RESULT_CODE_1: // 등록된 아이가 있는 경우
                        Log.e("LoginFragment", "onSuccess code1");
                        PropertyManager.getInstance().setPrefBaby_id(result.id);   // 선택되있는 아이 아이디 저장
                        intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.putExtra(TAG_BABY_IMAGE,result.image);
                        intent.putExtra(TAG_BABY_NAME,result.name);
                        intent.putExtra(TAG_BABY_BIRTH,result.birth);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case RESULT_CODE_3: // 등록된 아이가 없는 경우
                        Log.e("LoginFragment", "onSuccess code3");
                        intent = new Intent(getActivity().getApplicationContext(), MenualActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;

                }

            }

            @Override
            public void onFail(int code) {
                Log.e("LoginFragment", "onFail");
                Toast.makeText(getActivity().getApplicationContext(), "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
