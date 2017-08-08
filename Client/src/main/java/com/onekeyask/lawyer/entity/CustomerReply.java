package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/08 14:00
 */

public class CustomerReply {

    /**
     * code : 0
     * msg : 正常
     * data : {"customerReply":[{"commentId":314,"content":"85","notes":null,"serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1502093209000},{"commentId":313,"content":"？？","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":9,"serviceName":"打赏咨询","commentDate":1501861540000},{"commentId":312,"content":"111","notes":"输入的评价","serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501824253000},{"commentId":311,"content":"我们等你的的111","notes":null,"serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501823164000},{"commentId":310,"content":"新的测试","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501575169000},{"commentId":309,"content":"我们等你的的111","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501565504000}],"hasMore":false}
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
         * customerReply : [{"commentId":314,"content":"85","notes":null,"serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1502093209000},{"commentId":313,"content":"？？","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":9,"serviceName":"打赏咨询","commentDate":1501861540000},{"commentId":312,"content":"111","notes":"输入的评价","serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501824253000},{"commentId":311,"content":"我们等你的的111","notes":null,"serviceScore":5,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501823164000},{"commentId":310,"content":"新的测试","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501575169000},{"commentId":309,"content":"我们等你的的111","notes":null,"serviceScore":3,"phoneNo":"1521086***6","headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","serviceType":7,"serviceName":"免费咨询","commentDate":1501565504000}]
         * hasMore : false
         */

        private boolean hasMore;
        private List<CustomerReplyBean> customerReply;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<CustomerReplyBean> getCustomerReply() {
            return customerReply;
        }

        public void setCustomerReply(List<CustomerReplyBean> customerReply) {
            this.customerReply = customerReply;
        }
    }
}
