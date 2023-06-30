package com.toyproject.nhg.network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient serverInstance = null ;
    private UserAPI userApi;

    // 서버에 연결할 url
//    private final static String BASE_URL = "https://";

    // test용 로컬 url
    private final static String BASE_URL = "https://127.0.0.1:8080/";


    //getInstance로만 객체를 생성해 가져갈 수 있게끔 private으로 설정
    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit Interface도 함께 생성
        this.userApi = retrofit.create(UserAPI.class);
        Log.d("RetrofitClient : ", "UserAPI 생성!");
    }

    public static RetrofitClient getInstance() {
        if (serverInstance == null) {
            serverInstance = new RetrofitClient();
            Log.d("RetrofitClient : ", "RetrofitClient 생성!");
        }
        return serverInstance;
    }

    public UserAPI getApi() {
        return userApi;
    }
}
