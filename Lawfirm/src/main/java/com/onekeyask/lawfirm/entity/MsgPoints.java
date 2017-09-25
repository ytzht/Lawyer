package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/24 10:52
 */

public class MsgPoints {
    /**
     * data : {"messageId":497}
     * activity : UserPointsChangeNotification
     */

    private DataBean data;
    private String activity;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public static class DataBean {
        /**
         * messageId : 497
         */

        private int messageId;

        private int chatId;

        public int getChatId() {
            return chatId;
        }

        public void setChatId(int chatId) {
            this.chatId = chatId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getUserServiceId() {
            return userServiceId;
        }

        public void setUserServiceId(String userServiceId) {
            this.userServiceId = userServiceId;
        }

        public int getLawyerId() {
            return lawyerId;
        }

        public void setLawyerId(int lawyerId) {
            this.lawyerId = lawyerId;
        }

        private String targetId;
        private String serviceType;

        private String userServiceId;
        private int lawyerId;



        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }
    }
}
