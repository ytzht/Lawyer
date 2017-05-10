package com.onekeyask.lawyer.http;


import com.onekeyask.lawyer.entity.AskResult;
import com.onekeyask.lawyer.entity.BaseResult;
import com.onekeyask.lawyer.entity.ChatList;
import com.onekeyask.lawyer.entity.CommonTagList;
import com.onekeyask.lawyer.entity.ConversationList;
import com.onekeyask.lawyer.entity.FreeAskCategory;
import com.onekeyask.lawyer.entity.FreeaskDetail;
import com.onekeyask.lawyer.entity.GiveMoneyOrderAndGetPayInfo;
import com.onekeyask.lawyer.entity.HomePage;
import com.onekeyask.lawyer.entity.IsFavorite;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.entity.PointsInfo;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.entity.SendMsg;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zht on 2017/04/19 17:08
 */

public class APIFactory extends RetrofitHttpUtil {

    public static APIFactory getInstance() {
        return new APIFactory();
    }


    public void getHomePage(Subscriber<HomePage> subscriber) {
        Observable<HomePage> observable = getService().getHomePage()
                .map(new HttpResultFunc<HomePage>());
        toSubscribe(observable, subscriber);
    }

    public void freeUpload(Map<String, RequestBody> map, Subscriber<AskResult> subscriber) {
        Observable<AskResult> observable = getService().freeUpload(map)
                .map(new HttpResultFunc<AskResult>());
        toSubscribe(observable, subscriber);
    }

    public void payUpload(Map<String, RequestBody> map, Subscriber<AskResult> subscriber) {
        Observable<AskResult> observable = getService().payUpload(map)
                .map(new HttpResultFunc<AskResult>());
        toSubscribe(observable, subscriber);
    }

    public void getTags(Subscriber<FreeAskCategory> subscriber) {
        Observable<FreeAskCategory> observable = getService().getTags()
                .map(new HttpResultFunc<FreeAskCategory>());
        toSubscribe(observable, subscriber);
    }

    public void getPointsInfo(String userId, Subscriber<PointsInfo> subscriber) {
        Observable<PointsInfo> observable = getService().getPointsInfo(userId)
                .map(new HttpResultFunc<PointsInfo>());
        toSubscribe(observable, subscriber);
    }

    public void getPicById(String id, Subscriber<BaseResult> subscriber) {
        Observable<BaseResult> observable = getService().getPicById(id);
        toSubscribe(observable, subscriber);
    }

    public void getConversationList(String userId, String chatId, String freeaskId, long conversationId, String direction, int size, Subscriber<ConversationList> subscriber) {
        Observable<ConversationList> observable = getService().getConversationList(userId, chatId, freeaskId, conversationId, direction, size)
                .map(new HttpResultFunc<ConversationList>());
        toSubscribe(observable, subscriber);
    }


    public void getFreeaskDetail(String userId, String freeaskId, Subscriber<FreeaskDetail> subscriber) {
        Observable<FreeaskDetail> observable = getService().getFreeaskDetail(userId, freeaskId)
                .map(new HttpResultFunc<FreeaskDetail>());
        toSubscribe(observable, subscriber);
    }

    public void getCommentLawyer(Map<String, String> map, Subscriber<BaseResult> subscriber) {
        Observable<BaseResult> observable = getService().getCommentLawyer(map);
        toSubscribe(observable, subscriber);
    }

    public void getCommonTagList(Subscriber<CommonTagList> subscriber) {
        Observable<CommonTagList> observable = getService().getCommonTagList()
                .map(new HttpResultFunc<CommonTagList>());
        toSubscribe(observable, subscriber);
    }

    public void getGiveMoneyOrderAndGetPayInfo(Map<String, String> map, Subscriber<GiveMoneyOrderAndGetPayInfo> subscriber) {
        Observable<GiveMoneyOrderAndGetPayInfo> observable = getService().getGiveMoneyOrderAndGetPayInfo(map)
                .map(new HttpResultFunc<GiveMoneyOrderAndGetPayInfo>());
        toSubscribe(observable, subscriber);
    }

    public void getIsFavorite(String userId, String lawyerId, Subscriber<IsFavorite> subscriber) {
        Observable<IsFavorite> observable = getService().getIsFavorite(userId, lawyerId)
                .map(new HttpResultFunc<IsFavorite>());
        toSubscribe(observable, subscriber);
    }

    public void getFavoriteLawyer(String userId, String lawyerId, boolean favorite, Subscriber<IsFavorite> subscriber) {
        Observable<IsFavorite> observable = getService().getFavoriteLawyer(userId, lawyerId, favorite)
                .map(new HttpResultFunc<IsFavorite>());
        toSubscribe(observable, subscriber);
    }

    public void getChatList(long userId, int page, int size, String status, int type, Subscriber<ChatList> subscriber) {
        Observable<ChatList> observable = getService().getChatList(userId, page, size, status, type)
                .map(new HttpResultFunc<ChatList>());
        toSubscribe(observable, subscriber);
    }

    public void getSendMsg(String userId, String chatId, String content, Subscriber<SendMsg> subscriber) {
        Observable<SendMsg> observable = getService().getSendMsg(userId, chatId, content)
                .map(new HttpResultFunc<SendMsg>());
        toSubscribe(observable, subscriber);
    }

    public void getSendPic(Map<String, RequestBody> map, Subscriber<SendMsg> subscriber) {
        Observable<SendMsg> observable = getService().getSendPic(map)
                .map(new HttpResultFunc<SendMsg>());
        toSubscribe(observable, subscriber);
    }


    public void payForeTest(String orderId, Subscriber<BaseResult> subscriber) {
        Observable<BaseResult> observable = getService().payForeTest(orderId);
        toSubscribe(observable, subscriber);
    }

    public void getPriceList(long serId, Subscriber<PriceList> subscriber) {
        Observable<PriceList> observable = getService().getPriceList(serId).map(new HttpResultFunc<PriceList>());
        toSubscribe(observable, subscriber);
    }

    public void getLawyerBasic(long lawyerId, Subscriber<LawyerBasic> subscriber) {
        Observable<LawyerBasic> observable = getService().getLawyerBasic(lawyerId).map(new HttpResultFunc<LawyerBasic>());
        toSubscribe(observable, subscriber);
    }

}