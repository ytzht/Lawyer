package com.onekeyask.lawfirm.global;


import com.onekeyask.lawfirm.entity.AskDetail;
import com.onekeyask.lawfirm.entity.BaseResult;
import com.onekeyask.lawfirm.entity.ConversationChatList;
import com.onekeyask.lawfirm.entity.ConversationGetList;
import com.onekeyask.lawfirm.entity.FoundFrag;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.entity.HomePage;
import com.onekeyask.lawfirm.entity.SendCon;
import com.onekeyask.lawfirm.http.HttpResult;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zht on 2017/04/19 17:09
 */

public interface APIService {

    //1.8首页
    @GET("l/homepage")
    Observable<HttpResult<HomePage>> getHomePage(@Query("lawyerId") String lawyerId);//律师ID

    //1.15消息（对话）列表
    @GET("l/conversation/chatList")
    Observable<HttpResult<ConversationChatList>> getConversationChatList(@Query("lawyerId") long lawyerId,//律师ID
                                                                         @Query("page") int page,//页数
                                                                         @Query("size") int size,//行数
                                                                         @Query("status") String status,
                                                                         @Query("type") int type);//对话类型 0或不传-全部 1-图文订单 2-免费提问 3-打赏咨询

    //1.24完成订单
    @FormUrlEncoded
    @POST("l/order/finshedOrder")
    Observable<BaseResult> getFinshedOrder(@FieldMap Map<String, String> params);

    //1.29获取我的对话列表
    @GET("l/conversation/getList")
    Observable<HttpResult<ConversationGetList>> getConversationList(@Query("lawyerId") long lawyerId,//律师ID
                                                                    @Query("chatId") String chatId,//订单ID
                                                                    @Query("conversationId") long conversationId,
                                                                    @Query("direction") String direction,//数据方向  0向上 1向下
                                                                    @Query("size") int size);//条数

    //1.30发送对话
    @FormUrlEncoded
    @POST("l/conversation/send")
    Observable<HttpResult<SendCon>> getSendCon(@Field("lawyerId") long lawyerId,//律师ID
                                               @Field("chatId") String chatId,//订单ID
                                               @Field("content") String content);
    @Multipart
    @POST("l/conversation/send")
    Observable<HttpResult<SendCon>> getSendPic(@PartMap Map<String, RequestBody> params);

    //1.31 免费提问列表，未接单状态的
    @GET("l/freeask/list")
    Observable<HttpResult<FoundFrag>> getFoundFragList(@Query("lawyerId") long lawyerId,//律师ID
                                                       @Query("page") int page,//页数
                                                       @Query("size") int size);//行数

    //1.32 免费提问详情
    @GET("l/freeask/detail")
    Observable<HttpResult<AskDetail>> getAskDetail(@Query("freeaskId") String freeaskId,//免费提问Id
                                                   @Query("lawyerId") long lawyerId);//律师ID

    //1.33 免费提问抢单
    @GET("l/freeask/order")
    Observable<HttpResult<FreeAskOrder>> getFreeAskOrder(@Query("freeaskId") String freeaskId,//免费提问Id
                                                         @Query("lawyerId") long lawyerId);//律师id

    //1.34完成对话咨询
    @GET("l/conversation/finishConversation")
    Observable<BaseResult> getFinishCon(@Query("lawyerId") long lawyerId,//律师ID
                                        @Query("chatId") String chatId);//订单ID
}
