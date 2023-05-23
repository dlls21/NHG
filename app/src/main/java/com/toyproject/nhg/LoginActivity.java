package com.toyproject.nhg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tiet_email, tiet_pw;
    Button btn_login_common_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tiet_email = findViewById(R.id.tiet_email);
        tiet_pw = findViewById(R.id.tiet_pw);
        btn_login_common_login = findViewById(R.id.btn_login_common_login); // 일반 로그인

        // 일반 로그인 클릭 시
        btn_login_common_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_email.getText().toString();
                String password = tiet_pw.getText().toString();

                Intent intent = new Intent(LoginActivity.this, LoginResultActivity.class);  // 로그인 결과 클래스 만들기
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivityForResult(intent, LoginRequestStatus.LOGIN_SUCCESS_CODE);
            }
        });
    }

    private void startActivityForResult(Intent intent, LoginRequestStatus loginSuccessCode) {
    }


}