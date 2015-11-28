package com.dnabuba.tacademy.abuband.SickReport;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SickReportListFragment extends Fragment {

    ListView sickListView;
    SickReportAdapter sickAdapter;

    public static final String TAG_SR__ID = "_id";
    public static final String TAG_SR_DATE = "date";
    public static final String TAG_SR_MAXTEMP = "maxTemp";
    public static final String TAG_SR_NAME = "name";
    public static final String TAG_SR_TITLE = "title";
    public static final String TAG_SR_MEMO = "memo";
    String name;


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
                SickReportItemData data = (SickReportItemData) object;
//                Toast.makeText(view.getContext(),"병명 : " + data.title, Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), "_id : " + data._id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), SickReportActivity.class);

                //상세 페이지로 전달
                intent.putExtra(TAG_SR_DATE, data.date);
                intent.putExtra(TAG_SR_NAME, name);
                intent.putExtra(TAG_SR_TITLE, data.title);
                intent.putExtra(TAG_SR_MEMO, data.memo);
                intent.putExtra(TAG_SR__ID, data._id);
                intent.putExtra(TAG_SR_MAXTEMP, data.maxTemp);
//                intent.putExtra("arr", data.tempGraph);

                startActivity(intent);
            }
        });


        searchSickReport();

//        initData();


        return rootView;
    }



    private void searchSickReport() {
        NetworkManager.getInstance().getSickReport(getContext(), new NetworkManager.OnResultListener<AbuSickReports>() {

            @Override
            public void onSuccess(AbuSickReports sickRoports) {
                Log.e("sickReport", "onSuccess");
                sickAdapter.clear();
                if(sickRoports.result != null) {
                    name = sickRoports.name;
                    for (SickReportItemData item : sickRoports.result) {
                        sickAdapter.add(item);
                    }
                }
            }

            @Override
            public void onFail(int code) {
//                Toast.makeText(getContext(), "error : " + code, Toast.LENGTH_SHORT).show();
                Log.e("SickReportListFragment", "search Fail" + code);
            }
        });
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


    @Override
    public void onResume() {
        super.onResume();
        searchSickReport();
    }
}
