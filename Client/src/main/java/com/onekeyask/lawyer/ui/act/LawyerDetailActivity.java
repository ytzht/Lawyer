package com.onekeyask.lawyer.ui.act;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

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
    @BindView(R.id.ll_type1)
    LinearLayout llType1;
    @BindView(R.id.ll_type2)
    LinearLayout llType2;
    @BindView(R.id.ll_type3)
    LinearLayout llType3;
    @BindView(R.id.special_rl)
    RecyclerView special_rl;
    @BindView(R.id.tags_fl)
    TagFlowLayout tagsFl;
    @BindView(R.id.service_list)
    RecyclerView serviceList;
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
    private int lawyerId;
    private LawyerDetail.DataBean data;
    private SpecialAdapter specialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detail);
        ButterKnife.bind(this);

        lawyerId = getIntent().getIntExtra("lawyerId", 0);


        initData();
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

        String[] mValues = new String[data.getLawyer().getTags().size()];
        for (int i = 0; i < data.getLawyer().getTags().size(); i++) {
            mValues[i] = data.getLawyer().getTags().get(i);
        }

        tagsFl.setAdapter(new TagAdapter<String>(mValues) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(LawyerDetailActivity.this).inflate(R.layout.tag_tv, tagsFl, false);
                tv.setText(s);
                return tv;
            }
        });
        tagsFl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                ArrayList<Boolean> booleanList = new ArrayList<>();
                booleanList.clear();
                for (int i = 0; i < data.getLawyer().getTags().size(); i++) {
                    booleanList.add(false);
                }
                for (Integer str : selectPosSet) {
                    booleanList.set(str, true);
                }
                for (int i = 0; i < booleanList.size(); i++) {
                }
                data.getLawyer().setTagIsSelected(booleanList);
            }
        });

        tagsFl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TextView tv = (TextView) view.findViewById(R.id.tag_tv);
                if (data.getLawyer().getTagIsSelected().get(position)) {
                    tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                } else {
                    tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                }
                return true;
            }
        });

        serviceCount.setText(String.valueOf(data.getLawyer().getServiceCount()));
        serviceScore.setText("" + data.getLawyer().getServiceScore() + "%");
        isFavorite.setFavorite(data.getLawyer().isFavorite());
        if (isFavorite.isFavorite()) {
            favoriteTv.setText("已关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_o));
        }else {
            favoriteTv.setText("关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_g));
        }
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFavorite();
            }
        });
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
}
