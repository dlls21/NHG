package com.toyproject.nhg;

import android.widget.Toast;
/**
 * !! 사용 하지 않는 인터페이스
 *
 * 뒤로 가기 키 두번 눌러 종료 기능
 * onBackPressed() 메서드 완성 시키기
 * 해당 인터페이스에 완성된 메서드가 있음
*/
public interface BackKeyPressed {

    public void onBackPressed();

    // 1. private long backBtnTime = 0;   // 뒤로 가기 종료 -> 선언
    // 2. 아래 붙여넣기 후 주석 해제

//    // 뒤로 가기 키 두 번 누르면 종료
//    @Override
//    public void onBackPressed() {
//        long curTime = System.currentTimeMillis();
//        long gapTime = curTime - backBtnTime;
//        if (gapTime >= 0 && gapTime <= 2000)
//            super.onBackPressed();
//        else {
//            backBtnTime = curTime;
//            Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
//        }
//    }
}
