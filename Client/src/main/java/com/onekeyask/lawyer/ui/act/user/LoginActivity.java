package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.Login;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.Forms;
import com.onekeyask.lawyer.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseToolBarActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.miss_pwd)
    TextView missPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setToolbarText("登录");
    }

    @Override
    protected void onResume() {
        super.onResume();

        UserService userService = UserService.service(this);
        etPhone.setText(userService.getPhone());
        etPwd.setText(userService.getPassword());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.register) {
            //注册
            startActivity(RegisterActivity.class);
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    @OnClick({R.id.login, R.id.miss_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:

                final String phoneNo = etPhone.getText().toString();
                if (Forms.disValid(phoneNo, Forms.PHONENUM)) {
                    etPhone.requestFocus();
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String password = etPwd.getText().toString();

                if (password.equals("")){
                    showShort("请输入密码");
                    return;
                }
                if (password.length() < 6){
                    showShort("密码长度过短");
                    return;
                }

                OkGo.<String>get(Apis.Login)
                        .params("phoneNo", phoneNo)
                        .params("password", password)
                        .params("phoneType", "android")
                        .params("model", android.os.Build.MODEL)
                        .params("deviceToken", UserService.service(getBaseContext()).getDeviceToken())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                Login login = (new Gson()).fromJson(response.body(), Login.class);

                                if (login.getCode() == 0){

                                    showShort("登录成功");
                                    UserService service = new UserService(getBaseContext());
                                    service.setToken(login.getData().getToken());
                                    service.setUserName(login.getData().getUser().getName());
                                    service.setHeadURL(login.getData().getUser().getHeadURL());
                                    service.setUserId(login.getData().getUser().getUserId());
                                    service.setPhone(phoneNo);
                                    service.setPassword(password);
                                    finish();
                                }else {
                                    showShort(login.getMsg());
                                }

                            }
                        });

//                startActivity(MyInfoActivity.class);
                break;
            case R.id.miss_pwd:
                startActivity(MissPasswordActivity.class);
                break;
        }
    }
}
