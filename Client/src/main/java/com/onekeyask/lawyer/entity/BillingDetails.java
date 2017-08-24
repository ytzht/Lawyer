package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/12 下午5:23
 */

public class BillingDetails {

    /**
     * code : 0
     * msg : 正常
     * data : {"balanceHistories":[{"id":274,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479385000},{"id":272,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479269000},{"id":270,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479107000},{"id":268,"userId":2,"tranType":"5","amount":0.2,"summary":"送心意","tranTime":1503478917000},{"id":255,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503474061000},{"id":252,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503309686000},{"id":250,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503309235000},{"id":249,"userId":2,"tranType":"2","amount":500,"summary":"私人律师","tranTime":1503303586000},{"id":245,"userId":2,"tranType":"2","amount":2,"summary":"打赏咨询","tranTime":1503298859000},{"id":243,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503298451000}],"hasMore":true}
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
         * balanceHistories : [{"id":274,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479385000},{"id":272,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479269000},{"id":270,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503479107000},{"id":268,"userId":2,"tranType":"5","amount":0.2,"summary":"送心意","tranTime":1503478917000},{"id":255,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503474061000},{"id":252,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503309686000},{"id":250,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503309235000},{"id":249,"userId":2,"tranType":"2","amount":500,"summary":"私人律师","tranTime":1503303586000},{"id":245,"userId":2,"tranType":"2","amount":2,"summary":"打赏咨询","tranTime":1503298859000},{"id":243,"userId":2,"tranType":"5","amount":2,"summary":"送心意","tranTime":1503298451000}]
         * hasMore : true
         */

        private boolean hasMore;
        private List<BalanceHistoriesBean> balanceHistories;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<BalanceHistoriesBean> getBalanceHistories() {
            return balanceHistories;
        }

        public void setBalanceHistories(List<BalanceHistoriesBean> balanceHistories) {
            this.balanceHistories = balanceHistories;
        }

        public static class BalanceHistoriesBean {
            /**
             * id : 274
             * userId : 2
             * tranType : 5
             * amount : 2.0
             * summary : 送心意
             * tranTime : 1503479385000
             */

            private int id;
            private int userId;
            private String tranType;
            private String amount;
            private String summary;
            private String withDrawInfoId;

            public String getWithDrawInfoId() {
                return withDrawInfoId;
            }

            public void setWithDrawInfoId(String withDrawInfoId) {
                this.withDrawInfoId = withDrawInfoId;
            }

            private long tranTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
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
