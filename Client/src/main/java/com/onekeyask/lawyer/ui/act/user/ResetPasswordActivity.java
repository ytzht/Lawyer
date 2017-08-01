package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseToolBarActivity {

    @BindView(R.id.old_pwd)
    EditText oldPwd;
    @BindView(R.id.setting)
    EditText setting;
    @BindView(R.id.confirm)
    EditText confirm;
    @BindView(R.id.finish)
    TextView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        setToolbarText("重置密码");

    }

    @OnClick(R.id.finish)
    public void onViewClicked() {
    }
}
