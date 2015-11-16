package com.example.tacademy.abuband.Menual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.abuband.Band.BandAddActivity;
import com.example.tacademy.abuband.R;
import com.example.tacademy.abuband.Setting.SettingFragment;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MenualFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final int POSITION_PAGE_1 = 1;
    public static final int POSITION_PAGE_2 = 2;
    public static final int POSITION_PAGE_3 = 3;

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


        Intent i = getActivity().getIntent();
        final int flag = i.getIntExtra(SettingFragment.MENUAL_FLAG,999);

        Button btn = (Button) rootView.findViewById(R.id.btn_menual);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flag값 받아서 설정에서 왔을때를 구분
                if(flag == 777) {   // 설정에서 넘어온 경우
                    getActivity().finish();
                }else   {   // 처음 왔을때는 밴드 등록화면으로
                    Intent intent = new Intent(v.getContext(), BandAddActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });


        switch (getArguments().getInt(ARG_SECTION_NUMBER))  {
            case POSITION_PAGE_1:
                break;
            case POSITION_PAGE_2:
                break;
            case POSITION_PAGE_3:
                //마지막페이지의 버튼만 보이게 하기
                btn.setVisibility(View.VISIBLE);
                break;
        }

        return rootView;

    }
}