package com.onekeyask.lawfirm.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.onekeyask.lawfirm.global.BuildConfig;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.global.MyPushIntentService;
import com.onekeyask.lawfirm.http.APIFactory;
import com.onekeyask.lawfirm.image.DemoDuiTangImageReSizer;
import com.onekeyask.lawfirm.image.PtrImageLoadHandler;
import com.onekeyask.lawfirm.utils.UserService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import in.srain.cube.util.CLog;
import in.srain.cube.util.CubeDebug;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.OkHttpClient;

/**
 * Created by zht on 2017/04/08 9:09
 */

public class MyApplication extends Application {

    public static Context aContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initCLog();

        initUMeng();

        initPush();

//        initShare();
        initOkGo(this);

        Utils.init(this);
    }

    private static MyApplication instance;

    public static MyApplication instance() {
        return instance;
    }

    private void initPush() {
        //如需手动获取device token，可以调用mPushAgent.getRegistrationId()方法
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                L.d("deviceToken " + deviceToken);
                UserService.service(getBaseContext()).setDeviceToken(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                L.e("push onFailure " + s + " " + s1);
            }
        });
        mPushAgent.setDebugMode(BuildConfig.DEBUG);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    public static void initRetrofit(Application aContext) {

        APIFactory.getInstance().init(aContext);
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


    public static void initOkGo(Application context) {
//        OkGo.getInstance().init(this);
        initRetrofit(context);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo=====");
//log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

//非必要情况，不建议使用，第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
//        builder.addInterceptor(new ChuckInterceptor(this));

        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(context)));
//使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
//使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
//方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
//方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
//配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());


//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("Authorization", "Bearer " + UserService.service(context).getToken());
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        if (!UserService.service(context).getToken().equals("-1"))
            params.put("token", UserService.service(context).getToken());

        OkGo.getInstance().init(context)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
