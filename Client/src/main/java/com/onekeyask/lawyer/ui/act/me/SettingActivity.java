package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.user.ResetPasswordActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setToolbarText("设置与帮助");
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
                break;
        }
    }
}
