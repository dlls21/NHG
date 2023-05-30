package com.toyproject.nhg.Controller;

import static com.toyproject.nhg.Constants.SignupConstants.*;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toyproject.nhg.Constants.SignupValidity;
import com.toyproject.nhg.Model.User;
import com.toyproject.nhg.Utils.MakeToast;
import com.toyproject.nhg.View.SignupView;

public class SignupController {
    private SignupView signupView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private MakeToast makeToast;

    private boolean email_Availability;
    private boolean nickName_Availability;



    public SignupController(SignupView view, FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.signupView = view;
        this.firebaseAuth = firebaseAuth;
        this.databaseReference = databaseReference;
    }

    /**
     * [테스트 중!!]
     *
     * 이메일 중복 확인
     * @param email 확인할 이메일 주소
     */
    public boolean checkEmailAvailability(String email) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isAvailable = task.getResult().getSignInMethods().isEmpty();

                        if (isAvailable == false){
                            makeToast = new MakeToast("이미 가입된 이메일입니다. 다른 이메일을 사용해 주세요.");
                            email_Availability = IS_UNAVAILABLE;
                        } else {
                            email_Availability = IS_AVAILABLE;
                            signupView.showEmailAvailability(email_Availability);
                        }
                    }
                });
        return email_Availability;
    }

    public boolean isEmail_Availability() {
        return email_Availability;
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
    private boolean isPasswordValid(String password) {
        return password.length() >= 8;  // 수정해야
    }


    /**
     * 닉네임 중복 확인
     *
     * @param nickname 확인할 닉네임
     */
    public boolean checkNicknameAvailability(String nickname) {
        // Firebase 실시간 데이터베이스를 사용하여 닉네임 중복 여부 확인
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").orderByChild("nickname").equalTo(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 중복된 닉네임
                    signupView.showNicknameAvailability(false);
                    nickName_Availability = IS_UNAVAILABLE;
                } else {
                    // 사용 가능한 닉네임
                    signupView.showNicknameAvailability(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 처리 중 오류 발생
                signupView.showNicknameAvailability(false);
            }
        });
        return nickName_Availability;
    }

    /**
     * 회원가입 시 입력 값의 유효성을 검사합니다.
     *
     * @param email
     * @param password
     * @param confirmPassword
     * @param name
     * @param nickName
     * @return
     */
    public SignupValidity isValidId(@NonNull String email, String password, String confirmPassword, String name, String nickName) {
        // 입력값 유효성 검사
        // 빈 칸이 있는지 검사
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || nickName.isEmpty()) {
            makeToast = new MakeToast("빈 칸을 모두 채워주세요.");
//            Toast.makeText(this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();

            return SignupValidity.SIGNUP_IS_INVALID;

        // 비밀번호가 서로 일치하는지
        } else if (!password.equals(confirmPassword)) {
            makeToast = new MakeToast("비밀번호가 일치하지 않습니다.");
//            Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

            return SignupValidity.SIGNUP_IS_INVALID;

        // 비밀번호가 조건에 맞는지
        } else if (!isPasswordValid(password)) {    // 수정중! 8자리 이상의 영문자, 숫자, 특수문자 조합 조건
            makeToast = new MakeToast("비밀번호는 8자리 이상의 영문자, 숫자, 특수문자의 조합이어야 합니다.");
//            Toast.makeText(SignupActivity.this, "비밀번호는 8자리 이상의 영문자, 숫자, 특수문자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();

            return SignupValidity.SIGNUP_IS_INVALID;

        } else {
            return SignupValidity.SIGNUP_IS_VALID;
        }
    }



    /**
     * 회원가입을 수행합니다.
     *
     * @param user 회원 가입 정보를 담고 있는 Member 객체
     */
    public void performSignup(User user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // 회원가입 성공
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser != null) {  // 이메일 및 비밀번호로 회원가입이 성공했으면, 이름과 닉네임 추가
                                saveExtraData(user);
//                                saveExtraData(currentUser.getUid(), member.getName(), member.getNickname());
                                showSignupSuccess();
                            }
                        } else {    // 회원가입 실패
                            showSignupFailure();
                        }
                    }


                });
    }

    private void showSignupSuccess() {
        makeToast = new MakeToast("회원가입 성공!");

    }
    private void showSignupFailure() {
        makeToast = new MakeToast("회원가입 실패!");
    }



    /**
     * 테스트 중!
     * 이름과 닉네임을 데이터베이스에 저장합니다.
     * @param userId Firebase 사용자 ID
     * @param name 저장할 이름
     * @param nickname 저장할 닉네임
     */
    private void saveExtraData(String userId, String name, String nickname) {
        DatabaseReference userRef = databaseReference.child("users").child(userId);
        userRef.child("name").setValue(name);
        userRef.child("nickname").setValue(nickname);
    }

    private void saveExtraData(User user) {
        DatabaseReference userRef = databaseReference.child("users").child(user.getIdToken());
        userRef.child("name").setValue(user.getName());
        userRef.child("nickname").setValue(user.getNickname());
    }

    /**
     *
     */

}
