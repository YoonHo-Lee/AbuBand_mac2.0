package com.dnabuba.tacademy.abuband.Temperature;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dacer.androidcharts.LineView;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {


    public TemperatureFragment() {
        // Required empty public constructor
    }

    public static Bitmap high_bg, mild_bg, normal_bg, low_bg;

    ArrayList<Integer> tempList;
    ArrayList<String> dateList;
    ArrayList<ArrayList<Integer>> dataLists;
    TextView textMainMessage, textMainTempState, textMainTempNumber;

    LineView lineView;
    View graph_mainTemp;
    HorizontalScrollView hsv;
    ProgressBar mProgressBar;
    LinearLayout mainTemp_bg;

    String startTime, endTime;

    //온도별 구간 구별 플래그 선언
    public static final int TAG_TEMP_FLAG_INIT = 0;
    public static final int TAG_TEMP_FLAG_LOW = 1;
    public static final int TAG_TEMP_FLAG_NORMAL = 2;
    public static final int TAG_TEMP_FLAG_MILD = 3;
    public static final int TAG_TEMP_FLAG_HIGH = 4;

    int tempLevelFlag =TAG_TEMP_FLAG_INIT;
    int tempSectionFlag =TAG_TEMP_FLAG_INIT;

    float currentTemp;

    private TimerTask mTask;
    private Timer mTimer;
    Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //루트뷰 선언
        final View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);
        //그래프 선언
        lineView = (LineView) rootView.findViewById(R.id.graph_mainTemp);
        graph_mainTemp = (View) rootView.findViewById(R.id.graph_mainTemp);
        hsv = (HorizontalScrollView) rootView.findViewById(R.id.horizontalScrollView);
        mainTemp_bg = (LinearLayout) rootView.findViewById(R.id.mainTemp_background);

        //out of memory 때문에 미리 이미지 불러와서 줄이기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        high_bg = BitmapFactory.decodeResource(getResources(), R.drawable.temp_bg_high, options);
        mild_bg = BitmapFactory.decodeResource(getResources(), R.drawable.temp_bg_mild, options);
        normal_bg = BitmapFactory.decodeResource(getResources(), R.drawable.temp_bg_normal, options);
        low_bg = BitmapFactory.decodeResource(getResources(), R.drawable.temp_bg_low, options);

        //서클프로그래스바 선언
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.cProgressBar);

        textMainMessage = (TextView) rootView.findViewById(R.id.text_mainMessage);
        textMainTempState = (TextView) rootView.findViewById(R.id.main_tempState);
        textMainTempNumber = (TextView) rootView.findViewById(R.id.main_tempNumber);

        handler = new Handler() {
            public void handleMessage(Message msg) {
                searchTemps(PropertyManager.getInstance().getPrefSerial());
                hsv.post(new Runnable() {
                    @Override
                    public void run() {
                        hsv.scrollTo(graph_mainTemp.getWidth(), 0);
                    }
                });
            }
        };


        //초기화
        tempList = new ArrayList<Integer>();    //온도 데이터
        dateList = new ArrayList<String>();     //시간 데이터

        //그래프 내의 x축을 도트로 표시
        lineView.setDrawDotLine(false);

        //누르면 수치 표시되는거같음
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);


//        Button btnT = (Button) rootView.findViewById(R.id.btn_test);
//        btnT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ((MainActivity)getActivity()).setNeviText();
////                Toast.makeText(v.getContext(), graph_mainTemp.getWidth() + "width", Toast.LENGTH_SHORT).show();
//                hsv.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        hsv.scrollTo(graph_mainTemp.getWidth(), 0);
//                    }
//                });
//            }
//        });

        //프로그래스바 설정 <-삭제 ㄱㄱ
