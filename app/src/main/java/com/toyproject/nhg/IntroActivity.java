package com.toyproject.nhg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nhg.R;

public class IntroActivity extends AppCompatActivity {  // 로딩 하는 모습 구현하기(수 초간)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(intent);
            finish();
    }
}