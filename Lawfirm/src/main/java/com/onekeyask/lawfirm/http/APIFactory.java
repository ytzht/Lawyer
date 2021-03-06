package com.onekeyask.lawfirm.http;


import com.onekeyask.lawfirm.entity.AskDetail;
import com.onekeyask.lawfirm.entity.BaseResult;
import com.onekeyask.lawfirm.entity.ConversationChatList;
import com.onekeyask.lawfirm.entity.ConversationGetList;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.entity.HomePage;
import com.onekeyask.lawfirm.entity.FoundBean;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.entity.SendCon;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zht on 2017/04/19 17:08
 */

public class APIFactory extends RetrofitHttpUtil{

    public static APIFactory getInstance() {
        return new APIFactory();
    }

    public void getHomePage(int lawyerId, Subscriber<HomePage> subscriber){
        Observable<HomePage> observable = getService().getHomePage(lawyerId)
                .map(new HttpResultFunc<HomePage>());
        toSubscribe(observable, subscriber);
    }

    public void getConversationChatList(long lawyerId, int page, int size, String status, int type, Subscriber<ConversationChatList> subscriber){
        Observable<ConversationChatList> observable = getService().getConversationChatList(lawyerId, page, size, status, type)
                .map(new HttpResultFunc<ConversationChatList>());
        toSubscribe(observable, subscriber);
    }

    public void getConversationList(long lawyerId, String chatId, long conversationId, String direction, int size, Subscriber<ConversationGetList> subscriber){
        Observable<ConversationGetList> observable = getService().getConversationList(lawyerId, chatId, conversationId, direction, size)
                .map(new HttpResultFunc<ConversationGetList>());
        toSubscribe(observable, subscriber);
    }

    public void getSendCon(long lawyerId, String chatId, String content, Subscriber<SendCon> subscriber){
        Observable<SendCon> observable = getService().getSendCon(lawyerId, chatId, content)
                .map(new HttpResultFunc<SendCon>());
        toSubscribe(observable, subscriber);
    }

    public void getFreeaskOldlist(long lawyerId, int page, int size, Subscriber<FoundBean.DataBean> subscriber){
        Observable<FoundBean.DataBean> observable = getService().getFoundOldFragList(lawyerId, page, size)
                .map(new HttpResultFunc<FoundBean.DataBean>());
        toSubscribe(observable, subscriber);
    }

    public void getFreeaskNewlist(long lawyerId, int page, int size, Subscriber<FoundBean.DataBean> subscriber){
        Observable<FoundBean.DataBean> observable = getService().getFoundNewFragList(lawyerId, page, size)
                .map(new HttpResultFunc<FoundBean.DataBean>());
        toSubscribe(observable, subscriber);
    }

    public void getFoundHotFragList(long lawyerId, int page, int size, Subscriber<FoundBean.DataBean> subscriber){
        Observable<FoundBean.DataBean> observable = getService().getFoundHotFragList(lawyerId, page, size)
                .map(new HttpResultFunc<FoundBean.DataBean>());
        toSubscribe(observable, subscriber);
    }

    public void getAskDetail(String freeaskId, long lawyerId, Subscriber<AskDetail> subscriber){
        Observable<AskDetail> observable = getService().getAskDetail(freeaskId, lawyerId)
                .map(new HttpResultFunc<AskDetail>());
        toSubscribe(observable, subscriber);
    }

    public void getFreeAskOrder(String freeaskId, long lawyerId, Subscriber<FreeAskOrder> subscriber) {
        Observable<FreeAskOrder> observable = getService().getFreeAskOrder(freeaskId, lawyerId)
                .map(new HttpResultFunc<FreeAskOrder>());
        toSubscribe(observable, subscriber);
    }

    public void getSendPic(Map<String, RequestBody> map, Subscriber<SendCon> subscriber) {
        Observable<SendCon> observable = getService().getSendPic(map)
                .map(new HttpResultFunc<SendCon>());
        toSubscribe(observable, subscriber);
    }

    public void gotoVerify(Map<String, RequestBody> map, Subscriber<ResultData> subscriber) {
        Observable<ResultData> observable = getService().gotoVerify(map);
        toSubscribe(observable, subscriber);
    }


    public void getSubmitadvice(Map<String, RequestBody> map, Subscriber<BaseResult> subscriber) {
        Observable<BaseResult> observable = getService().getSubmitadvice(map);
        toSubscribe(observable, subscriber);
    }




}