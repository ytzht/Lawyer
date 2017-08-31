package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/31 15:20
 */

public class AllSpecial {

    private List<AllSpecialBean> allSpecial;

    public List<AllSpecialBean> getAllSpecial() {
        return allSpecial;
    }

    public void setAllSpecial(List<AllSpecialBean> allSpecial) {
        this.allSpecial = allSpecial;
    }

    public static class AllSpecialBean {
        /**
         * id : 11
         * name : 其他
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
