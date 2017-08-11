package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class MyIntegralActivity extends BaseToolBarActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView rlv_integral;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        setToolbarText("我的积分");

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        rlv_integral = (RecyclerView) findViewById(R.id.rlv_integral);

    }
}
