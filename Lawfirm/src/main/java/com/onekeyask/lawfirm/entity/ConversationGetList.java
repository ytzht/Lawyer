package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/15 21:56
 */

public class ConversationGetList {

    /**
     * chatId : 149
     * status : 2
     * rounds : 5
     * conversationList : [{"conversationId":592,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"家里考虑考虑来来来。陌陌","isPicture":false,"time":"2017-04-21 14:06:50"},{"conversationId":616,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1837","isPicture":true,"time":"2017-04-21 14:30:28"},{"conversationId":617,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1838","isPicture":true,"time":"2017-04-21 14:30:37"},{"conversationId":618,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"用户之家","content":"http://139.198.11.78:8080/mylawyer/pic/1839","isPicture":true,"time":"2017-04-21 14:30:49"},{"conversationId":659,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"律师之家","content":"http://139.198.11.78:8080/mylawyer/pic/1865","isPicture":true,"time":"2017-04-21 15:48:38"},{"conversationId":660,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"律师之家","content":"。？？","isPicture":false,"time":"2017-04-21 15:48:53"},{"conversationId":661,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"律师之家","content":"？？？？","isPicture":false,"time":"2017-04-21 15:48:58"},{"conversationId":668,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"律师之家","content":"http://139.198.11.78:8080/mylawyer/pic/1869","isPicture":true,"time":"2017-04-21 15:54:34"},{"conversationId":669,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","name":"律师之家","content":"？？？","isPicture":false,"time":"2017-04-21 15:54:40"}]
     * evaStatus : true
     * evaluation : {"comment":"评价一下","score":"非常满意","tagList":[{"tag":"非常敬业"},{"tag":"666"},{"tag":"大牛人啊"},{"tag":"输入的"}]}
     * hasMore : false
     * fromType : 3
     * order : {"orderId":389,"status":2}
     * freeask : {"freeaskId":985,"content":"家里考虑考虑来来来。陌陌","status":2}
     */

    private int chatId;
    private String status;
    private int rounds;
    private boolean evaStatus;
    private EvaluationBean evaluation;
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

    public EvaluationBean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationBean evaluation) {
        this.evaluation = evaluation;
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

    public static class EvaluationBean {
        /**
         * comment : 评价一下
         * score : 非常满意
         * tagList : [{"tag":"非常敬业"},{"tag":"666"},{"tag":"大牛人啊"},{"tag":"输入的"}]
         */

        private String comment;
        private String score;
        private List<TagListBean> tagList;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public static class TagListBean {
            /**
             * tag : 非常敬业
             */

            private String tag;

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }
        }
    }

    public static class OrderBean {
        /**
         * orderId : 389
         * status : 2
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
         * freeaskId : 985
         * content : 家里考虑考虑来来来。陌陌
         * status : 2
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
        public ConversationListBean(int conversationId, int from, int userId, String headURL, String name, String content, boolean isPicture, String time, int lawyerId) {
            this.conversationId = conversationId;
            this.from = from;
            this.userId = userId;
            this.headURL = headURL;
            this.name = name;
            this.content = content;
            this.isPicture = isPicture;
            this.time = time;
            this.lawyerId = lawyerId;
        }

        /**
         * conversationId : 592
         * from : 0
         * userId : 2
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
         * name : 用户之家
         * content : 家里考虑考虑来来来。陌陌
         * isPicture : false
         * time : 2017-04-21 14:06:50
         * lawyerId : 3
         */

        private int conversationId;
        private int from;
        private int userId;
        private String headURL;
        private String name;
        private String content;
        private boolean isPicture;
        private String time;
        private int lawyerId;

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
