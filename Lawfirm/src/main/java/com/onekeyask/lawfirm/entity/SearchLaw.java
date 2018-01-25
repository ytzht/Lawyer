package com.onekeyask.lawfirm.entity;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ytzht on 2017/11/08 下午9:19
 */

public class SearchLaw {
    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"hasMore":true,"laws":[{"issueDate":"2007.05.18","implementDate":"2007.05.19","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"91493","fullText":null,"documentNO":"建金管[2007]123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60214":"建设部(已撤销) "},"category":{"074":"房地产","07401":"房地产综合规定","037":"银行","03705":"银行业务","0370502":"贷款","0370508":"利率"},"title":"建设部关于调整个人住房公积金存贷款利率的通知(建金管[2007]<span class='hitClass'>123<\/span>号)"},{"issueDate":"2010.08.11","implementDate":"2010.08.11","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"136523","fullText":null,"documentNO":"","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","603":"各委","60307":"中国证券监督管理委员会"},"category":{"040":"证券","04008":"证券监督管理机构与市场监管"},"title":"中国证券监督管理委员会发审委2010年第<span class='hitClass'>123<\/span>次会议审核结果公告"},{"issueDate":"2009.12.02","implementDate":"2009.12.02","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"124290","fullText":null,"documentNO":"财政部公告2009年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60210":"财政部"},"category":{"040":"证券","04004":"债券","031":"财政","03102":"国债管理"},"title":"财政部公告2009年第<span class='hitClass'>123<\/span>号\u2015\u2015发行2009年记账式附息(三十一期)国债"},{"issueDate":"2016.08.11","implementDate":"2016.08.11","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0304":"XE0304"},"gid":"289324","fullText":null,"documentNO":"银函[2016]230号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","607":"各银行","60701":"中国人民银行"},"category":{"037":"银行","03702":"银行综合规定"},"title":"中国人民银行对十二届全国人大四次会议第<span class='hitClass'>123<\/span>号议案的答复"},{"issueDate":"2013.12.13","implementDate":"2013.12.13","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"216012","fullText":null,"documentNO":"体竞字[2013]187号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","604":"各局","60407":"国家体育总局"},"category":{"098":"体育","09801":"体育综合规定"},"title":"国家体育总局关于授予吕隆劲等<span class='hitClass'>123<\/span>人运动健将称号的通知"},{"issueDate":"2011.09.12","implementDate":"2011.09.12","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"158535","fullText":null,"documentNO":"国测国发[2011]46号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"7":"中央其他机构","706":"其他","70603":"各局(其他)","7060312":"国家测绘地理信息局(原国家测绘局)"},"category":{"027":"测绘","02701":"测绘综合规定","088":"人事","08803":"职位职称"},"title":"国家测绘地理信息局国土测绘司关于延长苏东英等<span class='hitClass'>123<\/span>人测绘计量检定员证有效期的通知"},{"issueDate":"2016.07.15","implementDate":"2016.07.15","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"275904","fullText":null,"documentNO":"公开市场业务交易公告[2016]第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","607":"各银行","60701":"中国人民银行"},"category":{"037":"银行","03705":"银行业务","0370508":"利率"},"title":"公开市场业务交易公告[2016]第<span class='hitClass'>123<\/span>号\u2015\u2015关于人民银行以利率招标方式开展了逆回购操作的公告"},{"issueDate":"2006.07.14","implementDate":"2006.07.14","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"218910","fullText":null,"documentNO":"国家发展和改革委员会公告2006年第43号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","603":"各委","60301":"国家发展和改革委员会(含原国家发展计划委员会、原国家计划委员会)"},"category":{"050":"工业管理","05001":"机械工业","0500101":"机械工业管理"},"title":"国家发展和改革委员会公告2006年第43号\u2015\u2015车辆生产企业及产品(第<span class='hitClass'>123<\/span>批)"},{"issueDate":"2006.01.16","implementDate":"2006.01.16","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"73159","fullText":null,"documentNO":"商务部公告2005年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60220":"商务部"},"category":{"075":"商贸物资","07510":"倾销与反倾销"},"title":"商务部公告2005年第<span class='hitClass'>123<\/span>号\u2014\u2014初级形态二甲基环体硅氧烷反倾销调查案终裁公告"},{"issueDate":"2013.08.27","implementDate":"2013.08.27","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"211593","fullText":null,"documentNO":"住房和城乡建设部公告第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60238":"住房和城乡建设部"},"category":{"049":"建设业","04906":"建筑安装施工"},"title":"住房和城乡建设部公告第<span class='hitClass'>123<\/span>号\u2015\u2015关于2013年度第十批绿色建筑评价标识项目的公告"}]}
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
         * hasMore : true
         * laws : [{"issueDate":"2007.05.18","implementDate":"2007.05.19","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"91493","fullText":null,"documentNO":"建金管[2007]123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60214":"建设部(已撤销) "},"category":{"074":"房地产","07401":"房地产综合规定","037":"银行","03705":"银行业务","0370502":"贷款","0370508":"利率"},"title":"建设部关于调整个人住房公积金存贷款利率的通知(建金管[2007]<span class='hitClass'>123<\/span>号)"},{"issueDate":"2010.08.11","implementDate":"2010.08.11","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"136523","fullText":null,"documentNO":"","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","603":"各委","60307":"中国证券监督管理委员会"},"category":{"040":"证券","04008":"证券监督管理机构与市场监管"},"title":"中国证券监督管理委员会发审委2010年第<span class='hitClass'>123<\/span>次会议审核结果公告"},{"issueDate":"2009.12.02","implementDate":"2009.12.02","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"124290","fullText":null,"documentNO":"财政部公告2009年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60210":"财政部"},"category":{"040":"证券","04004":"债券","031":"财政","03102":"国债管理"},"title":"财政部公告2009年第<span class='hitClass'>123<\/span>号\u2015\u2015发行2009年记账式附息(三十一期)国债"},{"issueDate":"2016.08.11","implementDate":"2016.08.11","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0304":"XE0304"},"gid":"289324","fullText":null,"documentNO":"银函[2016]230号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","607":"各银行","60701":"中国人民银行"},"category":{"037":"银行","03702":"银行综合规定"},"title":"中国人民银行对十二届全国人大四次会议第<span class='hitClass'>123<\/span>号议案的答复"},{"issueDate":"2013.12.13","implementDate":"2013.12.13","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"216012","fullText":null,"documentNO":"体竞字[2013]187号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","604":"各局","60407":"国家体育总局"},"category":{"098":"体育","09801":"体育综合规定"},"title":"国家体育总局关于授予吕隆劲等<span class='hitClass'>123<\/span>人运动健将称号的通知"},{"issueDate":"2011.09.12","implementDate":"2011.09.12","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"158535","fullText":null,"documentNO":"国测国发[2011]46号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"7":"中央其他机构","706":"其他","70603":"各局(其他)","7060312":"国家测绘地理信息局(原国家测绘局)"},"category":{"027":"测绘","02701":"测绘综合规定","088":"人事","08803":"职位职称"},"title":"国家测绘地理信息局国土测绘司关于延长苏东英等<span class='hitClass'>123<\/span>人测绘计量检定员证有效期的通知"},{"issueDate":"2016.07.15","implementDate":"2016.07.15","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"275904","fullText":null,"documentNO":"公开市场业务交易公告[2016]第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","607":"各银行","60701":"中国人民银行"},"category":{"037":"银行","03705":"银行业务","0370508":"利率"},"title":"公开市场业务交易公告[2016]第<span class='hitClass'>123<\/span>号\u2015\u2015关于人民银行以利率招标方式开展了逆回购操作的公告"},{"issueDate":"2006.07.14","implementDate":"2006.07.14","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0302":"部门规范性文件"},"gid":"218910","fullText":null,"documentNO":"国家发展和改革委员会公告2006年第43号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","603":"各委","60301":"国家发展和改革委员会(含原国家发展计划委员会、原国家计划委员会)"},"category":{"050":"工业管理","05001":"机械工业","0500101":"机械工业管理"},"title":"国家发展和改革委员会公告2006年第43号\u2015\u2015车辆生产企业及产品(第<span class='hitClass'>123<\/span>批)"},{"issueDate":"2006.01.16","implementDate":"2006.01.16","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"73159","fullText":null,"documentNO":"商务部公告2005年第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60220":"商务部"},"category":{"075":"商贸物资","07510":"倾销与反倾销"},"title":"商务部公告2005年第<span class='hitClass'>123<\/span>号\u2014\u2014初级形态二甲基环体硅氧烷反倾销调查案终裁公告"},{"issueDate":"2013.08.27","implementDate":"2013.08.27","timelinessDic":{"01":"现行有效"},"effectivenessDic":{"XE03":"部门规章","XE0303":"XE0303"},"gid":"211593","fullText":null,"documentNO":"住房和城乡建设部公告第123号","ratifyDepartment":{},"ratifyDate":"","issueDepartment":{"6":"国务院各机构","602":"各部","60238":"住房和城乡建设部"},"category":{"049":"建设业","04906":"建筑安装施工"},"title":"住房和城乡建设部公告第<span class='hitClass'>123<\/span>号\u2015\u2015关于2013年度第十批绿色建筑评价标识项目的公告"}]
         */

        private boolean hasMore;
        private List<LawsBean> laws;

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<LawsBean> getLaws() {
            return laws;
        }

        public void setLaws(List<LawsBean> laws) {
            this.laws = laws;
        }

        public static class LawsBean {
            /**
             * issueDate : 2007.05.18
             * implementDate : 2007.05.19
             * timelinessDic : {"01":"现行有效"}
             * effectivenessDic : {"XE03":"部门规章","XE0302":"部门规范性文件"}
             * gid : 91493
             * fullText : null
             * documentNO : 建金管[2007]123号
             * ratifyDepartment : {}
             * ratifyDate :
             * issueDepartment : {"6":"国务院各机构","602":"各部","60214":"建设部(已撤销) "}
             * category : {"074":"房地产","07401":"房地产综合规定","037":"银行","03705":"银行业务","0370502":"贷款","0370508":"利率"}
             * title : 建设部关于调整个人住房公积金存贷款利率的通知(建金管[2007]<span class='hitClass'>123</span>号)
             */

            private String issueDate;
            private String implementDate;
            private LinkedHashMap<String, String> effectivenessDic;
            private String gid;
            private Object fullText;
            private String documentNO;
            private LinkedHashMap<String, String> ratifyDepartment;
            private String ratifyDate;
            private LinkedHashMap<String, String> issueDepartment;
            private LinkedHashMap<String, String> category;
            private String title;

            private LinkedHashMap<String, String> timelinessDic = new LinkedHashMap<String, String>();
            public LinkedHashMap<String, String> getTimelinessDic() {
                return timelinessDic;
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

            public LinkedHashMap<String, String> getEffectivenessDic() {
                return effectivenessDic;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public Object getFullText() {
                return fullText;
            }

            public void setFullText(Object fullText) {
                this.fullText = fullText;
            }

            public String getDocumentNO() {
                return documentNO;
            }

            public void setDocumentNO(String documentNO) {
                this.documentNO = documentNO;
            }

            public LinkedHashMap<String, String> getRatifyDepartment() {
                return ratifyDepartment;
            }


            public String getRatifyDate() {
                return ratifyDate;
            }

            public void setRatifyDate(String ratifyDate) {
                this.ratifyDate = ratifyDate;
            }

            public LinkedHashMap<String, String> getIssueDepartment() {
                return issueDepartment;
            }

            public LinkedHashMap<String, String> getCategory() {
                return category;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }
    }
}
