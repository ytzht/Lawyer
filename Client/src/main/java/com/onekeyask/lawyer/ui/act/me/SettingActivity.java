package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.GetSwitch;
import com.onekeyask.lawyer.entity.ResultData;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.user.ResetPasswordActivity;
import com.onekeyask.lawyer.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseToolBarActivity {

    @BindView(R.id.check_update)
    LinearLayout checkUpdate;
    @BindView(R.id.re_pwd)
    LinearLayout rePwd;
    @BindView(R.id.sb_phone)
    SwitchButton sbPhone;
    @BindView(R.id.notice_set)
    LinearLayout noticeSet;
    @BindView(R.id.clean_cache)
    LinearLayout cleanCache;
    @BindView(R.id.statement)
    LinearLayout statement;
    @BindView(R.id.about_us)
    LinearLayout aboutUs;
    @BindView(R.id.return_my)
    TextView returnMy;

    private boolean isOn = false;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setToolbarText("设置与帮助");
        service = new UserService(getBaseContext());
        initSwitch();
    }

    private void initSwitch() {
        OkGo.<String>post(Apis.Getswitch).params("userId", service.getUserId())
                .params("deviceToken", service.getDeviceToken()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetSwitch getSwitch = JSON.parseObject(response.body(), GetSwitch.class);
                isOn = getSwitch.isIsOn();
                sbPhone.setChecked(isOn);
            }
        });
    }
    private AlertDialog alert;
    @OnClick({R.id.check_update, R.id.re_pwd, R.id.sb_phone, R.id.notice_set, R.id.clean_cache, R.id.statement, R.id.about_us, R.id.return_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_update:
                break;
            case R.id.re_pwd:
                startActivity(ResetPasswordActivity.class);
                break;
            case R.id.sb_phone:
                OkGo.<String>post(Apis.Saveswitch).params("userId", service.getUserId()).params("isOn", !isOn)
                        .params("deviceToken", service.getDeviceToken()).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResultData resultData = JSON.parseObject(response.body(), ResultData.class);
                        if (resultData.getCode() == 0) {
                            isOn = !isOn;
                            sbPhone.setChecked(isOn);
                        } else {
                            showShort(resultData.getMsg());
                        }
                    }
                });
                break;
            case R.id.notice_set:

                break;
            case R.id.clean_cache:
                showShort("缓存清理成功");
                break;
            case R.id.statement:
                startActivity(StatementActivity.class);
                break;
            case R.id.about_us:
                startActivity(AboutUsActivity.class);
                break;
            case R.id.return_my:
                View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
                alert = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
                TextView tvMsg = (TextView)view1.findViewById(R.id.tv_msg);
                TextView cancel = (TextView)view1.findViewById(R.id.tv_cancel);
                TextView next = (TextView)view1.findViewById(R.id.tv_share_con);
                cancel.setVisibility(View.VISIBLE);
                tvMsg.setText("确定退出当前账号吗？");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkGo.<String>get(Apis.Logout)
                                .params("userId", service.getUserId())
                                .params("deviceToken", service.getDeviceToken())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                        if (data.getCode() == 0) {
                                            service.setUserName("");
                                            service.setToken("-1");
                                            service.setHeadURL("");
                                            service.setUserId(0);
                                            finish();
                                        } else {
                                            showShort(data.getMsg());
                                        }
                                    }
                                });
                        if (alert.isShowing()) alert.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alert.isShowing()) alert.dismiss();
                    }
                });

                break;
        }
    }
}
