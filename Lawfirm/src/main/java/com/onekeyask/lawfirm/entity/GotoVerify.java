package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/28 10:36
 */

public class GotoVerify {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"lawyer":{"name":"安卓测试","headURL":"http://139.198.13.26:80/mylawyer/pic/1662?r=null","sex":1,"status":"0","lawyerId":238},"lawyerReviewInfo":{"lawyerOfficeName":"安卓测试律所","lawyerOfficeTel":"13280933117","lisencePicURL":"http://139.198.13.26:80/mylawyer/pic/1661","city":"北京市","district":"北京市市辖区"}}
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
         * lawyer : {"name":"安卓测试","headURL":"http://139.198.13.26:80/mylawyer/pic/1662?r=null","sex":1,"status":"0","lawyerId":238}
         * lawyerReviewInfo : {"lawyerOfficeName":"安卓测试律所","lawyerOfficeTel":"13280933117","lisencePicURL":"http://139.198.13.26:80/mylawyer/pic/1661","city":"北京市","district":"北京市市辖区"}
         */

        private LawyerBean lawyer;
        private LawyerReviewInfoBean lawyerReviewInfo;

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public LawyerReviewInfoBean getLawyerReviewInfo() {
            return lawyerReviewInfo;
        }

        public void setLawyerReviewInfo(LawyerReviewInfoBean lawyerReviewInfo) {
            this.lawyerReviewInfo = lawyerReviewInfo;
        }

        public static class LawyerBean {
            /**
             * name : 安卓测试
             * headURL : http://139.198.13.26:80/mylawyer/pic/1662?r=null
             * sex : 1
             * status : 0
             * lawyerId : 238
             */

            private String name;
            private String headURL;
            private int sex;
            private String status;
            private int lawyerId;

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

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }
        }

        public static class LawyerReviewInfoBean {
            /**
             * lawyerOfficeName : 安卓测试律所
             * lawyerOfficeTel : 13280933117
             * lisencePicURL : http://139.198.13.26:80/mylawyer/pic/1661
             * city : 北京市
             * district : 北京市市辖区
             */

            private String lawyerOfficeName;
            private String lawyerOfficeTel;
            private String lisencePicURL;
            private String city;
            private String district;

            public String getLawyerOfficeName() {
                return lawyerOfficeName;
            }

            public void setLawyerOfficeName(String lawyerOfficeName) {
                this.lawyerOfficeName = lawyerOfficeName;
            }

            public String getLawyerOfficeTel() {
                return lawyerOfficeTel;
            }

            public void setLawyerOfficeTel(String lawyerOfficeTel) {
                this.lawyerOfficeTel = lawyerOfficeTel;
            }

            public String getLisencePicURL() {
                return lisencePicURL;
            }

            public void setLisencePicURL(String lisencePicURL) {
                this.lisencePicURL = lisencePicURL;
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
        }
    }
}
