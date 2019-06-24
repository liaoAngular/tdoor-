package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 廖某某
 * @create 2019/02/20 09:41:02
 * 动态收藏
 */
@Entity
@Table(name = "post_collect")
public class PostCollect {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "post_id")
    private String post_id;  //动态ID
    @Column(name = "user_id")
    private String user_id; //用户ID

    @Override
    public String toString() {
        return "PostCollect{" +
                "id='" + id + '\'' +
                ", post_id='" + post_id + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
