package com.onekeyask.lawfirm.ui.act.service;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.CallDetail;
import com.onekeyask.lawfirm.entity.CallUp;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.MainActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CallDetailActivity extends BaseToolBarActivity {

    private UserService service;
    private Intent intent;
    private TextView expiretime;
    private ImageView ivwenhao;
    private CircleImageView civimg;
    private TextView tv_intro;
    private TextView tv_btn1;
    private TextView tv_speak_eva;
    private TextView tv_satisfied;
    private TextView tv_txt;
    private TextView tvname;
    private TextView ordertime;
    private TextView no_eva;
    private LinearLayout ll_eva;
    private ImageView iv_state;
    private TextView tv_state;
    private RecyclerView rlvhistory;
    private HistoryAdapter adapter;
    private TagFlowLayout tag_flow;
    private String exp;
    int lawyerId;
    List<CallDetail.DataBean.HistoriesBean> histories = new ArrayList<>();
    private String TAG = "CallDetAct=====";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail);

        setToolbarText("电话咨询");

        initView();


    }

    CallDetail detail;

    private void initView() {
        this.rlvhistory = (RecyclerView) findViewById(R.id.rlv_history);
        this.iv_state = (ImageView) findViewById(R.id.iv_state);
        this.tv_btn1 = (TextView) findViewById(R.id.tv_btn1);
        this.tv_satisfied = (TextView) findViewById(R.id.tv_satisfied);
        this.tv_speak_eva = (TextView) findViewById(R.id.tv_speak_eva);
        this.tv_txt = (TextView) findViewById(R.id.tv_txt);
        this.tv_state = (TextView) findViewById(R.id.tv_state);
        this.ordertime = (TextView) findViewById(R.id.order_time);
        this.no_eva = (TextView) findViewById(R.id.no_eva);
        this.ll_eva = (LinearLayout) findViewById(R.id.ll_eva);
        this.tvname = (TextView) findViewById(R.id.tv_name);
        this.civimg = (CircleImageView) findViewById(R.id.civ_img);
        this.ivwenhao = (ImageView) findViewById(R.id.iv_wenhao);
        this.expiretime = (TextView) findViewById(R.id.expire_time);
        tag_flow = (TagFlowLayout) findViewById(R.id.tag_flow);

        rlvhistory.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rlvhistory.addItemDecoration(new DividerItemDecoration(CallDetailActivity.this, DividerItemDecoration.VERTICAL));

        adapter = new HistoryAdapter();
        rlvhistory.setAdapter(adapter);
        contentView = LayoutInflater.from(getBaseContext()).inflate(R.layout.pop_txt, null);
        tv_intro = (TextView) contentView.findViewById(R.id.tv_intro);
        tv_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
        intent = getIntent();
        service = new UserService(getBaseContext());
        OkGo.<String>get(Apis.CallDetail).params("lawyerId", service.getLawyerId())
                .params("userServiceId", intent.getStringExtra("userServiceId"))
                .params("orderId", intent.getStringExtra("oid"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        detail = (new Gson()).fromJson(response.body(), CallDetail.class);
                        if (detail.getCode() == 0) {

                            lawyerId = detail.getData().getUserId();
                            Glide.with(getBaseContext()).load(detail.getData().getHeadURL())
                                    .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                                    .into(civimg);
                            tvname.setText(detail.getData().getUserName());
                            exp = detail.getData().getExpireTime();
                            expiretime.setText("(" + detail.getData().getExpireTime() + ")");
                            ordertime.setText("订单时间：" + detail.getData().getOrderTime());
                            histories = detail.getData().getHistories();
                            adapter.notifyDataSetChanged();

                            setState(detail.getData().getCallStatus());

                        } else {
                            showShort(detail.getMsg());
                            finish();
                        }

                    }
                });

    }

    private PopupWindow popupWindow = null;
    private View contentView;

    //跳出选项框
    public PopupWindow getPopwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.alpha = 1f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.showAtLocation(getActivity().findViewById(R.id.rl_ser_list), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(ivwenhao);
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
//        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.update();
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
        return popupWindow;
    }

    private void setState(int callStatus) {
//        服务状态：
//        1：正在服务中
//        2：已完成通话，未评价
//        3：已完成通话
//        4：超时未服务
        switch (callStatus) {
            case 1:
                iv_state.setImageDrawable(getResources().getDrawable(R.drawable.service_));
                tv_state.setText("正在服务中");
                expiretime.setVisibility(View.VISIBLE);
                ivwenhao.setVisibility(View.VISIBLE);
                tv_txt.setText("律师已经接单，请耐心等待，律师稍后会打电话联系您，如果超过30分钟律师还未接单，您支付的费用会自动退还到您的账户中。");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn1.setText("点击拨打");
                tv_btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //拨打电话
                        OkGo.<String>get(Apis.CallUp).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                                .params("userServiceId", intent.getStringExtra("userServiceId"))
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {

                                        CallUp callUp = (new Gson()).fromJson(response.body(), CallUp.class);
                                        if (callUp.getCode() == 0) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callUp.getData().getCallNo()));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            showShort(callUp.getMsg());
                                        }
                                    }
                                });
                    }
                });


                new CountDownTimer(compare_date(), 1000) {

                    public void onTick(long millisUntilFinished) {
                        Log.d(TAG, "onTick: " + millisUntilFinished);

                        long min = (millisUntilFinished / 1000) / 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        String mins = "" + min;
                        String secs = "" + sec;
                        if (min < 10) {
                            mins = "0" + min;
                        }
                        if (sec < 10) {
                            secs = "0" + sec;
                        }
                        expiretime.setText("(" + mins + ":" + secs + ")");
//                        LogUtil.i(TAG, "seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        finish();
//                        LogUtil.i(TAG, "done!");
                    }
                }.start();


                ivwenhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (popupWindow != null) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        } else {
                            popupWindow = getPopwindow(contentView);
                            popupWindow.showAsDropDown(ivwenhao);
                        }
                    }
                });
                break;
            case 2:
                iv_state.setImageDrawable(getResources().getDrawable(R.drawable.complete));
                tv_state.setText("通话完成");
                expiretime.setVisibility(View.GONE);
                ivwenhao.setVisibility(View.GONE);
                tv_txt.setText("您和用户的服务已结束，感谢您的耐心服务，您的收入会在12小时内打到您的账户");
                no_eva.setVisibility(View.VISIBLE);
                break;
            case 3:
                iv_state.setImageDrawable(getResources().getDrawable(R.drawable.complete));
                tv_state.setText("通话完成");
                expiretime.setVisibility(View.GONE);
                ivwenhao.setVisibility(View.GONE);
                tv_txt.setText("您和用户的服务已结束，感谢您的耐心服务，您的收入会在12小时内打到您的账户");

                ll_eva.setVisibility(View.VISIBLE);
                initEva();


                break;
            case 4:
                iv_state.setImageDrawable(getResources().getDrawable(R.drawable.timeout));
                tv_state.setText("超时未接通");
                expiretime.setVisibility(View.GONE);
                ivwenhao.setVisibility(View.GONE);
                tv_txt.setText("您和用户的服务已结束，感谢您的耐心服务，您的收入会在12小时内打到您的账户");

                break;

        }
    }

    private long compare_date() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(exp);

            if (dt1.getTime() > (new Date()).getTime()) {
                return dt1.getTime() - (new Date()).getTime();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    private void initEva() {


        tv_satisfied.setText(detail.getData().getEvaluation().getScore());
        tv_speak_eva.setText(detail.getData().getEvaluation().getComment());

        String[] mValues = new String[detail.getData().getEvaluation().getTagList().size()];
        for (int i = 0; i < detail.getData().getEvaluation().getTagList().size(); i++) {
            mValues[i] = detail.getData().getEvaluation().getTagList().get(i).getTag();
        }

        tag_flow.setAdapter(new TagAdapter<String>(mValues) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(CallDetailActivity.this).inflate(R.layout.tag_tv_ext, tag_flow, false);
                tv.setText(s);
                return tv;
            }
        });


    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(CallDetailActivity.this).inflate(R.layout.cell_history_call, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.call_time.setText("拨打时间：" + histories.get(position).getStartTime());
            holder.call_min.setText("通话时长：" + histories.get(position).getDuration());
        }

        @Override
        public int getItemCount() {
            return histories.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView call_time, call_min;

            public ViewHolder(View itemView) {
                super(itemView);
                call_time = (TextView) itemView.findViewById(R.id.call_time);
                call_min = (TextView) itemView.findViewById(R.id.call_min);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            startActivity(MainActivity.class);
            finish();
            return true;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(BaseEvent.event(BaseEvent.GO_SERVICE));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(MainActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
