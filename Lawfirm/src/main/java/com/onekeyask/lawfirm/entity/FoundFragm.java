package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/09/19 14:53
 */

public class FoundFragm {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"freeaskList":[{"freeaskId":1735,"userId":242,"name":"用户2986","headURL":"http://139.198.13.26:80/mylawyer/pic/1666","content":"吧巴巴爸爸巴巴爸爸巴巴爸爸","picList":[],"createTime":"2017-09-02 15:31:13","status":1,"category":2,"categoryName":"房产纠纷","type":"1"}],"hasMore":false}
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
        /**
         * freeaskList : [{"freeaskId":1735,"userId":242,"name":"用户2986","headURL":"http://139.198.13.26:80/mylawyer/pic/1666","content":"吧巴巴爸爸巴巴爸爸巴巴爸爸","picList":[],"createTime":"2017-09-02 15:31:13","status":1,"category":2,"categoryName":"房产纠纷","type":"1"}]
         * hasMore : false
         */

        private boolean hasMore;
        private List<FreeaskListBean> freeaskList;

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

        public static class FreeaskListBean {
            /**
             * freeaskId : 1735
             * userId : 242
             * name : 用户2986
             * headURL : http://139.198.13.26:80/mylawyer/pic/1666
             * content : 吧巴巴爸爸巴巴爸爸巴巴爸爸
             * picList : []
             * createTime : 2017-09-02 15:31:13
             * status : 1
             * category : 2
             * categoryName : 房产纠纷
             * type : 1
             */

            private int freeaskId;
            private int userId;
            private String name;
            private String headURL;
            private String content;
            private String createTime;
            private String money;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            private int status;
            private int category;
            private String categoryName;
            private String type;
            private List<?> picList;

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

            public List<?> getPicList() {
                return picList;
            }

            public void setPicList(List<?> picList) {
                this.picList = picList;
            }
        }
    }
}
