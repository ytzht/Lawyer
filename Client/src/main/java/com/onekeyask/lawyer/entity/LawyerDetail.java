package com.onekeyask.lawyer.entity;

import java.util.List;

/**
 * Created by zht on 2017/08/05 11:25
 */

public class LawyerDetail {

    /**
     * code : 0
     * msg : 正常
     * data : {"lawyer":{"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","lawyerOfficeName":"不知道","tags":["回复很及时","非常敬业","回答很专业","态度非常好","666"],"special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"serviceScore":63.2,"serviceCount":207,"favorite":true,"favoriteNum":1,"notes":"我该说点啥好","sex":1,"district":"北京市市辖区","city":"北京市","serviceList":[{"serviceName":"私人律师","serviceType":1,"isOn":true,"notes":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/1","inService":true,"priceList":[{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]},{"serviceName":"图文咨询","serviceType":2,"isOn":true,"notes":"以图文方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/2","inService":true,"priceList":[{"priceId":4,"isOpen":true,"cycle":"次","price":20}]},{"serviceName":"电话咨询","serviceType":3,"isOn":true,"notes":"以电话方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/3","inService":true,"priceList":[{"priceId":5,"isOpen":true,"cycle":"分钟","price":5}]}],"scoreCounts":[{"score":0,"scoreShowName":"全部","scount":247},{"score":5,"scoreShowName":"很满意","scount":210},{"score":3,"scoreShowName":"满意","scount":32},{"score":1,"scoreShowName":"不满意","scount":5}],"giveMoneyCount":5},"customerReply":[{"commentId":314,"phoneNo":"1521086***6","content":"85","notes":"[]","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-07 16:06:49"},{"commentId":313,"phoneNo":"1521086***6","content":"？？","notes":"[]","serviceScore":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 23:45:40"},{"commentId":312,"phoneNo":"1521086***6","content":"111","notes":"输入的评价","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 13:24:13"},{"commentId":311,"phoneNo":"1521086***6","content":"我们等你的的111","notes":"[]","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 13:06:04"},{"commentId":310,"phoneNo":"1521086***6","content":"新的测试","notes":"[]","serviceScore":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-01 16:12:49"}]}
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
         * lawyer : {"name":"律师之家","lawyerId":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1539","lawyerOfficeName":"不知道","tags":["回复很及时","非常敬业","回答很专业","态度非常好","666"],"special":["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"],"serviceScore":63.2,"serviceCount":207,"favorite":true,"favoriteNum":1,"notes":"我该说点啥好","sex":1,"district":"北京市市辖区","city":"北京市","serviceList":[{"serviceName":"私人律师","serviceType":1,"isOn":true,"notes":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/1","inService":true,"priceList":[{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]},{"serviceName":"图文咨询","serviceType":2,"isOn":true,"notes":"以图文方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/2","inService":true,"priceList":[{"priceId":4,"isOpen":true,"cycle":"次","price":20}]},{"serviceName":"电话咨询","serviceType":3,"isOn":true,"notes":"以电话方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/3","inService":true,"priceList":[{"priceId":5,"isOpen":true,"cycle":"分钟","price":5}]}],"scoreCounts":[{"score":0,"scoreShowName":"全部","scount":247},{"score":5,"scoreShowName":"很满意","scount":210},{"score":3,"scoreShowName":"满意","scount":32},{"score":1,"scoreShowName":"不满意","scount":5}],"giveMoneyCount":5}
         * customerReply : [{"commentId":314,"phoneNo":"1521086***6","content":"85","notes":"[]","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-07 16:06:49"},{"commentId":313,"phoneNo":"1521086***6","content":"？？","notes":"[]","serviceScore":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 23:45:40"},{"commentId":312,"phoneNo":"1521086***6","content":"111","notes":"输入的评价","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 13:24:13"},{"commentId":311,"phoneNo":"1521086***6","content":"我们等你的的111","notes":"[]","serviceScore":5,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-04 13:06:04"},{"commentId":310,"phoneNo":"1521086***6","content":"新的测试","notes":"[]","serviceScore":3,"headURL":"http://139.198.11.78:8080/mylawyer/pic/1540","commentDate":"2017-08-01 16:12:49"}]
         */

