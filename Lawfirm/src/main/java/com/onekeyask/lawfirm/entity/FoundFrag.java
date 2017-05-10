package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/16 08:31
 */

public class FoundFrag {


    /**
     * err : {"code":0,"msg":"正常","eventId":""}
     * freeaskList : [{"freeaskId":355,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only2222","picList":[],"createTime":"2017-04-14 16:38:17","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":290,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only22","picList":[],"createTime":"2017-04-14 13:19:57","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":13,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 15:24:46","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":12,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 15:06:23","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":11,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 14:56:39","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":10,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 14:49:49","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":9,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 13:53:26","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":8,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 13:30:04","status":1,"category":3,"categoryName":"婚姻继承","type":"2","money":4},{"freeaskId":7,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":[],"createTime":"2017-04-12 12:21:56","status":1,"category":3,"categoryName":"婚姻继承","type":"1"},{"freeaskId":6,"userId":2,"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1543","content":"for test only","picList":["","",""],"createTime":"2017-04-12 11:50:40","status":1,"category":3,"categoryName":"婚姻继承","type":"1"}]
     * hasMore : true
     */

    private ErrBean err;
    private boolean hasMore;
    private List<FreeaskListBean> freeaskList;

    public ErrBean getErr() {
        return err;
    }

    public void setErr(ErrBean err) {
        this.err = err;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<FreeaskListBean> getFreeaskList() {
        return freeaskList;
    }

    public void setFreeaskList(List<FreeaskListBean> freeaskList) {
        this.freeaskList = freeaskList;
    }

    public static class ErrBean {
        /**
         * code : 0
         * msg : 正常
         * eventId :
         */

        private int code;
        private String msg;
        private String eventId;

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
    }

    public static class FreeaskListBean {
        /**
         * freeaskId : 355
         * userId : 2
         * name : 用户之家
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
         * content : for test only2222
         * picList : []
         * createTime : 2017-04-14 16:38:17
         * status : 1
         * category : 3
         * categoryName : 婚姻继承
         * type : 2
         * money : 4
         */

        private int freeaskId;
        private int userId;
        private String name;
        private String headURL;
        private String content;
        private String createTime;
        private int status;
        private int category;
        private String categoryName;
        private String type;
        private int money;
        private List<String> picList;

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

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
