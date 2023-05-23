package com.toyproject.nhg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nhg.R;

public class IntroActivity extends AppCompatActivity {

    private long curTime = System.currentTimeMillis();
    private long backBtnTime = 0;   // 뒤로 가기 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        if ((int) curTime == 2) {   // 화면 띄운지 2초 지나면 다음 화면으로 전환
            startActivity(intent);
            finish();
        }
    }

    // 뒤로 가기 키 두 번 누르면 종료
    @Override
    public void onBackPressed() {
//        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (gapTime >= 0 && gapTime <= 2000)
            super.onBackPressed();
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}