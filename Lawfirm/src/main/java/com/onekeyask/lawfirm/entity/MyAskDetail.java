package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2018/01/26 13:15
 */

public class MyAskDetail {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"freeask":{"freeaskId":61,"readCount":25,"userId":100,"name":"用户2562","headURL":"http://139.198.11.78:8080/mylawyer/pic/201","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","picList":["http://139.198.11.78:8080/mylawyer/pic/288"],"createTime":"2018-01-25 16:03:47","status":2,"category":9,"categoryName":"交通事故","type":"2","money":6.1}}
     */

    private int code;
    private String msg;
    private String eventId;
    private AskDetail data;

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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public AskDetail getData() {
        return data;
    }

    public void setData(AskDetail data) {
        this.data = data;
    }


}
