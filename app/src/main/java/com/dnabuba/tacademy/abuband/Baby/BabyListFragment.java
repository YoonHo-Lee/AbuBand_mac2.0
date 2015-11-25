package com.dnabuba.tacademy.abuband.Baby;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabyListFragment extends Fragment {

    ListView babyListView;
    BabyAdapter babyAdapter;
    View rootView;
    ImageView babyUpdate;

    public static final String TAG__ID = "_id";
    public static final String TAG_BABYNAME = "babyName";
    public static final String TAG_BABYBIRTH = "babyBirth";
    public static final String TAG_BABYGENDER = "babyGender";
    public static final String TAG_BABYFLAG = "baby_flag";

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
        searchBabies();
//        initData();

        //아이 선택!!!!!!!!!!
        babyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = babyListView.getItemAtPosition(position);
                BabyItemData data = (BabyItemData) object;

                Toast.makeText(view.getContext(), data.birth + "년생 " + data.name + "(" + data.gender + ")", Toast.LENGTH_SHORT).show();
            }
        });


        // 아이 수정 삭제
        babyAdapter.setOnAdapterImageListener(new BabyAdapter.OnAdapterImageListener() {
            @Override
            public void onAdapterImageClick(BabyAdapter adapter, BabyItemView view, BabyItemData data) {
                Intent intent = new Intent(view.getContext(), BabyUpdeleteActivity.class);
                intent.putExtra(TAG__ID, data._id);
                intent.putExtra(TAG_BABYNAME, data.name);
                intent.putExtra(TAG_BABYBIRTH, data.birth + "");
                intent.putExtra(TAG_BABYGENDER, data.gender + "");
                startActivity(intent);


//                Toast.makeText(view.getContext(), "눌림 : " + data._id + data.name + data.birth + data.gender, Toast.LENGTH_SHORT).show();
            }
        });

        //아이 추가
        final Button babyAddBtn = (Button) rootView.findViewById(R.id.btn_babyListAdd);
        babyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), BabyAddActivity.class);
                intent.putExtra(TAG_BABYFLAG, 777);
                startActivity(intent);
            }
        });


        return rootView;
    }


    /********************
     * 네 트 워 크
     ********************/
    private void searchBabies() {

        NetworkManager.getInstance().getBabies(getContext(), new NetworkManager.OnResultListener<AbuBabies>() {

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

    }


    /*************
     * End of Network
     **************************/


    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(rootView.getContext(), "RESUME...!!!!", Toast.LENGTH_SHORT).show();
        searchBabies();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().cancelAll(getContext());
    }
}
