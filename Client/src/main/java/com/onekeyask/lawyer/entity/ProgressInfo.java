package com.onekeyask.lawyer.entity;

import java.io.Serializable;

/**
 * Created by zht on 2017/08/31 8:37
 */

public class ProgressInfo implements Serializable {

    /**
     * id : 13
     * lawyerId : 2
     * cardNum : ***4975
     * bankName : 建设银行
     * applyName : 123
     * money : 2
     * status : 1
     * createTime : 1503046761000
     * userType : 0
     * userId : 2
     */

    private int id;
    private int lawyerId;
    private String cardNum;
    private String bankName;
    private String applyName;
    private int money;
    private int status;
    private String createTime;

    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}