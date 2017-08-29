package com.onekeyask.lawfirm.global;

/**
 * Created by zht on 2017/04/12 10:41
 */

public interface Apis {
    String API_BASE = "http://139.198.11.78:8080/mylawyer/";//调试期间， 客户端userId  传 2 ，律师端 lawyerId传 3
    String Base = "http://139.198.11.78:8080/mylawyer/";//调试期间， 客户端userId  传 2 ，律师端 lawyerId传 3

    String SMSCode = Apis.Base + "l/SMSCode";//1.1获取手机验证码(注册)
    String Register = Apis.Base + "l/regist";//1.2注册
    String GotoVerify = Apis.Base + "l/gotoVerify";//1.4进入验证页
    String Resetpwd = Apis.Base + "l/resetpwd";//1.5重置密码
    String Login = Apis.Base + "l/login";//1.6登陆
    String Logout = Apis.Base + "l/logout";//1.7退出登陆
    String IntoSetting = Apis.Base + "l/service/intoSetting";//1.9进入服务设置
    String SaveSwitch = Apis.Base + "l/service/saveswitch";//1.10设置服务开关保存
    String Saveprice = Apis.Base + "l/service/saveprice";//1.11设置服务费用保存

    String Getswitch = Apis.Base + "l/message/getswitch";//1.20消息通知设置开关状态获取
    String Saveswitch = Apis.Base + "l/message/saveswitch";//1.21消息通知设置开关状态保存
    String MessageList = Apis.Base + "l/message/list";//1.22消息页面
    String MessageDetail = Apis.Base + "l/message/detail";//1.23消息详情页
    String MsgDelete = Apis.Base + "l/message/delete";//1.24删除消息
    String MyWallet = Apis.Base + "l/wallet/myMoney";//1.25 我的钱包
    String IncomeAll = Apis.Base + "l/order/incomeAll";//1.26律师收入总额
    String IncomeDetail = Apis.Base + "l/order/incomeDetail";//1.27收入明细
    String BankCardAdd = Apis.Base + "l/wallet/bankCardAdd";//1.28添加银行卡
    String BankCardList = Apis.Base + "l/wallet/bankCardList";//1.29 管理银行卡
    String DelBankCard = Apis.Base + "l/wallet/delBankCard";//1.30 银行卡删除
    String ApplyTX = Apis.Base + "l/wallet/applyTX";//1.31 提交提现申请
    String ProgressTX = Apis.Base + "l/wallet/progressTX";//1.32提现进度
    String TxHistory = Apis.Base + "l/wallet/txHistory";//1.33提现历史明细
    String HeadPic = Apis.Base + "l/pic/head";//1.35上传头像
    String ChangePwd = Apis.Base + "l/changePwd";//1.36修改密码
    String SaveIntroduce = Apis.Base + "l/saveIntroduce";//1.37 保存律师介绍
    String GetSpecialInfoList = Apis.Base + "l/service/getSpecialInfoList";//1.38专长类型获取
    String SaveSpecialService = Apis.Base + "l/service/saveSpecialService";//1.39专长类别设置
    String Checkupdate = Apis.Base + "pub/c/checkupdate";//1.41系统检查更新
    String GetRed = Apis.Base + "c/getRed";//1.42 小红点
















}