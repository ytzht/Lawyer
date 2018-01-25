package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/04/19 14:46
 */

public class BaseCallModel<T> {
    public int errno;
    public String msg;
    public T data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
