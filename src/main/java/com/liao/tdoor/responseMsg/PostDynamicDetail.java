package com.liao.tdoor.responseMsg;
/**
 * 信息详情数据
 * @author 廖某某
 * @date 2019/02/19
 */
public class PostDynamicDetail {
    private String user_id;
    private String user_name;
    private String user_headUrl;
    private Object evaluate;

    public PostDynamicDetail(){

    }
    public PostDynamicDetail(String user_id,String user_name,String user_headUrl,Object evaluate){
        this.user_id=user_id;
        this.user_name=user_name;
        this.user_headUrl=user_headUrl;
        this.evaluate=evaluate;
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

    public String getUser_headUrl() {
        return user_headUrl;
    }

    public void setUser_headUrl(String user_headUrl) {
        this.user_headUrl = user_headUrl;
    }

    public Object getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Object evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "PostDynamicDetail{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_headUrl='" + user_headUrl + '\'' +
                ", evaluate=" + evaluate +
                '}';
    }
}
