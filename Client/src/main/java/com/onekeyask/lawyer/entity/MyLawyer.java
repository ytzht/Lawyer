package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/04 16:12
 */

public class MyLawyer {

    /**
     * code : 0
     * msg : 正常
     * data : {"hasMore":false,"lawyerList":[{"name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"lawyerOfficeName":"不知道","serviceCount":205,"serviceScore":63.5,"serviced":true,"lawyerId":3}]}
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
         * lawyerList : [{"name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"lawyerOfficeName":"不知道","serviceCount":205,"serviceScore":63.5,"serviced":true,"lawyerId":3}]
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
             * lawyerOfficeName : 不知道
             * serviceCount : 205
             * serviceScore : 63.5
             * serviced : true
             * lawyerId : 3
             */

            private String name;
            private String headURL;
            private String lawyerOfficeName;
            private int serviceCount;
            private double serviceScore;
            private boolean serviced;
            private int lawyerId;
            private List<String> special;

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
        }
    }
}
