package com.toyproject.nhg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증
    private DatabaseReference databaseReference;    // 실시간 데이터베이스

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

        // 이메일 중복확인 버튼 구현
        btn_join_email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 닉네임 중복확인 버튼 구현
        btn_join_nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                // 입력값 유효성 검사
                if (strEmail.isEmpty() || strPassword.isEmpty() || strconfirmPassword.isEmpty() || strName.isEmpty() || strNickName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
                } else if (!strPassword.equals(strconfirmPassword)) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(strPassword)) {
                    Toast.makeText(SignupActivity.this, "비밀번호는 8자리 이상의 영문자, 숫자, 특수문자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
                } else {


                    // 중복 확인 로직을 구현하고, 중복이 없으면 회원가입 처리를 진행합니다.
                    if (isEmailAvailable(strEmail) && isNicknameAvailable(strNickName)) {
                        // 회원가입 처리를 수행합니다.
                        performSignUp(strEmail, strPassword, strNickName);
                    } else {
                        Toast.makeText(SignupActivity.this, "이미 사용 중인 이메일 또는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                // Firebase Auth 진행
                firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            UserAccount userAccount = new UserAccount();
                            userAccount.setIdToken(firebaseUser.getUid());
                            userAccount.setEmail(firebaseUser.getEmail());
                            userAccount.setPassword(strPassword);

                            // setValue : 데이터베이스에 INSERT
                            databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(userAccount);

                            Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {    // 실패한 이유들 하나씩 추가하기(이메일 인증, 비밀번호 조건 불충족,
                            Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    private boolean isValidId(@NonNull String strEmail, String strPassword, String strconfirmPassword, String strName, String strNickName) {
        // 입력값 유효성 검사
        if (strEmail.isEmpty() || strPassword.isEmpty() || strconfirmPassword.isEmpty() || strName.isEmpty() || strNickName.isEmpty()) {
            Toast.makeText(SignupActivity.this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strPassword.equals(strconfirmPassword)) {
            Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isPasswordValid(strPassword)) {
            Toast.makeText(SignupActivity.this, "비밀번호는 8자리 이상의 영문자, 숫자, 특수문자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        // 비밀번호가 8자리 이상의 영문자, 숫자, 특수문자의 조합인지 확인하는 로직을 작성합니다.
        // 여기서는 간단한 예시로 비밀번호가 8자리 이상인지만 확인합니다.
        return password.length() >= 8;
    }

    private boolean isEmailAvailable(String email) {
        // 이메일 중복 확인 로직을 작성합니다.
        // 이미 가입된 이메일인지 확인하는 과정을 수행합니다.
        // 예를 들어, 데이터베이스에서 이메일을 조회하여 중복 여부를 판단합니다.
        // 여기서는 간단한 예시로 중복이 없다고 가정합니다.
        return true;
    }

    private boolean isNicknameAvailable(String nickname) {
        // 닉네임 중복 확인 로직을 작성합니다.
        // 이미 사용 중인 닉네임인지 확인하는 과정을 수행합니다.
        // 예를 들어, 데이터베이스에서 닉네임을 조회하여 중복 여부를 판단합니다.
        // 여기서는 간단한 예시로 중복이 없다고 가정합니다.
        return true;
    }

    private void performSignUp(String email, String password, String nickname) {
        // 실제 회원가입 처리를 수행하는 로직을 작성합니다.
        // 여기서는 간단한 예시로 가입 정보를 출력하는 것으로 대체합니다.
        String message = "회원가입 정보:\n" +
                "이메일: " + email + "\n" +
                "비밀번호: " + password + "\n" +
                "닉네임: " + nickname;
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
