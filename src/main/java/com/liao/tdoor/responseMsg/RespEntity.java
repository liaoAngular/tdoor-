package com.liao.tdoor.responseMsg;

import java.util.Date;

public class RespEntity {
    private int code;
    private String msg;
    private Object data;
    private String token;
    private int sign;
    private Date signTime;
    public RespEntity(RespCode respCode){
        this.code=respCode.getCode();
        this.msg=respCode.getMsg();
    }

    public RespEntity(){

    }

    /**
     * 序列化参数
     * @param respCode 状态码
     * @param token 登录成功返回的token
     * @param userData 登录成功返回的用户信息
     * @param sign 是否今天签到
     * @param signTime 签到记录时间
     */
    public RespEntity(RespCode respCode,String token,Object userData,int sign,Date signTime){
        this(respCode);
        this.token=token;
        this.data=userData;
        this.sign=sign;
        this.signTime=signTime;
    }
    public RespEntity(int sign,Object userData){
        this.sign=sign;
        this.data=userData;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    @Override
    public String toString() {
        return "RespEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                ", sign=" + sign +
                ", signTime=" + signTime +
                '}';
    }
}
