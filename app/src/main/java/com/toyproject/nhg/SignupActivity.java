package com.toyproject.nhg;

import static com.toyproject.nhg.constants.SignupConstants.IS_AVAILABLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.toyproject.nhg.controller.SignupController;
import com.toyproject.nhg.model.User;
import com.toyproject.nhg.utils.BackKeyShutDownFunction;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증 객체
    private SignupController signupController;
    private User user;

    // 이메일 패턴 (문자@문자.문자 형식)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$");

    // 비밀번호 패턴 (영소문자, 대문자, 숫자, 특수문자(~!@#$%^&*_+.?)를 조합 6 ~ 자 (영소문자와 숫자는 필수)
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z0-9~!@#$%^&*_+.?]{6,}$");

//    private boolean email_is_checked;
//    private boolean nickName_is_checked;

    private TextInputLayout til_login_email,
                            til_login_pswd;

    private TextInputEditText tiet_join_email,
                            tiet_join_pswd,
                            tiet_join_confirmpswd,
                            tiet_join_name,
                            tiet_join_nickname;

    private Button btn_join_email_check,
                    btn_join_nick_check,
                    btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 디테일 한 것 더 다듬기 (ex. 입력을 했다가 다시 지운 경우, 빨간 박스가 다시 보라색으로 돌아가게 등)
        // 이메일 조건 힌트 로직
        til_login_email = findViewById(R.id.til_login_email);
        til_login_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!(EMAIL_PATTERN.matcher(s).matches())) {
                    til_login_email.setError("이메일 형식에 맞게 입력해 주세요.");
                } else if (s.equals(null)){
                    til_login_email.setError(null);
                } else {
                    til_login_email.setError(null);
                }
            }
        });

        // 비밀번호 조건 힌트 로직
        til_login_pswd = findViewById(R.id.til_login_pswd);
        til_login_pswd.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(PASSWORD_PATTERN.matcher(s).matches())) {
                    til_login_pswd.setError("비밀번호 형식에 맞게 입력해 주세요.");
                } else if (s.equals(null)){
                til_login_pswd.setError(null);
                } else {
                til_login_pswd.setError(null);
                }
            }
        });

        tiet_join_email = findViewById(R.id.tiet_login_email);
        tiet_join_pswd = findViewById(R.id.tiet_login_pswd);
        tiet_join_confirmpswd = findViewById(R.id.tiet_join_confirmpswd);
        tiet_join_name = findViewById(R.id.tiet_join_name);
        tiet_join_nickname = findViewById(R.id.tiet_join_nickname);


        btn_join_email_check = findViewById(R.id.btn_join_email_check);
        btn_join_nick_check = findViewById(R.id.btn_join_nick_check);
        btn_join = findViewById(R.id.btn_join);

        signupController = SignupController.getController();


        // 이메일 중복확인 버튼 구현
        btn_join_email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_join_email.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    signupController.checkEmailAvailability(email);
                } else {
                    showToast("이메일을 입력해주세요.");
                }
            }
        });


        // 닉네임 중복확인 버튼 구현
        btn_join_nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = tiet_join_nickname.getText().toString();
                if (!TextUtils.isEmpty(nickname)) {
                    signupController.checkNicknameAvailability(nickname);
                } else {
                    showToast("닉네임을 입력해주세요.");
                }
            }
        });


        // 회원가입 버튼 구현
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email = tiet_join_email.getText().toString();
                    String password = tiet_join_pswd.getText().toString();
                    String confirmPassword = tiet_join_confirmpswd.getText().toString();
                    String name = tiet_join_name.getText().toString();
                    String nickname = tiet_join_nickname.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(nickname)) {
                        showToast("모든 값을 입력해 주세요.");
                } else if (!password.equals(confirmPassword)) {
                        showToast("비밀번호가 일치하지 않습니다.");
                } else {
                        User user = new User(email, password, name, nickname);
                        signupController.performSignup(user);
                        showSignUpSuccess();

//                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        finish();
                }
            }
        });
    }


    public void showEmailAvailability(boolean isAvailable) {
        if (isAvailable == IS_AVAILABLE)
            showToast("사용 가능한 이메일입니다.");
        else
            showToast("이미 사용 중인 이메일입니다.");
    }

    public void showNicknameAvailability(boolean isAvailable) {
        if (isAvailable == IS_AVAILABLE)
            showToast("사용 가능한 닉네임입니다.");
        else
            showToast("이미 사용 중인 닉네임입니다.");
    }

    public void showSignUpSuccess() {
        showToast("회원가입에 성공했습니다.");
        finish();
    }

    public void showSignUpFailure() {
        showToast("회원가입에 실패했습니다.");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
