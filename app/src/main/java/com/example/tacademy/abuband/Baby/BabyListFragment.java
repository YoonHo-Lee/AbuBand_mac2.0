package com.example.tacademy.abuband.Baby;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.abuband.Alarm.AlarmItemData;
import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabyListFragment extends Fragment {

    ListView babyListView;
    BabyAdapter babyAdapter;
    View rootView;

    public static final String TAG_BABYLIST_FRAGMENT = "BabyListFragment";


    public BabyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_baby_list, container, false);

        babyListView = (ListView) rootView.findViewById(R.id.list_baby);
        babyAdapter = new BabyAdapter();
        babyListView.setAdapter(babyAdapter);

        /***************** 로그인시 sharedpreferences로 저장한 이메일주소 넣어주기 *******************/
        searchBabies("test02");


        babyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = babyListView.getItemAtPosition(position);
                BabyItemData data = (BabyItemData) object;
                Toast.makeText(view.getContext(), data.birth + "년생 " + data.name + "(" + data.gender + ")", Toast.LENGTH_SHORT).show();
            }
        });

//        initData();

        Button btn = (Button) rootView.findViewById(R.id.btn_babyListAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), BabyAddActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void searchBabies(String email) {
        if (!TextUtils.isEmpty(email)) {
            NetworkManager.getInstance().getNetworkMovie(getContext(), email, new NetworkManager.OnResultListener<AbuBabies>() {

                @Override
                public void onSuccess(AbuBabies result) {
                    babyAdapter.clear();
                    for(BabyItemData item : result.result)   {
                        babyAdapter.add(item);
                    }
                }

                @Override
                public void onFail(int code) {
                    Toast.makeText(getContext(), "error : " + code, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            babyAdapter.clear();
            babyAdapter.setEmail(email);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().cancelAll(getContext());
    }


//    private void initData() {
//        int birth = 20151105;
//        String[] sample_gender = {"남아", "여아"};
//
//        for(int i = 0; i<10; i++)   {
//            BabyItemData d = new BabyItemData();
////            d.babyList_icon =
//            d.babyList_Name = "안드로 " + i + "호";
//            d.babyList_birth = birth+"";
//            birth++;
//            d.babyList_gender = sample_gender[i%2];
//            babyAdapter.add(d);
//        }
//    }


}
