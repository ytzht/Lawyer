package com.onekeyask.lawfirm.ui.act.found;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.TalkingConversationList;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.utils.HideUtil;
import com.onekeyask.lawfirm.utils.UserService;
import com.onekeyask.lawfirm.utils.photo.Info;
import com.onekeyask.lawfirm.utils.photo.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DiscoveryDetailActivity extends BaseActivity {

    @BindView(R.id.rlv_talking)
    RecyclerView rlvTalking;

    private String fid;
    private int sid;
    private int LEFT = 1;
    private int size = 6;
    private int page = 1;
    private boolean hasMore = true;
    private Toolbar talk_toolbar;
    private PhotoView mPhotoView;
    View mParent;
    View mBg;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);
    private TextView talk_toolbar_title;
    private TalkingContentAdapter adapter;
    private int lawyerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_detail);
        ButterKnife.bind(this);

        initData();

        initPhotoView();


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
        HideUtil.init(this);
        talk_toolbar_title = (TextView) findViewById(R.id.talk_toolbar_title);
        talk_toolbar_title.setText("咨询详情页");
        talk_toolbar = (Toolbar) findViewById(R.id.talk_toolbar);
        setSupportActionBar(talk_toolbar);
        fid = getIntent().getStringExtra("fid");

        rlvTalking.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TalkingContentAdapter();
        rlvTalking.setAdapter(adapter);

        beanList.clear();
        loadData();

        rlvTalking.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition()
                            == recyclerView.getLayoutManager().getItemCount() - 1) {
                        if (hasMore) {
                            page++;
                            loadData();
                        }
                    }
                }

            }
        });
    }


    List<TalkingConversationList.DataBean.ConversationListBean> beanList = new ArrayList<>();

    private void loadData() {
        OkGo.<String>get(Apis.getDiscoveryDetail)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("freeaskId", fid)
                .params("size", size)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        TalkingConversationList conversationList = (new Gson()).fromJson(response.body(), TalkingConversationList.class);
                        if (conversationList.getCode() == 0) {
                            hasMore = conversationList.getData().isHasMore();
                            lawyerId = conversationList.getData().getLawyerId();

                            List<TalkingConversationList.DataBean.ConversationListBean> listBeen = conversationList.getData().getConversationList();
//                            conversationId = listBeen.get(listBeen.size() - 1).getConversationId();
                            sid = conversationList.getData().getUserServiceId();
                            beanList.addAll(listBeen);

                            adapter.notifyDataSetChanged();
                        } else {
                            showShort(conversationList.getMsg());
                        }
                    }
                });
    }


    private class TalkingContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {

            if (position == beanList.size()) {
                return R.layout.cell_load_more;
            } else if (beanList.get(position).getFrom() == LEFT) {
                return R.layout.cell_talk_left;
            } else {
                return R.layout.cell_talk_right;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_load_more) {
                return new MoreViewHolder(view);
            } else {
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (position == beanList.size()) {
                if (hasMore) {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("   ");
                } else {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
                }

                if (beanList.size() < size) {
                    ((MoreViewHolder) holder).progress_bar_more.setVisibility(View.GONE);
                    ((MoreViewHolder) holder).tv_progress_more.setVisibility(View.VISIBLE);
                    ((MoreViewHolder) holder).tv_progress_more.setText("已全部加载，没有更多消息了");
                }
            } else {
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showShort(beanList.get(position).getContent());
                        L.d(beanList.get(position).getContent());
                    }
                });
                Glide.with(DiscoveryDetailActivity.this).load(beanList.get(position).getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(((ViewHolder) holder).civ_talking_avatar);
//                if (beanList.get(position).getFrom() == LEFT)
//                    ((ViewHolder) holder).civ_talking_avatar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(DiscoveryDetailActivity.this, LawyerDetailActivity.class);
//                            intent.putExtra("lawyerId", beanList.get(position).getLawyerId());
//                            startActivity(intent);
//                        }
//                    });
                if (beanList.get(position).isIsPicture()) {
                    ((ViewHolder) holder).tv_talking_msg.setVisibility(View.GONE);
                    ((ViewHolder) holder).ll_iv_msg.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).iv_talking_msg.disenable();
                    Glide.with(DiscoveryDetailActivity.this).load(beanList.get(position).getContent()).into(((ViewHolder) holder).iv_talking_msg);

                    ((ViewHolder) holder).iv_talking_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PhotoView p = (PhotoView) view;
                            mInfo = p.getInfo();
                            Picasso.with(DiscoveryDetailActivity.this).load(beanList.get(position).getContent()).into(mPhotoView);
                            mBg.startAnimation(in);
                            mBg.setVisibility(View.VISIBLE);
                            mParent.setVisibility(View.VISIBLE);
                            mPhotoView.animaFrom(mInfo);
                        }
                    });

                } else {
                    ((ViewHolder) holder).tv_talking_msg.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).ll_iv_msg.setVisibility(View.GONE);
                    ((ViewHolder) holder).tv_talking_msg.setText(beanList.get(position).getContent());
                }

                ((ViewHolder) holder).tv_time_conversation.setText(beanList.get(position).getTime());
            }
        }

        @Override
        public int getItemCount() {
            return beanList.size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView civ_talking_avatar;
            private TextView tv_talking_msg, tv_time_conversation;
            private PhotoView iv_talking_msg;
            private LinearLayout ll_iv_msg;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_iv_msg = (LinearLayout) itemView.findViewById(R.id.ll_iv_msg);
                civ_talking_avatar = (CircleImageView) itemView.findViewById(R.id.civ_talking_avatar);
                tv_talking_msg = (TextView) itemView.findViewById(R.id.tv_talking_msg);
                iv_talking_msg = (PhotoView) itemView.findViewById(R.id.iv_talking_msg);
                tv_time_conversation = (TextView) itemView.findViewById(R.id.tv_time_conversation);
            }
        }

        public class MoreViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_progress_more;
            private ProgressBar progress_bar_more;

            public MoreViewHolder(View itemView) {
                super(itemView);
                tv_progress_more = (TextView) itemView.findViewById(R.id.tv_progress_more);
                progress_bar_more = (ProgressBar) itemView.findViewById(R.id.progress_bar_more);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
