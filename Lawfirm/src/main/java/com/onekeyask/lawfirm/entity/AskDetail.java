package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/16 11:01
 */

public class AskDetail {
    /**
     * freeask : {"freeaskId":994,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"有图","picList":["http://139.198.11.78:8080/mylawyer/pic/1890","http://139.198.11.78:8080/mylawyer/pic/1891"],"createTime":"2017-04-21 16:24:00","status":1,"category":1,"categoryName":"合同纠纷","type":"1"}
     */

    private FreeaskBean freeask;

    public FreeaskBean getFreeask() {
        return freeask;
    }

    public void setFreeask(FreeaskBean freeask) {
        this.freeask = freeask;
    }

    public static class FreeaskBean {
        /**
         * freeaskId : 994
         * userId : 2
         * name : 用户之家
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
         * content : 有图
         * picList : ["http://139.198.11.78:8080/mylawyer/pic/1890","http://139.198.11.78:8080/mylawyer/pic/1891"]
         * createTime : 2017-04-21 16:24:00
         * status : 1
         * category : 1
         * categoryName : 合同纠纷
         * type : 1
         */

        private int freeaskId;//
        private int userId;//
        private String name;//
        private String headURL;//
        private String content;//
        private String createTime;//
        private int status;//
        private int category;//
        private String categoryName;//
        private String type;//
        private List<String> picList;//
        private double money;
        private int readCount;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getFreeaskId() {
            return freeaskId;
        }

        public void setFreeaskId(int freeaskId) {
            this.freeaskId = freeaskId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
