package com.onekeyask.lawyer.ui.act.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.register) {
            //注册
            startActivity(RegisterActivity.class);
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
                startActivity(MyInfoActivity.class);
                break;
            case R.id.miss_pwd:
                startActivity(MissPasswordActivity.class);
                break;
        }
    }
}
