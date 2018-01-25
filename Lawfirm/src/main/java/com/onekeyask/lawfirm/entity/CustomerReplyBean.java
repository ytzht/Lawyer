package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/08 14:27
 */

public class CustomerReplyBean {
    /**
     * commentId : 314
     * content : 85
     * notes : null
     * serviceScore : 5
     * phoneNo : 1521086***6
     * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
     * serviceType : 7
     * serviceName : 免费咨询
     * commentDate : 1502093209000
     */

    private int commentId;
    private String content;
    private String notes;
    private int serviceScore;
    private String phoneNo;
    private String headURL;
    private int serviceType;
    private String serviceName;
    private long commentDate;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(long commentDate) {
        this.commentDate = commentDate;
    }
}
