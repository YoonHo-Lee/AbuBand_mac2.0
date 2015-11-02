package com.example.tacademy.abuband.Member;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tacademy.abuband.Menual.MenualActivity;
import com.example.tacademy.abuband.Menual.MenualFragment;
import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    CheckBox sampleCheckFlag;

    public static final String TAG_SAMPLE_CHECK_FLAG = "flag";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        //로그인 버튼
        Button btn_login = (Button) rootView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenualFragment mf = new MenualFragment();
                Bundle bundle = new Bundle();
//                bundle.putBoolean(TAG_SAMPLE_CHECK_FLAG, sampleCheckFlag.isChecked());
                bundle.putString(TAG_SAMPLE_CHECK_FLAG, "android");
                mf.setArguments(bundle);

                Intent intent = new Intent(v.getContext(), MenualActivity.class);
                startActivity(intent);

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

        sampleCheckFlag = (CheckBox) rootView.findViewById(R.id.check_sampleFlag);

        return rootView;
    }


}
