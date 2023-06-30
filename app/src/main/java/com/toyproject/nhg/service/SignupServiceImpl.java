package com.toyproject.nhg.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.toyproject.nhg.model.User;
import com.toyproject.nhg.network.RetrofitClient;
import com.toyproject.nhg.network.UserAPI;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupServiceImpl implements SignupService{

    private static SignupServiceImpl signupServiceImpl;

    private UserAPI userAPI;
    private FirebaseAuth firebaseAuth;
    private RetrofitClient retrofitClient;


    // 서비스 구현체 생성자
    // Retrofit클라이언트, Retrofit인터페이스, Firebase 인증 생성 검증
    public SignupServiceImpl() {
        if (this.retrofitClient == null)
            this.retrofitClient = RetrofitClient.getInstance();
        if (this.userAPI == null) {
            this.userAPI = retrofitClient.getApi();
            Log.d("SignupController : ", "userAPI 받음");
        }
        if (this.firebaseAuth == null) {
            this.firebaseAuth = FirebaseAuth.getInstance();
            Log.d("SignupController : ", "firebaseAuth 받음");
        }
    }

    public static SignupServiceImpl getInstance() {
        if (signupServiceImpl == null) {
            signupServiceImpl = new SignupServiceImpl();
            Log.d("RetrofitClient : ", "RetrofitClient 생성!");
        }
        return signupServiceImpl;
    }


    // 회원가입 요청을 서버에 보내는 메소드
    @Override
    public void signupUser(User user, Callback<ResponseBody> callback) {
        // Map을 사용하여 회원 정보를 키-값 쌍으로 저장
        Map<String, String> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        userData.put("name", user.getName());
        userData.put("nickname", user.getNickname());

        Call<ResponseBody> call = userAPI.signupUser(userData); // 회원가입 요청 생성
        call.enqueue(callback); // 비동기적으로 요청을 보내고 응답을 처리할 Callback 등록
    }


    // Firebase를 사용하여 이메일 중복 체크를 수행하는 메소드
    @Override
    public void checkEmailDuplication(String email, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, "dummyPassword")
                .addOnCompleteListener(listener);
    }

    /**
     * [테스트 중!!]
     *
     * 이메일 중복 확인
     * @param email 확인할 이메일 주소
     */
    @Override
    public void checkEmailAvailability(String email) {
        // Firebase 인증에서 중복체크
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            boolean isAvailable = task.getResult().getSignInMethods().isEmpty();

                            if (isAvailable) {
                                // 이메일 사용 가능
                                emailAvailabilityCallback(true);
                                Log.d("CheckEmail", "Callback true!");
                            } else {
                                // 이메일 중복
                                emailAvailabilityCallback(false);
                            }
                        } else {
                            // 작업 실패
                            emailAvailabilityCallback(false);
                        }
                    }
                });
    }



    /**
     * [테스트중!!]
     *
     * 비밀번호 유효성을 검사합니다.
     * 비밀번호가 8자리 이상의 영문자, 숫자, 특수문자의 조합인지 확인합니다.
     *
     * @param password
     * @return
     */
    @Override
    public boolean isPasswordValid(String password) {
        return password.length() >= 8;  // 수정해야
    }


    /**
     * [테스트 중!!]
     * 닉네임 중복 확인
     *
     * @param nickname 확인할 닉네임
     */
    @Override
    public void checkNicknameAvailability(String nickname) {

    }


    /**
     * 회원가입을 수행합니다.
     *
     * @param user 회원 가입 정보를 담고 있는 Member 객체
     */
    @Override
    public void performSignup(User user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())    // 회원가입 시작
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // 회원가입 성공
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser != null) { // 이메일 및 비밀번호로 회원가입이 성공했으면(Auth에 저장됨),
                                saveExtraData(currentUser.getUid(), user);  // 데이터베이스에 모든 입력값 넘기기
//                                signupView.showSignUpSuccess();
                            }
                        } else {    // 회원가입 실패
//                            signupView.showSignUpFailure();
                        }
                    }
                });
    }


    /**
     * [테스트 중!]
     *
     * 이름과 닉네임을 데이터베이스에 저장합니다.
     * @param uid Firebase 사용자 ID
     * @param user 저장할 이름과 닉네임을 가지고 있는 사용자 객체
     */
    @Override
    public void saveExtraData(String uid, User user) {

    }


    // 이메일 중복확인 콜백 메서드
    @Override
    public void emailAvailabilityCallback(boolean isAvailable) {
        // 이메일 사용 가능 여부 처리
        if (isAvailable) {
            // 사용 가능한 경우
//            signupView.showEmailAvailability(true);

        } else {
            // 중복된 경우
//            signupView.showEmailAvailability(false);
        }
    }


    // 닉네임 중복확인 콜백 메서드
    @Override
    public void nicknameAvailabilityCallback(boolean isAvailable) {
        // 닉네임 사용 가능 여부 처리
        if (isAvailable) {
            // 사용 가능한 경우
//            signupView.showNicknameAvailability(true);
        } else {
            // 중복된 경우
//            signupView.showNicknameAvailability(false);
        }
    }
}
