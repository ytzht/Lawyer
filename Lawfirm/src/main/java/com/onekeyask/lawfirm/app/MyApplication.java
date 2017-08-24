package com.onekeyask.lawfirm.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.onekeyask.lawfirm.global.BuildConfig;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.APIFactory;
import com.onekeyask.lawfirm.image.DemoDuiTangImageReSizer;
import com.onekeyask.lawfirm.image.PtrImageLoadHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import in.srain.cube.util.CLog;
import in.srain.cube.util.CubeDebug;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zht on 2017/04/08 9:09
 */

public class MyApplication extends Application {

    public static Context aContext;

    @Override
    public void onCreate() {
        super.onCreate();

        initCLog();

        initUMeng();

        initRetrofit();

        initPush();

//        initShare();

        Utils.init(this);
    }

//    private void initShare() {
//        UMShareAPI.get(this);
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//    }
    private void initPush() {
        //如需手动获取device token，可以调用mPushAgent.getRegistrationId()方法
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                L.d("deviceToken " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                L.e("push onFailure " + s + " " + s1);
            }
        });
        mPushAgent.setDebugMode(BuildConfig.DEBUG);
    }

    private void initRetrofit() {
        aContext = this;
        //初始化网络请求工具
        APIFactory.getInstance().init(this);
    }

    private void initUMeng() {
        MobclickAgent.setDebugMode(true);

    }

    private void initCLog() {
        String environment = "";

        if (environment.equals("production")) {
            CLog.setLogLevel(CLog.LEVEL_ERROR);
        } else if (environment.equals("beta")) {
            CLog.setLogLevel(CLog.LEVEL_WARNING);
        } else {
            // development
            CLog.setLogLevel(CLog.LEVEL_VERBOSE);
        }

        CubeDebug.DEBUG_IMAGE = true;
        PtrFrameLayout.DEBUG = true;
        PtrFrameLayout.DEBUG = false;

        ImageLoaderFactory.setDefaultImageReSizer(DemoDuiTangImageReSizer.getInstance());
        ImageLoaderFactory.setDefaultImageLoadHandler(new PtrImageLoadHandler());
        String dir = "request-cache";
        // ImageLoaderFactory.init(this);
        RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10);
        Cube.onCreate(this);

    }






    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
