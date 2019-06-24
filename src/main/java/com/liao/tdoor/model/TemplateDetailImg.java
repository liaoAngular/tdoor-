package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 廖某某
 * @date 2019/2/24 13:53
 **/
@Entity
@Table(name = "template_detail_img")
public class TemplateDetailImg {
    @Id
    @Column(name = "img_detail_id")
    private String img_detail_id;
    @Column(name = "template_id")
    private String template_id;
    @Column(name = "img_url")
    private String img_url;

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

    @Override
    public String toString() {
        return "TemplateDetailImg{" +
                "img_detail_id='" + img_detail_id + '\'' +
                ", template_id='" + template_id + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
