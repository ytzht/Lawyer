package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/4/16 12:57
 */

public class ConversationChatList {


    /**
     * serviceList : [{"serviceId":"46","type":"1","targetId":"369","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"[图片]","lastServiceTime":"2017-08-03 21:41:45"},{"serviceId":"45","type":"1","targetId":"368","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"吧","lastServiceTime":"2017-08-04 10:48:12"},{"serviceId":"27","type":"1","targetId":"350","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试08-01","lastServiceTime":"2017-08-01 15:40:14"},{"serviceId":"26","type":"1","targetId":"349","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"我们等你的的111","lastServiceTime":"2017-08-01 16:46:14"},{"serviceId":"25","type":"1","targetId":"348","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 10:08:26"},{"serviceId":"24","type":"1","targetId":"347","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 09:45:34"},{"serviceId":"23","type":"1","targetId":"346","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 09:34:53"},{"serviceId":"21","type":"1","targetId":"344","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 09:32:11"},{"serviceId":"20","type":"1","targetId":"343","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 09:12:14"},{"serviceId":"19","type":"1","targetId":"342","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 09:01:21"},{"serviceId":"18","type":"1","targetId":"341","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 09:00:13"},{"serviceId":"17","type":"1","targetId":"340","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 08:58:32"},{"serviceId":"16","type":"1","targetId":"339","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 08:41:34"},{"serviceId":"15","type":"1","targetId":"338","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-07-31","lastServiceTime":"2017-07-31 16:57:51"}]
     * hasMore : false
     */

    private boolean hasMore;
    private List<ServiceListBean> serviceList;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
    }

    public static class ServiceListBean {
        /**
         * serviceId : 46
         * type : 1
         * targetId : 369
         * lawyer : {"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"}
         * user : {"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"}
         * status : 未评价
         * serviceContent : [图片]
         * lastServiceTime : 2017-08-03 21:41:45
         */

        private String serviceId;
        private String type;
        private String targetId;
        private LawyerBean lawyer;
        private UserBean user;
        private String status;
        private String serviceContent;
        private String lastServiceTime;
        private String serviceAmount;

        public String getServiceAmount() {
            return serviceAmount;
        }

        public void setServiceAmount(String serviceAmount) {
            this.serviceAmount = serviceAmount;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public String getLastServiceTime() {
            return lastServiceTime;
        }

        public void setLastServiceTime(String lastServiceTime) {
            this.lastServiceTime = lastServiceTime;
        }

        public static class LawyerBean {
            /**
             * name : 律师之家
             * lawyerId : 3
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
             */

            private String name;
            private String lawyerId;
            private String headURL;
            private String lawfirm;
            private String phoneNo;

            public String getLawfirm() {
                return lawfirm;
            }

            public void setLawfirm(String lawfirm) {
                this.lawfirm = lawfirm;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

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
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
             */

            private String name;
            private String userId;
            private String headURL;
            private String phoneNo;

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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
