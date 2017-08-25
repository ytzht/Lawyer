package com.onekeyask.lawfirm.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/25 18:06
 */

public class GetSpecialInfoList {
    /**
     * code : 0
     * msg : 正常
     * data : {"specialList":[{"specialId":1,"specialName":"合同纠纷","isSelected":true},{"specialId":2,"specialName":"房产纠纷","isSelected":true},{"specialId":3,"specialName":"婚姻继承","isSelected":false},{"specialId":4,"specialName":"人身损害","isSelected":true},{"specialId":5,"specialName":"劳动争议","isSelected":false},{"specialId":6,"specialName":"债权债务","isSelected":false},{"specialId":7,"specialName":"侵权纠纷","isSelected":true},{"specialId":8,"specialName":"消费维权","isSelected":false},{"specialId":9,"specialName":"交通事故","isSelected":false},{"specialId":10,"specialName":"刑事辩护","isSelected":false},{"specialId":11,"specialName":"全部","isSelected":false},{"specialId":12,"specialName":"投资","isSelected":false},{"specialId":13,"specialName":"融资","isSelected":false},{"specialId":14,"specialName":"兼并收购","isSelected":false},{"specialId":15,"specialName":"上市","isSelected":false},{"specialId":16,"specialName":"知识产权","isSelected":false},{"specialId":17,"specialName":"新三板","isSelected":false}]}
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
        private List<SpecialListBean> specialList;

        public List<SpecialListBean> getSpecialList() {
            return specialList;
        }

        public void setSpecialList(List<SpecialListBean> specialList) {
            this.specialList = specialList;
        }

        public static class SpecialListBean {
            /**
             * specialId : 1
             * specialName : 合同纠纷
             * isSelected : true
             */

            private int specialId;
            private String specialName;
            private boolean isSelected;

            public int getSpecialId() {
                return specialId;
            }

            public void setSpecialId(int specialId) {
                this.specialId = specialId;
            }

            public String getSpecialName() {
                return specialName;
            }

            public void setSpecialName(String specialName) {
                this.specialName = specialName;
            }

            public boolean isIsSelected() {
                return isSelected;
            }

            public void setIsSelected(boolean isSelected) {
                this.isSelected = isSelected;
            }
        }
    }
}
