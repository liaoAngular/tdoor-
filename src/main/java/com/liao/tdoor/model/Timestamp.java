package com.liao.tdoor.model;

import java.util.Date;

/**
 * @author 廖某某
 * @date 2019/3/16 14:00
 * token时间戳
 **/
public class Timestamp {
    private Date access_time; //创建时间
    private  String user_email; //用户邮箱

    public Date getAccess_time() {
        return access_time;
    }

    public void setAccess_time(Date access_time) {
        this.access_time = access_time;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "Timestamp{" +
                "access_time=" + access_time +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
