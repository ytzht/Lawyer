package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/15 21:56
 */

public class ConversationGetList {


    /**
     * chatId : 368
     * userServiceId : 45
     * status : 2
     * fromType : 7
     * rounds : 5
     * conversationList : [{"conversationId":1854,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"111","isPicture":false,"time":"2017-08-04 13:23:16"},{"conversationId":1855,"from":0,"userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","name":"用户之家","content":"111","isPicture":false,"time":"2017-08-04 13:23:28"},{"conversationId":1856,"from":1,"lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","name":"律师之家","content":"111","isPicture":false,"time":"2017-08-04 13:23:33"}]
     * hasMore : false
     * evaStatus : true
     * evaluation : {"comment":"输入的评价","score":"非常满意","tagList":[{"tag":"态度非常好"},{"tag":"666"},{"tag":"测试"},{"tag":"输入的标签"}]}
     */

    private int chatId;
    private int userServiceId;
    private String userName;
    private String userHeadURL;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadURL() {
        return userHeadURL;
    }

    public void setUserHeadURL(String userHeadURL) {
        this.userHeadURL = userHeadURL;
    }

    private String status;
    private int fromType;
    private int rounds;
    private boolean hasMore;
    private boolean evaStatus;
    private String expireDate;
    private EvaluationBean evaluation;
    private List<ConversationListBean> conversationList;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

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

    public EvaluationBean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationBean evaluation) {
        this.evaluation = evaluation;
    }

    public List<ConversationListBean> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationListBean> conversationList) {
        this.conversationList = conversationList;
    }

    public static class EvaluationBean {
        /**
         * comment : 输入的评价
         * score : 非常满意
         * tagList : [{"tag":"态度非常好"},{"tag":"666"},{"tag":"测试"},{"tag":"输入的标签"}]
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
             * tag : 态度非常好
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

    public static class ConversationListBean {
        public ConversationListBean(int conversationId, int from, int lawyerId, String headURL, String name, String content, boolean isPicture, String time, int userId) {
            this.conversationId = conversationId;
            this.from = from;
            this.lawyerId = lawyerId;
            this.headURL = headURL;
            this.name = name;
            this.content = content;
            this.isPicture = isPicture;
            this.time = time;
            this.userId = userId;
        }

        /**

         * conversationId : 1854
         * from : 1
         * lawyerId : 3
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
         * name : 律师之家
         * content : 111
         * isPicture : false
         * time : 2017-08-04 13:23:16
         * userId : 2
         */

        private int conversationId;
        private int from;
        private int lawyerId;
        private String headURL;
        private String name;
        private String content;
        private boolean isPicture;
        private String time;
        private int userId;

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

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
