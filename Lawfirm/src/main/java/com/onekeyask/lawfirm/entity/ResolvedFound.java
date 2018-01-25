package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2018/01/23 15:54
 */

public class ResolvedFound {


    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"freeaskList":[{"freeaskId":43,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"哦公民，哦，哦。我在学校门口等你来的更猛烈一些事情一些东西？你来我往！嗯嗯","picList":[],"createTime":"2018-01-09 16:42:05","status":3,"category":1,"categoryName":"合同纠纷","type":"1","money":0},{"freeaskId":42,"readCount":1,"userId":134,"name":"用户2132","headURL":"","content":"Feecesdcedfdfgadfesssss","picList":[],"createTime":"2018-01-09 13:11:56","status":3,"category":7,"categoryName":"侵权纠纷","type":"1","money":0},{"freeaskId":40,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"好可口可乐了可口可乐了可口可乐","picList":[],"createTime":"2018-01-09 09:22:13","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":39,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"咯我现在在裤头子看了一下午","picList":[],"createTime":"2018-01-02 11:10:44","status":3,"category":13,"categoryName":"涉外","type":"2","money":2},{"freeaskId":38,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"我分享受的问题是什么时候来着的问题是什么时候来着的问题是什么时候来着的问题是什么时候来","picList":[],"createTime":"2017-12-20 11:10:41","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":37,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"咯我想问一下呜呜呜呜呜我想问下","picList":[],"createTime":"2017-12-12 09:23:35","status":3,"category":11,"categoryName":"其他","type":"1","money":0},{"freeaskId":36,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊","picList":[],"createTime":"2017-12-11 22:02:11","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":27,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"提示无比用户哈哈哈哈哈哈哈哈哈哈我的路","picList":[],"createTime":"2017-12-11 14:17:10","status":3,"category":11,"categoryName":"其他","type":"1","money":0},{"freeaskId":24,"readCount":0,"userId":112,"name":"用户6244","headURL":"","content":"我在晚上加班回家的时候出了车祸，这个算工伤吗，怎么赔偿呢","picList":[],"createTime":"2017-11-22 14:37:35","status":3,"category":5,"categoryName":"劳动争议","type":"1","money":0},{"freeaskId":23,"readCount":0,"userId":118,"name":"用户3357","headURL":"","content":"可口可乐了看看可口可乐了了","picList":[],"createTime":"2017-11-14 23:11:46","status":3,"category":4,"categoryName":"人身损害","type":"2","money":2}],"hasMore":true}
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
         * freeaskList : [{"freeaskId":43,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"哦公民，哦，哦。我在学校门口等你来的更猛烈一些事情一些东西？你来我往！嗯嗯","picList":[],"createTime":"2018-01-09 16:42:05","status":3,"category":1,"categoryName":"合同纠纷","type":"1","money":0},{"freeaskId":42,"readCount":1,"userId":134,"name":"用户2132","headURL":"","content":"Feecesdcedfdfgadfesssss","picList":[],"createTime":"2018-01-09 13:11:56","status":3,"category":7,"categoryName":"侵权纠纷","type":"1","money":0},{"freeaskId":40,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"好可口可乐了可口可乐了可口可乐","picList":[],"createTime":"2018-01-09 09:22:13","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":39,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"咯我现在在裤头子看了一下午","picList":[],"createTime":"2018-01-02 11:10:44","status":3,"category":13,"categoryName":"涉外","type":"2","money":2},{"freeaskId":38,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"我分享受的问题是什么时候来着的问题是什么时候来着的问题是什么时候来着的问题是什么时候来","picList":[],"createTime":"2017-12-20 11:10:41","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":37,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"咯我想问一下呜呜呜呜呜我想问下","picList":[],"createTime":"2017-12-12 09:23:35","status":3,"category":11,"categoryName":"其他","type":"1","money":0},{"freeaskId":36,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊","picList":[],"createTime":"2017-12-11 22:02:11","status":3,"category":1,"categoryName":"合同纠纷","type":"2","money":2},{"freeaskId":27,"readCount":0,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"提示无比用户哈哈哈哈哈哈哈哈哈哈我的路","picList":[],"createTime":"2017-12-11 14:17:10","status":3,"category":11,"categoryName":"其他","type":"1","money":0},{"freeaskId":24,"readCount":0,"userId":112,"name":"用户6244","headURL":"","content":"我在晚上加班回家的时候出了车祸，这个算工伤吗，怎么赔偿呢","picList":[],"createTime":"2017-11-22 14:37:35","status":3,"category":5,"categoryName":"劳动争议","type":"1","money":0},{"freeaskId":23,"readCount":0,"userId":118,"name":"用户3357","headURL":"","content":"可口可乐了看看可口可乐了了","picList":[],"createTime":"2017-11-14 23:11:46","status":3,"category":4,"categoryName":"人身损害","type":"2","money":2}]
         * hasMore : true
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
             * freeaskId : 43
             * readCount : 0
             * userId : 100
             * name : 用户2562
             * headURL : http://139.198.11.78:8080/mylawyer/pic/201
             * content : 哦公民，哦，哦。我在学校门口等你来的更猛烈一些事情一些东西？你来我往！嗯嗯
             * picList : []
             * createTime : 2018-01-09 16:42:05
             * status : 3
             * category : 1
             * categoryName : 合同纠纷
             * type : 1
             * money : 0
             */

            private int freeaskId;
            private int readCount;
            private int userId;
            private String name;
            private String headURL;
            private String content;
            private String createTime;
            private int status;
            private int category;
            private String categoryName;
            private String type;
            private double money;
            private List<String> picList;

            public int getFreeaskId() {
                return freeaskId;
            }

            public void setFreeaskId(int freeaskId) {
                this.freeaskId = freeaskId;
            }

            public int getReadCount() {
                return readCount;
            }

            public void setReadCount(int readCount) {
                this.readCount = readCount;
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

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
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
}
