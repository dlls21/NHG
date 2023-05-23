package com.toyproject.nhg;

/**
 * 사용자 계정 정보 모델 클래스
 *
 */
public class UserAccount {

    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String email;
    private String password;
    private String name;
    private String nickname;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public UserAccount() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
