package com.toyproject.nhg;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 뒤로 가기 키 두 번 눌러 앱을 종료하는 기능
 *
 * 구현 방법: 클래스 내에서 getBackKeyShutDownFunction() 메서드로 기능 가져오기
 */
public class BackKeyShutDownFunction extends AppCompatActivity {
    private BackKeyShutDownFunction () {};

    // 정적 변수로 종료 기능 객체 선언 (null)
    private static BackKeyShutDownFunction backKeyShutDownFunction = null;

    // 종료 기능 객체 가져오기 (없으면 채워넣기)
    public static BackKeyShutDownFunction getBackKeyShutDownFunction() {
        if (backKeyShutDownFunction == null) {
            backKeyShutDownFunction = new BackKeyShutDownFunction();
        }
        return backKeyShutDownFunction;
    }

    // 종료 기능 구현
     private long backBtnTime = 0;   // 뒤로 가기 누른 시간

    // 뒤로 가기 키 두 번 누르면 종료
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - this.backBtnTime;
        if (gapTime >= 0 && gapTime <= 2000)
            super.onBackPressed();
        else {
            this.backBtnTime = curTime;
            Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
