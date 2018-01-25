package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/29 13:54
 */

public class IntoSetting {
    /**
     * code : 0
     * msg : 正常
     * data : {"serviceInfo":{"isOn":false,"serviceType":1,"serviceName":"私人律师","sellNo":0,"priceList":[{"priceId":21,"cycle":"周","price":0,"isOpen":false},{"priceId":22,"cycle":"月","price":0,"isOpen":false},{"priceId":23,"cycle":"年","price":0,"isOpen":false}],"notes":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","content":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/1"}}
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
         * serviceInfo : {"isOn":false,"serviceType":1,"serviceName":"私人律师","sellNo":0,"priceList":[{"priceId":21,"cycle":"周","price":0,"isOpen":false},{"priceId":22,"cycle":"月","price":0,"isOpen":false},{"priceId":23,"cycle":"年","price":0,"isOpen":false}],"notes":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","content":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/1"}
         */

        private ServiceInfoBean serviceInfo;

        public ServiceInfoBean getServiceInfo() {
            return serviceInfo;
        }

        public void setServiceInfo(ServiceInfoBean serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public static class ServiceInfoBean {
            /**
             * isOn : false
             * serviceType : 1
             * serviceName : 私人律师
             * sellNo : 0
             * priceList : [{"priceId":21,"cycle":"周","price":0,"isOpen":false},{"priceId":22,"cycle":"月","price":0,"isOpen":false},{"priceId":23,"cycle":"年","price":0,"isOpen":false}]
             * notes : 服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。
             * content : 服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。
             * imgURL : http://139.198.11.78:8080/mylawyer/pic/1
             */

            private boolean isOn;
            private int serviceType;
            private String serviceName;
            private int sellNo;
            private String notes;
            private String content;
            private String imgURL;
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

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImgURL() {
                return imgURL;
            }

            public void setImgURL(String imgURL) {
                this.imgURL = imgURL;
            }

            public List<PriceListBean> getPriceList() {
                return priceList;
            }

            public void setPriceList(List<PriceListBean> priceList) {
                this.priceList = priceList;
            }

            public static class PriceListBean {
                /**
                 * priceId : 21
                 * cycle : 周
                 * price : 0
                 * isOpen : false
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
