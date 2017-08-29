package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/12 下午5:23
 */

public class BillingDetails {

    /**
     * code : 0
     * msg : 正常
     * data : {"hasMore":false,"withdrawHistories":[{"id":15,"lawyerId":3,"cardNum":"6225768759511619","bankName":"招商银行","applyName":"杨国艺","money":1865.58,"status":1,"createTime":1503579158000,"lawyer":{"phoneNo":"15210862987","name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","userType":"1","lawyerId":3},"userType":0,"userId":3}]}
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
         * hasMore : false
         * withdrawHistories : [{"id":15,"lawyerId":3,"cardNum":"6225768759511619","bankName":"招商银行","applyName":"杨国艺","money":1865.58,"status":1,"createTime":1503579158000,"lawyer":{"phoneNo":"15210862987","name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","userType":"1","lawyerId":3},"userType":0,"userId":3}]
         */

        private boolean hasMore;
        private List<WithdrawHistoriesBean> withdrawHistories;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<WithdrawHistoriesBean> getWithdrawHistories() {
            return withdrawHistories;
        }

        public void setWithdrawHistories(List<WithdrawHistoriesBean> withdrawHistories) {
            this.withdrawHistories = withdrawHistories;
        }

        public static class WithdrawHistoriesBean {
            /**
             * id : 15
             * lawyerId : 3
             * cardNum : 6225768759511619
             * bankName : 招商银行
             * applyName : 杨国艺
             * money : 1865.58
             * status : 1
             * createTime : 1503579158000
             * lawyer : {"phoneNo":"15210862987","name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","userType":"1","lawyerId":3}
             * userType : 0
             * userId : 3
             */

            private int id;
            private int lawyerId;
            private String cardNum;
            private String bankName;
            private String applyName;
            private double money;
            private int status;
            private long createTime;
            private LawyerBean lawyer;
            private int userType;
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

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
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

            public LawyerBean getLawyer() {
                return lawyer;
            }

            public void setLawyer(LawyerBean lawyer) {
                this.lawyer = lawyer;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public static class LawyerBean {
                /**
                 * phoneNo : 15210862987
                 * name : 律师之家
                 * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
                 * userType : 1
                 * lawyerId : 3
                 */

                private String phoneNo;
                private String name;
                private String headURL;
                private String userType;
                private int lawyerId;

                public String getPhoneNo() {
                    return phoneNo;
                }

                public void setPhoneNo(String phoneNo) {
                    this.phoneNo = phoneNo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHeadURL() {
                    return headURL;
                }

                public void setHeadURL(String headURL) {
                    this.headURL = headURL;
                }

                public String getUserType() {
                    return userType;
                }

                public void setUserType(String userType) {
                    this.userType = userType;
                }

                public int getLawyerId() {
                    return lawyerId;
                }

                public void setLawyerId(int lawyerId) {
                    this.lawyerId = lawyerId;
                }
            }
        }
    }
}
