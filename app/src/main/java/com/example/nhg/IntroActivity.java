package com.example.nhg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    private long backBtnTime = 0;   // 뒤로 가기 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    // 뒤로 가기 키 두 번 누르면 종료
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (gapTime >= 0 && gapTime <= 2000)
            super.onBackPressed();
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}