package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name = "post_evaluate")
public class PostEvaluate {
    @Id
    @Column(name = "evaluate_id")
    private String evaluate_id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "post_id")
    private String post_id;
    @Column(name = "evaluate_content")
    private String evaluate_content;
    @Column(name = "evaluate_time")
    private Date evaluate_time;
    public String getEvaluate_id() {
        return evaluate_id;
    }

    public void setEvaluate_id(String evaluate_id) {
        this.evaluate_id = evaluate_id;
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

    public String getEvaluate_content() {
        return evaluate_content;
    }

    public void setEvaluate_content(String evaluate_content) {
        this.evaluate_content = evaluate_content;
    }

    public Date getEvaluate_time() {
        return evaluate_time;
    }

    public void setEvaluate_time(Date evaluate_time) {
        this.evaluate_time = evaluate_time;
    }
    @Override
    public String toString() {
        return "PostEvaluate{" +
                "evaluate_id='" + evaluate_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", post_id='" + post_id + '\'' +
                ", evaluate_content='" + evaluate_content + '\'' +
                ", evaluate_time=" + evaluate_time +
                '}';
    }
}
