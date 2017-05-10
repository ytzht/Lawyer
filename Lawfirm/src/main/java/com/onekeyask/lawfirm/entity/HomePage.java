package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/15 20:47
 */

public class HomePage {

    /**
     * err : {"code":0,"msg":"正常","eventid":""}
     * lawyer : {"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","status":0}
     * adList : [{"adId":"2","picURL":"http://139.198.11.78:8080/mylawyer/pic/7","innerPicUrl":"http://139.198.11.78:8080/mylawyer/pic/7","content":"这是律师端的！！！！！！猎鹰私人律师平台秉持\u201c个人平等享受司法保护\u201d的原则，免费为部分经济困难、急需法律帮助的刑事犯罪嫌疑人、刑事被告人、刑事被害人提供法律援助。 如有迫切需要法律援助的当事人，可将您的相关情况介绍和材料及您的联系方式，发到平台的免费法律援助专门联系邮箱：falvyuanzhu@mylawyerchina.com。私人律师平台将对相关情况予以评估，如决定免费提供法律援助，将按照您留下的联系方式和您联系。"}]
     * serviceList : [{"serviceName":"私人律师","serviceType":1,"isOn":true},{"serviceName":"图文咨询","serviceType":2,"isOn":true},{"serviceName":"电话咨询","serviceType":3,"isOn":true},{"serviceName":"现场律师","serviceType":4,"isOn":true},{"serviceName":"诉讼代理","serviceType":5,"isOn":true},{"serviceName":"非诉（合同）服务","serviceType":6,"isOn":true}]
     * monthIncome : 150
     * balance : 1000
     */
    
    private LawyerBean lawyer;
    private String monthIncome;
    private String balance;
    private List<AdListBean> adList;
    private List<ServiceListBean> serviceList;


    public LawyerBean getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerBean lawyer) {
        this.lawyer = lawyer;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
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

    public static class LawyerBean {
        /**
         * name : 律师之家
         * lawyerId : 3
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
         * status : 0
         */

        private String name;
        private int lawyerId;
        private String headURL;
        private int status;

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
    }

    public static class AdListBean {
        /**
         * adId : 2
         * picURL : http://139.198.11.78:8080/mylawyer/pic/7
         * innerPicUrl : http://139.198.11.78:8080/mylawyer/pic/7
         * content : 这是律师端的！！！！！！猎鹰私人律师平台秉持“个人平等享受司法保护”的原则，免费为部分经济困难、急需法律帮助的刑事犯罪嫌疑人、刑事被告人、刑事被害人提供法律援助。 如有迫切需要法律援助的当事人，可将您的相关情况介绍和材料及您的联系方式，发到平台的免费法律援助专门联系邮箱：falvyuanzhu@mylawyerchina.com。私人律师平台将对相关情况予以评估，如决定免费提供法律援助，将按照您留下的联系方式和您联系。
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
         * serviceName : 私人律师
         * serviceType : 1
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
}
