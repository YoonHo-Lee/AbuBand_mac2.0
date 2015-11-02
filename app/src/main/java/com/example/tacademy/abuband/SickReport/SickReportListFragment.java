package com.example.tacademy.abuband.SickReport;


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
public class SickReportListFragment extends Fragment {

    ListView sickListView;
    SickReportAdapter sickAdapter;


    public SickReportListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_sick_report_list, container, false);

        sickListView = (ListView) rootView.findViewById(R.id.list_sickreport);
        sickAdapter = new SickReportAdapter();
        sickListView.setAdapter(sickAdapter);

        sickListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = sickListView.getItemAtPosition(position);
                SickReportItemData data = (SickReportItemData)object;
                Toast.makeText(view.getContext(),"병명 : " + data.sickTitle, Toast.LENGTH_SHORT).show();
            }
        });

        initData();


        return rootView;
    }

    private void initData() {
        int date = 20151102;

        for(int i = 0; i<20; i++)   {
            SickReportItemData d = new SickReportItemData();
            d.sickDate = date+"";
            date++;
            d.sickMaxTemp = 40.2f;
            d.sickTitle = "메르스";
            sickAdapter.add(d);
        }
    }


}
