package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户类
 * @author 廖某某
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    //邮箱
    private String email;
    @Column(name = "password")
    //密码
    private String password;
    @Column(name = "tcoin")
    //t币
    private int tcoin;
    @Column(name = "nickname")
    //昵称
    private String nickname;
    @Column(name = "sex")
    //性别
    private int sex;
    @Column(name = "address")
    //地址
    private String address;
    @Column(name = "profiles")
    //简介
    private String profiles;
    @Column(name = "headUrl")
    //头像
    private String headUrl;

    public User(String email,String password,String nickname){
        this.email=email;
        this.password=password;
        this.nickname=nickname;
    }
    public User(String nickname,String user_id){
        this.nickname=nickname;
        this.id=user_id;
    }
    public User(){

    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getTcoin() {
        return tcoin;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getProfiles() {
        return profiles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTcoin(int tcoin) {
        this.tcoin = tcoin;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", tcoin=" + tcoin +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", profiles='" + profiles + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
