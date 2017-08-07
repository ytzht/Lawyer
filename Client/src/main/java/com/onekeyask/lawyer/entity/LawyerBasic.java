package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by ytzht on 2017/5/8.a
 */

public class LawyerBasic {


    /**
     * lawyer : {"name":"律师之家","headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]}
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
         * name : 律师之家
         * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
         * special : ["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]
         */

        private String name;
        private String headURL;
        private List<String> special;

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

        public List<String> getSpecial() {
            return special;
        }

        public void setSpecial(List<String> special) {
            this.special = special;
        }
    }
}
