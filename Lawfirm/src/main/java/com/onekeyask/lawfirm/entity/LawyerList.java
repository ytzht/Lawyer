package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/09 16:17
 */

public class LawyerList {
    /**
     * code : 0
     * msg : 正常
     * data : {"hasMore":false,"lawyerList":[{"name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"tags":["回复很及时","非常敬业","回答很专业","态度非常好","666"],"lawyerOfficeName":"京都律师事务所","serviceYears":1,"serviceCount":207,"serviceScore":63.2,"textPrice":20,"telPrice":5,"favoriteNum":1,"serviced":true,"lawyerId":3}]}
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
         * lawyerList : [{"name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"tags":["回复很及时","非常敬业","回答很专业","态度非常好","666"],"lawyerOfficeName":"京都律师事务所","serviceYears":1,"serviceCount":207,"serviceScore":63.2,"textPrice":20,"telPrice":5,"favoriteNum":1,"serviced":true,"lawyerId":3}]
         */

        private boolean hasMore;
        private List<LawyerListBean> lawyerList;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<LawyerListBean> getLawyerList() {
            return lawyerList;
        }

        public void setLawyerList(List<LawyerListBean> lawyerList) {
            this.lawyerList = lawyerList;
        }

        public static class LawyerListBean {
            /**
             * name : 律师之家
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
             * special : ["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]
             * tags : ["回复很及时","非常敬业","回答很专业","态度非常好","666"]
             * lawyerOfficeName : 京都律师事务所
             * serviceYears : 1
             * serviceCount : 207
             * serviceScore : 63.2
             * textPrice : 20
             * telPrice : 5
             * favoriteNum : 1
             * serviced : true
             * lawyerId : 3
             */

            private String name;
            private String headURL;
            private String lawyerOfficeName;
            private int serviceYears;
            private int serviceCount;
            private double serviceScore;
            private int textPrice;
            private int telPrice;
            private int favoriteNum;
            private boolean serviced;
            private int lawyerId;
            private List<String> special;
            private List<String> tags;

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

            public String getLawyerOfficeName() {
                return lawyerOfficeName;
            }

            public void setLawyerOfficeName(String lawyerOfficeName) {
                this.lawyerOfficeName = lawyerOfficeName;
            }

            public int getServiceYears() {
                return serviceYears;
            }

            public void setServiceYears(int serviceYears) {
                this.serviceYears = serviceYears;
            }

            public int getServiceCount() {
                return serviceCount;
            }

            public void setServiceCount(int serviceCount) {
                this.serviceCount = serviceCount;
            }

            public double getServiceScore() {
                return serviceScore;
            }

            public void setServiceScore(double serviceScore) {
                this.serviceScore = serviceScore;
            }

            public int getTextPrice() {
                return textPrice;
            }

            public void setTextPrice(int textPrice) {
                this.textPrice = textPrice;
            }

            public int getTelPrice() {
                return telPrice;
            }

            public void setTelPrice(int telPrice) {
                this.telPrice = telPrice;
            }

            public int getFavoriteNum() {
                return favoriteNum;
            }

            public void setFavoriteNum(int favoriteNum) {
                this.favoriteNum = favoriteNum;
            }

            public boolean isServiced() {
                return serviced;
            }

            public void setServiced(boolean serviced) {
                this.serviced = serviced;
            }

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }

            public List<String> getSpecial() {
                return special;
            }

            public void setSpecial(List<String> special) {
                this.special = special;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }
}
