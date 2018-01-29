package com.onekeyask.lawfirm.ui.act.found;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.AskDetail;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.entity.MyAskDetail;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.ui.act.service.TalkingActivity;
import com.onekeyask.lawfirm.utils.AssetsUtils;
import com.onekeyask.lawfirm.utils.UserService;
import com.onekeyask.lawfirm.utils.photo.Info;
import com.onekeyask.lawfirm.utils.photo.PhotoView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoundDetailActivity extends BaseActivity {

    private CircleImageView detailimg;
    private TextView detailname;
    private TextView foundtag;
    private TextView foundprice;
    private TextView detailtime;
    private TextView detailread;
    private TextView wanttalk;
    private TextView detail_txt;
    private LinearLayout ll_pic;
    private PhotoView pic_1, pic_2, pic_3;
    private String fid;

    private PhotoView mPhotoView;
    View mParent;
    View mBg;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_detail);
        initView();
        initPhotoView();
        initData();
    }
    private AlertDialog alertDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void initPhotoView() {
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mParent = findViewById(R.id.parent);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });

    }


    private void initData() {
        fid = getIntent().getStringExtra("fid");
        OkGo.<String>get(Apis.FreeaskDetail)
                .params("freeaskId", fid)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyAskDetail detail = (new Gson()).fromJson(response.body(), MyAskDetail.class);

                        if (detail.getCode() == 0) {
                            initDetail(detail.getData());
                        }else {
                            showShort(detail.getMsg());
                            finish();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("=====", "onError: "+response.body());
                    }
                });
    }

    private void initDetail(final AskDetail detail) {
        Glide.with(FoundDetailActivity.this).load(detail.getFreeask().getHeadURL())
                .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                .into(detailimg);
        detailname.setText(detail.getFreeask().getName());
        foundtag.setText(detail.getFreeask().getCategoryName());
        foundprice.setText("￥" + detail.getFreeask().getMoney());
        detailtime.setText(AssetsUtils.getGapTime(detail.getFreeask().getCreateTime()));
        detailread.setText(detail.getFreeask().getReadCount() + "阅读");
        detail_txt.setText(detail.getFreeask().getContent());

        if (detail.getFreeask().getPicList().size() == 0){
            ll_pic.setVisibility(View.GONE);
        }else {
            ll_pic.setVisibility(View.VISIBLE);
            if (detail.getFreeask().getPicList().size() >= 1) {
                pic_1.setVisibility(View.VISIBLE);
                Glide.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(0)).into(pic_1);
                pic_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoView p = (PhotoView) v;
                        mInfo = p.getInfo();
                        Picasso.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(0)).into(mPhotoView);
                        mBg.startAnimation(in);
                        mBg.setVisibility(View.VISIBLE);
                        mParent.setVisibility(View.VISIBLE);
                        mPhotoView.animaFrom(mInfo);
                    }
                });
            }
            if (detail.getFreeask().getPicList().size() >= 2) {
                pic_2.setVisibility(View.VISIBLE);
                Glide.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(1)).into(pic_2);
                pic_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoView p = (PhotoView) v;
                        mInfo = p.getInfo();
                        Picasso.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(1)).into(mPhotoView);
                        mBg.startAnimation(in);
                        mBg.setVisibility(View.VISIBLE);
                        mParent.setVisibility(View.VISIBLE);
                        mPhotoView.animaFrom(mInfo);
                    }
                });
            }
            if (detail.getFreeask().getPicList().size() >= 3) {
                pic_3.setVisibility(View.VISIBLE);
                Glide.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(2)).into(pic_3);
                pic_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoView p = (PhotoView) v;
                        mInfo = p.getInfo();
                        Picasso.with(FoundDetailActivity.this).load(detail.getFreeask().getPicList().get(2)).into(mPhotoView);
                        mBg.startAnimation(in);
                        mBg.setVisibility(View.VISIBLE);
                        mParent.setVisibility(View.VISIBLE);
                        mPhotoView.animaFrom(mInfo);
                    }
                });
            }
        }

        if (detail.getFreeask().getStatus() == 2){//已回复
            wanttalk.setBackground(ContextCompat.getDrawable(FoundDetailActivity.this, R.drawable.talkover));
            wanttalk.setText("已被回复");
        }else {
            wanttalk.setBackground(ContextCompat.getDrawable(FoundDetailActivity.this, R.drawable.wanttalk));
            wanttalk.setText("我要回复");
            wanttalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = LayoutInflater.from(FoundDetailActivity.this).inflate(R.layout.dialog_order, null);

                    alertDialog = new AlertDialog.Builder(FoundDetailActivity.this).create();
                    alertDialog.setView(dialogView);
                    alertDialog.setCancelable(true);
                    alertDialog.show();

                    TextView tv_msg = (TextView) dialogView.findViewById(R.id.alert_tv_msg);
                    TextView cancel_tv = (TextView) dialogView.findViewById(R.id.dialog_cancel_tv);
                    TextView next_tv = (TextView) dialogView.findViewById(R.id.dialog_next_tv);

                    tv_msg.setText("您是否确认要回答客户问题");
                    cancel_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (alertDialog.isShowing()) alertDialog.dismiss();
                        }
                    });


                    {
                        next_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (alertDialog.isShowing()) alertDialog.dismiss();
                                grabSingle();

                            }
                        });
                    }

                }
            });
        }
    }

    private Toolbar talk_toolbar;
    private void initView() {
        this.wanttalk = (TextView) findViewById(R.id.want_talk);
        this.detailread = (TextView) findViewById(R.id.detail_read);
        this.detailtime = (TextView) findViewById(R.id.detail_time);
        this.foundprice = (TextView) findViewById(R.id.found_price);
        this.foundtag = (TextView) findViewById(R.id.found_tag);
        this.detailname = (TextView) findViewById(R.id.detail_name);
        this.detail_txt = (TextView) findViewById(R.id.detail_txt);
        this.pic_1 = (PhotoView) findViewById(R.id.pic_1);
        this.pic_2 = (PhotoView) findViewById(R.id.pic_2);
        this.pic_3 = (PhotoView) findViewById(R.id.pic_3);
        this.ll_pic = (LinearLayout) findViewById(R.id.ll_pic);
        this.detailimg = (CircleImageView) findViewById(R.id.detail_img);
        talk_toolbar = (Toolbar) findViewById(R.id.talk_toolbar);
        setSupportActionBar(talk_toolbar);
    }

    private void grabSingle() {
        OkGo.<String>get(Apis.FreeAskOrder).params("freeaskId", fid)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        FreeAskOrder order = (new Gson()).fromJson(response.body(), FreeAskOrder.class);
                        if (order.getCode() == 0) {
                            startActivity(TalkingActivity.class, "chatId", order.getData().getChatId() + "");
                            finish();
                        } else {
                            showShort(response.body());
                            wanttalk.setVisibility(View.GONE);

                            showShort("十分抱歉，您没有抢到该单，已经被别人抢到了，去发现页面再看看，去抢其他问题吧！");
                        }
                    }
                });
    }
}
