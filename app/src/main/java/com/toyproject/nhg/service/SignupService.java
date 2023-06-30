package com.toyproject.nhg.service;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.toyproject.nhg.model.User;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public interface SignupService {

    /*
        ※ 회원가입 로직
        - 입력값 : 이메일, 비밀번호, 비밀번호 확인, 이름, 닉네임
        - 중복확인 : 이메일, 닉네임
        - 회원가입 조건
         1. 모든 빈칸을 채울 것
         2. 이메일 패턴에 맞는 값을 받을 것
         3. 이메일 중복확인이 완료될 것
         4. 비밀번호 패턴에 맞는 값을 받을 것
         5. 비밀번호와 비밀번호 확인 칸이 같은 값일 것
         6. 닉네임 중복확인이 완료될 것
         7. 입력값 상한을 넘지 않을 것
     */


    // 회원가입 요청을 서버에 보내는 메소드
    void signupUser(User user, Callback<ResponseBody> callback);

    // Firebase를 사용하여 이메일 중복 체크를 수행하는 메소드
    // 3번 조건
    void checkEmailDuplication(String email, OnCompleteListener<AuthResult> listener);

    /**
     * 이메일 중복 확인
     * @param email 확인할 이메일 주소
     */
    void checkEmailAvailability(String email);

    /**
     * 비밀번호 유효성을 검사합니다.
     * 비밀번호가 8자리 이상의 영문자, 숫자, 특수문자의 조합인지 확인합니다.
     *
     * @param password
     * @return
     */
    boolean isPasswordValid(String password);

    /**
     * 닉네임 중복 확인
     *
     * @param nickname 확인할 닉네임
     */
    void checkNicknameAvailability(String nickname);

    /**
     * 회원가입을 수행합니다.
     *
     * @param user 회원 가입 정보를 담고 있는 Member 객체
     */
    void performSignup(User user);

    /**
     * 이름과 닉네임을 데이터베이스에 저장합니다.
     * @param uid Firebase 사용자 ID
     * @param user 저장할 이름과 닉네임을 가지고 있는 사용자 객체
     */
    void saveExtraData(String uid, User user);


    // 이메일 중복확인 콜백 메서드
    void emailAvailabilityCallback(boolean isAvailable);

    // 닉네임 중복확인 콜백 메서드
    void nicknameAvailabilityCallback(boolean isAvailable);
}