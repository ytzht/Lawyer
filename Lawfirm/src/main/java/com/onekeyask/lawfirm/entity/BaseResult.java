package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/04/15 14:33
 */

public class BaseResult {
    /**
     * code : 0
     * msg : 正常
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
