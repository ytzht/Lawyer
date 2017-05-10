package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/5/8.a
 */

public class LawyerBasic {


    /**
     * lawyer : {"name":"用户之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","special":[]}
     */

    private LawyerBean lawyer;

    public LawyerBean getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerBean lawyer) {
        this.lawyer = lawyer;
    }

    public static class LawyerBean {
        /**
         * name : 用户之家
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
         * special : []
         */

        private String name;
        private String headURL;
        private List<?> special;

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

        public List<?> getSpecial() {
            return special;
        }

        public void setSpecial(List<?> special) {
            this.special = special;
        }
    }
}
