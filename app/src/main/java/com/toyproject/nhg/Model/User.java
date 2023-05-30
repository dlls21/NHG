package com.toyproject.nhg.Model;

public class User {

    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String email;
    private String password;



    private String confirmPassword;
    private String name;
    private String nickname;

    public User(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }
    public String getIdToken() {
        return idToken;
    }
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }


}