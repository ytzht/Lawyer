package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/11/07 下午10:39
 */

public class CallDetail {

    /**
     * code : 0
     * msg : 正常
     * eventid :
     * data : {"userServiceId":32,"userId":100,"userName":"152*******6","headURL":"http://139.198.11.78:8080/mylawyer/pic/201?r=1506738650","callStatus":4,"expireTime":"2017-11-07 21:56:04","orderTime":"2017-11-07 21:26:04","histories":[{"startTime":"","duration":""}],"evaStatus":false}
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
         * userServiceId : 32
         * userId : 100
         * userName : 152*******6
         * headURL : http://139.198.11.78:8080/mylawyer/pic/201?r=1506738650
         * callStatus : 4
         * expireTime : 2017-11-07 21:56:04
         * orderTime : 2017-11-07 21:26:04
         * histories : [{"startTime":"","duration":""}]
         * evaStatus : false
         */

        private int userServiceId;
        private int userId;
        private String userName;
        private String headURL;
        private int callStatus;
        private String expireTime;
        private String orderTime;
        private boolean evaStatus;
        private List<HistoriesBean> histories;
        private EvaluationBean evaluation;

        public EvaluationBean getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(EvaluationBean evaluation) {
            this.evaluation = evaluation;
        }

        public int getUserServiceId() {
            return userServiceId;
        }

        public void setUserServiceId(int userServiceId) {
            this.userServiceId = userServiceId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadURL() {
            return headURL;
        }

        public void setHeadURL(String headURL) {
            this.headURL = headURL;
        }

        public int getCallStatus() {
            return callStatus;
        }

        public void setCallStatus(int callStatus) {
            this.callStatus = callStatus;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public boolean isEvaStatus() {
            return evaStatus;
        }

        public void setEvaStatus(boolean evaStatus) {
            this.evaStatus = evaStatus;
        }

        public List<HistoriesBean> getHistories() {
            return histories;
        }

        public void setHistories(List<HistoriesBean> histories) {
            this.histories = histories;
        }

        public static class HistoriesBean {
            /**
             * startTime :
             * duration :
             */

            private String startTime;
            private String duration;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }
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

    }
}
