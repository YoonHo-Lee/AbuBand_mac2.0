package com.example.tacademy.abuband.Setting;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

        Button btn;

        btn = (Button) rootView.findViewById(R.id.btn_Setting_version);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("버전 : v0.0.1")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        btn = (Button) rootView.findViewById(R.id.btn_Setting_Band);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btn = (Button) rootView.findViewById(R.id.btn_Setting_account);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn = (Button) rootView.findViewById(R.id.btn_Setting_terms);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TermsActivity.class);
                startActivity(intent);
            }
        });


        btn = (Button) rootView.findViewById(R.id.btn_Setting_tempUnit);
        btn.setOnClickListener(new View.OnClickListener() {
            int selectPosition;
            @Override
            public void onClick(View v) {
                selectPosition = 0;
                final String[] tempUnit = {"섭씨℃", "화씨℉"};
                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                builder.setTitle("온도 단위");
                builder.setSingleChoiceItems(tempUnit, selectPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectPosition = which;
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(rootView.getContext(), tempUnit[selectPosition] + "로 설정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();

            }
        });


/*        btn = (Button) rootView.findViewById(R.id.btn_Setting_sickReportBackUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        btn = (Button) rootView.findViewById(R.id.btn_Setting_menual);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenualActivity.class);
                startActivity(intent);
            }
        });


        btn = (Button) rootView.findViewById(R.id.btn_Setting_help);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("도움말")
                        .setMessage("고객센터 : 고객센터가 아직 없습니다.")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });





        return rootView;
    }



}
