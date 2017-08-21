package com.onekeyask.lawyer.ui.act.lawyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AliPayResult;
import com.onekeyask.lawyer.entity.PayResult;
import com.onekeyask.lawyer.entity.PersonalLawyer;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_ACTION;
import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_EXTRA;
import static com.onekeyask.lawyer.global.Constant.WeChatAppID;

public class PersonLawyerActivity extends BaseToolBarActivity {

    @BindView(R.id.civ_eva_lawyer)
    CircleImageView civEvaLawyer;
    @BindView(R.id.tv_name_eva)
    TextView tvNameEva;
    @BindView(R.id.tv_skilled)
    TextView tvSkilled;
    @BindView(R.id.rl_info_header)
    RelativeLayout rlInfoHeader;
    @BindView(R.id.combo_rlv)
    RecyclerView comboRlv;
    @BindView(R.id.tv_time_down)
    TextView tvTimeDown;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.cb_ali)
    ImageView cbAli;
    @BindView(R.id.rl_ali)
    RelativeLayout rlAli;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.cb_wx)
    ImageView cbWx;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_wx)
    RelativeLayout rlWx;
    @BindView(R.id.cb_ye)
    ImageView cbYe;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rl_ye)
    RelativeLayout rlYe;
    @BindView(R.id.notice_ll)
    LinearLayout noticeLl;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.btn_pay)
    TextView btnPay;

    private ComboAdapter adapter;
    private int priceId;
    private int price;
    private String lawyerId;
    private double balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_lawyer);
        ButterKnife.bind(this);
        setToolbarText("购买私人律师服务");
        lawyerId = getIntent().getStringExtra("lawyerId");

        initData();

        initClick();

        initBalance();

    }

    private void initBalance() {
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

        retrofitUtil.getPriceList(UserService.service(getBaseContext()).getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, PersonLawyerActivity.this, true));

    }


    int payType = 1;//1支付宝2微信3余额

    private void initClick() {
        rlAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                cbAli.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
            }
        });

        rlWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 2;
                cbAli.setImageResource(R.drawable.select);
                cbWx.setImageResource(R.drawable.select_c);
                cbYe.setImageResource(R.drawable.select);
            }
        });

        rlYe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 3;
                cbAli.setImageResource(R.drawable.select);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select_c);
            }
        });

        noticeLl.setVisibility(View.VISIBLE);

        String s = "1、您可根据案件类型复杂程度购买图文咨询，如果时间或次数超时，只能再次购买进行图文咨询；" +
                "<br/>2.当订单产生，根据您消费的金额<font color='#f79f0a'>赠送50积分</font>；" +
                "<br/>3.如有什么疑问您可以拨打客服电话，号码是01082668266。";
        tvNotice.setText(Html.fromHtml(s));


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showShort(payType + "" + priceId + "" + price);
                goPay();
            }
        });
    }

    private void goPay() {

        OkGo.<String>get(Apis.MakeOrderAndGetPayInfo)
                .params("userId", "2")
                .params("lawyerId", lawyerId)
                .params("priceId", priceId)
                .params("number", "1")
                .params("payType", payType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                        AliPayResult result = (new Gson()).fromJson(response.body(), AliPayResult.class);
                        if (result.getCode() == 0) {

                            if (payType == 1){
                            final String orderInfo = result.getData().getZfbNew().getOrderPayInfoString();
                            L.d("开始支付宝支付 ", orderInfo);
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PersonLawyerActivity.this);
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
                            }else if (payType == 2){
                                WePay(result.getData().getWx());
                            }else {
                                if (balance < price){
                                    showShort("余额不足");
                                }else {
                                    showShort("支付成功");
                                }
                            }

                        } else {
                            showShort(result.getMsg());
                        }
                    }
                });

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
                finish();
