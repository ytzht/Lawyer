package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/08/16 16:22
 */

public class Login {

    /**
     * code : 0
     * msg : 正常
     * data : {"token":"ecba4a64a0704398a26689161b3c57ec","user":{"name":"用户3112","headURL":"","userId":20}}
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
         * token : ecba4a64a0704398a26689161b3c57ec
         * user : {"name":"用户3112","headURL":"","userId":20}
         */

        private String token;
        private UserBean user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * name : 用户3112
             * headURL :
             * userId : 20
             */

            private String name;
            private String headURL;
            private int userId;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
