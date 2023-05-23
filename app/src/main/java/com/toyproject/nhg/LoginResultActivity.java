package com.toyproject.nhg;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;

public class LoginResultActivity extends AppCompatActivity {    // 로그인 값으로 받아온 결과 처리하는 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        //가져온 이메일과 비밀번호 체크하는 메서드 만들기!!!

    }
//    public String getDataFromLoginActivity(@Nullable Intent intent) {
//        if (intent != null) {
//            intent = getIntent();
//            Bundle bundle = intent.getExtras();
//            String email = bundle.getString("email");
//            String password = bundle.getString("password");
//            return email, password;
//        }
//        else
//            return false;
}
