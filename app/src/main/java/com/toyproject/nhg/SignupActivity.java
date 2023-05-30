package com.toyproject.nhg;

import static com.toyproject.nhg.Constants.SignupConstants.IS_AVAILABLE;
import static com.toyproject.nhg.Constants.SignupConstants.IS_UNAVAILABLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toyproject.nhg.Constants.SignupValidity;
import com.toyproject.nhg.Controller.SignupController;
import com.toyproject.nhg.Model.User;
import com.toyproject.nhg.Utils.BackKeyShutDownFunction;
import com.toyproject.nhg.Utils.MakeToast;
import com.toyproject.nhg.View.SignupView;

public class SignupActivity extends AppCompatActivity implements SignupView {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증 객체
    private DatabaseReference databaseReference;    // 실시간 데이터베이스

    private boolean email_is_checked;
    private boolean nickName_is_checked;

    private TextInputEditText tiet_join_email,
                            tiet_join_pswd,
                            tiet_join_confirmpswd,
                            tiet_join_name,
                            tiet_join_nickname;

    private Button btn_join_email_check,
                    btn_join_nick_check,
                    btn_join;

    private SignupController signupController;
    private User user;
    private MakeToast makeToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("NHG");

        tiet_join_email = findViewById(R.id.tiet_login_email);
        tiet_join_pswd = findViewById(R.id.tiet_login_pswd);
        tiet_join_confirmpswd = findViewById(R.id.tiet_join_confirmpswd);
        tiet_join_name = findViewById(R.id.tiet_join_name);
        tiet_join_nickname = findViewById(R.id.tiet_join_nickname);


        btn_join_email_check = findViewById(R.id.btn_join_email_check);
        btn_join_nick_check = findViewById(R.id.btn_join_nick_check);
        btn_join = findViewById(R.id.btn_join);

        signupController = new SignupController(SignupActivity.this, firebaseAuth, databaseReference);


        // 이메일 중복확인 버튼 구현
        btn_join_email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_join_email.getText().toString();
                boolean emailAvailability = signupController.checkEmailAvailability(email);

                if (emailAvailability == IS_AVAILABLE) {
                    email_is_checked = IS_AVAILABLE;
                } else {
                    email_is_checked = IS_UNAVAILABLE;
                }
            }
        });

        //// 여기서부터 다시 디버깅!!
        // 닉네임 중복확인 버튼 구현
        btn_join_nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = tiet_join_nickname.getText().toString();
                boolean nickNameAvailability = signupController.checkNicknameAvailability(nickName);

                if (nickNameAvailability == IS_AVAILABLE) {
                    nickName_is_checked = IS_AVAILABLE;

                } else {
                    nickName_is_checked = IS_UNAVAILABLE;

                }
            }
        });

        // 회원가입 버튼 구현
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = tiet_join_email.getText().toString();
                String strPassword = tiet_join_pswd.getText().toString();
                String strconfirmPassword = tiet_join_confirmpswd.getText().toString();
                String strName = tiet_join_name.getText().toString();
                String strNickName = tiet_join_nickname.getText().toString();
                user = new User(strEmail, strPassword, strName, strNickName);

                // 회원가입 항목별 입력값들 유효성 검사 변수
                SignupValidity signup_validity;

                // 이메일, 닉네임 중복확인을 모두 끝냈으면, 모든 입력값의 유효성 검사를 시작하고
                // 회원가입 유효성을 SIGNUP_IS_VALID을 가짐
                if (!(email_is_checked == IS_AVAILABLE && nickName_is_checked == IS_AVAILABLE)) {
                    signup_validity = SignupValidity.SIGNUP_IS_INVALID;
                } else { // 각 입력값들 유효성 검사 메서드
                    signup_validity = signupController.
                            isValidId(user.getEmail(), user.getPassword(), user.getConfirmPassword(), user.getConfirmPassword(), user.getNickname());
                }

                // 회원가입 유효성이 있다면
                if (signup_validity.equals(SignupValidity.SIGNUP_IS_VALID)) {

                        // 회원가입 처리를 수행합니다.
                    signupController.performSignup(user);
                }
//                else {
//                    makeToast = new MakeToast("회원가입 실패!");
//                }
            }
        });
    }


    @Override
    public void showEmailAvailability(boolean isAvailable) {
        if (isAvailable == IS_AVAILABLE)
            makeToast = new MakeToast("사용 가능한 이메일입니다.");

    }

    @Override
    public void showNicknameAvailability(boolean isAvailable) {
        if (isAvailable == IS_AVAILABLE)
            makeToast = new MakeToast("사용 가능한 닉네임입니다.");
    }
}
