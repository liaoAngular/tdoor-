package com.liao.tdoor.responseMsg;

/**
 * 定义一个枚举类来维护我们的状态码
 */
public enum RespCode {
    SUCCESS(0,"操作成功"),
    CANCEL(2,"取消订阅成功"),
    REGISTER_SUCCESS(100,"验证通过"),
    REGISTER_FAILED(101,"验证码不正确"),
    REGISTER_SEND(102,"验证码发送成功"),
    REGISTER_NOTS(103,"该邮箱已被注册"),
    CODE_EXPIRED(102,"验证码过期"),
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_FAILED(201,"账号密码错误"),
    PASSWORD_FAILED(202,"密码错误"),
    NOT_ENOUGH(111,"你的余额不足"),
    LINK_FAILED(404,"找不到你的链接"),
    BOUGHT(3,"已经操作过了"),
    WARN(-1,"操作失败");

    private int code;
    private String msg;
    RespCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
