package com.toyproject.nhg.View;

public interface SignupView {

    // 이메일 중복 확인
    void showEmailAvailability(boolean isAvailable);
    // 닉네임 중복 확인
    void showNicknameAvailability(boolean isAvailable);


}
