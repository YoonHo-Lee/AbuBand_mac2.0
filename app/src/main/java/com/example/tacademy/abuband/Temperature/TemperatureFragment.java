package com.example.tacademy.abuband.Temperature;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dacer.androidcharts.LineView;
import com.example.tacademy.abuband.NetworkManager;
import com.example.tacademy.abuband.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {


    public TemperatureFragment() {
        // Required empty public constructor
    }


    ArrayList<Integer> tempList;
    ArrayList<ArrayList<Integer>> dataLists;
    TextView textMainMessage;

    LineView lineView;

    //그래프에 찍히는 갯수
    int randomint = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //루트뷰 선언
        final View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);
        //그래프 선언
        lineView = (LineView)rootView.findViewById(R.id.graph_mainTemp);

        textMainMessage = (TextView) rootView.findViewById(R.id.text_mainMessage);
        tempList = new ArrayList<Integer>();

        //must*
        //머스트래.....
        ArrayList<String> test = new ArrayList<String>();

        //x축에 표시될 내용
        for (int i=0; i<randomint; i++){
            test.add(String.valueOf(i+1));
        }
        lineView.setBottomTextList(test);


        //그래프 내의 x축을 도트로 표시
        lineView.setDrawDotLine(true);

        //누르면 수치 표시되는거같음
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

        Button lineButton = (Button)rootView.findViewById(R.id.line_button);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomSet(lineView);
                ProgressBar mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
                mProgressBar.setProgress((int) (Math.random() * 1000 % 61));

            }
        });
        searchTemps("LEEKR203NR5");
        randomSet(lineView);

        return rootView;
    }



    //그래프 수치 랜덤셋팅
    //ArrayList에 더미 온도 값 넣기.
    private void randomSet(LineView lineView){
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        int random = (int)(Math.random()*9+1);
        for (int i=0; i<randomint; i++){
            dataList.add((int)(Math.random()*random));
        }

//        tempList.add(372);
//        tempList.add(373);
//        tempList.add(374);
//        tempList.add(375);
//        tempList.add(376);
//        tempList.add(377);
//        tempList.add(380);
//        tempList.add(385);
//        tempList.add(389);
//        tempList.add(400);

        //ArrayList에 있는 데이터를 그래프 데이터인 DataLists에 넣는다.
//        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
////        dataLists.add(dataList);
//        dataLists.add(tempList);
//
//        //그래프 데이터를 그래프에 출력
//        lineView.setDataList(dataLists);
    }


    /******************** 네 트 워 크 ********************/
    private void searchTemps(final String serial) {
        if (!TextUtils.isEmpty(serial)) {
            NetworkManager.getInstance().getTemperature(getContext(), serial, new NetworkManager.OnResultListener<AbuTemps>() {

                StringBuffer sb = new StringBuffer();

                @Override
                public void onSuccess(AbuTemps result) {
                    for (TemperatureItemData item : result.result) {
                        tempList.add((int)((item.temp - 35) * 10));
                    }
                    for (int i : tempList) {
                        sb.append(i + " / ");
                    }


                    textMainMessage.setText(sb);
//                    Toast.makeText(getContext(), serial + "성공...!!!", Toast.LENGTH_SHORT).show();


                    dataLists = new ArrayList<ArrayList<Integer>>();
                    dataLists.add(tempList);

                    //그래프 데이터를 그래프에 출력
                    lineView.setDataList(dataLists);

                }

                @Override
                public void onFail(int code) {
                    Toast.makeText(getContext(), "error : " + code, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /************* End of Network **************************/



}