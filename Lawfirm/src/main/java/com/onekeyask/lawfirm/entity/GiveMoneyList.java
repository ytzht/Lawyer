package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/08 15:21
 */

public class GiveMoneyList {
    /**
     * code : 0
     * msg : 正常
     * data : {"giveMoney":[{"userId":2,"phoneNo":"1521086***6","type":1,"money":2,"summary":"看看。","giveTime":1501911509000},{"userId":2,"phoneNo":"1521086***6","type":1,"money":2,"summary":"(null)","giveTime":1501826422000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"","giveTime":1501825355000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":4,"summary":"我吃得多111","giveTime":1501574665000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841416000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841330000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841188000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":2,"summary":"","giveTime":1492660623000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"是是是","giveTime":1492568740000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多","giveTime":1492568268000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多"},{"userId":2,"phoneNo":"1521086***6","type":1,"money":200,"summary":"态度很好，回答很清楚。"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"态度很好，回答很清楚。"}],"hasMore":false}
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
        /**
         * giveMoney : [{"userId":2,"phoneNo":"1521086***6","type":1,"money":2,"summary":"看看。","giveTime":1501911509000},{"userId":2,"phoneNo":"1521086***6","type":1,"money":2,"summary":"(null)","giveTime":1501826422000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"","giveTime":1501825355000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":4,"summary":"我吃得多111","giveTime":1501574665000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841416000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841330000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":0.02,"summary":"","giveTime":1492841188000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":2,"summary":"","giveTime":1492660623000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"是是是","giveTime":1492568740000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多","giveTime":1492568268000},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":1,"summary":"111"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"我吃得多"},{"userId":2,"phoneNo":"1521086***6","type":1,"money":200,"summary":"态度很好，回答很清楚。"},{"userId":2,"phoneNo":"1521086***6","type":2,"money":200,"summary":"态度很好，回答很清楚。"}]
         * hasMore : false
         */

        private boolean hasMore;
        private List<GiveMoneyBean> giveMoney;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<GiveMoneyBean> getGiveMoney() {
            return giveMoney;
        }

        public void setGiveMoney(List<GiveMoneyBean> giveMoney) {
            this.giveMoney = giveMoney;
        }

        public static class GiveMoneyBean {
            /**
             * userId : 2
             * phoneNo : 1521086***6
             * type : 1
             * money : 2
             * summary : 看看。
             * giveTime : 1501911509000
             */

            private int userId;
            private String phoneNo;
            private int type;
            private String money;
            private String summary;
            private long giveTime;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public long getGiveTime() {
                return giveTime;
            }

            public void setGiveTime(long giveTime) {
                this.giveTime = giveTime;
            }
        }
    }
}
