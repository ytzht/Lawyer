package com.onekeyask.lawyer.ui.act.consulting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.FreeaskBean;
import com.onekeyask.lawyer.entity.PointsInfo;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.user.LoginActivity;
import com.onekeyask.lawyer.utils.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class QuickConsultingActivity extends BaseToolBarActivity {

    private RelativeLayout rl_free_con, rl_pay_con;
    private TextView tv_points;
    private long askPrice = 0, points = 0;
    private ArrayList<String> photos;
    private String content;
    private int category = -1;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_consulting);
        setToolbarText("咨询");
        service = UserService.service(getBaseContext());
        initView();
    }

    private void initView() {
        rl_free_con = (RelativeLayout) findViewById(R.id.rl_free_con);
        rl_pay_con = (RelativeLayout) findViewById(R.id.rl_pay_con);
        tv_points = (TextView) findViewById(R.id.tv_points);
        photos = new ArrayList<>();

        photos = getIntent().getStringArrayListExtra("photos");
        content = getIntent().getStringExtra("content");
        category = getIntent().getIntExtra("category", 0);

        getResultOnNext = new SubscriberOnNextListener<PointsInfo>() {
            @Override
            public void onNext(PointsInfo info) {
                askPrice = info.getAskPrice();
                points = info.getPoints();
                tv_points.setText("消耗" + askPrice + "积分(当前积分" + points + ")");
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getPointsInfo(UserService.service(getBaseContext()).getUserId(), new ProgressSubscriber<PointsInfo>(getResultOnNext, QuickConsultingActivity.this, true));

        rl_free_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!service.isLogin()) {
                    startActivity(LoginActivity.class);
                } else {


                    if (points > askPrice) {
                        photoMap.clear();
                        i = 0;
                        k = 0;
                        if (photos.size() > 0) {
                            hud = KProgressHUD.create(QuickConsultingActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                    .setCancellable(true)
                                    .show();
                            i = 0;
                            for (int j = 0; j < photos.size(); j++) {

                                goLuban();

                            }
                        } else {
                            goSubmit();
                        }

                    } else {
                        showShort("金币不足");
                    }
                }
            }
        });

        rl_pay_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!service.isLogin()) {
                    startActivity(LoginActivity.class);
                } else {
                    Intent intent = new Intent(QuickConsultingActivity.this, PayQuickConsultingActivity.class);
                    intent.putExtra("content", content);
                    intent.putExtra("category", category);
                    intent.putStringArrayListExtra("photos", photos);
                    startActivity(intent);
                }
            }
        });
    }

    private KProgressHUD hud;
    private Map<String, RequestBody> photoMap = new HashMap<>();

    private int i = 0;

    private int k = 0;

    private void goLuban() {
        i += 1;
        File file = new File(photos.get(i - 1));
        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        k += 1;
                        String key = "pic" + k + "\"; filename=\"pic" + k + photos.get(k - 1).substring(photos.get(k - 1).lastIndexOf("."));
                        photoMap.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), file));
                        L.d(key);

                        L.d("photoMap.size() " + photoMap.size() + " photos.size() " + photos.size());
                        if (photoMap.size() == photos.size()) {
                            if (hud.isShowing()) hud.dismiss();
                            goSubmit();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(e.toString());
                        if (hud.isShowing()) hud.dismiss();
                        showShort("第" + i + "张图片压缩出错");
                    }
                }).launch();    //启动压缩
    }

    private void goSubmit() {
        photoMap.put("userId", RequestBody.create(null, UserService.service(getBaseContext()).getUserId() + ""));
        photoMap.put("content", RequestBody.create(null, content));
        photoMap.put("category", RequestBody.create(null, category + ""));

        photoMap.put("type", RequestBody.create(null, "1"));

        getResultOnNext = new SubscriberOnNextListener<FreeaskBean>() {
            @Override
            public void onNext(FreeaskBean askResult) {

                startActivity(TalkingActivity.class, "fid", askResult.getFreeaskId() + "", "cid", askResult.getChatId() + "");
                finish();
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.freeUpload(photoMap, new ProgressSubscriber<FreeaskBean>(getResultOnNext, QuickConsultingActivity.this, true));

    }

}
