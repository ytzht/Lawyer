package com.onekeyask.lawyer.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

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
import com.onekeyask.lawyer.BuildConfig;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.global.MyPushIntentService;
import com.onekeyask.lawyer.http.APIFactory;
import com.onekeyask.lawyer.image.DemoDuiTangImageReSizer;
import com.onekeyask.lawyer.image.PtrImageLoadHandler;
import com.onekeyask.lawyer.utils.UserService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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

public class MyApplication extends MultiDexApplication {

    private static final String TAG = "app=====";
    public static Context aContext;


    @Override
    public void onCreate() {
        super.onCreate();

//        com.wanjian.sak.LayoutManager.init(this);

        initCLog();

        initAnalytics();

        initRetrofit();

        initPush();

        initShare();

        initOkGo(this);

        Utils.init(this);
    }

    private void initShare() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx51d42419270bac61", "3aa5bdca57372f5ea207a493d3766696");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    private void initPush() {
        //如需手动获取device token，可以调用mPushAgent.getRegistrationId()方法
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                L.d("deviceToken===== " + deviceToken);
                UserService.service(getBaseContext()).setDeviceToken(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                L.e("push onFailure " + s + " " + s1);
            }
        });
        mPushAgent.setDebugMode(BuildConfig.DEBUG);

        //自定义通知打开动作
//        UmengMessageHandler notificationClickHandler = new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage uMessage) {
//                Log.d(TAG, "getNotification: "+uMessage.custom);
//                return null;
//            }
//        };
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//        为免过度打扰用户，SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯。如果需要改变默认的静音时间，可以使用以下接口：
//        mPushAgent.setNoDisturbMode(23, 0, 7, 0);
//        mPushAgent.setNoDisturbMode(0, 0, 0, 0);

//        默认情况下，同一台设备在1分钟内收到同一个应用的多条通知时，不会重复提醒，同时在通知栏里新的通知会替换掉旧的通知。可以通过如下方法来设置冷却时间：
//        mPushAgent.setMuteDurationSeconds(int seconds);

//        通知栏可以设置最多显示通知的条数，当有新通知到达时，会把旧的通知隐藏。可以设置为0~10之间任意整数。当参数为0时，表示不合并通知。
//        mPushAgent.setDisplayNotificationNumber(int number);

//        客户端控制通知到达响铃、震动、呼吸灯
//        MsgConstant.NOTIFICATIONPLAYSERVER（服务端控制）
//        MsgConstant.NOTIFICATIONPLAYSDKENABLE（客户端允许）
//        MsgConstant.NOTIFICATIONPLAYSDKDISABLE（客户端禁止）
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动

//        图标    24*24px在drawable-mdpi 下    64*64px在drawable-mdpi 下， 128*128px 在drawable-xhdpi 中。
//        制作图标时， 注意图片各边留一个像素的透明。 建议使用黑白图标。

    }


    private void initRetrofit() {

        aContext = this;
        //初始化网络请求工具
        APIFactory.getInstance().init(this);

    }

    private void initAnalytics() {
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
//        params.put("commonParamsKey2", "这里支持中文参数");
//-------------------------------------------------------------------------------------//

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
