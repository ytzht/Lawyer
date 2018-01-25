package com.onekeyask.lawfirm.entity;

import java.util.HashMap;

/**
 * Created by ytzht on 2017/11/09 下午2:20
 */

public class LawDoc {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"law":{"title":"财政部公告2009年第123号\u2015\u2015发行2009年记账式附息(三十一期)国债","category":{"040":"证券","04004":"债券","031":"财政","03102":"国债管理"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"124290","fullText":"<p align=\"center\"><font class=\"MTitle\">财政部公告<br>（2009年第123号）<br><\/font><\/p><div class=\"TiaoYinV2\" id=\"div0\"><\/div><br>\n<br>\n　　根据国家国债发行的有关规定，财政部决定发行2009年记账式附息（三十一期）国债（以下简称本期国债），现将有关事项公告如下：<br>\n<br>\n<span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_1\"><\/a><\/span>　　一、本期国债通过全国银行间债券市场、证券交易所市场（以下简称各交易场所）及试点商业银行柜台面向社会各类投资者发行。试点商业银行包括中国工商银行股份有限公司、中国农业银行、中国银行股份有限公司、中国建设银行股份有限公司、招商银行股份有限公司、中国民生银行股份有限公司、北京银行股份有限公司和南京银行股份有限公司在全国已经开通国债柜台交易系统的分支机构（以下简称试点银行）。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div1\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_2\"><\/a><\/span>　　二、本期国债实际发行面值金额为273.9亿元，其中计划发行260亿元，根据有关规定甲类成员追加认购13.9亿元。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div2\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_3\"><\/a><\/span>　　三、本期国债期限5年，经投标确定的票面年利率为2.90％，2009年12月3日开始发行并计息，12月7日发行结束，12月9日起在各交易场所和试点银行柜台上市交易。本期国债在各交易场所交易方式为现券买卖和回购，试点银行柜台为现券买卖。通过试点银行柜台购买的本期国债，可以在债权托管银行质押贷款，具体办法由各试点银行制订。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div3\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_4\"><\/a><\/span>　　四、本期国债为固定利率附息债，利息按年支付，利息支付日为每年的12月3日（节假日顺延，下同），2014年12月3日偿还本金并支付最后一年利息。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div4\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_5\"><\/a><\/span>　　五、本期国债在2009年12月3日至12月7日的发行期内，采取场内挂牌、场外签订分销合同和试点银行柜台销售的方式分销，分销对象为在中国证券登记结算有限责任公司开立股票和基金账户，在中央国债登记结算有限责任公司、试点银行开立债券账户的各类投资者。承销机构根据市场情况自定价格分销。<br>\n<br>\n　　特此公告。<br>\n<br>\n<div align=right>中华人民共和国财政部<br>\n<br>\n二OO九年十二月二日<br>\n<\/div><br>\n<div class=\"TiaoYinV2\" id=\"div5\"><\/div><br>chl_124290<br>\n","documentNO":"财政部公告2009年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60210":"财政部"},"issueDate":"2009.12.02","implementDate":"2009.12.02","timelinessDic":{"01":"现行有效"}}}
     */

    private int code;
    private String msg;
    private String eventId;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * law : {"title":"财政部公告2009年第123号\u2015\u2015发行2009年记账式附息(三十一期)国债","category":{"040":"证券","04004":"债券","031":"财政","03102":"国债管理"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"124290","fullText":"<p align=\"center\"><font class=\"MTitle\">财政部公告<br>（2009年第123号）<br><\/font><\/p><div class=\"TiaoYinV2\" id=\"div0\"><\/div><br>\n<br>\n　　根据国家国债发行的有关规定，财政部决定发行2009年记账式附息（三十一期）国债（以下简称本期国债），现将有关事项公告如下：<br>\n<br>\n<span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_1\"><\/a><\/span>　　一、本期国债通过全国银行间债券市场、证券交易所市场（以下简称各交易场所）及试点商业银行柜台面向社会各类投资者发行。试点商业银行包括中国工商银行股份有限公司、中国农业银行、中国银行股份有限公司、中国建设银行股份有限公司、招商银行股份有限公司、中国民生银行股份有限公司、北京银行股份有限公司和南京银行股份有限公司在全国已经开通国债柜台交易系统的分支机构（以下简称试点银行）。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div1\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_2\"><\/a><\/span>　　二、本期国债实际发行面值金额为273.9亿元，其中计划发行260亿元，根据有关规定甲类成员追加认购13.9亿元。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div2\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_3\"><\/a><\/span>　　三、本期国债期限5年，经投标确定的票面年利率为2.90％，2009年12月3日开始发行并计息，12月7日发行结束，12月9日起在各交易场所和试点银行柜台上市交易。本期国债在各交易场所交易方式为现券买卖和回购，试点银行柜台为现券买卖。通过试点银行柜台购买的本期国债，可以在债权托管银行质押贷款，具体办法由各试点银行制订。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div3\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_4\"><\/a><\/span>　　四、本期国债为固定利率附息债，利息按年支付，利息支付日为每年的12月3日（节假日顺延，下同），2014年12月3日偿还本金并支付最后一年利息。<br>\n<br>\n<div class=\"TiaoYinV2\" id=\"div4\"><\/div><br><span anchor=\"anchor\" class=\"navtiao\"><a name=\"tiao_5\"><\/a><\/span>　　五、本期国债在2009年12月3日至12月7日的发行期内，采取场内挂牌、场外签订分销合同和试点银行柜台销售的方式分销，分销对象为在中国证券登记结算有限责任公司开立股票和基金账户，在中央国债登记结算有限责任公司、试点银行开立债券账户的各类投资者。承销机构根据市场情况自定价格分销。<br>\n<br>\n　　特此公告。<br>\n<br>\n<div align=right>中华人民共和国财政部<br>\n<br>\n二OO九年十二月二日<br>\n<\/div><br>\n<div class=\"TiaoYinV2\" id=\"div5\"><\/div><br>chl_124290<br>\n","documentNO":"财政部公告2009年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60210":"财政部"},"issueDate":"2009.12.02","implementDate":"2009.12.02","timelinessDic":{"01":"现行有效"}}
         */

        private LawBean law;

        public LawBean getLaw() {
            return law;
        }

        public void setLaw(LawBean law) {
            this.law = law;
        }

        public static class LawBean {
            /**
             * title : 财政部公告2009年第123号――发行2009年记账式附息(三十一期)国债
             * category : {"040":"证券","04004":"债券","031":"财政","03102":"国债管理"}
             * effectivenessDic : {"XE03":"部门规章","XE0303":"XE0303"}
             * gid : 124290
             * fullText : <p align="center"><font class="MTitle">财政部公告<br>（2009年第123号）<br></font></p><div class="TiaoYinV2" id="div0"></div><br>
             <br>
             　　根据国家国债发行的有关规定，财政部决定发行2009年记账式附息（三十一期）国债（以下简称本期国债），现将有关事项公告如下：<br>
             <br>
             <span anchor="anchor" class="navtiao"><a name="tiao_1"></a></span>　　一、本期国债通过全国银行间债券市场、证券交易所市场（以下简称各交易场所）及试点商业银行柜台面向社会各类投资者发行。试点商业银行包括中国工商银行股份有限公司、中国农业银行、中国银行股份有限公司、中国建设银行股份有限公司、招商银行股份有限公司、中国民生银行股份有限公司、北京银行股份有限公司和南京银行股份有限公司在全国已经开通国债柜台交易系统的分支机构（以下简称试点银行）。<br>
             <br>
             <div class="TiaoYinV2" id="div1"></div><br><span anchor="anchor" class="navtiao"><a name="tiao_2"></a></span>　　二、本期国债实际发行面值金额为273.9亿元，其中计划发行260亿元，根据有关规定甲类成员追加认购13.9亿元。<br>
             <br>
             <div class="TiaoYinV2" id="div2"></div><br><span anchor="anchor" class="navtiao"><a name="tiao_3"></a></span>　　三、本期国债期限5年，经投标确定的票面年利率为2.90％，2009年12月3日开始发行并计息，12月7日发行结束，12月9日起在各交易场所和试点银行柜台上市交易。本期国债在各交易场所交易方式为现券买卖和回购，试点银行柜台为现券买卖。通过试点银行柜台购买的本期国债，可以在债权托管银行质押贷款，具体办法由各试点银行制订。<br>
             <br>
             <div class="TiaoYinV2" id="div3"></div><br><span anchor="anchor" class="navtiao"><a name="tiao_4"></a></span>　　四、本期国债为固定利率附息债，利息按年支付，利息支付日为每年的12月3日（节假日顺延，下同），2014年12月3日偿还本金并支付最后一年利息。<br>
             <br>
             <div class="TiaoYinV2" id="div4"></div><br><span anchor="anchor" class="navtiao"><a name="tiao_5"></a></span>　　五、本期国债在2009年12月3日至12月7日的发行期内，采取场内挂牌、场外签订分销合同和试点银行柜台销售的方式分销，分销对象为在中国证券登记结算有限责任公司开立股票和基金账户，在中央国债登记结算有限责任公司、试点银行开立债券账户的各类投资者。承销机构根据市场情况自定价格分销。<br>
             <br>
             　　特此公告。<br>
             <br>
             <div align=right>中华人民共和国财政部<br>
             <br>
             二OO九年十二月二日<br>
             </div><br>
             <div class="TiaoYinV2" id="div5"></div><br>chl_124290<br>

             * documentNO : 财政部公告2009年第123号
             * ratifyDepartment : {}
             * ratifyDate :
             * issueDepartment : {"6":"国务院各机构","602":"各部","60210":"财政部"}
             * issueDate : 2009.12.02
             * implementDate : 2009.12.02
             * timelinessDic : {"01":"现行有效"}
             */

            private String title;
            private HashMap<String, String> category;
            private EffectivenessDicBean effectivenessDic;
            private String gid;
            private String fullText;
            private String documentNO;
            private RatifyDepartmentBean ratifyDepartment;
            private String ratifyDate;
            private HashMap<String, String> issueDepartment;
            private String issueDate;
            private String implementDate;
            private HashMap<String, String> timelinessDic;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public HashMap<String, String> getCategory() {
                return category;
            }


            public EffectivenessDicBean getEffectivenessDic() {
                return effectivenessDic;
            }

            public void setEffectivenessDic(EffectivenessDicBean effectivenessDic) {
                this.effectivenessDic = effectivenessDic;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getFullText() {
                return fullText;
            }

            public void setFullText(String fullText) {
                this.fullText = fullText;
            }

            public String getDocumentNO() {
                return documentNO;
            }

            public void setDocumentNO(String documentNO) {
                this.documentNO = documentNO;
            }

            public RatifyDepartmentBean getRatifyDepartment() {
                return ratifyDepartment;
            }

            public void setRatifyDepartment(RatifyDepartmentBean ratifyDepartment) {
                this.ratifyDepartment = ratifyDepartment;
            }

            public String getRatifyDate() {
                return ratifyDate;
            }

            public void setRatifyDate(String ratifyDate) {
                this.ratifyDate = ratifyDate;
            }

            public HashMap<String, String> getIssueDepartment() {
                return issueDepartment;
            }


            public String getIssueDate() {
                return issueDate;
            }

            public void setIssueDate(String issueDate) {
                this.issueDate = issueDate;
            }

            public String getImplementDate() {
                return implementDate;
            }

            public void setImplementDate(String implementDate) {
                this.implementDate = implementDate;
            }

            public HashMap<String, String> getTimelinessDic() {
                return timelinessDic;
            }




            public static class EffectivenessDicBean {
            }

            public static class RatifyDepartmentBean {
            }
        }
    }
}
