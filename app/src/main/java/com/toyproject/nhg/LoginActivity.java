package com.toyproject.nhg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    TextInputEditText tiet_email,
                    tiet_pw;
    Button btn_login_common_login,
            btn_login_naver_login;
    TextView tv_login_find_account,
            tv_login_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tiet_email = findViewById(R.id.tiet_join_email);
        tiet_pw = findViewById(R.id.tiet_join_pw);

        btn_login_common_login = findViewById(R.id.btn_login_common_login); // 일반 로그인
        btn_login_naver_login = findViewById(R.id.btn_login_naver_login);

        tv_login_find_account = findViewById(R.id.tv_login_find_account);
        tv_login_join = findViewById(R.id.tv_login_join);

        // 일반 로그인 클릭 시
        btn_login_common_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_email.getText().toString();
                String password = tiet_pw.getText().toString();

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);  // 로그인 결과 클래스 만들기
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivityForResult(intent, LoginRequestStatus.LOGIN_SUCCESS_CODE);
            }
        });

        // 네이버 로그인 클릭 시

        // 계정 찾기 클릭 시

        // 회원가입 클릭 시 (TextView를 버튼으로 만드는 방법 검색해서 구현하기)
        tv_login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startActivityForResult(Intent intent, LoginRequestStatus loginSuccessCode) {
    }
}