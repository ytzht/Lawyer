package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissPasswordActivity extends BaseToolBarActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.register_submit)
    TextView registerSubmit;
    @BindView(R.id.login)
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_password);
        ButterKnife.bind(this);
        setToolbarText("忘记密码");

    }

    @OnClick({R.id.tv_get_code, R.id.register_submit, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                break;
            case R.id.register_submit:
                break;
            case R.id.login:
                break;
        }
    }
}
