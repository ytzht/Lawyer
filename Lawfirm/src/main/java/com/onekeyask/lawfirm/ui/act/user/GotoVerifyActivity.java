package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GotoVerify;
import com.onekeyask.lawfirm.entity.MsgDetail;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.UserService;

public class GotoVerifyActivity extends BaseToolBarActivity {

    private TextView tvunre;
    private LinearLayout llunpass;
    private TextView tvre;
    private LinearLayout llpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_verify);
        this.llpass = (LinearLayout) findViewById(R.id.ll_pass);
        this.tvre = (TextView) findViewById(R.id.tv_re);
        this.llunpass = (LinearLayout) findViewById(R.id.ll_un_pass);
        this.tvunre = (TextView) findViewById(R.id.tv_un_re);
        setToolbarText("身份验证");

        if (getIntent().hasExtra("id")){
            if (getIntent().getIntExtra("id", 0) != 0){
                OkGo.<String>get(Apis.MessageDetail).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("messageId", getIntent().getIntExtra("id", 0)).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MsgDetail msgDetail = JSON.parseObject(response.body(), MsgDetail.class);
                        if (msgDetail.getCode() == 0){

                        }else {
                            showShort(msgDetail.getMsg());
                            finish();
                        }
                    }
                });
            }

        }

        OkGo.<String>get(Apis.GotoVerify).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        L.d(response.body());

                        GotoVerify verify = (new Gson()).fromJson(response.body(), GotoVerify.class);
                        if (verify.getCode() == 0) {
                            switch (verify.getData().getLawyer().getStatus()) {
                                case "0"://正常
                                    showShort("正常");
                                    llpass.setVisibility(View.VISIBLE);
                                    llunpass.setVisibility(View.GONE);
                                    break;
                                case "1"://冻结
                                    showShort("冻结");
                                    llpass.setVisibility(View.GONE);
                                    llunpass.setVisibility(View.VISIBLE);
                                    break;
                                case "2"://未提交审核
                                    llpass.setVisibility(View.GONE);
                                    llunpass.setVisibility(View.VISIBLE);
                                    UserService.service(getBaseContext()).setLawyerId(verify.getData().getLawyer().getLawyerId());
                                    startActivity(IdentityVerificationActivity.class);

                                    break;
                                case "3"://等待审核
                                    showShort("等待审核");
                                    llpass.setVisibility(View.VISIBLE);
                                    llunpass.setVisibility(View.GONE);
                                    break;
                                case "4"://审核不通过
                                    showShort("审核不通过");
                                    llpass.setVisibility(View.GONE);
                                    llunpass.setVisibility(View.VISIBLE);
                                    break;

                            }
                        } else {
                            showShort(verify.getMsg());
                        }
                    }
                });


        tvre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IdentityVerificationActivity.class);
            }
        });
        tvunre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IdentityVerificationActivity.class);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.next_change) {
            //注册
            startActivity(LoginActivity.class);
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
