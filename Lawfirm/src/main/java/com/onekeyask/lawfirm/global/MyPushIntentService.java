package com.onekeyask.lawfirm.global;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MsgPoints;
import com.onekeyask.lawfirm.ui.act.MainActivity;
import com.onekeyask.lawfirm.ui.act.found.FoundDetailActivity;
import com.onekeyask.lawfirm.ui.act.me.BillingDetailsActivity;
import com.onekeyask.lawfirm.ui.act.service.CallDetailActivity;
import com.onekeyask.lawfirm.ui.act.service.TalkingActivity;
import com.onekeyask.lawfirm.ui.act.user.IncomeDetailActivity;
import com.onekeyask.lawfirm.ui.act.user.TopMsgActivity;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by zht on 2017/08/29 9:48
 */

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName() + "=====";


    private static int NOTIFICATION_ID = 0x123;

    NotificationManager nm;


    @Override
    public void onMessage(Context context, Intent intent) {

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NOTIFICATION_ID += 1;
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            UmLog.d(TAG, "message=" + message);      //消息体
            UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            UmLog.d(TAG, "title=" + msg.title);      //通知标题
            UmLog.d(TAG, "text=" + msg.text);        //通知内容
            // code  to handle message here
            sendNotific(msg);

            // 对完全自定义消息的处理方式，点击或者忽略
//            boolean isClickOrDismissed = true;
//            if (isClickOrDismissed) {
//                //完全自定义消息的点击统计
//                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//            } else {
//                //完全自定义消息的忽略统计
//                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//            }

            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
//            JSONObject json = new JSONObject(msg.custom);
//            String topic = json.getString("topic");
//            UmLog.d(TAG, "topic=" + topic);
//            if (topic != null && topic.equals("appName:startService")) {
//                // 在友盟+portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:startService"}
////                if (Helper.isServiceRunning(context, NotificationService.class.getName()))
////                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.startService(intent1);
//            } else if (topic != null && topic.equals("appName:stopService")) {
//                // 在友盟+portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:stopService"}
//                if (!Helper.isServiceRunning(context,NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.stopService(intent1);
//            }
        } catch (Exception e) {
            UmLog.e(TAG, e.getMessage());
        }


    }

    private boolean isChat = false;
    private boolean isShow = true;

    private void sendNotific(UMessage msg) {

        MsgPoints msgPoints = (new Gson()).fromJson(msg.custom, MsgPoints.class);
        Intent intent;
        PendingIntent pi;
        switch (msgPoints.getActivity()) {
            case "ChatInfoNotification":
                isChat = true;
                isShow = true;
                //跳转到聊天详情    “chatId”:聊天Id
                intent = new Intent(MyPushIntentService.this, TalkingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cid", msgPoints.getData().getChatId()+"");
                intent.putExtra("chatId", msgPoints.getData().getChatId()+"");
                L.d("custom="+ msgPoints.getData().getChatId());
                break;
            case "UserServiceInfoNotification":
                isChat = true;
                isShow = true;
                //跳转到聊天详情    “chatId”:聊天Id
                intent = new Intent(MyPushIntentService.this, TalkingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cid", msgPoints.getData().getTargetId());
                intent.putExtra("chatId", msgPoints.getData().getTargetId());
                L.d("custom="+ msgPoints.getData().getTargetId());
                break;
            case "UserServiceEndNotification":
                isChat = true;
                isShow = true;
                //跳转到聊天详情 律所端的聊天结束带 评价页面的页面    “chatId”:聊天Id
                intent = new Intent(MyPushIntentService.this, TalkingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cid", msgPoints.getData().getTargetId());
                intent.putExtra("chatId", msgPoints.getData().getTargetId());
                L.d("custom="+ msgPoints.getData().getTargetId());
                break;
//            case "UserServiceInfoNotification":
//                //“targetId”:对应的聊天标识,
//                //“serviceType”:服务类型
//                //根据服务类型跳转到相应的服务详情。（目前只有聊天，未来会有电话咨询）
//                intent = new Intent(MyPushIntentService.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("chatId", msgPoints.getData().getTargetId());
//                break;
//            case "UserServiceEvaNotification":
//                //“userServiceId”:对应的服务标识,
//                //“lawyerId”:律师Id
//                //跳转到服务评价页
//                intent = new Intent(MyPushIntentService.this, EvaluateLawyerActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("userServiceId", msgPoints.getData().getUserServiceId());
//                intent.putExtra("lawyerId",  msgPoints.getData().getLawyerId());
//                break;
            case "VerifyNotification":
                isChat = false;
                isShow = true;
                //“messageId”:””
                //参数值为对应的消息中心的Id,
                //点击跳转到积分明细，且同时请求1.50接口
                intent = new Intent(MyPushIntentService.this, TopMsgActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", msgPoints.getData().getMessageId());

                break;
            case "BalanceIncreaseNotification":
                isChat = false;
                isShow = true;
                //律师的余额发生增加的通知，点击进入收入明细
                intent = new Intent(MyPushIntentService.this, IncomeDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", msgPoints.getData().getMessageId());
                break;
            case "BalanceDecreaseNotification":
                isChat = false;
                isShow = true;
                //律师的余额发生减少的通知，点击进入提现明细
                intent = new Intent(MyPushIntentService.this, BillingDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", msgPoints.getData().getMessageId());
                break;
            case "Call_UserServiceInfoNotification":
                isChat = false;
                isShow = true;
                //电话咨询开通通知
                intent = new Intent(MyPushIntentService.this, CallDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userServiceId", msgPoints.getData().getTargetId());
                intent.putExtra("oid", "0");
                break;
            case "Call_UserServiceEndNotification":
                isChat = false;
                isShow = true;
                //电话咨询结束通知
                intent = new Intent(MyPushIntentService.this, CallDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", msgPoints.getData().getTargetId());
                intent.putExtra("oid", "0");
                break;
            case "NewFreeAskNotification":
                isChat = false;
                isShow = true;
                intent = new Intent(MyPushIntentService.this, FoundDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fid", msgPoints.getData().getTargetId());
                break;
            default:
                isChat = false;
                isShow = false;
                intent = new Intent(MyPushIntentService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                break;
        }
        pi = PendingIntent.getActivity(MyPushIntentService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        EventBus.getDefault().post(BaseEvent.event(BaseEvent.NOTIFICATION_MSG));
        Notification notify = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker(msg.ticker)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg.title)
                .setContentText(msg.text)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .build();
        if (isChat) {
            if (Constant.ChatId.equals("") || !Constant.ChatId.equals(msgPoints.getData().getChatId()+"")) {
                if (isShow)
                nm.notify(msgPoints.getData().getChatId(), notify);
            }
        } else {
            if (isShow)
            nm.notify(NOTIFICATION_ID, notify);
        }

    }

}