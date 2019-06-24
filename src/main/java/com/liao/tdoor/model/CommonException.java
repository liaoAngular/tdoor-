package com.liao.tdoor.model;

/**
 * 自定义异常
 */
public class CommonException extends RuntimeException{
    private int code;
    private String msg;
    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
