package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/21 8:53
 */

public class ChatList {

    /**
     * serviceList : [{"serviceId":"48","type":"1","targetId":"371","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"测试看看数据，免费咨询详情","lastServiceTime":"2017-08-04 08:53:50"},{"serviceId":"47","type":"1","targetId":"370","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"[图片]","lastServiceTime":"2017-08-04 08:47:44"},{"serviceId":"46","type":"1","targetId":"369","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"[图片]","lastServiceTime":"2017-08-03 21:41:45"},{"serviceId":"45","type":"1","targetId":"368","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"？？","lastServiceTime":"2017-08-03 21:40:27"},{"serviceId":"44","type":"1","targetId":"367","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"阿啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","lastServiceTime":"2017-08-03 17:29:47"},{"serviceId":"43","type":"1","targetId":"366","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"啊啊啊啊阿，的孩子，了都没有用不上课时作业","lastServiceTime":"2017-08-03 17:11:41"},{"serviceId":"42","type":"1","targetId":"365","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"把我的孩子，*把累哭了KKK图咯阿里啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦，的","lastServiceTime":"2017-08-03 17:07:33"},{"serviceId":"41","type":"1","targetId":"364","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"把我的孩子，*把累哭了KKK图咯阿里啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦，的","lastServiceTime":"2017-08-03 16:57:53"},{"serviceId":"40","type":"1","targetId":"363","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"把我的孩子，*把累哭了KKK图咯阿里啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦，的","lastServiceTime":"2017-08-03 16:57:51"},{"serviceId":"39","type":"1","targetId":"362","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"把我的孩子，*把累哭了KKK图咯阿里啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦，的","lastServiceTime":"2017-08-03 16:57:42"},{"serviceId":"38","type":"1","targetId":"361","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"把我的孩子，*把累哭了KKK图咯阿里啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦，的","lastServiceTime":"2017-08-03 16:57:38"},{"serviceId":"30","type":"1","targetId":"353","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","lastServiceTime":"2017-08-02 20:58:16"},{"serviceId":"29","type":"1","targetId":"352","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-02 14:32:47"},{"serviceId":"28","type":"1","targetId":"351","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 15:44:59"},{"serviceId":"27","type":"1","targetId":"350","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试08-01","lastServiceTime":"2017-08-01 15:40:14"},{"serviceId":"26","type":"1","targetId":"349","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"我们等你的的111","lastServiceTime":"2017-08-01 16:46:14"},{"serviceId":"25","type":"1","targetId":"348","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 10:08:26"},{"serviceId":"24","type":"1","targetId":"347","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 09:45:34"},{"serviceId":"23","type":"1","targetId":"346","lawyer":{"name":"律师之家","lawyerId":"3","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539"},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"新的测试","lastServiceTime":"2017-08-01 09:34:53"},{"serviceId":"22","type":"1","targetId":"345","lawyer":{"name":"尚未接单","lawyerId":"","headURL":""},"user":{"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"},"status":"未评价","serviceContent":"for test only-08-01","lastServiceTime":"2017-08-01 09:32:27"}]
     * hasMore : true
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
         * serviceId : 48
         * type : 1
         * targetId : 371
         * lawyer : {"name":"尚未接单","lawyerId":"","headURL":""}
         * user : {"name":"用户之家","userId":"2","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540"}
         * status : 未评价
         * serviceContent : 测试看看数据，免费咨询详情
         * lastServiceTime : 2017-08-04 08:53:50
         */

        private String serviceId;
        private String type;
        private String targetId;
        private LawyerBean lawyer;
        private UserBean user;
        private String status;
        private String serviceContent;
        private String lastServiceTime;

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
             * name : 尚未接单
             * lawyerId :
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
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
             */

            private String name;
            private String userId;
            private String headURL;

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
