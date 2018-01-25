package com.onekeyask.lawfirm.ui.act.me;

import android.content.pm.PackageManager;
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
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetSwitch;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.user.LoginActivity;
import com.onekeyask.lawfirm.ui.act.user.ResetPasswordActivity;
import com.onekeyask.lawfirm.utils.GlideCacheUtil;
import com.onekeyask.lawfirm.utils.UserService;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.version)
    TextView version;

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

        try {
            version.setText("当前版本："+getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (UserService.service(getBaseContext()).getToken().equals("-1")) {
            returnMy.setVisibility(View.GONE);
        }
    }

    private void initSwitch() {
        OkGo.<String>post(Apis.Getswitch).params("lawyerId", service.getLawyerId())
                .params("deviceToken", service.getDeviceToken()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetSwitch getSwitch = (new Gson()).fromJson(response.body(), GetSwitch.class);
                isOn = getSwitch.getData().isIsOn();
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
                OkGo.<String>post(Apis.Saveswitch).params("lawyerId", service.getLawyerId()).params("isOn", !isOn).params("plat", "android")
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
                showShort("成功清理" + GlideCacheUtil.getInstance().getCacheSize(getBaseContext()) + "缓存");
                GlideCacheUtil.getInstance().clearImageAllCache(getBaseContext());
                break;
            case R.id.statement:
//                startActivity(StatementActivity.class);
                break;
            case R.id.about_us:
                startActivity(AboutUsActivity.class);
                break;
            case R.id.return_my:
                View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
                alert = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
                TextView tvMsg = (TextView) view1.findViewById(R.id.tv_msg);
                TextView cancel = (TextView) view1.findViewById(R.id.tv_cancel);
                TextView next = (TextView) view1.findViewById(R.id.tv_share_con);
                cancel.setVisibility(View.VISIBLE);
                tvMsg.setText("确定退出当前账号吗？");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkGo.<String>get(Apis.Logout)
                                .params("lawyerId", service.getLawyerId())
                                .params("deviceToken", service.getDeviceToken())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                        if (data.getCode() == 0) {
                                            service.setUserName("");
                                            service.setToken("-1");
                                            service.setHeadURL("");
                                            service.setLawyerId(0);
                                            finish();
                                            startActivity(LoginActivity.class);
                                            EventBus.getDefault().post(BaseEvent.event(BaseEvent.FINISH_MAIN));
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

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if (event.getCode() == BaseEvent.FINISH_SETTING){
            finish();
        }
    }
}
