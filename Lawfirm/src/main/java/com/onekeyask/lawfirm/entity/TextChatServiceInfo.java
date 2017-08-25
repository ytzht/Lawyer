package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/07 15:50
 */

public class TextChatServiceInfo {

    /**
     * code : 0
     * msg : 正常
     * data : {"textChat":{"isOn":true,"serviceType":2,"serviceName":"图文咨询","sellNo":2,"priceList":[{"priceId":4,"cycle":"次","price":20,"isOpen":true}],"content":"以图文方式按次解答客户的法律咨询问题，如果律师不能及时回答bla。"}}
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
         * textChat : {"isOn":true,"serviceType":2,"serviceName":"图文咨询","sellNo":2,"priceList":[{"priceId":4,"cycle":"次","price":20,"isOpen":true}],"content":"以图文方式按次解答客户的法律咨询问题，如果律师不能及时回答bla。"}
         */

        private TextChatBean textChat;

        public TextChatBean getTextChat() {
            return textChat;
        }

        public void setTextChat(TextChatBean textChat) {
            this.textChat = textChat;
        }

        public static class TextChatBean {
            /**
             * isOn : true
             * serviceType : 2
             * serviceName : 图文咨询
             * sellNo : 2
             * priceList : [{"priceId":4,"cycle":"次","price":20,"isOpen":true}]
             * content : 以图文方式按次解答客户的法律咨询问题，如果律师不能及时回答bla。
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
                 * priceId : 4
                 * cycle : 次
                 * price : 20
                 * isOpen : true
                 */

                private int priceId;
                private String cycle;
                private int price;
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

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
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
