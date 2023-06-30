package com.toyproject.nhg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SerializedName 으로 JSON 객체와 해당 변수를 매칭
 * @SerializedName 괄호 안에는 해당 JSON 객체의 변수 명 적기.
 * 이때, POST 매핑으로 받아올 값은, 굳이 annotation 을 붙이지 않고, JSON 객체의 변수명과 일치하는 변수만 선언하면 됨
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @SerializedName("idToken")
    @Expose
    private String idToken; // Firebase Uid (고유 토큰 정보)

//    // 서버에서 자동으로 추가될 id
//    @SerializedName("userId")
//    @Expose
//    private int userId;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    public User(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }


//    @SerializedName("joindate")
//    @Expose
//    private String joindate;
//
//    public User(String idToken, String email, String password, String name, String nickname) {
//        this.idToken = idToken;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.nickname = nickname;
////        this.joindate = joindate;
//    }

//    public String getIdToken() {
//        return idToken;
//    }
//    public void setIdToken(String idToken) {
//        this.idToken = idToken;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }

//    public String getJoindate() {
//        return joindate;
//    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "idToken='" + idToken + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", nickname='" + nickname + '\'' +
//                ", joindate='" + joindate + '\'' +
//                '}';
//    }
}