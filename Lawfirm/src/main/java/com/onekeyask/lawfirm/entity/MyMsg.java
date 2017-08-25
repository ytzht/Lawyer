package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/08/20 上午9:17
 */

public class MyMsg {
    /**
     * code : 0
     * msg : 正常
     * data : {"messageList":[{"messageId":360,"content":"您的积分增加了300，请到我的积分中查看。","status":0,"createTime":1502956240000}],"hasMore":false}
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
         * messageList : [{"messageId":360,"content":"您的积分增加了300，请到我的积分中查看。","status":0,"createTime":1502956240000}]
         * hasMore : false
         */

        private boolean hasMore;
        private List<MessageListBean> messageList;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public static class MessageListBean {
            /**
             * messageId : 360
             * content : 您的积分增加了300，请到我的积分中查看。
             * status : 0
             * createTime : 1502956240000
             */

            private int messageId;
            private String content;
            private int status;
            private long createTime;

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
