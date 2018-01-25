package com.onekeyask.lawyer.ui.act.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.UserDiscoveries;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.lawyer.AskDetailActivity;
import com.onekeyask.lawyer.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscoverSearchActivity extends BaseToolBarActivity {

    private String keyword;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rlv_index;
    private int index = 1;
    private int size = 10;
    private RelativeLayout rl_no_content;
    private boolean hasMore = true;
    private List<UserDiscoveries.DataBean.UserDiscoveriesBean> data = new ArrayList<>();
    private IndexAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_search);

        keyword = getIntent().getStringExtra("keyword");

        setToolbarText("搜“" + keyword + "”");

        initView();
    }

    private void initView() {
        data.clear();
        rlv_index = (RecyclerView) findViewById(R.id.rlv_search);
        rlv_index.setLayoutManager(new LinearLayoutManager(this));
        rlv_index.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rl_no_content = (RelativeLayout) findViewById(R.id.rl_no_content);
        adapter = new IndexAdapter();
        rlv_index.setAdapter(adapter);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.search_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getBaseContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getBaseContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200);
                index = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(200);
                if (hasMore) {
                    index += 1;
                    initData();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        initData();
    }

    private void initData() {
        OkGo.<String>post(Apis.Discovery)
                .params("userId", UserService.service(getBaseContext()).getUserId())
                .params("keywords", keyword)
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserDiscoveries discoveries = (new Gson()).fromJson(response.body(), UserDiscoveries.class);
                        hasMore = discoveries.getData().isHasMore();
                        if (discoveries.getCode() == 0) {
                            if (index == 1) {
                                data.clear();
                                data.addAll(discoveries.getData().getUserDiscoveries());
                                rlv_index.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                if (data.size() == 0){
                                    rl_no_content.setVisibility(View.VISIBLE);
                                }else {
                                    rl_no_content.setVisibility(View.GONE);
                                }
                            } else {
                                data.addAll(discoveries.getData().getUserDiscoveries());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(discoveries.getMsg());
                        }
                    }
                });


    }


    private class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_index, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.dis_type.setText(data.get(position).getCategoryName());
            String s = data.get(position).getContent().replace(keyword, "<font color='#f79f0a'>" + keyword + "</font>");
            holder.dis_context.setText(Html.fromHtml(s));
            holder.dis_name.setText(data.get(position).getLawyerName());
            holder.dis_office.setText(data.get(position).getOfficeName());
            holder.dis_count.setText(String.valueOf(data.get(position).getSupportCount()));
            Glide.with(getBaseContext()).load(data.get(position).getHeadURL()).into(holder.dis_img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscoverSearchActivity.this, AskDetailActivity.class);
                    intent.putExtra("cid", data.get(position).getChatId());
                    intent.putExtra("lawyerName", data.get(position).getLawyerName());
                    intent.putExtra("officeName", data.get(position).getOfficeName());
                    intent.putExtra("headUrl", data.get(position).getHeadURL());
                    intent.putExtra("sid", data.get(position).getUserServiceId());
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView dis_type, dis_context, dis_name, dis_office, dis_count;
            private ImageView dis_praise;
            private CircleImageView dis_img;


            public ViewHolder(View itemView) {
                super(itemView);
                dis_type = (TextView) itemView.findViewById(R.id.tv_tag_now);
                dis_context = (TextView) itemView.findViewById(R.id.tv_content_index);
                dis_name = (TextView) itemView.findViewById(R.id.tv_law_name);
                dis_office = (TextView) itemView.findViewById(R.id.lawyer_office);
                dis_count = (TextView) itemView.findViewById(R.id.count_zan);
                dis_praise = (ImageView) itemView.findViewById(R.id.iv_like);
                dis_img = (CircleImageView) itemView.findViewById(R.id.iv_law);
            }
        }
    }

}