//        mProgressBar.setProgress((int) (Math.random() * 1000 % 61));
        searchTemps(PropertyManager.getInstance().getPrefSerial());

        onTempSearch();

        return rootView;
    }

    private void onTempSearch() {

        mTask = new TimerTask() {
            @Override
            public void run() {
                new Thread() {
                    public void run() {
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 4000);

    }


    /********************
     * 네 트 워 크
     ********************/
    private void searchTemps(final String serial) {

        NetworkManager.getInstance().getTemperature(getContext(), serial, new NetworkManager.OnResultListener<AbuTemps>() {

            StringBuffer sb = new StringBuffer();

            @Override
            public void onSuccess(AbuTemps result) {
                tempList.clear();
                dateList.clear();
                switch (result.code) {
                    case 1:
//                        Log.e("qazwsx", "템프성공1번"+ result.code + result.result);
                        for (TemperatureItemData item : result.result) {
                            currentTemp = item.temp;
                            textMainTempNumber.setText(currentTemp + ""); // 최신(마지막)온도를 출력
                            tempList.add((int) ((item.temp - 35f) * 10)); //36.5 => 36.5 - 35 => 1.5 * 10 => 15 거지같은 정수 그래프라 이따구로함.
                            int kor_hour = Integer.parseInt(item.date.substring(11, 13)) + 9;
                            dateList.add(kor_hour + item.date.substring(13, 19)); // '년-월-일T시:분:초밀리'로된 데이터에서 시분초만 추출
                            endTime = kor_hour + item.date.substring(13,19);

//                            SimpleDateFormat change_time = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
//                            String kor_time = change_time.format(item.date.substring(11,19));
//                            dateList.add(kor_time);

                            tempLevel();
                        }

                        //시간 데이터 테스트
//                        for (String s : dateList) {
//                            sb.append(s + " / ");
//                        }
//                        textMainMessage.setText(sb);// x축에 나오는 시간 데이터 테스트

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
                        /********더미데이터 넣으니 개판되는거같아서 일단 주석********/
//                        //디자인을 위해 넣는 더미 데이터
//                        for(int i = 0; i<8; i++)    {
//                             tempList.add(15);
//                            dateList.add("-");
//                        }
//
//
//                        //그래프를 그려주는 dataLists를 생성하고, 온도데이터가 있는 tempList를 넣어준다.
//                        dataLists = new ArrayList<ArrayList<Integer>>();
//                        dataLists.add(tempList);
//                        //x축에 표시될 내용
//                        lineView.setBottomTextList(dateList);
//                        //그래프 데이터를 그래프에 출력
//                        lineView.setDataList(dataLists);
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

    //온도 레벨플래그 설정과 백그라운드 색상 설정.
    //구간별 도움 메시지 출력.
    private void tempLevel() {
        if(currentTemp > 37.9f)   {
            tempLevelFlag = TAG_TEMP_FLAG_HIGH;
//            mainTemp_bg.setBackgroundResource(R.drawable.temp_bg_high);
            mainTemp_bg.setBackground(new BitmapDrawable(getActivity().getResources(), high_bg));
//            mainTemp_bg.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.temp_bg_high)));
            textMainMessage.setText(R.string.help_messege_high);
            textMainMessage.setTextColor(getResources().getColor(R.color.temp_high));
            textMainTempState.setText(R.string.temp_high);
            textMainTempState.setTextColor(getResources().getColor(R.color.temp_high));
            textMainTempNumber.setTextColor(getResources().getColor(R.color.temp_high));
            mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_drawable_high));
        }else if(currentTemp > 37.4f)   {
            tempLevelFlag = TAG_TEMP_FLAG_MILD;
//            mainTemp_bg.setBackgroundResource(R.drawable.temp_bg_mild);
            mainTemp_bg.setBackground(new BitmapDrawable(getActivity().getResources(), mild_bg));
//            mainTemp_bg.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.temp_bg_mild)));
            textMainMessage.setText(R.string.help_messege_mild);
            textMainMessage.setTextColor(getResources().getColor(R.color.temp_mild));
            textMainTempState.setText(R.string.temp_mild);
            textMainTempState.setTextColor(getResources().getColor(R.color.temp_mild));
            textMainTempNumber.setTextColor(getResources().getColor(R.color.temp_mild));
            mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_drawable_mild));
        }else if(currentTemp > 35.9f)   {
            tempLevelFlag = TAG_TEMP_FLAG_NORMAL;
//            mainTemp_bg.setBackgroundResource(R.drawable.temp_bg_normal);
            mainTemp_bg.setBackground(new BitmapDrawable(getActivity().getResources(), normal_bg));
//            mainTemp_bg.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.temp_bg_normal)));
            textMainMessage.setText(R.string.help_messege_normal);
            textMainMessage.setTextColor(getResources().getColor(R.color.temp_normal));
            textMainTempState.setText(R.string.temp_normal);
            textMainTempState.setTextColor(getResources().getColor(R.color.temp_normal));
            textMainTempNumber.setTextColor(getResources().getColor(R.color.temp_normal));
            mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_drawable_normal));
        }else if(currentTemp < 35.9f)    {
            tempLevelFlag = TAG_TEMP_FLAG_LOW;
//            mainTemp_bg.setBackgroundResource(R.drawable.temp_bg_low);
            mainTemp_bg.setBackground(new BitmapDrawable(getActivity().getResources(), low_bg));
//            mainTemp_bg.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.temp_bg_low)));
            textMainMessage.setText(R.string.help_messege_low);
            textMainMessage.setTextColor(getResources().getColor(R.color.temp_low));
            textMainTempState.setText(R.string.temp_low);
            textMainTempState.setTextColor(getResources().getColor(R.color.temp_low));
            textMainTempNumber.setTextColor(getResources().getColor(R.color.temp_low));
            mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_drawable_low));
        }

        //tempSectionFlag가 비어있으면 현재 상태의 tempLevelFlag를 넣어준다.
        if(tempSectionFlag == TAG_TEMP_FLAG_INIT)    {
            tempSectionFlag = tempLevelFlag;
            startTime = endTime;
        }

        //tempSectionFlag와 tempLevelFlag를 비교해서 같으면
        //같은 구간이므로 시간차를 계산한다.
        if(tempSectionFlag == tempLevelFlag)    {
//            Log.e("TemperatureFragment", "startTime" + startTime);
//            Log.e("TemperatureFragment", "endTime" + endTime);

            SimpleDateFormat dataFormat = new SimpleDateFormat("kk:mm:ss", Locale.KOREA);
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dataFormat.parse(startTime);
                endDate = dataFormat.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long duration = endDate.getTime() - startDate.getTime();
            int progressTime = (int) (duration / 1000);
            if(progressTime >= 60)  {
                progressTime = progressTime - 60;
            }
            mProgressBar.setProgress(progressTime);

        }

        //tempSectionFlag와 tempLevelFlag를 비교해서 다르면
        //다른 구간이므로 tempSectionFlag를 재설정하고, startTime도 재설정해준다.
        if(tempSectionFlag !=tempLevelFlag) {
            tempSectionFlag = tempLevelFlag;
            startTime = endTime;
        }


    }


}