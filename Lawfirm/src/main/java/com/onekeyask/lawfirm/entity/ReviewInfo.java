package com.onekeyask.lawfirm.entity;

/**
 * Created by ytzht on 2017/09/23 下午4:01
 */

public class ReviewInfo {
    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"lawyerReviewInfo":{"lawyerId":238,"lawyerOfficeId":204,"lawyerOfficeTel":"13280933117","city":"北京市","district":"北京市市辖区","sex":1,"name":"安卓测试","status":"1","lawyerOfficeName":"安卓测试律所","lisencePicURL":"http://139.198.13.26:80/mylawyer/pic/1661"}}
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
         * lawyerReviewInfo : {"lawyerId":238,"lawyerOfficeId":204,"lawyerOfficeTel":"13280933117","city":"北京市","district":"北京市市辖区","sex":1,"name":"安卓测试","status":"1","lawyerOfficeName":"安卓测试律所","lisencePicURL":"http://139.198.13.26:80/mylawyer/pic/1661"}
         */

        private LawyerReviewInfoBean lawyerReviewInfo;

        public LawyerReviewInfoBean getLawyerReviewInfo() {
            return lawyerReviewInfo;
        }

        public void setLawyerReviewInfo(LawyerReviewInfoBean lawyerReviewInfo) {
            this.lawyerReviewInfo = lawyerReviewInfo;
        }

        public static class LawyerReviewInfoBean {
            /**
             * lawyerId : 238
             * lawyerOfficeId : 204
             * lawyerOfficeTel : 13280933117
             * city : 北京市
             * district : 北京市市辖区
             * sex : 1
             * name : 安卓测试
             * status : 1
             * lawyerOfficeName : 安卓测试律所
             * lisencePicURL : http://139.198.13.26:80/mylawyer/pic/1661
             */

            private int lawyerId;
            private int lawyerOfficeId;
            private String lawyerOfficeTel;
            private String city;
            private String district;
            private int sex;
            private String name;
            private String status;
            private String lawyerOfficeName;
            private String lisencePicURL;

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }

            public int getLawyerOfficeId() {
                return lawyerOfficeId;
            }

            public void setLawyerOfficeId(int lawyerOfficeId) {
                this.lawyerOfficeId = lawyerOfficeId;
            }

            public String getLawyerOfficeTel() {
                return lawyerOfficeTel;
            }

            public void setLawyerOfficeTel(String lawyerOfficeTel) {
                this.lawyerOfficeTel = lawyerOfficeTel;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getLawyerOfficeName() {
                return lawyerOfficeName;
            }

            public void setLawyerOfficeName(String lawyerOfficeName) {
                this.lawyerOfficeName = lawyerOfficeName;
            }

            public String getLisencePicURL() {
                return lisencePicURL;
            }

            public void setLisencePicURL(String lisencePicURL) {
                this.lisencePicURL = lisencePicURL;
            }
        }
    }
}