        private LawyerBean lawyer;
        private List<CustomerReplyBean> customerReply;

        public LawyerBean getLawyer() {
            return lawyer;
        }

        public void setLawyer(LawyerBean lawyer) {
            this.lawyer = lawyer;
        }

        public List<CustomerReplyBean> getCustomerReply() {
            return customerReply;
        }

        public void setCustomerReply(List<CustomerReplyBean> customerReply) {
            this.customerReply = customerReply;
        }

        public static class LawyerBean {
            /**
             * name : 律师之家
             * lawyerId : 3
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1539
             * lawyerOfficeName : 不知道
             * tags : ["回复很及时","非常敬业","回答很专业","态度非常好","666"]
             * special : ["合同纠纷","房产纠纷","婚姻继承","债权债务","侵权纠纷"]
             * serviceScore : 63.2
             * serviceCount : 207
             * favorite : true
             * favoriteNum : 1
             * notes : 我该说点啥好
             * sex : 1
             * district : 北京市市辖区
             * city : 北京市
             * serviceList : [{"serviceName":"私人律师","serviceType":1,"isOn":true,"notes":"服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/1","inService":true,"priceList":[{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]},{"serviceName":"图文咨询","serviceType":2,"isOn":true,"notes":"以图文方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/2","inService":true,"priceList":[{"priceId":4,"isOpen":true,"cycle":"次","price":20}]},{"serviceName":"电话咨询","serviceType":3,"isOn":true,"notes":"以电话方式按次解答客户的法律咨询问题。","imgURL":"http://139.198.11.78:8080/mylawyer/pic/3","inService":true,"priceList":[{"priceId":5,"isOpen":true,"cycle":"分钟","price":5}]}]
             * scoreCounts : [{"score":0,"scoreShowName":"全部","scount":247},{"score":5,"scoreShowName":"很满意","scount":210},{"score":3,"scoreShowName":"满意","scount":32},{"score":1,"scoreShowName":"不满意","scount":5}]
             * giveMoneyCount : 5
             */

            private String name;
            private int lawyerId;
            private String headURL;
            private String lawyerOfficeName;
            private double serviceScore;
            private int serviceCount;
            private boolean favorite;
            private int favoriteNum;
            private String notes;
            private int sex;
            private String district;
            private String city;
            private int giveMoneyCount;
            private List<String> tags;

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            private int position;


            private List<String> special;
            private List<ServiceListBean> serviceList;
            private List<ScoreCountsBean> scoreCounts;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLawyerId() {
                return lawyerId;
            }

            public void setLawyerId(int lawyerId) {
                this.lawyerId = lawyerId;
            }

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }

            public String getLawyerOfficeName() {
                return lawyerOfficeName;
            }

            public void setLawyerOfficeName(String lawyerOfficeName) {
                this.lawyerOfficeName = lawyerOfficeName;
            }

            public double getServiceScore() {
                return serviceScore;
            }

            public void setServiceScore(double serviceScore) {
                this.serviceScore = serviceScore;
            }

            public int getServiceCount() {
                return serviceCount;
            }

            public void setServiceCount(int serviceCount) {
                this.serviceCount = serviceCount;
            }

            public boolean isFavorite() {
                return favorite;
            }

            public void setFavorite(boolean favorite) {
                this.favorite = favorite;
            }

            public int getFavoriteNum() {
                return favoriteNum;
            }

