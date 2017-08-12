package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/12 下午5:23
 */

public class BillingDetails {

    /**
     * code : 0
     * msg : 正常
     * data : {"balanceHistories":[{"id":226,"userId":2,"tranType":"3","amount":1,"withDrawInfoId":8,"summary":"提现","tranTime":1502441966000},{"id":224,"userId":2,"tranType":"2","amount":2,"summary":"送心意","tranTime":1502433545000},{"id":222,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":7,"summary":"提现","tranTime":1502366457000},{"id":221,"userId":2,"tranType":"3","amount":55,"withDrawInfoId":6,"summary":"提现","tranTime":1502366326000},{"id":220,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":5,"summary":"提现","tranTime":1502366185000},{"id":219,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":4,"summary":"提现","tranTime":1502365841000},{"id":218,"userId":2,"tranType":"3","amount":15,"withDrawInfoId":3,"summary":"提现","tranTime":1502355999000},{"id":217,"userId":2,"tranType":"3","amount":50,"withDrawInfoId":2,"summary":"提现","tranTime":1502330557000},{"id":216,"userId":2,"tranType":"3","amount":50,"withDrawInfoId":1,"summary":"提现","tranTime":1502330316000},{"id":215,"userId":2,"tranType":"2","amount":500,"summary":"私人律师","tranTime":1502026535000}],"hasMore":true}
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
         * balanceHistories : [{"id":226,"userId":2,"tranType":"3","amount":1,"withDrawInfoId":8,"summary":"提现","tranTime":1502441966000},{"id":224,"userId":2,"tranType":"2","amount":2,"summary":"送心意","tranTime":1502433545000},{"id":222,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":7,"summary":"提现","tranTime":1502366457000},{"id":221,"userId":2,"tranType":"3","amount":55,"withDrawInfoId":6,"summary":"提现","tranTime":1502366326000},{"id":220,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":5,"summary":"提现","tranTime":1502366185000},{"id":219,"userId":2,"tranType":"3","amount":5,"withDrawInfoId":4,"summary":"提现","tranTime":1502365841000},{"id":218,"userId":2,"tranType":"3","amount":15,"withDrawInfoId":3,"summary":"提现","tranTime":1502355999000},{"id":217,"userId":2,"tranType":"3","amount":50,"withDrawInfoId":2,"summary":"提现","tranTime":1502330557000},{"id":216,"userId":2,"tranType":"3","amount":50,"withDrawInfoId":1,"summary":"提现","tranTime":1502330316000},{"id":215,"userId":2,"tranType":"2","amount":500,"summary":"私人律师","tranTime":1502026535000}]
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
             * id : 226
             * userId : 2
             * tranType : 3
             * amount : 1
             * withDrawInfoId : 8
             * summary : 提现
             * tranTime : 1502441966000
             */

            private int id;
            private int userId;
            private String tranType;
            private int amount;
            private int withDrawInfoId;
            private String summary;
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

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getWithDrawInfoId() {
                return withDrawInfoId;
            }

            public void setWithDrawInfoId(int withDrawInfoId) {
                this.withDrawInfoId = withDrawInfoId;
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
