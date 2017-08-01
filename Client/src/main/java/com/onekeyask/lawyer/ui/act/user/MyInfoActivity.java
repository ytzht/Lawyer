package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends BaseToolBarActivity {

    @BindView(R.id.ll_image)
    LinearLayout llImage;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        setToolbarText("个人信息");


    }


    @OnClick({R.id.ll_image, R.id.ll_name, R.id.ll_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_image:
                break;
            case R.id.ll_name:
                break;
            case R.id.ll_phone:
                break;
        }
    }
}
