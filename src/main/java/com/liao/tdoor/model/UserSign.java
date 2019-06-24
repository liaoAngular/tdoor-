package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * 签到表
 * @author 廖某某
 * @date 2019/2/26 15:15
 **/
@Entity
@Table(name = "user_sign")
public class UserSign {
    @Id
    @Column(name = "sign_id")
    private String sign_id; //表ID
    @Column(name = "user_id")
    private String user_id; //用户ID
    @Column(name = "count")
    private int count;  //签到总数
    @Column(name = "create_time")
    private Date create_time;  //创建时间
    @Column(name = "update_time")
    private Date update_time; //更新时间

    public String getSign_id() {
        return sign_id;
    }

    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "UserSign{" +
                "sign_id='" + sign_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", count=" + count +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
