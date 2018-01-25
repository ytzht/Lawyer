package com.onekeyask.lawyer.http;

/**
 * Created by zht on 2017/04/19 17:13
 */

public class APIException extends RuntimeException{
    public int code;
    public String message;

    public APIException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}