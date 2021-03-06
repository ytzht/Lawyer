package com.onekeyask.lawyer.ui.act;

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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.app.MyApplication;
import com.onekeyask.lawyer.entity.GetRed;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.utils.UserService;

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
        if (UserService.service(getBaseContext()).getUserId() == 0) {
            UserService service = UserService.service(getBaseContext());
            service.setUserName("");
            service.setToken("-1");
            service.setHeadURL("");
            service.setUserId(0);
            MyApplication.initOkGo(MyApplication.instance());
            startActivity(MainActivity.class);
            finishA();
        } else {
            OkGo.<String>get(Apis.GetRed)
                    .params("userId", UserService.service(getBaseContext()).getUserId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                            if (red.getCode() == 0) {
                                startActivity(MainActivity.class);
                                finishA();

                            } else if (red.getCode() == -100) {
                                UserService service = UserService.service(getBaseContext());
                                service.setUserName("");
                                service.setToken("-1");
                                service.setHeadURL("");
                                service.setUserId(0);
                                MyApplication.initOkGo(MyApplication.instance());
                                startActivity(MainActivity.class);
                                finishA();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            UserService service = UserService.service(getBaseContext());
                            service.setUserName("");
                            service.setToken("-1");
                            service.setHeadURL("");
                            service.setUserId(0);
                            MyApplication.initOkGo(MyApplication.instance());
                            startActivity(MainActivity.class);
                            finishA();
                        }
                    });

        }


    }


    public void finishA() {
        super.finish();
    }


//    private class SamplePagerAdapter extends PagerAdapter {
//        private int mSize;
//        private Context context;
//
//        public SamplePagerAdapter(Context context) {
//            this.context = context;
//            mSize = 2;
//        }
//
//        @Override
//        public int getCount() {
//            return mSize;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup view, int position, Object object) {
//            view.removeView((View) object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup view, int position) {
//            ImageView imageView = new ImageView(view.getContext());
//            if (position == 0) {
//                imageView.setImageResource(R.drawable.launcher_01);
//            } else if (position == 1) {
//                imageView.setImageResource(R.drawable.launcher_02);
//            }
//
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//
//            if (position == 1) {
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setClass(view.getContext(), MainActivity.class);
//                        view.getContext().startActivity(intent);
//                        context.getApplicationContext();
//                        superFinish();
//                    }
//                });
//            }
//            view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
//                    .MATCH_PARENT);
//            return imageView;
//        }
//
//    }
}
