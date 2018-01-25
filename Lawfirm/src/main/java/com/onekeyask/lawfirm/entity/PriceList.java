package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/5/8.p
 */

public class PriceList {


    /**
     * balance : 8532.94
     * priceList : [{"priceId":10,"cycle":"","price":0,"isOpen":true}]
     */

    private double balance;
    private List<PriceListBean> priceList;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<PriceListBean> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceListBean> priceList) {
        this.priceList = priceList;
    }

    public static class PriceListBean {
        /**
         * priceId : 10
         * cycle :
         * price : 0
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
