package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模板收藏
 * @author 廖某某
 * @date 2019/02/20
 */
@Entity
@Table(name = "template_collect")
public class TemplateCollect {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "template_id")
    private String template_id; //模板ID
    @Column(name = "user_id")
    private String user_id; //用户ID

    @Override
    public String toString() {
        return "TemplateCollect{" +
                "id='" + id + '\'' +
                ", template_id='" + template_id + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
