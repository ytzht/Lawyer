package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends BaseToolBarActivity {

    @BindView(R.id.can_with)
    TextView canWith;
    @BindView(R.id.et_with)
    EditText etWith;
    @BindView(R.id.with_next)
    TextView withNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);
        setToolbarText("余额提现");
    }

    @OnClick(R.id.with_next)
    public void onViewClicked() {
        startActivity(PwdAuthActivity.class);
    }
}
