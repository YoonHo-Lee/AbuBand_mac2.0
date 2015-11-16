package com.example.tacademy.abuband;

import android.content.Context;
import android.util.Log;

import com.example.tacademy.abuband.Baby.AbuBabies;
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
    private static final String BABY_URL = "http://54.65.97.166/babies";
    private static final String BABY_SEARCH_URL = "http://54.65.97.166/getBabies";

    private static final String TEMPERATURE_URL = "http://54.65.97.166/getTemperature";
    /************* End of U R L ****************/


    /**************** BABIES PARTS **********************/
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

                Log.i("abuband", responseString);

                AbuBabies babies = gson.fromJson(responseString, AbuBabies.class);
                listener.onSuccess(babies);
            }

        });

    }
    //End of Get Babies

    //  Add Babies
    public void setNetworkBabyAdd(Context context, String name, String birth, String gender, final OnResultListener<String> listener) {
        final RequestParams params = new RequestParams();
        params.put("email", "test02@naver.com");
        params.put("name", name);
        params.put("birth", Integer.parseInt(birth));
        params.put("gender", Integer.parseInt(gender));

/*        params.put("email", "test02@naver.com");
        params.put("name", "하이");
        params.put("birth", 20151116);
        params.put("gender", 0);*/
//        params.put("image", "test02");

        client.put(context, BABY_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFail(statusCode);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("qazwsx", responseString);

                /*************이부분 오류 인듯**************/
//                AbuBabies babies = gson.toJson(responseString, AbuBabies.class);
                String json = gson.toJson(responseString);
                listener.onSuccess(json);
            }

        });
    }
    //end of Set Babies
    /****************END OF BABIES PARTS **********************/





    /*********************** TEMPERATURE PARTS  *********************************/
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



    /*********************** End of TEMPERATURE PARTS  *********************************/




    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}
