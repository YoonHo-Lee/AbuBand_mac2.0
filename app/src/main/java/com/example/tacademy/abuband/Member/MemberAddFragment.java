package com.example.tacademy.abuband.Member;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberAddFragment extends Fragment {

    EditText email, pw;


    public MemberAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_sign_up));

        final View rootView = inflater.inflate(R.layout.fragment_member_add, container, false);

        email = (EditText) rootView.findViewById(R.id.edit_MemberAdd_eMail);
        pw = (EditText) rootView.findViewById(R.id.edit_MemberAdd_password);

        Button btn = (Button) rootView.findViewById(R.id.btn_Memberadd_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignUp(email.getText().toString(), pw.getText().toString());

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }

    private void setSignUp(String email, String pw) {
        NetworkManager.getInstance().setSignUp(getContext(), email, pw, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(int code) {

            }
        });
    }


}
