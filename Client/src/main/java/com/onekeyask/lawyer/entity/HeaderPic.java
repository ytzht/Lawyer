package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/08/16 17:41
 */

public class HeaderPic {

    /**
     * code : 0
     * msg : 正常
     * data : {"headUrl":"http://139.198.11.78:8080/mylawyer/pic/2242"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * headUrl : http://139.198.11.78:8080/mylawyer/pic/2242
         */

        private String headUrl;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }
    }
}
