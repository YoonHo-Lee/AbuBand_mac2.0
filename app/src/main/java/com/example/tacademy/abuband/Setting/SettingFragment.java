package com.example.tacademy.abuband.Setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tacademy.abuband.MainActivity;
import com.example.tacademy.abuband.Menual.MenualActivity;
import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        Button btn = (Button) rootView.findViewById(R.id.btn_Setting_menual);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenualActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}
