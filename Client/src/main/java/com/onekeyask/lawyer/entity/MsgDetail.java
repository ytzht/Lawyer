package com.onekeyask.lawyer.entity;

/**
 * Created by ytzht on 2017/08/20 上午9:51
 */

public class MsgDetail {

    /**
     * code : 0
     * msg : 正常
     * message : {"messageId":323,"content":"您的积分增加了300，请到我的积分中查看。","status":1,"createTime":1502871323000}
     */

    private int code;
    private String msg;
    private MessageBean message;

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

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * messageId : 323
         * content : 您的积分增加了300，请到我的积分中查看。
         * status : 1
         * createTime : 1502871323000
         */

        private int messageId;
        private String content;
        private int status;
        private long createTime;

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
