package com.example.tacademy.abuband.Alarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmListFragment extends Fragment {

    ListView alarmListView;
    AlarmAdapter alarmAdapter;

    public static final String TAG_ALARMTEMP = "alarmTemp";



    public AlarmListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_alram_list, container, false);

        alarmListView = (ListView) rootView.findViewById(R.id.list_alarm);
        alarmAdapter = new AlarmAdapter();
        alarmListView.setAdapter(alarmAdapter);

        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = alarmListView.getItemAtPosition(position);
                AlarmItemData data = (AlarmItemData) object;
                Toast.makeText(view.getContext(), data.alarm_temp + "도", Toast.LENGTH_SHORT).show();

            }
        });

        initData();

        return rootView;
    }

    //데이터 입력!!!!!!
    private void initData() {
        float temp = 50.0f;

        for(int i = 0; i<10; i++)   {
            AlarmItemData d = new AlarmItemData();
            d.alarm_temp = temp;
            temp = temp + 0.7f * i;
            alarmAdapter.add(d);
        }
    }


}
