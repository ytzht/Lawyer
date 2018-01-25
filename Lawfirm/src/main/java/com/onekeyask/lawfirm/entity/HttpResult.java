package com.onekeyask.lawfirm.entity;


import com.onekeyask.lawfirm.global.Constant;

/**
 * Created by zht on 2017/04/19 17:06
 */

public class HttpResult<T> {

    public int code;
    public String desc;
    public T content;


    public boolean isSuccess() {
        return code == Constant.SUCCESS;
    }

    public boolean isEmpty() {
        return code == Constant.EMPTY;
    }

    public boolean isNoMore() {
        return code == Constant.NOMORE;
    }
}