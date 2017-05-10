package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/15 14:39
 */

public class ConversationList {

    /**
     * chatId : 147
     * status : 1
     * rounds : 4
     * conversationList : [{"conversationId":613,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1834","isPicture":true,"time":"2017-04-21 14:23:14"},{"conversationId":614,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1835","isPicture":true,"time":"2017-04-21 14:23:22"},{"conversationId":615,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1836","isPicture":true,"time":"2017-04-21 14:23:52"}]
     * evaStatus : false
     * hasMore : false
     * fromType : 3
     * order : {"orderId":387,"status":1}
     * freeask : {"freeaskId":983,"content":"绿地率达百分之百逼的太","status":1}
     */

    private int chatId;
    private String status;
    private int rounds;
    private boolean evaStatus;
    private boolean hasMore;
    private int fromType;
    private OrderBean order;
    private FreeaskBean freeask;
    private List<ConversationListBean> conversationList;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(boolean evaStatus) {
        this.evaStatus = evaStatus;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public FreeaskBean getFreeask() {
        return freeask;
    }

    public void setFreeask(FreeaskBean freeask) {
        this.freeask = freeask;
    }

    public List<ConversationListBean> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationListBean> conversationList) {
        this.conversationList = conversationList;
    }

    public static class OrderBean {
        /**
         * orderId : 387
         * status : 1
         */

        private int orderId;
        private int status;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class FreeaskBean {
        /**
         * freeaskId : 983
         * content : 绿地率达百分之百逼的太
         * status : 1
         */

        private int freeaskId;
        private String content;
        private int status;

        public int getFreeaskId() {
            return freeaskId;
        }

        public void setFreeaskId(int freeaskId) {
            this.freeaskId = freeaskId;
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
    }

    public static class ConversationListBean {

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

        /**
         * conversationId : 613
         * from : 0
         * userId : 2
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
         * name : 用户之家
         * content : http://139.198.11.78:8080/mylawyer/pic/1834
         * isPicture : true
         * time : 2017-04-21 14:23:14
         */


        private int conversationId;
        private int from;
        private int userId;
        private String headURL;
        private String name;
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
