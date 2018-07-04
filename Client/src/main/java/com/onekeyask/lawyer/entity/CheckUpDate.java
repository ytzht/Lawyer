package com.onekeyask.lawyer.entity;

/**
 * Created by ytzht on 2018/03/27 上午8:50
 */

public class CheckUpDate {

    /**
     * code : 0
     * msg : 正常
     * eventId : 1
     * data : {"UpdateUrl":"http://www.zhimalawyer.com/download/client_latest.apk","Version":"1.2.3"}
     */

    private int code;
    private String msg;
    private String eventId;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * UpdateUrl : http://www.zhimalawyer.com/download/client_latest.apk
         * Version : 1.2.3
         */

        private String UpdateUrl;
        private String Version;

        public String getUpdateUrl() {
            return UpdateUrl;
        }

        public void setUpdateUrl(String UpdateUrl) {
            this.UpdateUrl = UpdateUrl;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }
    }
}
