package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpinionActivity extends BaseToolBarActivity {

    @BindView(R.id.et_opinion)
    EditText etOpinion;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.submit_opinion)
    TextView submitOpinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);
        setToolbarText("意见反馈");


    }

    @OnClick(R.id.submit_opinion)
    public void onViewClicked() {
        startActivity(SubmitOpinionActivity.class);
    }
}
