package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/09 11:24
 */

public class PraiseSupported {
    /**
     * code : 0
     * msg : 正常
     * data : {"supported":true}
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
         * supported : true
         */

        private boolean supported;

        public boolean isSupported() {
            return supported;
        }

        public void setSupported(boolean supported) {
            this.supported = supported;
        }
    }
}
