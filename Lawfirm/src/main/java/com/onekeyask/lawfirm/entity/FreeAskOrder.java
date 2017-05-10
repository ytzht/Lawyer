package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/4/16 11:29
 */

public class FreeAskOrder {

    /**
     * err : {"code":0,"msg":"正常","eventid":"xxxx-xxxx-xxxx"}
     * chatId : 1
     */

    private ErrBean err;
    private int chatId;

    public ErrBean getErr() {
        return err;
    }

    public void setErr(ErrBean err) {
        this.err = err;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public static class ErrBean {
        /**
         * code : 0
         * msg : 正常
         * eventid : xxxx-xxxx-xxxx
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
