package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/15 14:43
 */

public class FreeaskDetail {


    /**
     * err : {"code":0,"msg":"正常","eventid":"xxxx-xxxx-xxxx"}
     * freeask : {"freeaskId":1,"userId":1,"content":"我有个问题想问问","picList":["http://xxxx/freeask/1","http://xxxx/freeask/2"],"createTime":"2015-07-02","status":1}
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
         * freeaskId : 1
         * userId : 1
         * content : 我有个问题想问问
         * picList : ["http://xxxx/freeask/1","http://xxxx/freeask/2"]
         * createTime : 2015-07-02
         * status : 1
         */

        private int freeaskId;
        private int userId;
        private int category;
        private String name;
        private String headURL;
        private String content;
        private String createTime;
        private String categoryName;
        private String type;
        private int status;
        private List<String> picList;

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
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

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
