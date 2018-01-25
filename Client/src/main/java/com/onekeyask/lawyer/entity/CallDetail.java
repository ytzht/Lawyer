package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/11/06 下午8:12
 */

public class CallDetail {
    /**
     * code : 0
     * msg : 正常
     * eventid :
     * data : {"userServiceId":22,"lawyerId":114,"callStatus":1,"expireTime":"2017-11-06 20:38:55","orderTime":"2017-11-06 20:08:55","histories":[{"startTime":"","duration":""}],"evaStatus":false}
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
         * userServiceId : 22
         * lawyerId : 114
         * callStatus : 1
         * expireTime : 2017-11-06 20:38:55
         * orderTime : 2017-11-06 20:08:55
         * histories : [{"startTime":"","duration":""}]
         * evaStatus : false
         */

        private int userServiceId;
        private int lawyerId;
        private int callStatus;
        private String expireTime;
        private String orderTime;
        private boolean evaStatus;
        private List<HistoriesBean> histories;

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
    }
}
