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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabyListFragment extends Fragment {

    ListView babyListView;
    BabyAdapter babyAdapter;
    View rootView;
    ImageView babyUpdate;

    public static final String TAG_BABYLIST_FRAGMENT = "BabyListFragment";


    public BabyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_baby_list, container, false);
        babyUpdate = (ImageView) rootView.findViewById(R.id.babyList_update_btn);
        babyListView = (ListView) rootView.findViewById(R.id.list_baby);
        babyAdapter = new BabyAdapter();

        View header = inflater.inflate(R.layout.header_baby_list, null);
        babyListView.addHeaderView(header);
        
        babyListView.setAdapter(babyAdapter);
        


        /***************** 로그인시 sharedpreferences로 저장한 이메일주소 넣어주기 *******************/
        //네트워크
        searchBabies("test02@naver.com");
//        initData();

        babyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = babyListView.getItemAtPosition(position);
                BabyItemData data = (BabyItemData) object;
                Toast.makeText(view.getContext(), data.birth + "년생 " + data.name + "(" + data.gender + ")", Toast.LENGTH_SHORT).show();


            }
        });

        babyAdapter.setOnAdapterImageListener(new BabyAdapter.OnAdapterImageListener() {
            @Override
            public void onAdapterImageClick(BabyAdapter adapter, BabyItemView view, BabyItemData data) {
                Toast.makeText(view.getContext(), "눌림 : "+data.name + data.birth, Toast.LENGTH_SHORT).show();
            }
        });

        final Button babyAddBtn = (Button) rootView.findViewById(R.id.btn_babyListAdd);
        babyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), BabyAddActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }


    /******************** 네 트 워 크 ********************/
    private void searchBabies(String email) {
        if (!TextUtils.isEmpty(email)) {
            NetworkManager.getInstance().getBabies(getContext(), email, new NetworkManager.OnResultListener<AbuBabies>() {

                @Override
                public void onSuccess(AbuBabies result) {
                    babyAdapter.clear();
                    for (BabyItemData item : result.result) {
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
    /************* End of Network **************************/


    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().cancelAll(getContext());
    }
}
