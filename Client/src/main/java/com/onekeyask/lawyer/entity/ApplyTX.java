package com.onekeyask.lawyer.entity;

/**
 * Created by ytzht on 2017/08/12 下午8:26
 */

public class ApplyTX {
    /**
     * code : 0
     * msg : 正常
     * data : {"progressInfo":{"id":1,"lawyerId":2,"cardNum":"***1094","bankName":"建设银行","applyName":"张三","money":50,"status":1,"createTime":1502330387000,"userId":2}}
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
         * progressInfo : {"id":1,"lawyerId":2,"cardNum":"***1094","bankName":"建设银行","applyName":"张三","money":50,"status":1,"createTime":1502330387000,"userId":2}
         */

        private ProgressInfoBean progressInfo;

        public ProgressInfoBean getProgressInfo() {
            return progressInfo;
        }

        public void setProgressInfo(ProgressInfoBean progressInfo) {
            this.progressInfo = progressInfo;
        }

        public static class ProgressInfoBean {
            /**
             * id : 1
             * lawyerId : 2
             * cardNum : ***1094
             * bankName : 建设银行
             * applyName : 张三
             * money : 50
             * status : 1
             * createTime : 1502330387000
             * userId : 2
             */

            private int id;
            private int lawyerId;
            private String cardNum;
            private String bankName;
            private String applyName;
            private int money;
            private int status;
            private long createTime;
            private int userId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }

            public String getCardNum() {
                return cardNum;
            }

            public void setCardNum(String cardNum) {
                this.cardNum = cardNum;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getApplyName() {
                return applyName;
            }

            public void setApplyName(String applyName) {
                this.applyName = applyName;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
