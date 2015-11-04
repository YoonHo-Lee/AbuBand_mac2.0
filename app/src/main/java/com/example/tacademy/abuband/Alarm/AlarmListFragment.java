package com.example.tacademy.abuband.Alarm;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmListFragment extends Fragment {

    ListView alarmListView;
    AlarmAdapter alarmAdapter;
    View rootView;
    String selectTemp;

    public static final String TAG_ALARMTEMP = "alarmTemp";



    public AlarmListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_alram_list, container, false);

        alarmListView = (ListView) rootView.findViewById(R.id.list_alarm);
        alarmAdapter = new AlarmAdapter();
        alarmListView.setAdapter(alarmAdapter);

        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = alarmListView.getItemAtPosition(position);
                AlarmItemData data = (AlarmItemData) object;
                Toast.makeText(view.getContext(), data.alarm_temp + "도", Toast.LENGTH_SHORT).show();
                selectTemp = data.alarm_temp+"";
                alarmUpdateDialog();

            }
        });

        initData();

        Button btn = (Button) rootView.findViewById(R.id.btn_alarmAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmAddDialog();
            }
        });

        return rootView;
    }

    //데이터 입력!!!!!!
    private void initData() {
        float temp = 20.0f;

        for(int i = 0; i<10; i++)   {
            AlarmItemData d = new AlarmItemData();
            d.alarm_temp = temp;
            temp = temp + 0.7f * i;
            alarmAdapter.add(d);
        }
    }

    public void alarmAddDialog()    {
        final Dialog d = new Dialog(rootView.getContext());
        d.setTitle("온도알람 등록");
        d.setContentView(R.layout.dialog_alarm_add);

        final NumberPicker npT1, npT2;

        npT1 = (NumberPicker) d.findViewById(R.id.numberPickerTemp1);
        npT1.setMaxValue(45);
        npT1.setMinValue(20);
        npT1.setValue(36);

        npT2 = (NumberPicker) d.findViewById(R.id.numberPickerTemp2);
        npT2.setMaxValue(9);
        npT2.setMinValue(0);
        npT2.setValue(5);

        Button btn_ok;
        btn_ok = (Button) d.findViewById(R.id.btn_alarmAdd_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), npT1.getValue() + "." + npT2.getValue() + "도 알람이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });


        d.show();


    }

    String selectTemp1, selectTemp2;
    public void alarmUpdateDialog()    {
        final Dialog d = new Dialog(rootView.getContext());
        d.setTitle("온도알람 수정");
        d.setContentView(R.layout.dialog_alarm_add);

        final NumberPicker npT1, npT2;

        selectTemp1 = selectTemp.substring(0,2);
        selectTemp2 = selectTemp.substring(3,4);

        npT1 = (NumberPicker) d.findViewById(R.id.numberPickerTemp1);
        npT1.setMaxValue(45);
        npT1.setMinValue(20);
        npT1.setValue(Integer.parseInt(selectTemp1));

        npT2 = (NumberPicker) d.findViewById(R.id.numberPickerTemp2);
        npT2.setMaxValue(9);
        npT2.setMinValue(0);
        npT2.setValue(Integer.parseInt(selectTemp2));

        Button btn_ok;
        btn_ok = (Button) d.findViewById(R.id.btn_alarmAdd_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), npT1.getValue() + "." + npT2.getValue() + "도 알람이 등록되었습니다.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(rootView.getContext(), selectTemp1 + " / " + selectTemp2, Toast.LENGTH_SHORT ).show();
                d.dismiss();
            }
        });


        d.show();


    }


}
