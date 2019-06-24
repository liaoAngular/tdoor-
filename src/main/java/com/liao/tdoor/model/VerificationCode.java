package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 验证码类
 * @author 廖某某
 */
@Entity
@Table(name = "verification_code")
public class VerificationCode {
    @Id
    @Column(name = "register_id")
    private String register_id;
    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;
    @Column(name = "time")
    private Date time;

    public String getRegister_id() {
        return register_id;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public Date getTime() {
        return time;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "register_id='" + register_id + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", time=" + time +
                '}';
    }
}
