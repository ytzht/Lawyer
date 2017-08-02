package com.onekeyask.lawyer.ui.act.consulting;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AskResult;
import com.onekeyask.lawyer.entity.BaseResult;
import com.onekeyask.lawyer.entity.PayResult;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.dialog.MDEditDialog;
import com.onekeyask.lawyer.utils.easypay.enums.PayWay;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_quick_consulting);
        ButterKnife.bind(this);

        setToolbarText("打赏咨询");
        photos = new ArrayList<>();

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

        retrofitUtil.getPriceList(2, new ProgressSubscriber<PriceList>(getResultOnNext, PayQuickConsultingActivity.this, true));


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
                    goPay(selectMoney, PayWay.ALiPay);
                } else if (payType == 2) {
                    goPay(selectMoney, PayWay.WechatPay);
                } else {
                    //假设余额付款成功,这里应该去付款，再付款成功的回调里上传咨询信息，上传成功进入咨询页面
                    //或者先上传咨询信息（打赏咨询的上传咨询信息包括打赏多少钱，故不可行），
                    // 这就可能会导致用户付款成功而信息上传失败，失败后如何处理？

                    if (balance >= selectMoney) {
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

    private void goSubmit() {
        photoMap.put("userId", RequestBody.create(null, "2"));
        photoMap.put("content", RequestBody.create(null, content));
        photoMap.put("category", RequestBody.create(null, category + ""));


        photoMap.put("type", RequestBody.create(null, "2"));
        photoMap.put("payType", RequestBody.create(null, "1"));//payType
        photoMap.put("money", RequestBody.create(null, selectMoney + ""));
        L.d(payType + " " + selectMoney);

        getResultOnNext = new SubscriberOnNextListener<AskResult>() {
            @Override
            public void onNext(AskResult askResult) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                L.d(askResult.getFreeaskId()+"");
                //上传成功后的跳转
                goNext(askResult.getOrderId()+"", askResult.getFreeaskId()+"");

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.payUpload(photoMap, new ProgressSubscriber<AskResult>(getResultOnNext, PayQuickConsultingActivity.this, true));


    }

    private void goNext(String orderId, final String fid) {
        SubscriberOnNextListener<BaseResult> listener = new SubscriberOnNextListener<BaseResult>() {
            @Override
            public void onNext(BaseResult result) {
                startActivity(TalkingActivity.class, "fid", fid, "cid", "0");
                finish();

            }

            @Override
            public void onError(int code, String message) {

                showShort(message);
            }
        };

        retrofitUtil.payForeTest(orderId, new ProgressSubscriber<BaseResult>(listener, PayQuickConsultingActivity.this, true));
    }


    private void goPay(double selectMoney, PayWay payWay) {

        if (progressDialog.isShowing()) progressDialog.dismiss();
        new Thread() {
            @Override
            public void run() {
                super.run();
                PayTask payTask = new PayTask(PayQuickConsultingActivity.this);
                Map<String, String> result = payTask.payV2("payInfo.getPay_info()", true);
                Message message = mHandler.obtainMessage();
                message.what = 200;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }.start();

//        PayParams params = new PayParams.Builder(this)
//                .wechatAppID("your_wechat_appid")// 仅当支付方式选择微信支付时需要此参数
//                .payWay(payWay)//PayWay.WechatPay
//                .goodsPrice((int) (selectMoney * 100))// 单位为：分
//                .goodsName("皮皮虾")
//                .goodsIntroduction("此商品属性过于强大，难以调教，一般人切勿轻易购买，吼吼！")
//                .httpType(HttpType.Get)
//                .httpClientType(NetworkClientType.Retrofit)
//                .requestBaseUrl("http://blog.csdn.net/")// 此处替换为为你的app服务器host主机地址
//                .build();
//
//
//        EasyPay.newInstance(params).requestPayInfo(new OnPayInfoRequestListener() {
//            @Override
//            public void onPayInfoRequetStart() {
//            }
//
//            @Override
//            public void onPayInfoRequstSuccess() {
//                showShort("请求预支付信息成功，开始跳转到客户端支付");
//            }
//
//            @Override
//            public void onPayInfoRequestFailure() {
//                showShort("获取预支付信息失败，会同时得到一个支付失败的回调。");
//            }
//        }).toPay(new OnPayResultListener() {
//            @Override
//            public void onPaySuccess(PayWay payWay) {
//                // 支付成功
//                alert("支付成功");
//            }
//
//            @Override
//            public void onPayCancel(PayWay payWay) {
//                // 支付流程被用户中途取消
//                alert("支付流程被用户中途取消");
//            }
//
//            @Override
//            public void onPayFailure(PayWay payWay, int errCode) {
//                alert("支付失败");
//            }
//        });

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(PayQuickConsultingActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(ResultActivity.class,"type","dateReturnBiycle");
                        finish();
                    }
                });
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(PayQuickConsultingActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
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
}
