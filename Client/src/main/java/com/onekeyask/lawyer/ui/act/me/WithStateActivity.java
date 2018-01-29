package com.onekeyask.lawyer.ui.act.me;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.ProgressInfo;
import com.onekeyask.lawyer.entity.ProgressTX;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.MainActivity;
import com.onekeyask.lawyer.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

public class WithStateActivity extends BaseToolBarActivity {

    private TextView bankname;
    private TextView money;
    private TextView status;
    private TextView progresstv;
    private ImageView img1;
    private TextView createtime1;
    private ImageView img2;
    private TextView passtime;
    private ImageView img3;
    private TextView name;
    private TextView createtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_state);
        this.createtime1 = (TextView) findViewById(R.id.create_time_);
        this.name = (TextView) findViewById(R.id.name);
        this.img3 = (ImageView) findViewById(R.id.img3);
        this.passtime = (TextView) findViewById(R.id.pass_time);
        this.img2 = (ImageView) findViewById(R.id.img2);
        this.createtime = (TextView) findViewById(R.id.create_time);
        this.img1 = (ImageView) findViewById(R.id.img1);
        this.progresstv = (TextView) findViewById(R.id.progress_tv);
        this.status = (TextView) findViewById(R.id.status);
        this.money = (TextView) findViewById(R.id.money);
        this.bankname = (TextView) findViewById(R.id.bank_name);
        setToolbarText("提现进度");

        if (getIntent().hasExtra("id")){
            OkGo.<String>get(Apis.ProgressTX).params("userId", UserService.service(getBaseContext()).getUserId())
                    .params("withDrawInfoId", getIntent().getStringExtra("id")).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {

                    ProgressTX tx = (new Gson()).fromJson(response.body(), ProgressTX.class);
                    if (tx.getCode() == 0) {
                        ProgressInfo info = tx.getData().getProgressInfo();

                        setData(info);




                    } else {
                        showShort(tx.getMsg());
                    }
                }
            });
        }else {

            ProgressInfo info = (ProgressInfo) getIntent().getSerializableExtra("info");

            if (info == null){
                finish();
            }else {
                setData(info);
            }

        }



    }

    private void setData(ProgressInfo info) {

        name.setText(info.getBankName() + "(" + info.getCardNum() + ")" + info.getApplyName());

        bankname.setText(info.getBankName());
        DecimalFormat df = new DecimalFormat("#.00");
        money.setText("提现金额：￥" + df.format(info.getMoney()));
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (info.getStatus() == 1){
            img3.setImageResource(R.drawable.select_g);
            passtime.setVisibility(View.GONE);
            status.setText("处理中");
        }else {
            img3.setImageResource(R.drawable.select_c);
            passtime.setVisibility(View.VISIBLE);
            passtime.setText(info.getCreateTime());
            status.setText("已完成");
        }

        createtime1.setText(info.getCreateTime());
        createtime.setText(info.getCreateTime());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            EventBus.getDefault().post(BaseEvent.event(BaseEvent.TXState));
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
            startActivity(MainActivity.class);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
