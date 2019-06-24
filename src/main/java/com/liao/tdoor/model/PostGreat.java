package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 点赞
 * @author 廖某某
 * @date 2019/2/21 9:38
 **/
@Entity
@Table(name = "post_great")
public class PostGreat {
    @Id
    @Column(name = "great_id")
    private String great_id; //ID
    @Column(name = "user_id")
    private String user_id; //用户ID
    @Column(name = "post_id")
    private String post_id; //动态ID

    public String getGreat_id() {
        return great_id;
    }

    public void setGreat_id(String great_id) {
        this.great_id = great_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "PostGreat{" +
                "great_id='" + great_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", post_id='" + post_id + '\'' +
                '}';
    }
}
