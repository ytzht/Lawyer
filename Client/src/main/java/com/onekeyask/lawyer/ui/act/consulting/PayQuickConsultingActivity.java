package com.onekeyask.lawyer.ui.act.consulting;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AskResult;
import com.onekeyask.lawyer.entity.PayResult;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;
import com.onekeyask.lawyer.utils.dialog.MDEditDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.nutz.lang.Strings;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_ACTION;
import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_EXTRA;
import static com.onekeyask.lawyer.global.Constant.WeChatAppID;

public class PayQuickConsultingActivity extends BaseToolBarActivity {

    @BindView(R.id.cb_ali)
    ImageView cbAli;
    @BindView(R.id.rl_ali)
    RelativeLayout rlAli;
    @BindView(R.id.cb_wx)
    ImageView cbWx;
    @BindView(R.id.rl_wx)
    RelativeLayout rlWx;
    @BindView(R.id.cb_ye)
    ImageView cbYe;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rl_ye)
    RelativeLayout rlYe;
    @BindView(R.id.btn_pay)
    TextView btnPay;
    @BindView(R.id.tv_sel_2)
    TextView tvSel2;
    @BindView(R.id.tv_sel_4)
    TextView tvSel4;
    @BindView(R.id.tv_sel_6)
    TextView tvSel6;
    @BindView(R.id.tv_sel_8)
    TextView tvSel8;

    private double selectMoney = 200;
    private int payType = 1;
    private MDEditDialog dialog;
    private ArrayList<String> photos;
    private String content;
    private int category = -1;
    private double balance = 0;

    private int oid, fid;
    //本地广播数据类型实例。
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_quick_consulting);
        ButterKnife.bind(this);

        setToolbarText("打赏咨询");
        photos = new ArrayList<>();
        //获取本地广播实例。
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //新建intentFilter并给其action标签赋值。
        intentFilter=new IntentFilter();
        intentFilter.addAction(WECHAT_PAY_RESULT_ACTION);

        //创建广播接收器实例，并注册。将其接收器与action标签进行绑定。
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);


        photos = getIntent().getStringArrayListExtra("photos");
        content = getIntent().getStringExtra("content");
        category = getIntent().getIntExtra("category", 0);


        SubscriberOnNextListener getResultOnNext = new SubscriberOnNextListener<PriceList>() {
            @Override
            public void onNext(PriceList list) {
                balance = list.getBalance();
                tvBalance.setText("(可用余额" + list.getBalance() + ")");

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getPriceList(UserService.service(getBaseContext()).getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, PayQuickConsultingActivity.this, true));


    }


    @OnClick({R.id.tv_sel_2, R.id.tv_sel_4, R.id.tv_sel_6, R.id.tv_sel_8, R.id.rl_ali, R.id.rl_wx, R.id.rl_ye, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_ali:
                payType = 1;
                cbAli.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
                break;
            case R.id.rl_wx:
                payType = 2;
                cbWx.setImageResource(R.drawable.select_c);
                cbAli.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
                break;
            case R.id.rl_ye:
                payType = 3;
                cbYe.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbAli.setImageResource(R.drawable.select);
                break;

            case R.id.btn_pay:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("正在支付...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                showShort("确认支付" + selectMoney + "支付方式" + payType);
                if (payType == 1) {
                    //PayWay.ALiPay

                    upLoadInfo();


                } else if (payType == 2) {
                    upLoadInfo();
                } else {
                    //假设余额付款成功,这里应该去付款，再付款成功的回调里上传咨询信息，上传成功进入咨询页面
                    //或者先上传咨询信息（打赏咨询的上传咨询信息包括打赏多少钱，故不可行），
                    // 这就可能会导致用户付款成功而信息上传失败，失败后如何处理？

                    if (balance >= selectMoney/100) {
                        showShort("payType " + payType + "money" + selectMoney);

                        upLoadInfo();
                    } else {
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                        showShort("余额不足");
                    }

                }
                break;

            case R.id.tv_sel_2:
                sel2();

                break;
            case R.id.tv_sel_4:
                sel4();

                break;
            case R.id.tv_sel_6:
                sel6();

                break;
            case R.id.tv_sel_8:

                dialog = new MDEditDialog.Builder(this)
                        .setTitleVisible(false)
                        .setHintText("请输入答谢金额")
                        .setMaxLines(1)
                        .setButtonTextSize(16)
                        .setLeftButtonText("取消")
                        .setRightButtonTextColor(R.color.white)
                        .setRightButtonText("确定")
                        .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                            @Override
                            public void clickLeftButton(View view, String text) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(View view, String text) {
                                if (Strings.isBlank(text)) {
                                    showShort("请输入");
                                } else {

                                    double input = (Double.parseDouble((new DecimalFormat("#.00")).format(Double.parseDouble(text)))) * 100;

                                    if (input == 0) {
                                        showShort("输入金额不能小于0.01元");
                                    } else {
                                        if (input > 20000) {
                                            showShort("输入金额不能大于200元");
                                        } else {
                                            dialog.dismiss();
                                            selectMoney = input;
                                            sel8();

                                        }
                                    }
                                }
                            }
                        })
                        .setMinHeight(0.1f)
                        .setWidth(0.8f)
                        .build();
                dialog.show();


                break;

        }
    }

    private ProgressDialog progressDialog;
    private Map<String, RequestBody> photoMap = new HashMap<>();

    //判断是否有图处理，下一步进行压缩图或提交
    private void upLoadInfo() {

        if (photos.size() > 0) {
            i = 0;
            for (int i = 0; i < photos.size(); i++) {

                goLuban();

            }
        } else {
            goSubmit();
        }
    }

    private int i = 0;
    private int k = 0;

    //压缩图并下一步准备提交
    private void goLuban() {
        i += 1;
        File file = new File(photos.get(i - 1));
        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        k += 1;
                        String key = "pic" + k + "\"; filename=\"pic" + k + photos.get(k - 1).substring(photos.get(k - 1).lastIndexOf("."));
                        photoMap.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), file));
                        L.d(key);

                        if (photoMap.size() == photos.size()) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            goSubmit();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(e.toString());
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                        showShort("第" + i + "张图片压缩出错");
                    }
                }).launch();    //启动压缩
    }

    //提交问题信息，可能有图
    private void goSubmit() {
        photoMap.put("userId", RequestBody.create(null, "2"));
        photoMap.put("content", RequestBody.create(null, content));
        photoMap.put("category", RequestBody.create(null, category + ""));


        photoMap.put("type", RequestBody.create(null, "2"));
        photoMap.put("payType", RequestBody.create(null, "" + payType));//payType
        photoMap.put("money", RequestBody.create(null, selectMoney / 100 + ""));
        L.d(payType + " " + selectMoney);

        getResultOnNext = new SubscriberOnNextListener<AskResult>() {
            @Override
            public void onNext(AskResult askResult) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                L.d(askResult.getFreeaskId() + "");
                oid = askResult.getOrderId();
                fid = askResult.getFreeaskId();
                //上传成功后的跳转
                if (payType == 1) {
                    ZfbPay(askResult.getZfbNew().getOrderPayInfoString());
                } else if (payType == 2) {

                    WePay(askResult.getWx());
                } else {
                    goNext(oid + "", fid + "");
                }


            }

            @Override
            public void onError(int code, String message) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                showShort(message);
            }
        };

        retrofitUtil.payUpload(photoMap, new ProgressSubscriber<AskResult>(getResultOnNext, PayQuickConsultingActivity.this, true));


    }



    private void WePay(AskResult.WxBean wx) {

        IWXAPI msgApi = WXAPIFactory.createWXAPI(getBaseContext(), null);
        // 将该app注册到微信
        WeChatAppID = wx.getAppid();
        msgApi.registerApp(WeChatAppID);
        PayReq request = new PayReq();
        request.appId = WeChatAppID;
        request.partnerId = wx.getPartnerId();
        request.prepayId = wx.getPrepayId();
        request.packageValue = wx.getPackageX();
        request.nonceStr = wx.getNonceStr();
        request.timeStamp = wx.getTimeStamp();
        request.sign = wx.getSign();
        msgApi.sendReq(request);

    }

    //进行支付宝调起支付
    private void ZfbPay(final String orderInfo) {
        //支付宝支付
        L.d("开始支付宝支付 ", orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayQuickConsultingActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                L.d("=====ali");
                Message msg = new Message();
                msg.what = 123;
                msg.obj = result;
                zfbHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    //支付宝支付结果回调
    private Handler zfbHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            L.d(resultInfo);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                showShort("支付成功");
                goNext(oid + "", fid + "");
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showShort("支付失败");
            }
        }
    };


    private void goNext(String orderId, final String fid) {
        startActivity(TalkingActivity.class, "fid", fid, "cid", "0", "oid", orderId);
        finish();
//        SubscriberOnNextListener<BaseResult> listener = new SubscriberOnNextListener<BaseResult>() {
//            @Override
//            public void onNext(BaseResult result) {
//                startActivity(TalkingActivity.class, "fid", fid, "cid", "0");
//                finish();
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//
//                showShort(message);
//            }
//        };
//
//        retrofitUtil.payForeTest(orderId, new ProgressSubscriber<BaseResult>(listener, PayQuickConsultingActivity.this, true));
    }


    private void sel8() {
        tvSel8.setText(String.valueOf(selectMoney / 100 + "元"));
        tvSel8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
        tvSel4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tvSel4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
    }

    private void sel6() {
        selectMoney = 600;
        tvSel8.setText("更多>>");
        tvSel6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
        tvSel4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tvSel4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
    }

    private void sel4() {
        selectMoney = 400;
        tvSel8.setText("更多>>");
        tvSel4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
        tvSel2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tvSel2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
    }

    private void sel2() {
        selectMoney = 200;
        tvSel8.setText("更多>>");
        tvSel2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
        tvSel4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
        tvSel2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tvSel4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
        tvSel8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
    }


    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            0	成功	展示成功页面
//            -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//            -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP
            switch (intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, 1)){
                case 1:
                    showShort("未知错误");
                    break;
                case 0:
                    showShort("支付成功");
                    goNext(oid + "", fid + "");
                    break;
                case -1:
                    showShort("-1\t错误\t可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。");
                    break;
                case -2:
                    showShort("订单已取消");
                    break;
            }

        }
    }

    @Override
    public void onDestroy(){//在onDestroy()方法中取消注册。
        super.onDestroy();
        //取消注册调用的是unregisterReceiver()方法，并传入接收器实例。
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

}
