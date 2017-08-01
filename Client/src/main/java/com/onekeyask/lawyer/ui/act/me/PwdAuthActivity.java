package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PwdAuthActivity extends BaseToolBarActivity {

    @BindView(R.id.et_pwd_auth)
    EditText etPwdAuth;
    @BindView(R.id.pwd_auth_next)
    TextView pwdAuthNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_auth);
        ButterKnife.bind(this);
        setToolbarText("密码验证");
    }

    @OnClick(R.id.pwd_auth_next)
    public void onViewClicked() {
        startActivity(BalanceWithActivity.class);
    }
}
