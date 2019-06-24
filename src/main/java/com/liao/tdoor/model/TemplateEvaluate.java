package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 廖某某
 * @date 2019/2/20 10:39
 * 模板评论
 **/
@Entity
@Table(name = "template_evaluate")
public class TemplateEvaluate {
    @Id
    @Column(name = "id")
    private String id; //评论ID
    @Column(name = "user_id")
    private String user_id; //用户ID
    @Column(name = "score")
    private int score; //用户评分
    @Column(name = "evaluate_content")
    private String evaluate_content; //用户评论内容
    @Column(name = "template_id")
    private String template_id; //模板ID
    @Column(name = "evaluate_time")
    private Date evaluate_time; //评论时间

    public Date getEvaluate_time() {
        return evaluate_time;
    }

    public void setEvaluate_time(Date evaluate_time) {
        this.evaluate_time = evaluate_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEvaluate_content() {
        return evaluate_content;
    }

    public void setEvaluate_content(String evaluate_content) {
        this.evaluate_content = evaluate_content;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    @Override
    public String toString() {
        return "TemplateEvaluate{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", score=" + score +
                ", evaluate_content='" + evaluate_content + '\'' +
                ", template_id='" + template_id + '\'' +
                ", evaluate_time=" + evaluate_time +
                '}';
    }
}
