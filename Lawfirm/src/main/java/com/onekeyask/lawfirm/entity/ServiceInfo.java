package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2018/01/09 12:57
 */

public class ServiceInfo {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"serviceList":[{"isOn":false,"serviceTypeId":2,"serviceName":"图文咨询","priceList":[{"priceId":1624,"cycle":"次","price":2,"isOpen":true}]},{"isOn":false,"serviceTypeId":3,"serviceName":"电话咨询","priceList":[{"priceId":1625,"cycle":"次","price":8,"isOpen":true}]},{"isOn":false,"serviceTypeId":1,"serviceName":"私人律师","priceList":[{"priceId":1621,"cycle":"周","price":1.2,"isOpen":true},{"priceId":1622,"cycle":"月","price":2,"isOpen":true},{"priceId":1623,"cycle":"年","price":3,"isOpen":true}]}]}
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
        private List<ServiceListBean> serviceList;

        public List<ServiceListBean> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceListBean> serviceList) {
            this.serviceList = serviceList;
        }

        public static class ServiceListBean {
            /**
             * isOn : false
             * serviceTypeId : 2
             * serviceName : 图文咨询
             * priceList : [{"priceId":1624,"cycle":"次","price":2,"isOpen":true}]
             */

            private boolean isOn;
            private int serviceTypeId;
            private String serviceName;
            private List<PriceListBean> priceList;

            public boolean isIsOn() {
                return isOn;
            }

            public void setIsOn(boolean isOn) {
                this.isOn = isOn;
            }

            public int getServiceTypeId() {
                return serviceTypeId;
            }

            public void setServiceTypeId(int serviceTypeId) {
                this.serviceTypeId = serviceTypeId;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public List<PriceListBean> getPriceList() {
                return priceList;
            }

            public void setPriceList(List<PriceListBean> priceList) {
                this.priceList = priceList;
            }

            public static class PriceListBean {
                /**
                 * priceId : 1624
                 * cycle : 次
                 * price : 2
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
