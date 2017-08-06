package com.onekeyask.lawyer.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.IsFavorite;
import com.onekeyask.lawyer.entity.LawyerDetail;
import com.onekeyask.lawyer.entity.MyLawyer;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.lawyer.LawyerIntroActivity;
import com.onekeyask.lawyer.ui.act.lawyer.PersonLawyerActivity;
import com.onekeyask.lawyer.ui.act.lawyer.TxtPicAskActivity;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LawyerDetailActivity extends BaseToolBarActivity {

    @BindView(R.id.lawyer_header)
    CircleImageView lawyerHeader;
    @BindView(R.id.lawyer_name)
    TextView lawyerName;
    @BindView(R.id.lawyer_office)
    TextView lawyerOffice;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.rl_lawyer_header)
    RelativeLayout rlLawyerHeader;
    @BindView(R.id.special_rl)
    RecyclerView special_rl;
    @BindView(R.id.tags_fl)
    RecyclerView tagsFl;
    @BindView(R.id.service_list)
    RecyclerView serviceList;
    @BindView(R.id.h_service_type)
    RecyclerView h_service_type;
    @BindView(R.id.serviceCount)
    TextView serviceCount;
    @BindView(R.id.serviceScore)
    TextView serviceScore;
    @BindView(R.id.ll_send_money)
    LinearLayout llSendMoney;
    @BindView(R.id.favorite_iv)
    ImageView favoriteIv;
    @BindView(R.id.favorite_tv)
    TextView favoriteTv;
    @BindView(R.id.ll_favorite)
    LinearLayout llFavorite;
    @BindView(R.id.consulting)
    TextView consulting;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.header_more)
    ImageView headerMore;
    @BindView(R.id.service_notes)
    TextView service_notes;
    private int lawyerId;
    private LawyerDetail.DataBean data;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private CommentListAdapter commentListAdapter;
    private SpecialAdapter specialAdapter;
    private CommentTagAdapter commentTagAdapter;
    private ServiceTypeAdapter serviceTypeAdapter;
    private List<MyLawyer.DataBean.LawyerListBean> comData = new ArrayList<>();

    private int type = 1;//图文咨询、电话咨询、私人律师

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detail);
        ButterKnife.bind(this);

        lawyerId = getIntent().getIntExtra("lawyerId", 0);


        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
                index = 1;
                initCListData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
                if (hasMore) {
                    index += 1;
                    initCListData();
                }
            }
        });


        initData();

        initClick();
    }

    private void initClick() {

        consulting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1){
                    startActivity(PersonLawyerActivity.class);
                }else if (type == 2){
                    startActivity(TxtPicAskActivity.class);
                }else {
                     showShort("电话咨询");
                }
            }
        });

        headerMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LawyerDetailActivity.this, LawyerIntroActivity.class);
                intent.putExtra("lawyerId", lawyerId);
                intent.putExtra("notes", data.getLawyer().getNotes());
                startActivity(intent);
            }
        });
    }

    private void initCListData() {
        OkGo.<String>get(Apis.CommentList)
                .params("score", "0") //满意度评分，0 全部5 很满意3 满意1 不满意
                .params("lawyerId", "3")
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        MyLawyer myLawyer = (new Gson()).fromJson(response.body(), MyLawyer.class);
//                        hasMore = myLawyer.getData().isHasMore();
//                        if (myLawyer.getCode() == 0) {
//
//                            if (index == 1) {
//                                comData.clear();
//                                comData.addAll(myLawyer.getData().getLawyerList());
//                                serviceList.setAdapter(commentListAdapter);
//                                commentListAdapter.notifyDataSetChanged();
//                            } else {
//                                comData.addAll(myLawyer.getData().getLawyerList());
//                                commentListAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            showShort(myLawyer.getMsg());
//                        }
                    }
                });
    }

    private void initData() {


        OkGo.<String>get(Apis.LawyerDetail)
                .params("lawyerId", lawyerId)
                .params("userId", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LawyerDetail detail = (new Gson()).fromJson(response.body(), LawyerDetail.class);
                        if (detail.getCode() == 0) {
                            data = detail.getData();
                            setValue();
                        } else {
                            finish();
                            showShort(detail.getMsg());
                        }
                    }
                });
    }

    private void setValue() {
        data.getLawyer().getServiceList().get(0).setCheck(true);
        service_notes.setText(data.getLawyer().getServiceList().get(0).getNotes());

        Glide.with(this).load(data.getLawyer().getHeadURL()).into(lawyerHeader);
        lawyerName.setText(data.getLawyer().getName());
        lawyerOffice.setText(data.getLawyer().getLawyerOfficeName());

        if (data.getLawyer().getTags().size() > 0) {
            tvTag1.setVisibility(View.VISIBLE);
            tvTag1.setText(data.getLawyer().getTags().get(0));
        }

        if (data.getLawyer().getTags().size() > 1) {
            tvTag2.setVisibility(View.VISIBLE);
            tvTag2.setText(data.getLawyer().getTags().get(1));
        }

        if (data.getLawyer().getTags().size() > 2) {
            tvTag3.setVisibility(View.VISIBLE);
            tvTag3.setText(data.getLawyer().getTags().get(2));
        }
        special_rl.setLayoutManager(new GridLayoutManager(LawyerDetailActivity.this, 4));
        specialAdapter = new SpecialAdapter();
        special_rl.setAdapter(specialAdapter);

        tagsFl.setLayoutManager(new GridLayoutManager(LawyerDetailActivity.this, 4));
        commentTagAdapter = new CommentTagAdapter();
        tagsFl.setAdapter(commentTagAdapter);

        String[] mValues = new String[data.getLawyer().getTags().size()];
        for (int i = 0; i < data.getLawyer().getTags().size(); i++) {
            mValues[i] = data.getLawyer().getTags().get(i);
        }

        serviceCount.setText(String.valueOf(data.getLawyer().getServiceCount()));
        serviceScore.setText("" + data.getLawyer().getServiceScore() + "%");
        isFavorite.setFavorite(data.getLawyer().isFavorite());
        if (isFavorite.isFavorite()) {
            favoriteTv.setText("已关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_o));
        } else {
            favoriteTv.setText("关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_g));
        }
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFavorite();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        h_service_type.setLayoutManager(linearLayoutManager);
        serviceTypeAdapter = new ServiceTypeAdapter();
        h_service_type.setAdapter(serviceTypeAdapter);


        serviceList.setLayoutManager(new LinearLayoutManager(this));
        serviceList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        commentListAdapter = new CommentListAdapter();
        serviceList.setAdapter(commentListAdapter);
    }


    private IsFavorite isFavorite = new IsFavorite();

    private void toFavorite() {

        getResultOnNext = new SubscriberOnNextListener<IsFavorite>() {
            @Override
            public void onNext(IsFavorite favorite) {
                initIsFavorite();
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getFavoriteLawyer("2", "3", !(isFavorite.isFavorite()), new ProgressSubscriber<IsFavorite>(getResultOnNext, LawyerDetailActivity.this, true));

    }

    private void initIsFavorite() {
        getResultOnNext = new SubscriberOnNextListener<IsFavorite>() {
            @Override
            public void onNext(IsFavorite o) {
                isFavorite = o;
                if (isFavorite.isFavorite()) {
                    //已经关注过了
                    favoriteTv.setText("已关注");
                    favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_o));
                } else {
                    //还未关注该律师
                    favoriteTv.setText("关注");
                    favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_g));
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getIsFavorite("2", "3", new ProgressSubscriber<IsFavorite>(getResultOnNext, LawyerDetailActivity.this, false));

    }


    private class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_law_tag, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_tag_text.setText(data.getLawyer().getSpecial().get(position));
        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getSpecial().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_tag_text;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag_text = (TextView) itemView.findViewById(R.id.tv_tag_text);
            }
        }
    }

    private class CommentTagAdapter extends RecyclerView.Adapter<CommentTagAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_law_tag, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv_tag_text.setText(data.getLawyer().getTags().get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.getLawyer().setPosition(position);
                    commentTagAdapter.notifyDataSetChanged();
                }
            });

            if (data.getLawyer().getPosition() == position){
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
            }else {
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_unselect));
            }




        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getTags().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_tag_text;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag_text = (TextView) itemView.findViewById(R.id.tv_tag_text);
            }
        }
    }


    private class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_comment, parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return comData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView comName;
            private TextView comTime;
            private TextView comType;
            private TextView comTxt;
            private TextView comContent;

            public ViewHolder(View itemView) {
                super(itemView);
                comName = (TextView) itemView.findViewById(R.id.com_name);
                comTime = (TextView) itemView.findViewById(R.id.com_time);
                comType = (TextView) itemView.findViewById(R.id.com_type);
                comTxt = (TextView) itemView.findViewById(R.id.com_txt);
                comContent = (TextView) itemView.findViewById(R.id.com_content);
            }
        }

    }


    private class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_service_type, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Glide.with(LawyerDetailActivity.this).load(data.getLawyer().getServiceList().get(position).getImgURL()).into(holder.service_pic);
            holder.service_name.setText(data.getLawyer().getServiceList().get(position).getServiceName());

            if (data.getLawyer().getServiceList().get(position).isCheck()) {
                holder.ll_type.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.tag_select));
            } else {
                holder.ll_type.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            }


            holder.ll_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = data.getLawyer().getServiceList().get(position).getServiceType();
                    consulting.setText(data.getLawyer().getServiceList().get(position).getServiceName()
                            + data.getLawyer().getServiceList().get(position).getPriceList().get(0).getPrice()
                            + "元/"
                            + data.getLawyer().getServiceList().get(position).getPriceList().get(0).getCycle());

                    if (!data.getLawyer().getServiceList().get(position).isCheck()) {
                        for (int i = 0; i < data.getLawyer().getServiceList().size(); i++) {
                            data.getLawyer().getServiceList().get(i).setCheck(false);
                        }
                        data.getLawyer().getServiceList().get(position).setCheck(true);
                        serviceTypeAdapter.notifyDataSetChanged();
                    }
                    service_notes.setText(data.getLawyer().getServiceList().get(position).getNotes());
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getServiceList().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout ll_type;
            private CircleImageView service_pic;
            private TextView service_name;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_type = (LinearLayout) itemView.findViewById(R.id.ll_type);
                service_pic = (CircleImageView) itemView.findViewById(R.id.service_pic);
                service_name = (TextView) itemView.findViewById(R.id.service_name);
            }
        }
    }
}
