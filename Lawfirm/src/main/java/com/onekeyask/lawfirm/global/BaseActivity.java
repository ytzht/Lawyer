package com.onekeyask.lawfirm.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.onekeyask.lawfirm.http.APIFactory;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.nutz.lang.Strings;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static anet.channel.util.Utils.context;

/**
 * Created by zht on 2017/04/07 9:04
 */

public class BaseActivity extends AppCompatActivity {

    public static String _ID = "_id_";
    public static String _TITLE = "_title_";

    public SubscriberOnNextListener getResultOnNext;

    public APIFactory retrofitUtil = (APIFactory) APIFactory.getInstance();

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        PushAgent.getInstance(context).onAppStart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    private Toast mToast = null;

    public void showLong(String msg) {
        if (!Strings.isBlank(msg)) {
            if (mToast == null) {
                mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }
    }

    public void showShort(String msg) {
        if (!Strings.isBlank(msg)) {
            if (mToast == null) {
                mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }
    }


    public void cancelToast(){
        if (mToast != null){
            mToast.cancel();
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }


    public void startActivity(Class<? extends Activity> clazz, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivity(intent);
    }


    public void alert(String msg) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.content(msg).isTitleShow(false).btnNum(1).btnText("确定").show();
    }

    public NormalDialog confirm(String msg, OnBtnClickL on) {
        NormalDialog dialog = new NormalDialog(this);
        dialog.setOnBtnClickL(null, on);
        dialog.content(msg).isTitleShow(false).btnNum(2).btnText("取消", "确定")
                .show();
        return dialog;
    }


    public String _ID() {
        return getIntent().getStringExtra(_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {

    }
}
