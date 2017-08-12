package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/12 下午4:55
 */

public class PointHistory {
    /**
     * code : 0
     * msg : 正常
     * data : {"hasMore":true,"pointsHistories":[{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502434702000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502434679000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431766000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431553000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431478000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431161000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430976000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430876000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430131000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502429926000}]}
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
         * hasMore : true
         * pointsHistories : [{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502434702000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502434679000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431766000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431553000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431478000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502431161000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430976000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430876000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502430131000},{"userId":2,"tranType":"7","number":25,"summary":"免费问","tranTime":1502429926000}]
         */

        private boolean hasMore;
        private List<PointsHistoriesBean> pointsHistories;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<PointsHistoriesBean> getPointsHistories() {
            return pointsHistories;
        }

        public void setPointsHistories(List<PointsHistoriesBean> pointsHistories) {
            this.pointsHistories = pointsHistories;
        }

        public static class PointsHistoriesBean {
            /**
             * userId : 2
             * tranType : 7
             * number : 25
             * summary : 免费问
             * tranTime : 1502434702000
             */

            private int userId;
            private String tranType;
            private int number;
            private String summary;
            private long tranTime;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getTranType() {
                return tranType;
            }

            public void setTranType(String tranType) {
                this.tranType = tranType;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public long getTranTime() {
                return tranTime;
            }

            public void setTranTime(long tranTime) {
                this.tranTime = tranTime;
            }
        }
    }
}