            public void setFavoriteNum(int favoriteNum) {
                this.favoriteNum = favoriteNum;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getGiveMoneyCount() {
                return giveMoneyCount;
            }

            public void setGiveMoneyCount(int giveMoneyCount) {
                this.giveMoneyCount = giveMoneyCount;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }

            public List<String> getSpecial() {
                return special;
            }

            public void setSpecial(List<String> special) {
                this.special = special;
            }

            public List<ServiceListBean> getServiceList() {
                return serviceList;
            }

            public void setServiceList(List<ServiceListBean> serviceList) {
                this.serviceList = serviceList;
            }

            public List<ScoreCountsBean> getScoreCounts() {
                return scoreCounts;
            }

            public void setScoreCounts(List<ScoreCountsBean> scoreCounts) {
                this.scoreCounts = scoreCounts;
            }

            public static class ServiceListBean {
                /**
                 * serviceName : 私人律师
                 * serviceType : 1
                 * isOn : true
                 * notes : 服务周期内，专业律师为客户提供个人法律顾问服务，解决法律需求。
                 * imgURL : http://139.198.11.78:8080/mylawyer/pic/1
                 * inService : true
                 * priceList : [{"priceId":1,"isOpen":true,"cycle":"周","price":500},{"priceId":2,"isOpen":false,"cycle":"月","price":0},{"priceId":3,"isOpen":false,"cycle":"年","price":0}]
                 */

                private String serviceName;
                private int serviceType;
                private boolean isOn;
                private String notes;
                private String imgURL;
                private boolean inService;
                private List<PriceListBean> priceList;

                public boolean isCheck() {
                    return isCheck;
                }

                public void setCheck(boolean check) {
                    isCheck = check;
                }

                private boolean isCheck;
                public String getServiceName() {
                    return serviceName;
                }

                public void setServiceName(String serviceName) {
                    this.serviceName = serviceName;
                }

                public int getServiceType() {
                    return serviceType;
                }

                public void setServiceType(int serviceType) {
                    this.serviceType = serviceType;
                }

                public boolean isIsOn() {
                    return isOn;
                }

                public void setIsOn(boolean isOn) {
                    this.isOn = isOn;
                }

                public String getNotes() {
                    return notes;
                }

                public void setNotes(String notes) {
                    this.notes = notes;
                }

                public String getImgURL() {
                    return imgURL;
                }

                public void setImgURL(String imgURL) {
                    this.imgURL = imgURL;
                }

                public boolean isInService() {
                    return inService;
                }

                public void setInService(boolean inService) {
                    this.inService = inService;
                }

                public List<PriceListBean> getPriceList() {
                    return priceList;
                }

                public void setPriceList(List<PriceListBean> priceList) {
                    this.priceList = priceList;
                }

                public static class PriceListBean {
                    /**
                     * priceId : 1
                     * isOpen : true
                     * cycle : 周
                     * price : 500
                     */

                    private int priceId;
                    private boolean isOpen;
                    private String cycle;
                    private String price;

                    public int getPriceId() {
                        return priceId;
                    }

                    public void setPriceId(int priceId) {
                        this.priceId = priceId;
                    }

                    public boolean isIsOpen() {
                        return isOpen;
                    }

                    public void setIsOpen(boolean isOpen) {
                        this.isOpen = isOpen;
                    }

                    public String getCycle() {
                        return cycle;
                    }

                    public void setCycle(String cycle) {
                        this.cycle = cycle;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }
                }
            }

            public static class ScoreCountsBean {
                /**
                 * score : 0
                 * scoreShowName : 全部
                 * scount : 247
                 */

                private int score;
                private String scoreShowName;
                private int scount;

                public int getScore() {
                    return score;
                }

                public void setScore(int score) {
                    this.score = score;
                }

                public String getScoreShowName() {
                    return scoreShowName;
                }

                public void setScoreShowName(String scoreShowName) {
                    this.scoreShowName = scoreShowName;
                }

                public int getScount() {
                    return scount;
                }

                public void setScount(int scount) {
                    this.scount = scount;
                }
            }
        }

        public static class CustomerReplyBean {
            /**
             * commentId : 314
             * phoneNo : 1521086***6
             * content : 85
             * notes : []
             * serviceScore : 5
             * headURL : http://139.198.11.78:8080/mylawyer/pic/1540
             * commentDate : 2017-08-07 16:06:49
             */

            private int commentId;
            private String phoneNo;
            private String content;
            private String notes;
            private int serviceScore;
            private String headURL;
            private String commentDate;

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
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

            public String getHeadURL() {
                return headURL;
            }

            public void setHeadURL(String headURL) {
                this.headURL = headURL;
            }

            public String getCommentDate() {
                return commentDate;
            }

            public void setCommentDate(String commentDate) {
                this.commentDate = commentDate;
            }
        }
    }
}
