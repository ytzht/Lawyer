package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;
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
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.Apis;
import com.onekeyask.lawfirm.ui.act.user.ResetPasswordActivity;
import com.onekeyask.lawfirm.utils.UserService;

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
        OkGo.<String>post(Apis.Getswitch).params("lawyerId", service.getLawyerId())
                .params("deviceToken", service.getDeviceToken()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetSwitch getSwitch = JSON.parseObject(response.body(), GetSwitch.class);
                isOn = getSwitch.isIsOn();
                sbPhone.setChecked(isOn);
            }
        });
    }

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
                break;
            case R.id.statement:
                startActivity(StatementActivity.class);
                break;
            case R.id.about_us:
                break;
            case R.id.return_my:

                OkGo.<String>get(Apis.Logout)
                        .params("lawyerId", service.getLawyerId())
                        .params("deviceToken", service.getDeviceToken())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0) {
                                    service.setUserName("");
                                    service.setToken("");
                                    service.setHeadURL("");
                                    service.setLawyerId(0);
                                    finish();
                                } else {
                                    showShort(data.getMsg());
                                }
                            }
                        });
                break;
        }
    }
}
