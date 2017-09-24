package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/15 20:47
 */

public class HomePage {


    /**
     * lawyer : {"name":"安卓测试","lawyerId":238,"headURL":"http://139.198.13.26:80/mylawyer/pic/1662?r=1505886328","status":0,"serviceCount":31,"favoriteNum":2,"serviceScore":100,"notes":"123669555588回来咯土著","city":"山东省","district":"烟台市","phoneNo":"13280933117","sex":1}
     * adList : [{"adId":"33","picURL":"http://139.198.13.26:80/mylawyer/pic/1660","innerPicUrl":"http://139.198.13.26:80/mylawyer/pic/1660","content":"律师端广告"}]
     * serviceList : [{"serviceName":"图文咨询","serviceType":2,"isOn":true},{"serviceName":"电话咨询","serviceType":3,"isOn":true},{"serviceName":"私人律师","serviceType":1,"isOn":true}]
     * monthIncome : 0
     * balance : 11187.51
     * incomeSum : [{"serviceName":"快速咨询","amountSum":259.51},{"serviceName":"图文咨询","amountSum":449},{"serviceName":"电话咨询","amountSum":0},{"serviceName":"私人律师","amountSum":500},{"serviceName":"送心意","amountSum":32}]
     */

    private LawyerBean lawyer;
    private int monthIncome;
    private double balance;
    private List<AdListBean> adList;
    private List<ServiceListBean> serviceList;
    private List<IncomeSumBean> incomeSum;

    public LawyerBean getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerBean lawyer) {
        this.lawyer = lawyer;
    }

    public int getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(int monthIncome) {
        this.monthIncome = monthIncome;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
    }

    public List<IncomeSumBean> getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(List<IncomeSumBean> incomeSum) {
        this.incomeSum = incomeSum;
    }

    public static class LawyerBean {
        /**
         * name : 安卓测试
         * lawyerId : 238
         * headURL : http://139.198.13.26:80/mylawyer/pic/1662?r=1505886328
         * status : 0
         * serviceCount : 31
         * favoriteNum : 2
         * serviceScore : 100
         * notes : 123669555588回来咯土著
         * city : 山东省
         * district : 烟台市
         * phoneNo : 13280933117
         * sex : 1
         */

        private String name;
        private int lawyerId;
        private String headURL;
        private int status;
        private String serviceCount;
        private int favoriteNum;
        private String serviceScore;
        private String notes;
        private String city;
        private String district;
        private String phoneNo;
        private int sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
        }

        public String getHeadURL() {
            return headURL;
        }

        public void setHeadURL(String headURL) {
            this.headURL = headURL;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getServiceCount() {
            return serviceCount;
        }

        public void setServiceCount(String serviceCount) {
            this.serviceCount = serviceCount;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public String getServiceScore() {
            return serviceScore;
        }

        public void setServiceScore(String serviceScore) {
            this.serviceScore = serviceScore;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
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

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }

    public static class AdListBean {
        /**
         * adId : 33
         * picURL : http://139.198.13.26:80/mylawyer/pic/1660
         * innerPicUrl : http://139.198.13.26:80/mylawyer/pic/1660
         * content : 律师端广告
         */

        private String adId;
        private String picURL;
        private String innerPicUrl;
        private String content;

        public String getAdId() {
            return adId;
        }

        public void setAdId(String adId) {
            this.adId = adId;
        }

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }

        public String getInnerPicUrl() {
            return innerPicUrl;
        }

        public void setInnerPicUrl(String innerPicUrl) {
            this.innerPicUrl = innerPicUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ServiceListBean {
        /**
         * serviceName : 图文咨询
         * serviceType : 2
         * isOn : true
         */

        private String serviceName;
        private int serviceType;
        private boolean isOn;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public boolean isIsOn() {
            return isOn;
        }

        public void setIsOn(boolean isOn) {
            this.isOn = isOn;
        }
    }

    public static class IncomeSumBean {
        /**
         * serviceName : 快速咨询
         * amountSum : 259.51
         */

        private String serviceName;
        private double amountSum;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public double getAmountSum() {
            return amountSum;
        }

        public void setAmountSum(double amountSum) {
            this.amountSum = amountSum;
        }
    }

}
