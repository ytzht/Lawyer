package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/29 16:24
 */

public class IncomeDetail {
    /**
     * code : 0
     * msg : 正常
     * data : {"totalMoney":273.52,"incomeList":[{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 11:47:18 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 10:43:52 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 10:01:59 CST 2017"},{"serviceName":"送心意","amount":0.1,"time":"Thu Aug 24 09:15:55 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:24:58 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:09:45 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:07:49 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:05:07 CST 2017"},{"serviceName":"送心意","amount":0.2,"time":"Wed Aug 23 17:01:57 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 16:59:49 CST 2017"}],"hasMore":true}
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
         * totalMoney : 273.52
         * incomeList : [{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 11:47:18 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 10:43:52 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Thu Aug 24 10:01:59 CST 2017"},{"serviceName":"送心意","amount":0.1,"time":"Thu Aug 24 09:15:55 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:24:58 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:09:45 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:07:49 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 17:05:07 CST 2017"},{"serviceName":"送心意","amount":0.2,"time":"Wed Aug 23 17:01:57 CST 2017"},{"serviceName":"送心意","amount":2,"time":"Wed Aug 23 16:59:49 CST 2017"}]
         * hasMore : true
         */

        private double totalMoney;
        private boolean hasMore;
        private List<IncomeListBean> incomeList;

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<IncomeListBean> getIncomeList() {
            return incomeList;
        }

        public void setIncomeList(List<IncomeListBean> incomeList) {
            this.incomeList = incomeList;
        }

        public static class IncomeListBean {
            /**
             * serviceName : 送心意
             * amount : 2
             * time : Thu Aug 24 11:47:18 CST 2017
             */

            private String serviceName;
            private int amount;
            private String time;

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
