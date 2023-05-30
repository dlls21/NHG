package com.toyproject.nhg.Utils;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MakeToast extends AppCompatActivity {

    /**
     * 토스트 생성기 (짧은 토스트 메시지)
     *
     * @param toastMessage 토스트 메시지
     */
    public MakeToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}
