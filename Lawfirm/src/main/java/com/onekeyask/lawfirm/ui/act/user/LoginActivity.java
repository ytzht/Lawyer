package com.onekeyask.lawfirm.ui.act.user;

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
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.app.MyApplication;
import com.onekeyask.lawfirm.entity.Login;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.MainActivity;
import com.onekeyask.lawfirm.utils.Forms;
import com.onekeyask.lawfirm.utils.UserService;

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

                                    service.setPhone(phoneNo);
                                    service.setUserName(login.getData().getLawyer().getName());
                                    service.setHeadURL(login.getData().getLawyer().getHeadURL());
                                    service.setPassword(password);
                                    service.setLawyerId(login.getData().getLawyer().getLawyerId());
                                    MyApplication.initOkGo(MyApplication.instance());
                                    switch (login.getData().getLawyer().getStatus()){
                                        case "0"://正常
                                            showShort("正常");
                                            finish();
                                            startActivity(MainActivity.class);
                                            break;
                                        case "1"://冻结
                                            showShort("冻结");
                                            break;
                                        case "2"://未提交审核

                                            UserService.service(getBaseContext()).setLawyerId(login.getData().getLawyer().getLawyerId());
                                            startActivity(IdentityVerificationActivity.class);

                                            break;
                                        case "3"://等待审核
                                            showShort("等待审核");
                                            finish();
                                            break;
                                        case "4"://审核不通过
                                            showShort("审核不通过");
                                            startActivity(GotoVerifyActivity.class);
                                            finish();
                                            break;

                                    }


                                }else {
                                    showShort(login.getMsg());
                                }

                            }
                        });

                break;
            case R.id.miss_pwd:
                startActivity(MissPasswordActivity.class);
                break;
        }
    }


}
