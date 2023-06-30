package com.toyproject.nhg.network;

import com.toyproject.nhg.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

// API 인터페이스 정의
public interface UserAPI {

//    @GET("users/{userId}")    // 테스트용
//    Call<User> getUser(@Path("userId") String userId);

    @POST("posts/{userId}") // 회원가입 (key, value)
    Call<ResponseBody> signupUser(@Body Map<String, String> map);

    @POST("posts/{userId}")  // 로그인
    Call<ResponseBody> loginUser(@Body Map<String, String> map);

    @GET("validate/{userId}")   // 중복체크
    Call<ResponseBody> validateUser(@Path("userId") String userId);

    @GET("users")   // 회원정보 조회
    Call<User> getUser(@Header("Authorization") String jwt);

    @DELETE("users")    // 회원탈퇴
    Call<ResponseBody> deleteUser(@Header("Authorization") String jwt);
}