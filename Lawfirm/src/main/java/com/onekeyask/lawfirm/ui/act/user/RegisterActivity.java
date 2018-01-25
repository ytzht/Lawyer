package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.app.MyApplication;
import com.onekeyask.lawfirm.entity.Register;
import com.onekeyask.lawfirm.entity.SMSCode;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.Forms;
import com.onekeyask.lawfirm.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseToolBarActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.register_submit)
    TextView registerSubmit;
    @BindView(R.id.login)
    TextView login;

    private int codeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setToolbarText("注册");

        initView();

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

    @OnClick({R.id.tv_get_code, R.id.register_submit, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                String accout = etPhone.getEditableText().toString();
                if (Forms.disValid(accout, Forms.PHONENUM)) {
                    etPhone.requestFocus();
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkGo.<String>get(Apis.SMSCode).params("phoneNo", accout).params("codeId", codeId).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        SMSCode data = (new Gson()).fromJson(response.body(), SMSCode.class);
                        if (data.getCode() == 0) {
                            codeId = data.getData().getCodeId();
                            djs();
                        } else {
                            showShort(data.getMsg());
                        }
                    }
                });

                break;
            case R.id.register_submit:

                String model = android.os.Build.MODEL;
                String deviceToken = UserService.service(getBaseContext()).getDeviceToken();

                final String phoneNo = etPhone.getText().toString();
                if (Forms.disValid(phoneNo, Forms.PHONENUM)) {
                    etPhone.requestFocus();
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String password = etPwd.getText().toString();

                if (password.equals("")) {
                    showShort("请输入密码");
                    return;
                }
                if (password.length() < 6) {
                    showShort("密码长度过短");
                    return;
                }

                String code = etCode.getText().toString();

                if (code.equals("")) {
                    showShort("请输入验证码");
                    return;
                }
                if (code.length() != 6) {
                    showShort("请输入正确验证码");
                    return;
                }

                OkGo.<String>get(Apis.Register)
                        .params("phoneNo", phoneNo)
                        .params("code", code)
                        .params("codeId", codeId)
                        .params("password", password)
                        .params("phoneType", "android")
                        .params("model", model)
                        .params("deviceToken", deviceToken)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Register register = (new Gson()).fromJson(response.body(), Register.class);

                                if (register.getCode() == 0) {

                                    UserService.service(getBaseContext()).setToken(register.getData().getToken());
                                    MyApplication.initOkGo(MyApplication.instance());

                                    switch (register.getData().getLawyer().getStatus()){
                                        case "0"://正常
                                            showShort("正常");
                                            break;
                                        case "1"://冻结
                                            showShort("冻结");
                                            break;
                                        case "2"://未提交审核

                                            UserService.service(getBaseContext()).setLawyerId(register.getData().getLawyer().getLawyerId());
                                            startActivity(IdentityVerificationActivity.class);

                                            break;
                                        case "3"://等待审核
                                            showShort("等待审核");
                                            break;
                                        case "4"://审核不通过
                                            showShort("审核不通过");
                                            break;

                                    }


                                    finish();
                                } else {
                                    showShort(register.getMsg());
                                }

                            }
                        });


                break;
            case R.id.login:
                startActivity(LoginActivity.class);
                finish();
                break;
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(LoginActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(LoginActivity.class);
            finish();
            return true;
        }

        return false;
    }

}
