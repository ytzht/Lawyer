package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/21 8:53
 */

public class ChatList {
    /**
     * chatList : [{"type":3,"chatId":113,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-20 21:31:12"},{"type":3,"chatId":112,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-20 21:26:23"},{"type":3,"chatId":111,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:23:15"},{"type":3,"chatId":110,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:22:11"},{"type":3,"chatId":107,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:22:00"},{"type":3,"chatId":108,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:22:00"},{"type":3,"chatId":109,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:22:00"},{"type":3,"chatId":106,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"绿地率为百分之百的确","lastWordIsPic":false,"lastWordTime":"2017-04-20 21:19:43"},{"type":3,"chatId":105,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:15:36"},{"type":3,"chatId":104,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:13:39"},{"type":3,"chatId":103,"lawyer":{"name":"","lawyerId":1,"headURL":""},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-20 21:03:24"}]
     * hasMore : true
     */

    private boolean hasMore;
    private List<ChatListBean> chatList;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ChatListBean> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatListBean> chatList) {
        this.chatList = chatList;
    }

    public static class ChatListBean {
        /**
         * type : 3
         * chatId : 113
         * lawyer : {"name":"","lawyerId":1,"headURL":""}
         * user : {"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"}
         * status : 1
         * lastWord :
         * lastWordIsPic : false
         * lastWordTime : 2017-04-20 21:31:12
         */

        private int type;
        private int chatId;
        private String freeaskId;
        private LawyerBean lawyer;
        private UserBean user;
        private int status;

        public String getFreeaskId() {
            return freeaskId;
        }

        public void setFreeaskId(String freeaskId) {
            this.freeaskId = freeaskId;
        }

        private String lastWord;
        private boolean lastWordIsPic;
        private String lastWordTime;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getChatId() {
            return chatId;
        }

        public void setChatId(int chatId) {
            this.chatId = chatId;
        }

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLastWord() {
            return lastWord;
        }

        public void setLastWord(String lastWord) {
            this.lastWord = lastWord;
        }

        public boolean isLastWordIsPic() {
            return lastWordIsPic;
        }

        public void setLastWordIsPic(boolean lastWordIsPic) {
            this.lastWordIsPic = lastWordIsPic;
        }

        public String getLastWordTime() {
            return lastWordTime;
        }

        public void setLastWordTime(String lastWordTime) {
            this.lastWordTime = lastWordTime;
        }

        public static class LawyerBean {
            /**
             * name :
             * lawyerId : 1
             * headURL :
             */

            private String name;
            private String lawyerId;
            private String headURL;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(String lawyerId) {
                this.lawyerId = lawyerId;
            }

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }
        }

        public static class UserBean {
            /**
             * name : 用户之家
             * userId : 2
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
             */

            private String name;
            private int userId;
            private String headURL;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }
        }
    }
}
