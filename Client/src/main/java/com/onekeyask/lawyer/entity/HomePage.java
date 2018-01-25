package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/04/13 14:57
 */

public class HomePage {

    private List<AdListBean> adList;

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class AdListBean {
        /**
         * adId : 1
         * picURL : http://139.198.11.78:8080/mylawyer/pic/7
         * innerPicUrl : http://139.198.11.78:8080/mylawyer/pic/7
         * content : 猎鹰私人律师平台秉持“个人平等享受司法保护”的原则，免费为部分经济困难、急需法律帮助的刑事犯罪嫌疑人、刑事被告人、刑事被害人提供法律援助。
         如有迫切需要法律援助的当事人，可将您的相关情况介绍和材料及您的联系方式，发到平台的免费法律援助专门联系邮箱：falvyuanzhu@mylawyerchina.com。私人律师平台将对相关情况予以评估，如决定免费提供法律援助，将按照您留下的联系方式和您联系。
         * type : 0
         */

        private int adId;
        private String picURL;
        private String innerPicUrl;
        private String content;
        private int type;

        public int getAdId() {
            return adId;
        }

        public void setAdId(int adId) {
            this.adId = adId;
        }

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }

        public String getInnerPicUrl() {
            return innerPicUrl;
        }

        public void setInnerPicUrl(String innerPicUrl) {
            this.innerPicUrl = innerPicUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
