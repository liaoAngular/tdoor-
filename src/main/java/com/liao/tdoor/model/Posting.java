package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 动态实体类
 * @author 廖某某
 */
@Table(name = "post")
@Entity
public class Posting implements Serializable {
    @Id
    @Column(name = "post_id")
    private String post_id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name="post_by_like")
    private int post_by_like;
    @Column(name = "post_title")
    private String post_title;
    @Column(name = "post_content")
    private String post_content;
    @Column(name = "post_date")
    private Date post_date;
    @Column(name = "category_id")
    private int category_id;
    @Column(name = "post_click_number")
    private int post_click_number;
    @Column(name = "post_by_evaluate")
    private int post_by_evaluate;
    @Column(name = "post_thumbnail")
    private String post_thumbnail;
    @Column(name = "post_web_title")
    private String post_web_title;

    public String getPost_thumbnail() {
        return post_thumbnail;
    }

    public void setPost_thumbnail(String post_thumbnail) {
        this.post_thumbnail = post_thumbnail;
    }

    public String getPost_web_title() {
        return post_web_title;
    }

    public void setPost_web_title(String post_web_title) {
        this.post_web_title = post_web_title;
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

    public int getPost_by_like() {
        return post_by_like;
    }

    public void setPost_by_like(int post_by_like) {
        this.post_by_like = post_by_like;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPost_click_number() {
        return post_click_number;
    }

    public void setPost_click_number(int post_click_number) {
        this.post_click_number = post_click_number;
    }

    public int getPost_by_evaluate() {
        return post_by_evaluate;
    }

    public void setPost_by_evaluate(int post_by_evaluate) {
        this.post_by_evaluate = post_by_evaluate;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "post_id='" + post_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", post_by_like=" + post_by_like +
                ", post_title='" + post_title + '\'' +
                ", post_content='" + post_content + '\'' +
                ", post_date=" + post_date +
                ", category_id=" + category_id +
                ", post_click_number=" + post_click_number +
                ", post_by_evaluate=" + post_by_evaluate +
                ", post_thumbnail='" + post_thumbnail + '\'' +
                ", post_web_title='" + post_web_title + '\'' +
                '}';
    }
}
