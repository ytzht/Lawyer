package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/09/21 15:33
 */

public class PersonalInfo {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"HeadURL":"http://139.198.13.26:80/mylawyer/pic/1662?r=1506150986","OfficeName":"安卓测试律所","Sex":1,"city":"山东省","district":"烟台市","favoriteNum":2,"giveMoneyCount":13,"introduce":"123669555588回来咯土","lawyerName":"安卓测试","serviceCount":31,"serviceScore":100}
     */

    private int code;
    private String msg;
    private String eventId;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * HeadURL : http://139.198.13.26:80/mylawyer/pic/1662?r=1506150986
         * OfficeName : 安卓测试律所
         * Sex : 1
         * city : 山东省
         * district : 烟台市
         * favoriteNum : 2
         * giveMoneyCount : 13
         * introduce : 123669555588回来咯土
         * lawyerName : 安卓测试
         * serviceCount : 31
         * serviceScore : 100
         */

        private String HeadURL;
        private String OfficeName;
        private int Sex;
        private String city;
        private String district;
        private int favoriteNum;
        private int giveMoneyCount;
        private String introduce;
        private String lawyerName;
        private String serviceCount;
        private String serviceScore;

        public String getHeadURL() {
            return HeadURL;
        }

        public void setHeadURL(String HeadURL) {
            this.HeadURL = HeadURL;
        }

        public String getOfficeName() {
            return OfficeName;
        }

        public void setOfficeName(String OfficeName) {
            this.OfficeName = OfficeName;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public int getGiveMoneyCount() {
            return giveMoneyCount;
        }

        public void setGiveMoneyCount(int giveMoneyCount) {
            this.giveMoneyCount = giveMoneyCount;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getLawyerName() {
            return lawyerName;
        }

        public void setLawyerName(String lawyerName) {
            this.lawyerName = lawyerName;
        }

        public String getServiceCount() {
            return serviceCount;
        }

        public void setServiceCount(String serviceCount) {
            this.serviceCount = serviceCount;
        }

        public String getServiceScore() {
            return serviceScore;
        }

        public void setServiceScore(String serviceScore) {
            this.serviceScore = serviceScore;
        }
    }
}
