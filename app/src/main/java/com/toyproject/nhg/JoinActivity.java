package com.toyproject.nhg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class JoinActivity extends AppCompatActivity {

    // 뒤로 가기 종료 기능
    private BackKeyShutDownFunction backKeyShutDownFunction = BackKeyShutDownFunction.getBackKeyShutDownFunction();

    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증
    private DatabaseReference databaseReference;    // 실시간 데이터베이스

    private TextInputEditText tiet_join_email,
                            tiet_join_pw,
                            tiet_join_pwcheck,
                            tiet_join_name,
                            tiet_join_nickname;

    private Button btn_join_email_check,
                    btn_join_nick_check,
                    btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("NHG");

        tiet_join_email = findViewById(R.id.tiet_join_email);
        tiet_join_pw = findViewById(R.id.tiet_join_pw);
        tiet_join_pwcheck = findViewById(R.id.tiet_join_pwcheck);
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
                String strPassword = tiet_join_pw.getText().toString();

                // Firebase Auth 진행
                firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
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

                            Toast.makeText(JoinActivity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                        } else {    // 실패한 이유들 하나씩 추가하기(이메일 인증, 비밀번호 조건 불충족,
                            Toast.makeText(JoinActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


//        Intent intent = getIntent();
//
//        Bundle bundle = intent.getExtras();
//        String email = bundle.getString("email");
//        String password = bundle.getString("password");
//
//        //가져온 이메일과 비밀번호 체크하는 메서드 만들기!!!
    }
}
