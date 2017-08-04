package com.onekeyask.lawyer.global;

/**
 * Created by zht on 2017/08/03 16:58
 */

public interface Apis {

    String Base = "http://139.198.11.78:8080/mylawyer/";

    String LawyerInfo = Apis.Base + "c/lawyer";//1.18 律师详情页

    String MyLawyerList = Apis.Base + "c/myLawyerList";//1.24 我的律师

}
