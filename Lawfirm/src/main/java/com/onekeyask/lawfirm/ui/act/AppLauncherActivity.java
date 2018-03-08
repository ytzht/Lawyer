package com.onekeyask.lawfirm.ui.act;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetRed;
import com.onekeyask.lawfirm.entity.GotoVerify;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.ui.act.user.GotoVerifyActivity;
import com.onekeyask.lawfirm.ui.act.user.LoginActivity;
import com.onekeyask.lawfirm.ui.act.user.SkillAreaActivity;
import com.onekeyask.lawfirm.utils.UserService;


public class AppLauncherActivity extends BaseActivity {

    private static final String TAG = "Launch=====";
    private String first = "";
    private long time = 2000;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();

        if (first.equals("no")) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    superFinish();
                }
            }, time);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_launcher);

        TextView textView_version = (TextView) findViewById(R.id.textView_version);

        PackageManager packageManager = getPackageManager();

        try {
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;

            textView_version.setText("版本号：" + version + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
//        imageView.setAnimation(animation);
        animation.setFillAfter(true);

        SharedPreferences preferences = getSharedPreferences("first_open", Context.MODE_PRIVATE);
        first = preferences.getString("first", "yes");
        Log.d(TAG, "onCreate: " + first);
        if (first.equals("yes")) {//第一次打开应用

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    superFinish();
                }
            }, time);
            //引导页
//            ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
//            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//            viewpager.setAdapter(new SamplePagerAdapter(this));
//            indicator.setViewPager(viewpager);
//
//            SharedPreferences.Editor editor = preferences.edit();
//            String fir = "no";
//            editor.putString("first", fir);
//            editor.apply();
        }

    }

    public void superFinish() {

        if (UserService.service(getBaseContext()).getLawyerId() == 0) {
            UserService service = UserService.service(getBaseContext());
            service.setUserName("");
            service.setToken("-1");
            service.setHeadURL("");
            service.setLawyerId(0);
            startActivity(LoginActivity.class);
            finishA();
        } else {

            OkGo.<String>get(Apis.GetRed).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                            if (red.getCode() == 0) {
                                gotoVerify();

                            } else if (red.getCode() == -100) {
                                UserService service = UserService.service(getBaseContext());
                                service.setUserName("");
                                service.setToken("-1");
                                service.setHeadURL("");
                                service.setLawyerId(0);
                                startActivity(LoginActivity.class);
                                finish();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            UserService service = UserService.service(getBaseContext());
                            service.setUserName("");
                            service.setToken("-1");
                            service.setHeadURL("");
                            service.setLawyerId(0);
                            startActivity(LoginActivity.class);
                            finish();
                        }
                    });


        }

    }

    private void gotoVerify() {
        OkGo.<String>get(Apis.GotoVerify).params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GotoVerify verify = (new Gson()).fromJson(response.body(), GotoVerify.class);
                        if (verify.getCode() == 0) {
                            switch (verify.getData().getLawyer().getStatus()) {
                                case "0"://正常
                                    if (UserService.service(getBaseContext()).getFirstHome() == 0) {
                                        startActivity(SkillAreaActivity.class);
                                    } else {
                                        startActivity(MainActivity.class);
                                    }
                                    break;
                                case "1"://冻结
                                    startActivity(GotoVerifyActivity.class);
                                    break;
                                case "2"://未提交审核
//                                        UserService.service(getBaseContext()).setLawyerId(verify.getData().getLawyer().getLawyerId());
                                    startActivity(LoginActivity.class);

                                    break;
                                case "3"://等待审核
                                    startActivity(GotoVerifyActivity.class);
                                    break;
                                case "4"://审核不通过
                                    startActivity(GotoVerifyActivity.class);
                                    break;
                            }
                        } else {
                            startActivity(LoginActivity.class);
                        }
                        finishA();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        finishA();
                        startActivity(LoginActivity.class);
                    }
                });

    }

    public void finishA() {
        super.finish();
    }

}
