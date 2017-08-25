package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/15 14:39
 */

public class ConversationList {


    /**
     * chatId : 366
     * userServiceId : 43
     * status : 0
     * fromType : 7
     * rounds : 0
     * conversationList : [{"conversationId":1793,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"啊啊啊啊阿，的孩子，了都没有用不上课时作业","isPicture":false,"time":"2017-08-03 17:11:41"}]
     * hasMore : false
     * evaStatus : false
     */

    private int chatId;
    private int userServiceId;
    private String status;
    private int fromType;
    private int rounds;
    private boolean hasMore;
    private boolean evaStatus;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(boolean evaStatus) {
        this.evaStatus = evaStatus;
    }

    public List<ConversationListBean> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationListBean> conversationList) {
        this.conversationList = conversationList;
    }

    public static class ConversationListBean {
        /**
         * conversationId : 1793
         * from : 0
         * userId : 2
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
         * name : 用户之家
         * content : 啊啊啊啊阿，的孩子，了都没有用不上课时作业
         * isPicture : false
         * time : 2017-08-03 17:11:41
         */

        private int conversationId;
        private int from;
        private int userId;
        private String headURL;
        private String name;
        private String content;
        private boolean isPicture;
        private String time;

        public ConversationListBean(int conversationId, int from, int userId, String headURL, String name, String content, boolean isPicture, String time) {
            this.conversationId = conversationId;
            this.from = from;
            this.userId = userId;
            this.headURL = headURL;
            this.name = name;
            this.content = content;
            this.isPicture = isPicture;
            this.time = time;
        }

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
    }
}
