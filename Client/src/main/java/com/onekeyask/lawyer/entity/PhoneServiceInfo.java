package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/11/06 下午7:32
 */

public class PhoneServiceInfo {
    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"lawyer":{"name":"安卓测试","headURL":"http://139.198.11.78:8080/mylawyer/pic/211?r=1509965448","lawyerId":116},"telChat":{"isOn":true,"serviceType":3,"serviceName":"电话咨询","sellNo":0,"priceList":[{"priceId":1625,"cycle":"次","price":2.1,"isOpen":true}],"content":"与律师进行电话咨询"}}
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
         * lawyer : {"name":"安卓测试","headURL":"http://139.198.11.78:8080/mylawyer/pic/211?r=1509965448","lawyerId":116}
         * telChat : {"isOn":true,"serviceType":3,"serviceName":"电话咨询","sellNo":0,"priceList":[{"priceId":1625,"cycle":"次","price":2.1,"isOpen":true}],"content":"与律师进行电话咨询"}
         */

        private LawyerBean lawyer;
        private TelChatBean telChat;

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public TelChatBean getTelChat() {
            return telChat;
        }

        public void setTelChat(TelChatBean telChat) {
            this.telChat = telChat;
        }

        public static class LawyerBean {
            /**
             * name : 安卓测试
             * headURL : http://139.198.11.78:8080/mylawyer/pic/211?r=1509965448
             * lawyerId : 116
             */

            private String name;
            private String headURL;
            private int lawyerId;

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

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }
        }

        public static class TelChatBean {
            /**
             * isOn : true
             * serviceType : 3
             * serviceName : 电话咨询
             * sellNo : 0
             * priceList : [{"priceId":1625,"cycle":"次","price":2.1,"isOpen":true}]
             * content : 与律师进行电话咨询
             */

            private boolean isOn;
            private int serviceType;
            private String serviceName;
            private int sellNo;
            private String content;
            private List<PriceListBean> priceList;

            public boolean isIsOn() {
                return isOn;
            }

            public void setIsOn(boolean isOn) {
                this.isOn = isOn;
            }

            public int getServiceType() {
                return serviceType;
            }

            public void setServiceType(int serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getSellNo() {
                return sellNo;
            }

            public void setSellNo(int sellNo) {
                this.sellNo = sellNo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<PriceListBean> getPriceList() {
                return priceList;
            }

            public void setPriceList(List<PriceListBean> priceList) {
                this.priceList = priceList;
            }

            public static class PriceListBean {
                /**
                 * priceId : 1625
                 * cycle : 次
                 * price : 2.1
                 * isOpen : true
                 */

                private int priceId;
                private String cycle;
                private double price;
                private boolean isOpen;

                public int getPriceId() {
                    return priceId;
                }

                public void setPriceId(int priceId) {
                    this.priceId = priceId;
                }

                public String getCycle() {
                    return cycle;
                }

                public void setCycle(String cycle) {
                    this.cycle = cycle;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public boolean isIsOpen() {
                    return isOpen;
                }

                public void setIsOpen(boolean isOpen) {
                    this.isOpen = isOpen;
                }
            }
        }
    }
}
