package com.toyproject.nhg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.material.textfield.TextInputEditText;
import com.toyproject.nhg.Constants.LoginRequestStatus;
import com.toyproject.nhg.Utils.BackKeyShutDownFunction;

public class LoginActivity extends AppCompatActivity {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    TextInputEditText tiet_login_email,
                    tiet_login_pswd;
    Button btn_login_common_login,
            btn_login_naver_login;
    TextView tv_login_find_account,
            tv_login_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tiet_login_email = findViewById(R.id.tiet_login_email);
        tiet_login_pswd = findViewById(R.id.tiet_login_pswd);

        btn_login_common_login = findViewById(R.id.btn_login_common_login); // 일반 로그인
        btn_login_naver_login = findViewById(R.id.btn_login_naver_login);

        tv_login_find_account = findViewById(R.id.tv_login_find_account);
        tv_login_signup = findViewById(R.id.tv_login_signup);

        // 일반 로그인 클릭 시
        btn_login_common_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_login_email.getText().toString();
                String password = tiet_login_pswd.getText().toString();

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);  // 로그인 결과 클래스 만들기
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivityForResult(intent, LoginRequestStatus.LOGIN_SUCCESS_CODE);
            }
        });

        // 네이버 로그인 클릭 시

        // 계정 찾기 클릭 시

        // 회원가입 클릭 시 (TextView를 버튼으로 만드는 방법 검색해서 구현하기)
        tv_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startActivityForResult(Intent intent, LoginRequestStatus loginSuccessCode) {
    }
}