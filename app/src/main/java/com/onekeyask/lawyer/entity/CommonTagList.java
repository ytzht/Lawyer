package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/18 9:35
 */

public class CommonTagList {

    /**
     * tagList : [{"id":1,"tag":"态度非常好"},{"id":2,"tag":"回复很及时"},{"id":3,"tag":"回答很专业"},{"id":4,"tag":"棒极了"},{"id":5,"tag":"意见有帮助"},{"id":6,"tag":"非常敬业"},{"id":7,"tag":"666"},{"id":11,"tag":"大牛人啊"},{"id":12,"tag":"测试"},{"id":13,"tag":"2cd"}]
     * err : {"code":0,"msg":"正常","eventId":""}
     */

    private List<TagListBean> tagList;


    public List<TagListBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagListBean> tagList) {
        this.tagList = tagList;
    }


    public static class TagListBean {
        /**
         * id : 1
         * tag : 态度非常好
         */

        private int id;
        private String tag;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
