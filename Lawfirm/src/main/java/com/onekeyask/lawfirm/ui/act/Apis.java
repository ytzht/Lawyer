package com.onekeyask.lawfirm.ui.act;

/**
 * Created by zht on 2017/04/12 10:41
 */

public interface Apis {
    String API_BASE = "http://139.198.11.78:8080/mylawyer/";//调试期间， 客户端userId  传 2 ，律师端 lawyerId传 3
    String Base = "http://139.198.11.78:8080/mylawyer/";//调试期间， 客户端userId  传 2 ，律师端 lawyerId传 3
    String API_Pic = API_BASE + "pic/";//图片
    String API_HomePage = "pub/c/homePage";//post首页
    String API_Freeask = "c/freeask";//post免费咨询提交
    String API_MakeMoneyAskOrderAndGetPayInfo = "c/makeMoneyAskOrderAndGetPayInfo";//post打赏咨询提交
    String API_FreeAskCategory = "c/freeAskCategory";//post快速咨询类别
    String API_PointsInfo = "c/my/pointsInfo";//post快速咨询类别




    String SMSCode = Apis.Base + "l/SMSCode";//1.1获取手机验证码(注册)
    String Register = Apis.Base + "l/regist";//1.2注册
    String Verify = Apis.Base + "l/verify";//1.3提交律师信息验证



    String Login = Apis.Base + "l/login";//1.6登陆








    String GetSpecialInfoList = Apis.Base + "l/service/getSpecialInfoList";//1.38专长类型获取
    String SaveSpecialService = Apis.Base + "l/service/saveSpecialService";//1.39专长类别设置






}
