package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MsgDetail;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.UserService;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;

public class MsgDetailActivity extends BaseToolBarActivity {

    TextView msgTimeDetail;
    TextView msgDetailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        ButterKnife.bind(this);
        setToolbarText("消息详情");

        msgDetailTv = (TextView)findViewById(R.id.msg_detail);
        msgTimeDetail = (TextView)findViewById(R.id.msg_time_detail);
        OkGo.<String>get(Apis.MessageDetail).params("userId", UserService.service(getBaseContext()).getUserId())
                .params("messageId", getIntent().getStringExtra("id")).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MsgDetail msgDetail = JSON.parseObject(response.body(), MsgDetail.class);
                if (msgDetail.getCode() == 0){
                    msgDetailTv.setText(msgDetail.getData().getContent());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                    msgTimeDetail.setText(format.format(msgDetail.getData().getCreateTime()));
                }else {
                    showShort(msgDetail.getMsg());
                    finish();
                }
            }
        });

    }


}
