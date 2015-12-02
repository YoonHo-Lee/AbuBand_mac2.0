package com.dnabuba.tacademy.abuband;

import android.content.Context;
import android.util.Log;

import com.dnabuba.tacademy.abuband.Baby.AbuBabies;
import com.dnabuba.tacademy.abuband.Baby.BabyaddCodeResult;
import com.dnabuba.tacademy.abuband.Band.BandItemData;
import com.dnabuba.tacademy.abuband.Member.LoginItemData;
import com.dnabuba.tacademy.abuband.SickReport.AbuSickReports;
import com.dnabuba.tacademy.abuband.Temperature.AbuTemps;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.CoderResult;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by dongja94 on 2015-10-20.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;
    Gson gson;

    private NetworkManager() {

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
            client.setSSLSocketFactory(socketFactory);
//            client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }


        gson = new Gson();
//        client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);

        public void onFail(int code);
    }


    /*************
     * U R L
     ****************/
    private static final String MAIN_URL = "http://54.65.97.166";


    private static final String LOG_IN_URL = "http://54.65.97.166/users/login";
    private static final String SIGN_UP_URL = "http://54.65.97.166/users/join";
    private static final String FORGOT_PASSWORD_URL = "http://54.65.97.166/users/newPw";

    private static final String BABY_URL = "http://54.65.97.166/babies";
    private static final String BABY_SEARCH_URL = "http://54.65.97.166/getBabies";
    private static final String BABY_SELECT_URL = "http://54.65.97.166/users/babyState";

    private static final String BAND_URL = "http://54.65.97.166/users/band";

    private static final String SICKREPORT_SEARCH_URL = "http://54.65.97.166/getRecords";
    private static final String SICKREPORT_UPDATE_URL = "http://54.65.97.166/records";
    private static final String SICKREPORT_SHOW_URL = "http://54.65.97.166/getRecord";

    private static final String TEMPERATURE_URL = "http://54.65.97.166/getTemperature";
    /************* End of U R L ****************/



    //TODO : 로그인
    public void setLogin(Context context, String email,String password, String token, final OnResultListener<LoginItemData> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("token", token);

        client.post(context, LOG_IN_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                LoginItemData loginItemData = gson.fromJson(responseString, LoginItemData.class);
                listener.onSuccess(loginItemData);
            }

        });
    }

    //TODO : 로그아웃
    public void setLogout(Context context, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();


        client.get(context, LOG_IN_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }


    //TODO : 비밀번호 변경
    public void setNewPassword(Context context, String email, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", email);

        client.post(context, FORGOT_PASSWORD_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("NetworkManager", "비밀번호 변경 실패");
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("NetworkManager", "비밀번호 변경 성공");

                String codeResult = gson.toJson(responseString);
                listener.onSuccess(codeResult);
            }

        });
    }



    //TODO : 회원가입
    public void setSignUp(Context context, String email, String password, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);


        client.post(context, SIGN_UP_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }


    //TODO : 아이 목록
    public void getBabies(Context context, final OnResultListener<AbuBabies> listener) {
        final RequestParams params = new RequestParams();
//        params.put("email", "test02@naver.com");

        client.post(context, BABY_SEARCH_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                AbuBabies babies = gson.fromJson(responseString, AbuBabies.class);
                listener.onSuccess(babies);
            }

        });

    }


    //TODO : 아이 추가
    public void setBabyAdd(Context context, File file, String name, String birth, String gender, final OnResultListener<BabyaddCodeResult> listener) throws FileNotFoundException {
        final RequestParams params = new RequestParams();
        params.put("file", file);
        params.put("name", name);
        params.put("birth", Integer.parseInt(birth));
        params.put("gender", Integer.parseInt(gender));


        client.put(context, BABY_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                BabyaddCodeResult codeResult = gson.fromJson(responseString, BabyaddCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }


    //TODO : 아이 수정
    public void setBabyUpdate(Context context,File image, String _id, String name, String birth, String gender, final OnResultListener<String> listener) throws FileNotFoundException {
        final RequestParams params = new RequestParams();
        params.put("file", image);
        params.put("_id", _id);
        params.put("name", name);
        params.put("birth", Integer.parseInt(birth));
        params.put("gender", Integer.parseInt(gender));


        client.put(context, BABY_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }


    //TODO : 아이 삭제
    public void setBabyDelete(Context context, String _id, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);// server의 body가 아니라 request param에 들어간다.

        Header[] header = null;

        client.delete(context, BABY_URL, header, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("NetworkManager", "아이 삭제 실패" + responseString);

                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("NetworkManager", "아이 삭제 성공" + responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }


    //TODO : 아이 선택
    public void setBabySelete(Context context, String baby_id, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
        params.put("baby_id", baby_id);


        client.post(context, BABY_SELECT_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }





    //TODO : 시리얼 체크
    public void getBandSerial(Context context, String serial, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
//        Log.e("qazwsx", "serial : " +serial);
        params.put("serial", serial);

        client.post(context, BAND_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });

    }


    //TODO : 시리얼 등록
    public void setBandSerial(Context context, String serial, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
//        Log.e("qazwsx", "serial : " +serial);
        params.put("serial", serial);

        client.put(context, BAND_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.e("NetworkManager", "serial add fail : " + responseString);
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);
                Log.e("NetworkManager", "serial add success : " + responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });

    }




    //TODO : 아픔일지 목록
    public void getSickReport(Context context, final OnResultListener<AbuSickReports> listener) {
        final RequestParams params = new RequestParams();

        client.post(context, SICKREPORT_SEARCH_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("NetworkManager", "sickreport success : " + responseString);

                AbuSickReports sickReports = gson.fromJson(responseString, AbuSickReports.class);
                listener.onSuccess(sickReports);
            }

        });

    }

    //TODO : 아픔일지 보기
    public void getSickReport_item(Context context, String _id, final OnResultListener<AbuSickReports> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);

        client.post(context, SICKREPORT_SHOW_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("NetworkManager", "sickreport_item success : " + responseString);

                AbuSickReports sickReports = gson.fromJson(responseString, AbuSickReports.class);
                listener.onSuccess(sickReports);
            }

        });

    }

    //TODO : 아픔일지 수정
    public void setSickReportUpdate(Context context, String _id, String title, String memo, String image, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);
        params.put("title", title);
        params.put("memo", memo);
        params.put("image", image);


        client.put(context, SICKREPORT_UPDATE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }


    //TODO : 아픔일지 삭제
    public void setSickReportDelete(Context context, String _id, final OnResultListener<NetworkCodeResult> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);

        Header[] header = null;

        client.delete(context, SICKREPORT_UPDATE_URL, header, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("NetworkManager", "아픔일지 삭제 실패" + responseString);

                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("NetworkManager", "아픔일지 삭제 성공" + responseString);

                NetworkCodeResult codeResult = gson.fromJson(responseString, NetworkCodeResult.class);
                listener.onSuccess(codeResult);
            }

        });
    }




    //TODO : 메인 온도
    public void getTemperature(final Context context, String serial, final OnResultListener<AbuTemps> listener) {
        final RequestParams params = new RequestParams();
        params.put("serial", serial);


        client.post(context, TEMPERATURE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("NetworkManager", "Network Temp FAIL"+responseString);
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("NetworkManager", "Network Temp Success"+responseString);
//                Toast.makeText(context, responseString, Toast.LENGTH_SHORT).show();

                AbuTemps abuTemps = gson.fromJson(responseString, AbuTemps.class);
                listener.onSuccess(abuTemps);
            }
        });
    }



    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}
