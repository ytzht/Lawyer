package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.entity.SMSCode;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.Forms;
import com.onekeyask.lawfirm.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissPasswordActivity extends BaseToolBarActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_confirm)
    EditText etPwdConfirm;
    @BindView(R.id.register_submit)
    TextView registerSubmit;
    @BindView(R.id.login)
    TextView login;

    private int codeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_password);
        ButterKnife.bind(this);
        setToolbarText("忘记密码");

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
                        if (data.getCode() == 0){
                            codeId = data.getData().getCodeId();
                            djs();
                        }else {
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
                final String etPwdCon = etPwdConfirm.getText().toString();

                if (password.equals("")){
                    showShort("请输入密码");
                    return;
                }
                if (password.length() < 6){
                    showShort("密码长度过短");
                    return;
                }

                if (!password.equals(etPwdCon)){
                    showShort("两次输入的密码不一致");
                    return;
                }

                String code = etCode.getText().toString();

                if (code.equals("")){
                    showShort("请输入验证码");
                    return;
                }
                if (code.length() != 6){
                    showShort("请输入正确验证码");
                    return;
                }
                OkGo.<String>get(Apis.Resetpwd)
                        .params("phoneNo", phoneNo)
                        .params("code", code)
                        .params("codeId", codeId)
                        .params("password", password)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ResultData register = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (register.getCode() == 0){
                                    showShort("修改成功");
                                    UserService service = new UserService(getBaseContext());
                                    service.setPhone(phoneNo);
                                    service.setPassword(password);
                                    finish();
                                }else {
                                    showShort(register.getMsg());
                                }

                            }
                        });



                break;
            case R.id.login:
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

}
