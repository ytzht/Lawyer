package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/16 16:22
 */

public class Login {


    /**
     * code : 0
     * msg : 正常
     * data : {"token":"abbd71d4c0e60b607c40dca99302afff","lawyer":{"name":"","headURL":"","status":"2","lawyerId":100}}
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
         * token : abbd71d4c0e60b607c40dca99302afff
         * lawyer : {"name":"","headURL":"","status":"2","lawyerId":100}
         */

        private String token;
        private LawyerBean lawyer;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public static class LawyerBean {
            /**
             * name :
             * headURL :
             * status : 2
             * lawyerId : 100
             */

            private String name;
            private String headURL;
            private String status;
            private int lawyerId;
            private int firstHome;

            public int getFirstHome() {
                return firstHome;
            }

            public void setFirstHome(int firstHome) {
                this.firstHome = firstHome;
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
    }
}
