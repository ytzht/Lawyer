package com.onekeyask.lawfirm.ui.act.talk;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.AskDetail;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.ui.act.service.TalkingActivity;
import com.onekeyask.lawfirm.utils.photo.Info;
import com.onekeyask.lawfirm.utils.photo.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AskDetailActivity extends BaseActivity {

    @BindView(R.id.tv_time_conversation)
    TextView tvTimeConversation;
    @BindView(R.id.civ_talking_avatar)
    CircleImageView civTalkingAvatar;
    @BindView(R.id.tv_talking_msg)
    TextView tvTalkingMsg;
    @BindView(R.id.iv_talking_msg)
    ImageView ivTalkingMsg;
    @BindView(R.id.btn_order_detail)
    TextView btnOrderDetail;
    @BindView(R.id.tv_talking_name)
    TextView tvTalkingName;
    @BindView(R.id.tv_notice_detail)
    TextView tvNoticeDetail;
    @BindView(R.id.rlv_ask_detail)
    RecyclerView rlvAskDetail;

    private String freeAskId;

    private TextView talk_toolbar_title;
    private Toolbar talk_toolbar;
    private PhotoView mPhotoView;
    View mParent;
    View mBg;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_detail);
        ButterKnife.bind(this);

        initView();


        freeAskId = getIntent().getStringExtra("freeAskId");
        initData();

        initPhotoView();

    }

    private void initView() {
        talk_toolbar_title = (TextView) findViewById(R.id.talk_toolbar_title);
        talk_toolbar_title.setText("问题详情抢单页");
        talk_toolbar = (Toolbar) findViewById(R.id.talk_toolbar);
        setSupportActionBar(talk_toolbar);
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
//    private ProgressDialog dialog;

    private void grabSingle() {
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("正在抢单...");
//        dialog.setCancelable(false);
//        dialog.show();

        SubscriberOnNextListener listener = new SubscriberOnNextListener<FreeAskOrder>() {
            @Override
            public void onNext(FreeAskOrder order) {
                showShort("抢单成功！");
                finish();

                startActivity(TalkingActivity.class, "chatId", order.getChatId() + "");
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
                btnOrderDetail.setVisibility(View.GONE);
                tvNoticeDetail.setText("十分抱歉，您没有抢到该单，已经被别人抢到了，去发现页面再看看，去抢其他问题吧！");
            }
        };

        retrofitUtil.getFreeAskOrder(freeAskId, 3, new ProgressSubscriber<FreeAskOrder>(listener, AskDetailActivity.this, true));

    }

    private AskDetail detail;
    private AlertDialog alertDialog;

    private void initData() {

        SubscriberOnNextListener listener = new SubscriberOnNextListener<AskDetail>() {
            @Override
            public void onNext(AskDetail o) {
                detail = o;
                tvTalkingName.setVisibility(View.VISIBLE);
                tvTalkingName.setText(detail.getFreeask().getName());
                tvTalkingMsg.setText(detail.getFreeask().getContent());
                Glide.with(AskDetailActivity.this).load(detail.getFreeask().getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(civTalkingAvatar);
                tvTimeConversation.setText(detail.getFreeask().getCreateTime());
                btnOrderDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        View dialogView = LayoutInflater.from(AskDetailActivity.this).inflate(R.layout.dialog_order, null);

                        alertDialog = new AlertDialog.Builder(AskDetailActivity.this).create();
                        alertDialog.setView(dialogView);
                        alertDialog.setCancelable(true);
                        alertDialog.show();

                        TextView tv_msg = (TextView) dialogView.findViewById(R.id.alert_tv_msg);
                        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.dialog_cancel_tv);
                        TextView next_tv = (TextView) dialogView.findViewById(R.id.dialog_next_tv);

                        tv_msg.setText("您是否要确认回答客户：" + detail.getFreeask().getName() + "的问题，如果回答请点击继续，钱自动打到您的账户，如果不是请点击取消！");
                        cancel_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (alertDialog.isShowing()) alertDialog.dismiss();
                            }
                        });
                        next_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (alertDialog.isShowing()) alertDialog.dismiss();
                                grabSingle();

                            }
                        });
                    }
                });
                if (detail.getFreeask().getPicList().size() > 0) {
                    rlvAskDetail = (RecyclerView) findViewById(R.id.rlv_ask_detail);
                    rlvAskDetail.setLayoutManager(new LinearLayoutManager(AskDetailActivity.this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    rlvAskDetail.setAdapter(new PictureDetailAdapter());
                }

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getAskDetail(freeAskId, 3, new ProgressSubscriber<AskDetail>(listener, AskDetailActivity.this, false));

    }

    private class PictureDetailAdapter extends RecyclerView.Adapter<PictureDetailAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_talk_left, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.ll_iv_msg.setVisibility(View.VISIBLE);
            holder.tv_talking_msg.setVisibility(View.GONE);
            if (!detail.getFreeask().getName().equals("")) {
                holder.tv_talking_name.setVisibility(View.VISIBLE);
                holder.tv_talking_name.setText(detail.getFreeask().getName());
            }
            holder.tv_time_conversation.setVisibility(View.GONE);
            Glide.with(AskDetailActivity.this).load(detail.getFreeask().getHeadURL()).into(holder.civ_talking_avatarl);
            Glide.with(AskDetailActivity.this).load(detail.getFreeask().getPicList().get(position)).into(holder.iv_pic);

            holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoView p = (PhotoView) view;
                    mInfo = p.getInfo();
                    Picasso.with(AskDetailActivity.this).load(detail.getFreeask().getPicList().get(position)).into(mPhotoView);
                    mBg.startAnimation(in);
                    mBg.setVisibility(View.VISIBLE);
                    mParent.setVisibility(View.VISIBLE);
                    mPhotoView.animaFrom(mInfo);
                }
            });

        }

        @Override
        public int getItemCount() {
            return detail.getFreeask().getPicList().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private PhotoView iv_pic;
            private LinearLayout ll_iv_msg;
            private TextView tv_talking_msg, tv_time_conversation, tv_talking_name;
            private CircleImageView civ_talking_avatarl;


            public ViewHolder(View itemView) {
                super(itemView);
                ll_iv_msg = (LinearLayout) itemView.findViewById(R.id.ll_iv_msg);
                civ_talking_avatarl = (CircleImageView) itemView.findViewById(R.id.civ_talking_avatar);
                iv_pic = (PhotoView) itemView.findViewById(R.id.iv_talking_msg);
                tv_talking_msg = (TextView) itemView.findViewById(R.id.tv_talking_msg);
                tv_time_conversation = (TextView) itemView.findViewById(R.id.tv_time_conversation);
                tv_talking_name = (TextView) itemView.findViewById(R.id.tv_talking_name);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