//                goNext(oid + "", fid + "");

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showShort("支付失败");
            }
        }
    };


    private void WePay(AliPayResult.DataBean.WxBean wx) {

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


    //本地广播数据类型实例。
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;


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
                    finish();
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

    private void initData() {
        //获取本地广播实例。
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //新建intentFilter并给其action标签赋值。
        intentFilter=new IntentFilter();
        intentFilter.addAction(WECHAT_PAY_RESULT_ACTION);

        //创建广播接收器实例，并注册。将其接收器与action标签进行绑定。
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

        OkGo.<String>get(Apis.PersonalLawyerServiceInfo)
                .params("lawyerId", "3")
                .params("userId", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        PersonalLawyer info = (new Gson()).fromJson(response.body(), PersonalLawyer.class);
                        if (info.getCode() == 0) {
                            initValue(info.getData());
                        } else {
                            showShort(info.getMsg());
                            finish();
                        }
                    }
                });
    }

    private void initValue(PersonalLawyer.DataBean data) {
        Glide.with(this).load(data.getLawyer().getHeadURL()).into(civEvaLawyer);
        tvNameEva.setText(data.getLawyer().getName());
        String s = "";
        for (int i = 0; i < data.getLawyer().getSpecial().size(); i++) {
            if (i == data.getLawyer().getSpecial().size() - 1) {
                s = s + data.getLawyer().getSpecial().get(i);
            } else {
                s = s + data.getLawyer().getSpecial().get(i) + "、";
            }
        }
        tvSkilled.setText("专业领域：" + s);


        if (data.getService().getPriceList().size() > 0) {
            for (int i = 0; i < data.getService().getPriceList().size(); i++) {
                if (data.getService().getPriceList().get(i).getCycle().equals("周")) {
                    priceId = data.getService().getPriceList().get(i).getPriceId();
                    price = data.getService().getPriceList().get(i).getPrice();
                    tvTimeDown.setText(addDate(0));
                }
            }


            payMoney.setText("￥" + price);
            comboRlv.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
            adapter = new ComboAdapter(data.getService().getPriceList());
            comboRlv.setAdapter(adapter);
        }


    }


    private int checkPosition = 0;

    private class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ViewHolder> {

        private List<PersonalLawyer.DataBean.ServiceBean.PriceListBean> bean;


        public ComboAdapter(List<PersonalLawyer.DataBean.ServiceBean.PriceListBean> bean) {
            this.bean = bean;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_person_service, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            L.d("=====VH", position + "");
            switch (position) {
                case 0:
                    for (int i = 0; i < bean.size(); i++) {
                        if (bean.get(i).getCycle().equals("周")) {
                            holder.tv_type_text.setText("1周(" + bean.get(i).getPrice() + "元)");

                            if (bean.get(i).isIsOpen()) {
                                final int finalI = i;
                                holder.tv_type_text.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        priceId = bean.get(finalI).getPriceId();
                                        price = bean.get(finalI).getPrice();
                                        payMoney.setText("￥" + price);


                                        tvTimeDown.setText(addDate(0));

                                        if (checkPosition != 0) {
                                            checkPosition = 0;
                                            adapter.notifyDataSetChanged();
                                        }

                                    }
                                });
                            }
                        }
                    }


                    break;
                case 1:
                    for (int i = 0; i < bean.size(); i++) {
                        if (bean.get(i).getCycle().equals("月")) {
                            holder.tv_type_text.setText("1个月(" + bean.get(i).getPrice() + "元)");

                            if (bean.get(i).isIsOpen()) {
                            final int finalI = i;
                            holder.tv_type_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    priceId = bean.get(finalI).getPriceId();
                                    price = bean.get(finalI).getPrice();
                                    payMoney.setText("￥" + price);
                                    tvTimeDown.setText(addDate(1));
                                    if (checkPosition != 1) {
                                        checkPosition = 1;
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            });
                            }

                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < bean.size(); i++) {
                        if (bean.get(i).getCycle().equals("月")) {
                            holder.tv_type_text.setText("3个月(" + bean.get(i).getPrice() * 3 + "元)");

                            if (bean.get(i).isIsOpen()) {
                            final int finalI = i;
                            holder.tv_type_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    priceId = bean.get(finalI).getPriceId();
                                    price = bean.get(finalI).getPrice() * 3;
                                    payMoney.setText("￥" + price);
                                    tvTimeDown.setText(addDate(2));
                                    if (checkPosition != 2) {
                                        checkPosition = 2;
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            });
                            }

                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < bean.size(); i++) {
                        if (bean.get(i).getCycle().equals("月")) {
                            holder.tv_type_text.setText("6个月(" + bean.get(i).getPrice() * 6 + "元)");
                            if (bean.get(i).isIsOpen()) {
                            final int finalI = i;
                            holder.tv_type_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    priceId = bean.get(finalI).getPriceId();
                                    price = bean.get(finalI).getPrice() * 6;
                                    payMoney.setText("￥" + price);
                                    tvTimeDown.setText(addDate(3));
                                    if (checkPosition != 3) {
                                        checkPosition = 3;
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            });
                            }
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < bean.size(); i++) {
                        if (bean.get(i).getCycle().equals("年")) {
                            holder.tv_type_text.setText("1年(" + bean.get(i).getPrice() + "元)");

                            if (bean.get(i).isIsOpen()) {
                            final int finalI = i;
                            holder.tv_type_text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    priceId = bean.get(finalI).getPriceId();
                                    price = bean.get(finalI).getPrice();
                                    payMoney.setText("￥" + price);
                                    tvTimeDown.setText(addDate(4));
                                    if (checkPosition != 4) {
                                        checkPosition = 4;
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            });
                            }

                        }
                    }
                    break;
            }

            if (position == checkPosition) {
                holder.tv_type_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                holder.tv_type_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            } else {
                holder.tv_type_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.type_unselect));
                holder.tv_type_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.type_gray));
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_type_text;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_type_text = (TextView) itemView.findViewById(R.id.tv_type_text);
            }
        }
    }


    private String addDate(int position) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(System.currentTimeMillis());
        String format = sdf.format(date);
        calendar.setTime(date);
        switch (position) {
            case 0:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case 1:
                calendar.add(Calendar.MONTH, 1);
                break;
            case 2:
                calendar.add(Calendar.MONTH, 3);
                break;
            case 3:
                calendar.add(Calendar.MONTH, 6);
                break;
            case 4:
                calendar.add(Calendar.YEAR, 1);
                break;
        }

        date = calendar.getTime();


        String format1 = sdf.format(date);

        return format + "—" + format1;

    }
}
