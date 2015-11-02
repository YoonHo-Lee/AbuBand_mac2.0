package com.example.tacademy.abuband.Menual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.abuband.Band.BandAddActivity;
import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.Member.LoginFragment;
import com.example.tacademy.abuband.R;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MenualFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static MenualFragment newInstance(int sectionNumber) {
        MenualFragment fragment = new MenualFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MenualFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_menual, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        Button btn = (Button) rootView.findViewById(R.id.btn_menual);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BandAddActivity.class);
                startActivity(intent);
            }
        });

        Bundle getSampleFlag = getArguments();
//        boolean flagIschecked = getSampleFlag.getBoolean(LoginFragment.TAG_SAMPLE_CHECK_FLAG);
        String flagIschecked = getSampleFlag.getString(LoginFragment.TAG_SAMPLE_CHECK_FLAG, "nono");

        Toast.makeText(rootView.getContext(), "체크" + flagIschecked , Toast.LENGTH_SHORT).show();

        //마지막페이지의 버튼만 보이게 하기
        if(getArguments().getInt(ARG_SECTION_NUMBER) == 3)   {
            btn.setVisibility(View.VISIBLE);
        }

        return rootView;

    }
}