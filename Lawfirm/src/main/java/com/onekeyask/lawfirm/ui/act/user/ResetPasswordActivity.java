package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseToolBarActivity {

    @BindView(R.id.old_pwd)
    EditText oldPwd;
    @BindView(R.id.setting)
    EditText setting;
    @BindView(R.id.confirm)
    EditText confirm;
    @BindView(R.id.finish)
    TextView finish;

    private UserService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        setToolbarText("重置密码");

        service = new UserService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在重置密码...");
        dialog.setCancelable(false);
    }

    @OnClick(R.id.finish)
    public void onViewClicked() {

        final String old = oldPwd.getText().toString();
        final String password = setting.getText().toString();
        final String etPwdCon = confirm.getText().toString();

        if (old.equals("")){
            showShort("请输入密码");
            return;
        }
        if (old.length() < 6){
            showShort("密码长度过短");
            return;
        }

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

        dialog.show();
        OkGo.<String>post(Apis.ChangePwd).params("lawyerId", service.getLawyerId())
                .params("oldPassword", old)
                .params("password", password).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (dialog.isShowing()) dialog.dismiss();
                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                if (data.getCode() == 0){
                    showShort("修改成功");
                    service.setHeadURL("");
                    service.setPassword("");
                    service.setLawyerId(0);
                    service.setUserName("");
                    service.setToken("-1");
                    finish();
                    startActivity(LoginActivity.class);
                    EventBus.getDefault().post(BaseEvent.event(BaseEvent.FINISH_SETTING));
                    EventBus.getDefault().post(BaseEvent.event(BaseEvent.FINISH_MAIN));
                }else {
                    showShort(data.getMsg());
                }
            }
        });


    }
}
