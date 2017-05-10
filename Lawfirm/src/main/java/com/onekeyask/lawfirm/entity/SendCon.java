package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/04/19 11:46
 */

public class SendCon {

    /**
     * err : {"code":0,"msg":"正常","eventid":"xxxx-xxxx-xxxx"}
     * conversation : {"conversationId":1,"from":1,"userId":1111,"lawyerId":1,"name":"李四","headURL":"http://www.microdreams.net:88/mylawyer-demo/c/pic/301","content":"好的，请说","isPicture":false,"time":"2015-06-29 17:22:11"}
     */

    private ErrBean err;
    private ConversationBean conversation;

    public ErrBean getErr() {
        return err;
    }

    public void setErr(ErrBean err) {
        this.err = err;
    }

    public ConversationBean getConversation() {
        return conversation;
    }

    public void setConversation(ConversationBean conversation) {
        this.conversation = conversation;
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

    public static class ConversationBean {
        /**
         * conversationId : 1
         * from : 1
         * userId : 1111
         * lawyerId : 1
         * name : 李四
         * headURL : http://www.microdreams.net:88/mylawyer-demo/c/pic/301
         * content : 好的，请说
         * isPicture : false
         * time : 2015-06-29 17:22:11
         */

        private int conversationId;
        private int from;
        private int userId;
        private int lawyerId;
        private String name;
        private String headURL;
        private String content;
        private boolean isPicture;
        private String time;

        public int getConversationId() {
            return conversationId;
        }

        public void setConversationId(int conversationId) {
            this.conversationId = conversationId;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadURL() {
            return headURL;
        }

        public void setHeadURL(String headURL) {
            this.headURL = headURL;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isIsPicture() {
            return isPicture;
        }

        public void setIsPicture(boolean isPicture) {
            this.isPicture = isPicture;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
