package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/09 15:12
 */

public class TalkingConversationList {
    /**
     * code : 0
     * msg : 正常
     * eventid :
     * data : {"chatId":384,"userServiceId":61,"lawyerId":3,"conversationList":[{"conversationId":1886,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","isPicture":false,"time":"2017-08-04 17:28:36"},{"conversationId":1887,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？？","isPicture":false,"time":"2017-08-04 23:45:13"},{"conversationId":1888,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:16"},{"conversationId":1889,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:28"},{"conversationId":1890,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:35"},{"conversationId":1891,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-05 11:57:52"},{"conversationId":1892,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"http://139.198.11.78:8080/mylawyer/pic/2203","isPicture":true,"time":"2017-08-05 11:58:16"},{"conversationId":1895,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-05 12:08:38"}],"hasMore":false}
     */

    private int code;
    private String msg;
    private String eventid;
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

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * chatId : 384
         * userServiceId : 61
         * lawyerId : 3
         * conversationList : [{"conversationId":1886,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","isPicture":false,"time":"2017-08-04 17:28:36"},{"conversationId":1887,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？？","isPicture":false,"time":"2017-08-04 23:45:13"},{"conversationId":1888,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:16"},{"conversationId":1889,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:28"},{"conversationId":1890,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"？？","isPicture":false,"time":"2017-08-04 23:45:35"},{"conversationId":1891,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-05 11:57:52"},{"conversationId":1892,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"http://139.198.11.78:8080/mylawyer/pic/2203","isPicture":true,"time":"2017-08-05 11:58:16"},{"conversationId":1895,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"？？","isPicture":false,"time":"2017-08-05 12:08:38"}]
         * hasMore : false
         */

        private int chatId;
        private int userServiceId;
        private int lawyerId;
        private boolean hasMore;
        private List<ConversationListBean> conversationList;

        public int getChatId() {
            return chatId;
        }

        public void setChatId(int chatId) {
            this.chatId = chatId;
        }

        public int getUserServiceId() {
            return userServiceId;
        }

        public void setUserServiceId(int userServiceId) {
            this.userServiceId = userServiceId;
        }

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<ConversationListBean> getConversationList() {
            return conversationList;
        }

        public void setConversationList(List<ConversationListBean> conversationList) {
            this.conversationList = conversationList;
        }

        public static class ConversationListBean {
            /**
             * conversationId : 1886
             * from : 0
             * userId : 2
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
             * name : 用户之家
             * content : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
             * isPicture : false
             * time : 2017-08-04 17:28:36
             * lawyerId : 3
             */

            private int conversationId;//
            private int from;//
            private int userId;//
            private String headURL;//
            private String name;//
            private String content;//
            private boolean isPicture;//
            private String time;//
            private int lawyerId;//

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

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }
        }
    }
}
