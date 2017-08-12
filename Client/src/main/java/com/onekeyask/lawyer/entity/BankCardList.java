package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/12 下午5:59
 */

public class BankCardList {
    /**
     * code : 0
     * msg : 正常
     * data : {"CardList":[{"id":4,"userId":2,"cardno":"***1091","cardholder":"张三","bankname":"建设银行","province":"山东省","cardtype":"信用卡"},{"id":10,"userId":2,"cardno":"***4975","cardholder":"æ\u009d\u008eå\u009b\u009b","bankname":"建设银行","province":"å±±ä¸\u009cç\u009c\u0081","cardtype":"å\u0082¨è\u0093\u0084å\u008d¡"},{"id":11,"userId":2,"cardno":"***4972","cardholder":"PS","bankname":"建设银行","province":"ç\u009c\u008bç\u009c\u008b","cardtype":"ä¿¡ç\u0094¨å\u008d¡"},{"id":12,"userId":2,"cardno":"***4970","cardholder":"æ\u009d\u008eå\u009b\u009b","bankname":"建设银行","province":"å±±ä¸\u009cç\u009c\u0081","cardtype":"å\u0082¨è\u0093\u0084å\u008d¡"}]}
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
        private List<CardListBean> CardList;

        public List<CardListBean> getCardList() {
            return CardList;
        }

        public void setCardList(List<CardListBean> CardList) {
            this.CardList = CardList;
        }

        public static class CardListBean {
            /**
             * id : 4
             * userId : 2
             * cardno : ***1091
             * cardholder : 张三
             * bankname : 建设银行
             * province : 山东省
             * cardtype : 信用卡
             */

            private int id;
            private int userId;
            private String cardno;
            private String cardholder;
            private String bankname;
            private String province;
            private String cardtype;
            private int select = 0;

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getCardno() {
                return cardno;
            }

            public void setCardno(String cardno) {
                this.cardno = cardno;
            }

            public String getCardholder() {
                return cardholder;
            }

            public void setCardholder(String cardholder) {
                this.cardholder = cardholder;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCardtype() {
                return cardtype;
            }

            public void setCardtype(String cardtype) {
                this.cardtype = cardtype;
            }
        }
    }
}
