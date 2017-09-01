package com.onekeyask.lawyer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.ui.act.lawyer.AskDetailActivity;
import com.onekeyask.lawyer.utils.MyDecoration;
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


public class SimpleCardFragment extends BaseFragment {
    private int id;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView discover_list;
    private View view;
    private DiscoverAdapter adapter;
    private int index = 1;
    private RelativeLayout rl_no_content;
    private int size = 10;
    private boolean hasMore = true;
    private List<UserDiscoveries.DataBean.UserDiscoveriesBean> data = new ArrayList<>();

    public static SimpleCardFragment getInstance(int id) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.id = id;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_card, null);
        initView();

        return view;
    }

    private void initView() {
        index = 1;
        data.clear();
        rl_no_content = (RelativeLayout) view.findViewById(R.id.rl_no_content);
        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.discover_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
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


        discover_list = (RecyclerView)view.findViewById(R.id.discover_list);
        discover_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        discover_list.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new DiscoverAdapter();
        discover_list.setAdapter(adapter);
        initData();

    }

    private void initData() {
        OkGo.<String>get(Apis.Discovery)
                .params("userId", UserService.service(getActivity()).getUserId())
                .params("category", id)
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
                                discover_list.setAdapter(adapter);
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

    private class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.cell_discover, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.dis_type.setText(data.get(position).getCategoryName());
            holder.dis_context.setText(data.get(position).getContent());
            holder.dis_name.setText(data.get(position).getLawyerName());
            holder.dis_office.setText(data.get(position).getOfficeName());
            holder.dis_count.setText(String.valueOf(data.get(position).getSupportCount()));
            Glide.with(getActivity()).load(data.get(position).getHeadURL()).into(holder.dis_img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AskDetailActivity.class);
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

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView dis_type, dis_context, dis_name, dis_office, dis_count;
            private ImageView dis_praise;
            private CircleImageView dis_img;

            public ViewHolder(View itemView) {
                super(itemView);
                dis_type = (TextView) itemView.findViewById(R.id.dis_type);
                dis_context = (TextView) itemView.findViewById(R.id.dis_context);
                dis_name = (TextView) itemView.findViewById(R.id.dis_name);
                dis_office = (TextView) itemView.findViewById(R.id.dis_office);
                dis_count = (TextView) itemView.findViewById(R.id.dis_count);
                dis_praise = (ImageView)  itemView.findViewById(R.id.dis_praise);
                dis_img = (CircleImageView) itemView.findViewById(R.id.dis_img);
            }
        }
    }
}