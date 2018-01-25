package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/13 15:18
 */

public class FreeAskCategory {


    /**
     * allSpecial : [{"id":1,"name":"鍚堝悓绾犵悍"},{"id":2,"name":"鎴夸骇绾犵悍"},{"id":3,"name":"濠氬Щ缁ф壙"},{"id":4,"name":"浜鸿韩鎹熷"},{"id":5,"name":"鍔冲姩浜夎"},{"id":6,"name":"鍊烘潈鍊哄姟"},{"id":7,"name":"渚垫潈绾犵悍"},{"id":8,"name":"娑堣垂缁存潈"},{"id":9,"name":"浜ら\u20ac氫簨鏁�"},{"id":10,"name":"鍒戜簨杈╂姢"},{"id":12,"name":"鎶曡祫"},{"id":13,"name":"铻嶈祫"},{"id":14,"name":"鍏煎苟鏀惰喘"},{"id":15,"name":"涓婂競"},{"id":16,"name":"鐭ヨ瘑浜ф潈"},{"id":17,"name":"鏂颁笁鏉�"},{"id":11,"name":"鍏朵粬"}]
     * err : {"code":0,"msg":"姝ｅ父","eventId":""}
     */


    private List<AllSpecialBean> allSpecial;


    public List<AllSpecialBean> getAllSpecial() {
        return allSpecial;
    }

    public void setAllSpecial(List<AllSpecialBean> allSpecial) {
        this.allSpecial = allSpecial;
    }


    public static class AllSpecialBean {

        public AllSpecialBean(int id, String name, boolean isCheck) {
            this.id = id;
            this.name = name;
            this.isCheck = isCheck;
        }

        /**
         * id : 1
         * name : 鍚堝悓绾犵悍
         */

        private int id;
        private String name;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private boolean isCheck = false;

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
