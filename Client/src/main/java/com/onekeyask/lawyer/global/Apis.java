package com.onekeyask.lawyer.global;

/**
 * Created by zht on 2017/08/03 16:58
 */

public interface Apis {

    String Base = "http://139.198.11.78:8080/mylawyer/";

    String FreeAskCategory = Apis.Base + "c/freeAskCategory";//1.4快速咨询类别

    String LawyerDetail = Apis.Base + "c/lawyer";//1.18 律师详情页

    String CommentList = Apis.Base + "c/getCommentList";//1.19 获取律师的被评价列表  无数据

    String GiveMoneyList = Apis.Base + "c/getGiveMoneyList";//1.20 获取律师的送心意列表  数据不正确，不知怎么用

    String PersonalLawyerServiceInfo = Apis.Base + "c/personalLawyerServiceInfo";//1.21 私人律师服务购买页

    String TextChatServiceInfo = Apis.Base + "c/textChatServiceInfo";//1.22 私人律师服务购买页

    String MakeOrderAndGetPayInfo = Apis.Base + "c/makeOrderAndGetPayInfo";//1.23 订单支付

    String MyLawyerList = Apis.Base + "c/myLawyerList";//1.24 我的律师

    String Discovery = Apis.Base + "c/discovery";//1.25 发现列表

    String GetDiscoveryDetail = Apis.Base + "c/conversation/getDiscoveryDetail";//1.26 发现内容详情

    String IsSupportUserService = Apis.Base + "c/isSupportUserService";//1.27 是否点赞某发现

    String SupportUserService = Apis.Base + "c/supportUserService";//1.28 用户点赞/取消点赞某发现

    String LawyerList = Apis.Base + "c/lawyerList";//1.29 找律师查询接口

}
