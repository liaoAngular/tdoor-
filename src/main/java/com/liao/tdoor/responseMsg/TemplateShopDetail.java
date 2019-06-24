package com.liao.tdoor.responseMsg;

import com.liao.tdoor.model.TemplateDetailImg;
import com.liao.tdoor.model.TemplateShop;
import com.liao.tdoor.model.User;

import java.util.List;

/**
 * 序列化商城信息返回
 * @author 廖某某
 * @date 2019/2/24 12:32
 **/
public class TemplateShopDetail {
    private String user_id;
    private String user_name;
    private String user_head_url;
    private String template_id;
    private String template_img_url;
    private String template_name;
    private int isCollect;
    private int isPurchase;
    private Object template_detail;
    private List<PostDynamicDetail> template_evaluate;
    private List<TemplateDetailImg> template_detail_img;

    public TemplateShopDetail(){

    }

    public TemplateShopDetail(User user,TemplateShop shop){
        this.user_id=user.getId();
        this.user_name=user.getNickname();
        this.template_id=shop.getTemplate_id();
        this.template_img_url=shop.getTemplate_img_url();
        this.template_name=shop.getTemplate_name();
    }
    public TemplateShopDetail(User user,int isCollect,int isPurchase,
                              Object detail,
                              List<TemplateDetailImg> detail_img){
        this.template_detail=detail;
        this.template_detail_img=detail_img;
        this.isCollect=isCollect;
        this.isPurchase=isPurchase;
        this.user_id=user.getId();
        this.user_name=user.getNickname();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_head_url() {
        return user_head_url;
    }

    public void setUser_head_url(String user_head_url) {
        this.user_head_url = user_head_url;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_img_url() {
        return template_img_url;
    }

    public void setTemplate_img_url(String template_img_url) {
        this.template_img_url = template_img_url;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(int isPurchase) {
        this.isPurchase = isPurchase;
    }

    public void setTemplate_detail(Object template_detail) {
        this.template_detail = template_detail;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public Object getTemplate_detail() {
        return template_detail;
    }

    public List<PostDynamicDetail> getTemplate_evaluate() {
        return template_evaluate;
    }

    public void setTemplate_evaluate(List<PostDynamicDetail> template_evaluate) {
        this.template_evaluate = template_evaluate;
    }

    public List<TemplateDetailImg> getTemplate_detail_img() {
        return template_detail_img;
    }

    public void setTemplate_detail_img(List<TemplateDetailImg> template_detail_img) {
        this.template_detail_img = template_detail_img;
    }

    @Override
    public String toString() {
        return "TemplateShopDetail{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_head_url='" + user_head_url + '\'' +
                ", template_id='" + template_id + '\'' +
                ", template_img_url='" + template_img_url + '\'' +
                ", template_name='" + template_name + '\'' +
                ", isCollect=" + isCollect +
                ", isPurchase=" + isPurchase +
                ", template_detail=" + template_detail +
                ", template_evaluate=" + template_evaluate +
                ", template_detail_img=" + template_detail_img +
                '}';
    }
}
