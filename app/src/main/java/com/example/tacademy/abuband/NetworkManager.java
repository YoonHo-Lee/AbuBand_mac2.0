package com.example.tacademy.abuband;

import android.content.Context;
import android.util.Log;

import com.example.tacademy.abuband.Baby.AbuBabies;
import com.example.tacademy.abuband.Band.BandItemData;
import com.example.tacademy.abuband.SickReport.AbuSickReports;
import com.example.tacademy.abuband.Temperature.AbuTemps;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.IOException;
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


    /************* U R L ****************/
    private static final String SIGN_UP_URL = "http://54.65.97.166/users/join";

    private static final String BABY_URL = "http://54.65.97.166/babies";
    private static final String BABY_SEARCH_URL = "http://54.65.97.166/getBabies";

    private static final String BAND_URL = "http://54.65.97.166/users/band";

    private static final String SICKREPORT_SEARCH_URL = "http://54.65.97.166/getRecords";

    private static final String TEMPERATURE_URL = "http://54.65.97.166/getTemperature";
    /************* End of U R L ****************/


    /******************************************* MEMBER PARTS *************************************************/
    //  Sign Up
    public void setSignUp(Context context, String email, String pw, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("pw", pw);


        client.put(context, SIGN_UP_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }
    //end of Sign Up
    /******************************************* END OF MEMBER PARTS *************************************************/

    /******************************************* BABIES PARTS *************************************************/
    // Get Babies
    public void getBabies(Context context, String email, final OnResultListener<AbuBabies> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", "test02@naver.com");

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
    //End of Get Babies


    //  Add Babies
    //TODO : 아이 추가
    public void setBabyAdd(Context context, String name, String birth, String gender, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", "test02@naver.com");
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

                /*************이부분 오류 인듯**************/
//                AbuBabies babies = gson.toJson(responseString, AbuBabies.class);
                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }
    //end of Set Babies

    //TODO : 아이 수정
    /*******************************************END OF BABIES PARTS *************************************************/
    public void setBabyUpdate(Context context, String _id, String name, String birth, String gender, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);
        params.put("email", "test02@naver.com");
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

                /*************이부분 오류 인듯**************/
//                AbuBabies babies = gson.toJson(responseString, AbuBabies.class);
                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }

    //TODO : 아이 삭제
    /*******************************************END OF BABIES PARTS *************************************************/
    public void setBabyDelete(Context context, String _id, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("_id", _id);

        client.put(context, BABY_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

//                Log.e("qazwsx", responseString);

                /*************이부분 오류 인듯**************/
//                AbuBabies babies = gson.toJson(responseString, AbuBabies.class);
                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }


    /******************************************* BAND PARTS *************************************************/
// Get Serial
    public void getBandSerial(Context context, String serial, final OnResultListener<BandItemData> listener) {
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

                BandItemData bands = gson.fromJson(responseString, BandItemData.class);
                listener.onSuccess(bands);
            }

        });

    }
    //End of Get Serial

    /*******************************************END OF BAND PARTS *************************************************/



    /********************************************SICK REPORT PARTS *************************************************/
    // Get SickReport
    public void getSickReport(Context context, String email, final OnResultListener<AbuSickReports> listener) {
        final RequestParams params = new RequestParams();
        Log.e("qazwsx", "email : " +email);
        params.put("email", "test02@naver.com");

        client.post(context, SICKREPORT_SEARCH_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("qazwsx","sickreport success : " + responseString);

                AbuSickReports sickReports = gson.fromJson(responseString, AbuSickReports.class);
                listener.onSuccess(sickReports);
            }

        });

    }
    //End of Get SickReprot

    /*******************************************END OF SICK REPORT PARTS *************************************************/





    /************************************************** TEMPERATURE PARTS  ************************************************************/
    //Get Temperature
    public void getTemperature(final Context context, String serial, final OnResultListener<AbuTemps> listener) {
        final RequestParams params = new RequestParams();
        params.put("serial", "LEEKR203NR5");


        client.post(context, TEMPERATURE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("qazwsx", "FAAAAAAAAAAAAAAAAAAAAAAAAAIL");
                listener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("qazwsx", responseString);
//                Toast.makeText(context, responseString, Toast.LENGTH_SHORT).show();

                AbuTemps abuTemps = gson.fromJson(responseString, AbuTemps.class);
                listener.onSuccess(abuTemps);
            }
        });
    }



    /************************************************** End of TEMPERATURE PARTS  ************************************************************/




    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}
