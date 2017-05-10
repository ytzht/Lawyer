package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/04/15 14:33
 */

public class BaseResult {
    /**
     * err : {"code":-130,"msg":"找不到对话信息","eventid":""}
     */

    private ErrBean err;

    public ErrBean getErr() {
        return err;
    }

    public void setErr(ErrBean err) {
        this.err = err;
    }

    public static class ErrBean {
        /**
         * code : -130
         * msg : 找不到对话信息
         * eventid :
         */

        private int code;
        private String msg;
        private String eventid;

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

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }
    }
}
