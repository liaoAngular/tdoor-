package com.liao.tdoor.responseMsg;

import java.util.Date;

/**
 * @author 廖某某
 * @date 2019/3/16 9:31
 * 我的订单详情返回信息
 **/
public class MyOrders {
    private String template_id;
    private String author_name;
    private String template_title;
    private String template_introduce;
    private String template_img;
    private int template_price;
    private Date trade_time;
    private String down_file_url;
    public MyOrders(String template_id, String author_name, String template_title, String template_introduce, String template_img, int template_price, Date trade_time,String down_file_url) {
        this.template_id = template_id;
        this.author_name = author_name;
        this.template_title = template_title;
        this.template_introduce = template_introduce;
        this.template_img = template_img;
        this.template_price = template_price;
        this.trade_time = trade_time;
        this.down_file_url=down_file_url;
    }
    public MyOrders() {
        super();
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getTemplate_title() {
        return template_title;
    }

    public void setTemplate_title(String template_title) {
        this.template_title = template_title;
    }

    public String getTemplate_introduce() {
        return template_introduce;
    }

    public void setTemplate_introduce(String template_introduce) {
        this.template_introduce = template_introduce;
    }

    public String getTemplate_img() {
        return template_img;
    }

    public void setTemplate_img(String template_img) {
        this.template_img = template_img;
    }

    public int getTemplate_price() {
        return template_price;
    }

    public void setTemplate_price(int template_price) {
        this.template_price = template_price;
    }

    public Date getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Date trade_time) {
        this.trade_time = trade_time;
    }

    public void setDown_file_url(String down_file_url) {
        this.down_file_url = down_file_url;
    }

    public String getDown_file_url() {
        return down_file_url;
    }

    @Override
    public String toString() {
        return "MyOrders{" +
                "template_id='" + template_id + '\'' +
                ", author_name='" + author_name + '\'' +
                ", template_title='" + template_title + '\'' +
                ", template_introduce='" + template_introduce + '\'' +
                ", template_img='" + template_img + '\'' +
                ", template_price='" + template_price + '\'' +
                ", trade_time='" + trade_time + '\'' +
                ", down_file_url='" + down_file_url + '\'' +
                '}';
    }
}
