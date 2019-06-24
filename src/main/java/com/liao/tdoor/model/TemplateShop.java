package com.liao.tdoor.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模板实体类
 * @author 廖某某
 * @date 2019/02/20
 */
@Entity
@Table(name = "template_shop")
public class TemplateShop {
    @Id
    @Column(name = "template_id")
    private String template_id; //模板ID
    @Column(name = "user_id")
    private String user_id; //拥有者ID
    @Column(name = "template_name")
    private String template_name; //模板名称
    @Column(name = "template_img_url")
    private String template_img_url; //简略图地址
    @Column(name = "template_dp_url")
    private String template_dp_url; //下载地址
    @Column(name = "template_price")
    private int template_price; //积分价格
    @Column(name = "template_by_collect")
    private int template_by_collect; //收藏数
    @Column(name = "purchase_number")
    private int purchase_number; //总购买数
    @Column(name = "evaluate_number")
    private int evaluate_number; //总评价数
    @Column(name = "template_introduce")
    private String template_introduce; //简介

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

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_img_url() {
        return template_img_url;
    }

    public void setTemplate_img_url(String template_img_url) {
        this.template_img_url = template_img_url;
    }

    public String getTemplate_dp_url() {
        return template_dp_url;
    }

    public void setTemplate_dp_url(String template_dp_url) {
        this.template_dp_url = template_dp_url;
    }

    public int getTemplate_price() {
        return template_price;
    }

    public void setTemplate_price(int template_price) {
        this.template_price = template_price;
    }

    public int getTemplate_by_collect() {
        return template_by_collect;
    }

    public void setTemplate_by_collect(int template_by_collect) {
        this.template_by_collect = template_by_collect;
    }

    public int getPurchase_number() {
        return purchase_number;
    }

    public void setPurchase_number(int purchase_number) {
        this.purchase_number = purchase_number;
    }

    public int getEvaluate_number() {
        return evaluate_number;
    }

    public void setEvaluate_number(int evaluate_number) {
        this.evaluate_number = evaluate_number;
    }

    public String getTemplate_introduce() {
        return template_introduce;
    }

    public void setTemplate_introduce(String template_introduce) {
        this.template_introduce = template_introduce;
    }

    @Override
    public String toString() {
        return "TemplateShop{" +
                "template_id='" + template_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", template_name='" + template_name + '\'' +
                ", template_img_url='" + template_img_url + '\'' +
                ", template_dp_url='" + template_dp_url + '\'' +
                ", template_price=" + template_price +
                ", template_by_collect=" + template_by_collect +
                ", purchase_number=" + purchase_number +
                ", evaluate_number=" + evaluate_number +
                ", template_introduce='" + template_introduce + '\'' +
                '}';
    }
}
