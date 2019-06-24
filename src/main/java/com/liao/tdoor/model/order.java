package com.liao.tdoor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 廖某某
 * @date 2019/2/20 10:28
 * 用户购买订单
 **/
@Entity
@Table(name = "purchase_order")
public class order {
    @Id
    @Column(name = "order_id")
    private String order_id; //订单ID
    @Column(name = "template_id")
    private String template_id; //模板ID
    @Column(name = "user_id")
    private String user_id; //用户ID
    @Column(name = "show_url")
    private String show_url; //演示地址
    @Column(name = "trade_time")
    private Date trade_time;//交易时间

    @Override
    public String toString() {
        return "order{" +
                "order_id='" + order_id + '\'' +
                ", template_id='" + template_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", show_url='" + show_url + '\'' +
                ", trade_time=" + trade_time +
                '}';
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public Date getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(Date trade_time) {
        this.trade_time = trade_time;
    }
}
