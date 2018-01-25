package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.ui.act.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitOpinionActivity extends BaseActivity {

    @BindView(R.id.return_my)
    TextView returnMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_opinion);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.return_my)
    public void onViewClicked() {
        startActivity(MainActivity.class);
        finish();
    }
}
