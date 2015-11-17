package com.example.tacademy.abuband.SickReport;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.abuband.NetworkManager;
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

        //리스트 클릭!!!!!!!
        sickListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = sickListView.getItemAtPosition(position);
                SickReportItemData data = (SickReportItemData)object;
//                Toast.makeText(view.getContext(),"병명 : " + data.title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), SickReportActivity.class);
                startActivity(intent);
            }
        });


        searchSickReport("test02@naver.com");

//        initData();


        return rootView;
    }

    private void searchSickReport(String email) {
        if (!TextUtils.isEmpty(email)) {
            NetworkManager.getInstance().getSickReport(getContext(), email, new NetworkManager.OnResultListener<AbuSickReports>() {

                @Override
                public void onSuccess(AbuSickReports result) {
                    sickAdapter.clear();
                    for (SickReportItemData item : result.result) {
                        sickAdapter.add(item);
                    }
                }

                @Override
                public void onFail(int code) {
                    Toast.makeText(getContext(), "error : " + code, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            sickAdapter.clear();
            sickAdapter.setEmail(email);
        }
    }

    //데이터 입력!!!!!!
//    private void initData() {
//        int date = 20151102;
//
//        for(int i = 0; i<20; i++)   {
//            SickReportItemData d = new SickReportItemData();
//            d.date = date+"";
//            date++;
//            d.sickMaxTemp = 40.2f+i;
//            d.title = "메르스" + i;
//            sickAdapter.add(d);
//        }
//    }


}
