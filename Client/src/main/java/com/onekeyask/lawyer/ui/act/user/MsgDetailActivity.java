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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

public class MsgDetailActivity extends BaseToolBarActivity {

    @BindView(R.id.msg_time_detail)
    TextView msgTimeDetail;
    @BindView(R.id.msg_detail)
    TextView msgDetailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        ButterKnife.bind(this);
        setToolbarText("消息详情");

        OkGo.<String>get(Apis.MessageDetail).params("userId", UserService.service(getBaseContext()).getUserId())
                .params("messageId", getIntent().getStringExtra("id")).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MsgDetail msgDetail = JSON.parseObject(response.body(), MsgDetail.class);
                if (msgDetail.getCode() == 0){
                    msgDetailTv.setText(msgDetail.getMessage().getContent());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                    msgTimeDetail.setText(format.format(msgDetail.getMessage().getCreateTime()));
                }else {
                    showShort(msgDetail.getMsg());
                    finish();
                }
            }
        });

    }


}
