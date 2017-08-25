package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/07 9:35
 */

public class PersonalLawyer {
    /**
     * code : 0
     * msg : 正常
     * data : {"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]},"service":{"serviceType":1,"serviceName":"私人律师","sellNo":0,"priceList":[{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]}}
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
         * lawyer : {"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]}
         * service : {"serviceType":1,"serviceName":"私人律师","sellNo":0,"priceList":[{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]}
         */

        private LawyerBean lawyer;
        private ServiceBean service;

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public ServiceBean getService() {
            return service;
        }

        public void setService(ServiceBean service) {
            this.service = service;
        }

        public static class LawyerBean {
            /**
             * name : 律师之家
             * lawyerId : 3
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
             * special : ["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]
             */

            private String name;
            private int lawyerId;
            private String headURL;
            private List<String> special;

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

            public List<String> getSpecial() {
                return special;
            }

            public void setSpecial(List<String> special) {
                this.special = special;
            }
        }

        public static class ServiceBean {
            /**
             * serviceType : 1
             * serviceName : 私人律师
             * sellNo : 0
             * priceList : [{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]
             */

            private int serviceType;
            private String serviceName;
            private int sellNo;
            private List<PriceListBean> priceList;

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

            public List<PriceListBean> getPriceList() {
                return priceList;
            }

            public void setPriceList(List<PriceListBean> priceList) {
                this.priceList = priceList;
            }

            public static class PriceListBean {
                /**
                 * priceId : 1
                 * isOpen : true
                 * cycle : 周
                 * price : 500
                 */

                private int priceId;
                private boolean isOpen;
                private String cycle;
                private int price;

                public int getPriceId() {
                    return priceId;
                }

                public void setPriceId(int priceId) {
                    this.priceId = priceId;
                }

                public boolean isIsOpen() {
                    return isOpen;
                }

                public void setIsOpen(boolean isOpen) {
                    this.isOpen = isOpen;
                }

                public String getCycle() {
                    return cycle;
                }

                public void setCycle(String cycle) {
                    this.cycle = cycle;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }
            }
        }
    }
}
