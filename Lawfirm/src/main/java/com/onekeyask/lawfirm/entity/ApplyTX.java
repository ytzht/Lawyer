package com.onekeyask.lawfirm.entity;

/**
 * Created by ytzht on 2017/08/12 下午8:26
 */

public class ApplyTX {
    /**
     * code : 0
     * msg : 正常
     * data : {"progressInfo":{"id":1,"lawyerId":2,"cardNum":"***1094","bankName":"建设银行","applyName":"张三","money":50,"status":1,"createTime":1502330387000,"userId":2}}
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
         * progressInfo : {"id":1,"lawyerId":2,"cardNum":"***1094","bankName":"建设银行","applyName":"张三","money":50,"status":1,"createTime":1502330387000,"userId":2}
         */

        private ProgressInfo progressInfo;

        public ProgressInfo getProgressInfo() {
            return progressInfo;
        }

        public void setProgressInfo(ProgressInfo progressInfo) {
            this.progressInfo = progressInfo;
        }

    }
}
