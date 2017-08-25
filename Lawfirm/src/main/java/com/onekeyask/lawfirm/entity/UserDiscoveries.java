package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/08 17:46
 */

public class UserDiscoveries {
    /**
     * code : 0
     * msg : 正常
     * data : {"userDiscoveries":[{"chatId":384,"userServiceId":61,"category":11,"categoryName":"全部","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":1,"createDate":1501838903000},{"chatId":369,"userServiceId":46,"category":3,"categoryName":"婚姻继承","content":"测试图片情况下，律师版本发现界面的情况。是否有图片显示。","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":0,"createDate":1501767704000},{"chatId":368,"userServiceId":45,"category":11,"categoryName":"全部","content":"这是个测试，测试免费咨询提交接口数据。","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":1,"createDate":1501762201000}],"hasMore":false}
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
         * userDiscoveries : [{"chatId":384,"userServiceId":61,"category":11,"categoryName":"全部","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":1,"createDate":1501838903000},{"chatId":369,"userServiceId":46,"category":3,"categoryName":"婚姻继承","content":"测试图片情况下，律师版本发现界面的情况。是否有图片显示。","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":0,"createDate":1501767704000},{"chatId":368,"userServiceId":45,"category":11,"categoryName":"全部","content":"这是个测试，测试免费咨询提交接口数据。","lawyerName":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","officeName":"不知道","supportCount":1,"createDate":1501762201000}]
         * hasMore : false
         */

        private boolean hasMore;
        private List<UserDiscoveriesBean> userDiscoveries;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<UserDiscoveriesBean> getUserDiscoveries() {
            return userDiscoveries;
        }

        public void setUserDiscoveries(List<UserDiscoveriesBean> userDiscoveries) {
            this.userDiscoveries = userDiscoveries;
        }

        public static class UserDiscoveriesBean {
            /**
             * chatId : 384
             * userServiceId : 61
             * category : 11
             * categoryName : 全部
             * content : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
             * lawyerName : 律师之家
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
             * officeName : 不知道
             * supportCount : 1
             * createDate : 1501838903000
             */

            private int chatId;
            private int userServiceId;
            private int category;
            private String categoryName;
            private String content;
            private String lawyerName;
            private String headURL;
            private String officeName;
            private int supportCount;
            private long createDate;

            public int getChatId() {
                return chatId;
            }

            public void setChatId(int chatId) {
                this.chatId = chatId;
            }

            public int getUserServiceId() {
                return userServiceId;
            }

            public void setUserServiceId(int userServiceId) {
                this.userServiceId = userServiceId;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLawyerName() {
                return lawyerName;
            }

            public void setLawyerName(String lawyerName) {
                this.lawyerName = lawyerName;
            }

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }

            public String getOfficeName() {
                return officeName;
            }

            public void setOfficeName(String officeName) {
                this.officeName = officeName;
            }

            public int getSupportCount() {
                return supportCount;
            }

            public void setSupportCount(int supportCount) {
                this.supportCount = supportCount;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }
        }
    }
}
