package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/16 12:57
 */

public class ConversationChatList {

    /**
     * err : {"code":0,"msg":"正常","eventid":""}
     * chatList : [{"type":3,"chatId":76,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-19 11:07:31"},{"type":3,"chatId":75,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-19 11:04:52"},{"type":3,"chatId":74,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"扣扣号码了吧唧吧唧吧唧吧唧","lastWordIsPic":false,"lastWordTime":"2017-04-19 11:04:06"},{"type":3,"chatId":45,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"for test only04-18-0","lastWordIsPic":false,"lastWordTime":"2017-04-18 17:27:19"},{"type":3,"chatId":38,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 17:11:00"},{"type":3,"chatId":36,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"累哭了","lastWordIsPic":false,"lastWordTime":"2017-04-18 17:04:58"},{"type":3,"chatId":25,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":3,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 16:06:58"},{"type":3,"chatId":24,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 16:01:45"},{"type":3,"chatId":23,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 15:57:15"},{"type":3,"chatId":22,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 15:34:10"},{"type":3,"chatId":21,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"[图片]","lastWordIsPic":true,"lastWordTime":"2017-04-18 14:13:30"},{"type":3,"chatId":20,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"for test only04-18-1401","lastWordIsPic":false,"lastWordTime":"2017-04-18 14:05:01"},{"type":3,"chatId":19,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 14:04:50"},{"type":3,"chatId":18,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 14:03:14"},{"type":3,"chatId":17,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 14:01:37"},{"type":3,"chatId":16,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 13:59:46"},{"type":3,"chatId":15,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 13:57:24"},{"type":3,"chatId":14,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 13:56:18"},{"type":3,"chatId":13,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-18 13:47:53"},{"type":3,"chatId":11,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":1,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-17 18:08:50"},{"type":3,"chatId":10,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:42:02"},{"type":3,"chatId":9,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:36:37"},{"type":3,"chatId":8,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:34:44"},{"type":3,"chatId":7,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:34:40"},{"type":3,"chatId":6,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:27:27"},{"type":3,"chatId":5,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-16 11:20:49"},{"type":3,"chatId":4,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-15 20:38:36"},{"type":3,"chatId":3,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":2,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-04-13 15:58:37"},{"orderId":5,"orderNo":"20170310161801466","type":1,"chatId":2,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":3,"lastWord":"","lastWordIsPic":false,"lastWordTime":"2017-03-10 16:18:01"},{"orderId":2,"orderNo":"20170303085805806","type":1,"chatId":1,"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"user":{"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"},"status":3,"lastWord":"yhgf","lastWordIsPic":false,"lastWordTime":"2017-03-03 10:47:43"}]
     * hasMore : false
     */

    private ErrBean err;
    private boolean hasMore;
    private List<ChatListBean> chatList;

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

    public List<ChatListBean> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatListBean> chatList) {
        this.chatList = chatList;
    }

    public static class ErrBean {
        /**
         * code : 0
         * msg : 正常
         * eventid :
         */

        private int code;
        private String msg;
        private String eventid;

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

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }
    }

    public static class ChatListBean {
        /**
         * type : 3
         * chatId : 76
         * lawyer : {"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"}
         * user : {"name":"用户之家","userId":2,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1543"}
         * status : 1
         * lastWord : [图片]
         * lastWordIsPic : true
         * lastWordTime : 2017-04-19 11:07:31
         * orderId : 5
         * orderNo : 20170310161801466
         */

        private int type;
        private int chatId;
        private LawyerBean lawyer;
        private UserBean user;
        private int status;
        private String lastWord;
        private boolean lastWordIsPic;
        private String lastWordTime;
        private int orderId;
        private String orderNo;

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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public static class LawyerBean {
            /**
             * name : 律师之家
             * lawyerId : 3
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1543
             */

            private String name;
            private int lawyerId;
            private String headURL;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
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
