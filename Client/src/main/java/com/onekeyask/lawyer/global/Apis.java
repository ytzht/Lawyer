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

    String PointsHistory = Apis.Base + "c/my/pointsHistory";//1.30 我的积分明细

    String BalanceHistory = Apis.Base + "c/my/balanceHistory";//1.31 我的钱包账目明细

    String BankCardAdd = Apis.Base + "c/wallet/bankCardAdd";//1.32 添加银行卡

    String BankCardList = Apis.Base + "c/wallet/bankCardList";//1.33 管理银行卡

    String DelBankCard = Apis.Base + "c/wallet/delBankCard";//1.34 银行卡删除

    String ApplyTX = Apis.Base + "c/wallet/applyTX";//1.35 提现

    String SMSCode = Apis.Base + "c/SMSCode";//1.39获取手机验证码(注册用)

    String Register = Apis.Base + "c/regist";//1.40注册

    String Resetpwd = Apis.Base + "c/resetpwd";//1.41找回密码

    String Login = Apis.Base + "c/login";//1.42登陆

    String Logout = Apis.Base + "c/logout";//1.43退出登陆

    String HeadPic = Apis.Base + "c/pic/head";//1.44上传头像

    String ChangeName = Apis.Base + "c/my/changeName";//1.45设置昵称

    String ChangePwd = Apis.Base + "c/my/changePwd";//1.46修改密码
}
