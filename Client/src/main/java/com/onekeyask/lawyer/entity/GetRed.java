package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/24 16:05
 */

public class GetRed {
    /**
     * code : 0
     * msg : 正常
     * data : {"chatIds":[413,416],"messageIds":[425,438,439,460,464,465,467,468,470,471,473,474],"userServiceInfoIds":[468,470,471,473,474]}
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
        private List<Integer> chatIds;
        private List<Integer> messageIds;
        private List<Integer> userServiceInfoIds;

        public List<Integer> getChatIds() {
            return chatIds;
        }

        public void setChatIds(List<Integer> chatIds) {
            this.chatIds = chatIds;
        }

        public List<Integer> getMessageIds() {
            return messageIds;
        }

        public void setMessageIds(List<Integer> messageIds) {
            this.messageIds = messageIds;
        }

        public List<Integer> getUserServiceInfoIds() {
            return userServiceInfoIds;
        }

        public void setUserServiceInfoIds(List<Integer> userServiceInfoIds) {
            this.userServiceInfoIds = userServiceInfoIds;
        }
    }
}
