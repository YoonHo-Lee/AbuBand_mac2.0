package com.dnabuba.tacademy.abuband.Temperature;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dacer.androidcharts.LineView;
import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {


    public TemperatureFragment() {
        // Required empty public constructor
    }


    ArrayList<Integer> tempList;
    ArrayList<String> dateList;
    ArrayList<String> x_axis;
    ArrayList<ArrayList<Integer>> dataLists;
    TextView textMainMessage, textMainTempState, textMainTempNumber;

    LineView lineView;
    View graph_mainTemp;
    HorizontalScrollView hsv;

    float lastTemp;

    //그래프에 찍히는 갯수
    int randomint = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //루트뷰 선언
        final View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);
        //그래프 선언
        lineView = (LineView) rootView.findViewById(R.id.graph_mainTemp);
        graph_mainTemp = (View) rootView.findViewById(R.id.graph_mainTemp);
        hsv = (HorizontalScrollView) rootView.findViewById(R.id.horizontalScrollView);

        textMainMessage = (TextView) rootView.findViewById(R.id.text_mainMessage);
        textMainTempState = (TextView) rootView.findViewById(R.id.main_tempState);
        textMainTempNumber = (TextView) rootView.findViewById(R.id.main_tempNumber);


        //초기화
        tempList = new ArrayList<Integer>();    //온도 데이터
        dateList = new ArrayList<String>();     //시간 데이터
        x_axis = new ArrayList<String>();       //x축에 표시

//        //x축에 표시될 내용
//        for (int i=0; i<randomint; i++){
//            x_axis.add(String.valueOf(i + 1 + "09:42:16"));
//        }
//        lineView.setBottomTextList(x_axis);


        //그래프 내의 x축을 도트로 표시
        lineView.setDrawDotLine(false);

        //누르면 수치 표시되는거같음
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

        //랜덤 생성 버튼
//        Button lineButton = (Button) rootView.findViewById(R.id.line_button);
//        lineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ProgressBar mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//                mProgressBar.setProgress((int) (Math.random() * 1000 % 61));
//
//            }
//        });

        Button btnT = (Button) rootView.findViewById(R.id.btn_test);
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity)getActivity()).setNeviText();
//                Toast.makeText(v.getContext(), graph_mainTemp.getWidth() + "width", Toast.LENGTH_SHORT).show();
                hsv.post(new Runnable() {
                    @Override
                    public void run() {
                        hsv.scrollTo(graph_mainTemp.getWidth(), 0);
                    }
                });
            }
        });


        searchTemps(PropertyManager.getInstance().getPrefSerial());

        return rootView;
    }


    /********************
     * 네 트 워 크
     ********************/
    private void searchTemps(final String serial) {

        NetworkManager.getInstance().getTemperature(getContext(), serial, new NetworkManager.OnResultListener<AbuTemps>() {

            StringBuffer sb = new StringBuffer();

            @Override
            public void onSuccess(AbuTemps result) {
                switch (result.code)    {
                    case 1:
//                        Log.e("qazwsx", "템프성공1번"+ result.code + result.result);
                        for (TemperatureItemData item : result.result) {
                            lastTemp = item.temp;
                            textMainTempNumber.setText(lastTemp+""); // 최신(마지막)온도를 출력
                            tempList.add((int) ((item.temp - 35) * 10)); //36.5 => 36.5 - 35 => 1.5 * 10 => 15 거지같은 정수 그래프라 이따구로함.
                            dateList.add(item.date.substring(11, 19)); // '년월일T시분초밀리'로된 데이터에서 시분초만 추출
                        }

                        //온도 데이터 테스트
//                    for (int i : tempList) {
//                        sb.append(i + " / ");
//                    }

                        //시간 데이터 테스트
                        for (String s : dateList) {
                            sb.append(s + " / ");
                        }


                        textMainMessage.setText(sb);
//                    Toast.makeText(getContext(), serial + "성공...!!!", Toast.LENGTH_SHORT).show();

                        //그래프를 그려주는 dataLists를 생성하고, 온도데이터가 있는 tempList를 넣어준다.
                        dataLists = new ArrayList<ArrayList<Integer>>();
                        dataLists.add(tempList);

                        //x축에 표시될 내용
                        lineView.setBottomTextList(dateList);
                        //그래프 데이터를 그래프에 출력
                        lineView.setDataList(dataLists);
                        break;
                    case 3:
//                        Log.e("qazwsx", "템프성공3"+ result.code + result.result);
                        textMainTempNumber.setText("--");

                        //디자인을 위해 넣는 더미 데이터
                        for(int i = 0; i<8; i++)    {
                            tempList.add(15);
                            dateList.add("-");
                        }

                        //그래프를 그려주는 dataLists를 생성하고, 온도데이터가 있는 tempList를 넣어준다.
                        dataLists = new ArrayList<ArrayList<Integer>>();
                        dataLists.add(tempList);
                        //x축에 표시될 내용
                        lineView.setBottomTextList(dateList);
                        //그래프 데이터를 그래프에 출력
                        lineView.setDataList(dataLists);

                        break;
                }


            }

            @Override
            public void onFail(int code) {
//                Toast.makeText(getContext(), "error : " + code, Toast.LENGTH_SHORT).show();
                Log.e("TemperatureFragment", "Temp Data Load Fail" + code);
            }
        });

    }
    /************* End of Network **************************/


}