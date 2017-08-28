package com.onekeyask.lawfirm.network;

import com.onekeyask.lawfirm.entity.AskDetail;
import com.onekeyask.lawfirm.entity.ConversationChatList;
import com.onekeyask.lawfirm.entity.ConversationGetList;
import com.onekeyask.lawfirm.entity.FoundFrag;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.entity.SendCon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zht on 2017/04/13 14:56
 */

public interface LBiz {

    //1.15消息（对话）列表
    @GET("l/conversation/chatList")
    Call<ConversationChatList> getConversationChatList(@Query("lawyerId") long lawyerId,//律师ID
                                                       @Query("page") int page,//页数
                                                       @Query("size") int size,//行数
                                                       @Query("type") int type);//对话类型 0或不传-全部 1-图文订单 2-免费提问 3-打赏咨询

//    //1.24完成订单
//    @FormUrlEncoded
//    @POST("l/order/finshedOrder")
//    Call<BaseResult> getFinshedOrder(@FieldMap Map<String, String> params);

    //1.29获取我的对话列表
    @GET("l/conversation/getList")
    Call<ConversationGetList> getConversationList(@Query("lawyerId") long lawyerId,//律师ID
                                                  @Query("chatId") String chatId,//订单ID
                                                  @Query("direction") int direction,//数据方向  0向上 1向下
                                                  @Query("size") int size);//条数

    //1.30发送对话
    @GET("l/conversation/send")
    Call<SendCon> getSendCon(@Query("lawyerId") long lawyerId,//律师ID
                             @Query("chatId") String chatId,//订单ID
                             @Query("content") String content);

    //1.31 免费提问列表，未接单状态的
    @GET("l/freeask/list")
    Call<FoundFrag> getFoundFragList(@Query("lawyerId") long lawyerId,//律师ID
                                     @Query("page") int page,//页数
                                     @Query("size") int size);//行数

    //1.32 免费提问详情
    @GET("l/freeask/detail")
    Call<AskDetail> getAskDetail(@Query("freeaskId") String freeaskId,//免费提问Id
                                 @Query("lawyerId") long lawyerId);//律师ID

    //1.33 免费提问抢单
    @GET("l/freeask/order")
    Call<FreeAskOrder> getFreeAskOrder(@Query("freeaskId") String freeaskId,//免费提问Id
                                       @Query("lawyerId") long lawyerId);//律师id

//    //1.34完成对话咨询
//    @GET("l/conversation/finishConversation")
//    Call<BaseResult> getFinishCon(@Query("lawyerId") long lawyerId,//律师ID
//                                  @Query("chatId") String chatId);//订单ID



















//    //1.1首页
//    @POST("pub/c/homePage")
//    Call<HomePage> getHomePage();
//
//    //1.4快速咨询类别
//    @POST("c/freeAskCategory")
//    Call<FreeAskCategory> getTags();
//
//    //1.2免费咨询提交
//    @Multipart
//    @POST("c/freeask")
//    Call<AskResult> freeUpload(@PartMap Map<String, RequestBody> params);
//
//    //1.3打赏咨询提交
//    @Multipart
//    @POST("c/makeMoneyAskOrderAndGetPayInfo")
//    Call<AskResult> payUpload(@PartMap Map<String, RequestBody> params);
//
//    //1.5免费咨询价格和用户当前积分
//    @POST("c/my/pointsInfo")
//    Call<PointsInfo> getPointsInfo(@Query("userId") String userId);
//
//    //1.6获取id对应的图片
//    @GET("pic/{picId}")
//    Call<BaseResult> getPicById(@Path("picId") String id);
//
//    //1.7获取单个会话列表
//    @GET("c/conversation/getList")
//    Call<ConversationList> getConversationList(@Query("userId") String userId,
//                                               @Query("chatId") String chatId,
//                                               @Query("freeaskId") String freeaskId,
//                                               @Query("direction") String direction,
//                                               @Query("size") String size);
//
//    //1.8获取免费提问详情
//    @GET("c/freeaskDetail")
//    Call<FreeaskDetail> getFreeaskDetail(@Query("userId") String userId,
//                                         @Query("freeaskId") String freeaskId);//免费提问Id



}
