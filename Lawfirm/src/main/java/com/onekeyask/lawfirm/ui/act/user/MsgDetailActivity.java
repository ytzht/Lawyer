package com.onekeyask.lawfirm.ui.act.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MsgDetail;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.UserService;

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

        msgDetailTv = (TextView) findViewById(R.id.msg_detail);
        msgTimeDetail = (TextView) findViewById(R.id.msg_time_detail);
        OkGo.<String>get(Apis.MessageDetail).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("messageId", getIntent().getStringExtra("id")).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MsgDetail msgDetail = JSON.parseObject(response.body(), MsgDetail.class);
                if (msgDetail.getCode() == 0) {
                    msgDetailTv.setText(msgDetail.getData().getContent());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                    msgTimeDetail.setText(format.format(msgDetail.getData().getCreateTime()));
                } else {
                    showShort(msgDetail.getMsg());
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.msg_detail) {
            new AlertDialog.Builder(MsgDetailActivity.this)
                    .setTitle("注意").setMessage("确定要删除此条消息吗？").setCancelable(true).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    OkGo.<String>get(Apis.MsgDelete).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                            .params("messageId", getIntent().getStringExtra("id")).execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                            if (data.getCode() == 0) {
                                showShort("删除成功");
                                finish();
                            } else {
                                showShort(data.getMsg());
                            }
                        }
                    });
                }
            }).show();
            //注册

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;


    }
}
