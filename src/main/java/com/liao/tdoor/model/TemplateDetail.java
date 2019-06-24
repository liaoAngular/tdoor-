package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模板图片详情
 * @author 廖某某
 * @date 2019/02/20
 */
@Entity
@Table(name = "template_detail_img")
public class TemplateDetail {
    @Id
    @Column(name = "img_detail_id")
    private String img_detail_id; //详情ID
    @Column(name = "template_id")
    private String template_id; //模板ID
    @Column(name = "img_url")
    private String img_url; //图片地址

    @Override
    public String toString() {
        return "TemplateDetail{" +
                "img_detail_id='" + img_detail_id + '\'' +
                ", template_id='" + template_id + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    public String getImg_detail_id() {
        return img_detail_id;
    }

    public void setImg_detail_id(String img_detail_id) {
        this.img_detail_id = img_detail_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
