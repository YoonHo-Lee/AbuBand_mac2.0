package com.dnabuba.tacademy.abuband.Member;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.Band.BandAddActivity;
import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.NetworkCodeResult;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberAddFragment extends Fragment {

    EditText email, password;


    public MemberAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_sign_up));

        final View rootView = inflater.inflate(R.layout.fragment_member_add, container, false);

        email = (EditText) rootView.findViewById(R.id.edit_MemberAdd_eMail);
        password = (EditText) rootView.findViewById(R.id.edit_MemberAdd_password);

        Button btn = (Button) rootView.findViewById(R.id.btn_Memberadd_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignUp(email.getText().toString(), password.getText().toString());


            }
        });
        return rootView;
    }

    private void setSignUp(String email, String password) {
        NetworkManager.getInstance().setSignUp(getContext(), email, password, new NetworkManager.OnResultListener<NetworkCodeResult>() {
            @Override
            public void onSuccess(NetworkCodeResult result) {
                Log.e("qazwsx","sighUp OK: " + result.code +" / "+result.result);
                switch (result.code)    {
                    case 1: // 성공
                        Intent intent = new Intent(getContext(), MemberActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case 3: // 중복아이디
                        Toast.makeText(getContext(), "이미 가입된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFail(int code) {
                Log.e("qazwsx","sighUp fail: " + code);
            }
        });
    }


}
