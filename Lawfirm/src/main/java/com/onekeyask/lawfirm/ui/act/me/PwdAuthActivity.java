package com.onekeyask.lawfirm.ui.act.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ApplyTX;
import com.onekeyask.lawfirm.entity.SMSCode;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

public class PwdAuthActivity extends BaseToolBarActivity {

    private EditText etpwdauth;
    private TextView tvGetCode;
    private TextView pwdauthnext;
    private int codeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_auth);
        this.pwdauthnext = (TextView) findViewById(R.id.pwd_auth_next);
        this.tvGetCode = (TextView) findViewById(R.id.tv_auth_code);
        this.etpwdauth = (EditText) findViewById(R.id.et_pwd_auth);
        setToolbarText("手机验证码");
        initView();
        initClick();

    }



    private void initView() {
        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                tvGetCode.setText(sec + "");
                sec--;
                if (sec > 0) {
                    tvGetCode.setEnabled(false);
                    if (handler != null && runnable != null) {
                        handler.postDelayed(runnable, 1000);
                    }
                } else {
                    tvGetCode.setText("获取验证码");
                    tvGetCode.setEnabled(true);
                }
            }
        };
    }


    private void initClick() {

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<String>get(Apis.SMSCode).params("phoneNo", UserService.service(getBaseContext()).getPhone()).params("codeId", codeId).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        SMSCode data = (new Gson()).fromJson(response.body(), SMSCode.class);
                        if (data.getCode() == 0){
                            codeId = data.getData().getCodeId();
                            djs();
                        }else {
                            showShort(data.getMsg());
                        }
                    }
                });

            }
        });



        pwdauthnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = etpwdauth.getText().toString();

                if (code.equals("")){
                    showShort("请输入验证码");
                    return;
                }
                if (code.length() != 6){
                    showShort("请输入正确验证码");
                    return;
                }
                OkGo.<String>get(Apis.ApplyTX)
                        .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("cardId", getIntent().getStringExtra("cardId"))
                        .params("money", getIntent().getStringExtra("money"))
                        .params("codeId", codeId)
                        .params("code", code)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ApplyTX tx = (new Gson()).fromJson(response.body(), ApplyTX.class);
                                if (tx.getCode() == 0) {
                                    Intent intent = new Intent(PwdAuthActivity.this, WithStateActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("info", tx.getData().getProgressInfo());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    showShort(tx.getMsg());
                                }
                            }
                        });

            }
        });
    }



    private Handler handler;
    private Runnable runnable;
    private int sec = 60;

    public void djs() {
        sec = 60;
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            if (runnable != null) {
                handler.removeCallbacks(runnable);
                runnable = null;
            }
            handler = null;
        }
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.TXState){
            finish();
        }
    }

}
