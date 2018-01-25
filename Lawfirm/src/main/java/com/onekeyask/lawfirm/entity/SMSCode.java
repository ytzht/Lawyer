package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/16 15:31
 */

public class SMSCode {

    /**
     * code : 0
     * msg : 正常
     * data : {"codeId":26}
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
         * codeId : 26
         */

        private int codeId;

        public int getCodeId() {
            return codeId;
        }

        public void setCodeId(int codeId) {
            this.codeId = codeId;
        }
    }
}
