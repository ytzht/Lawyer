package com.onekeyask.lawyer.ui.act.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MyLawyer;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.ui.act.lawyer.LawyerDetailActivity;
import com.onekeyask.lawyer.ui.act.search.SearchLawActivity;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.onekeyask.lawyer.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.onekeyask.lawyer.global.Apis.MyLawyerList;

/**
 * Created by zht on 2017/04/12 17:24
 */

public class MyLawyerFragment extends BaseFragment {

    RecyclerView myLawyerList;
    LinearLayout search_lawyer_ll;
    SmartRefreshLayout refreshLayout;
    private View view;
    private MyLawyerAdapter adapter;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private List<MyLawyer.DataBean.LawyerListBean> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_lawyer_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        search_lawyer_ll = (LinearLayout) view.findViewById(R.id.search_lawyer_ll);
        search_lawyer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchLawActivity.class);
            }
        });
        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
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

        myLawyerList = (RecyclerView)view.findViewById(R.id.my_lawyer_list);
        myLawyerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myLawyerList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new MyLawyerAdapter();
        myLawyerList.setAdapter(adapter);
        initData();
    }

    private void initData() {

        OkGo.<String>get(MyLawyerList)
                .params("userId", UserService.service(getActivity()).getUserId())
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLawyer myLawyer = (new Gson()).fromJson(response.body(), MyLawyer.class);

                        if (myLawyer.getCode() == 0) {
                            hasMore = myLawyer.getData().isHasMore();
                            if (index == 1) {
                                data.clear();
                                data.addAll(myLawyer.getData().getLawyerList());
                                myLawyerList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                data.addAll(myLawyer.getData().getLawyerList());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(myLawyer.getMsg());
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        hasMore = true;
        index = 1;
        initView(view);

    }

    public class MyLawyerAdapter extends RecyclerView.Adapter<MyLawyerAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cell_my_lawyer, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LawyerDetailActivity.class);
                    intent.putExtra("lawyerId", data.get(position).getLawyerId());

                    startActivity(intent);
                }
            });

            Picasso.with(getActivity()).load(data.get(position).getHeadURL()).into(holder.law_img);
            holder.law_name.setText(data.get(position).getName());
            holder.law_office.setText(data.get(position).getLawyerOfficeName());
            if (data.get(position).isServiced()) {
                holder.law_state.setTextColor(ContextCompat.getColor(getActivity(), R.color.burro_primary_ext));
                holder.law_state.setText("购买过");
            } else {
                holder.law_state.setTextColor(ContextCompat.getColor(getActivity(), R.color.org));
                holder.law_state.setText("已关注");
            }


        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView law_img;
            private TextView law_name, law_state, law_office;

            public ViewHolder(View itemView) {
                super(itemView);
                law_img = (CircleImageView) itemView.findViewById(R.id.law_img);
                law_name = (TextView) itemView.findViewById(R.id.law_name);
                law_state = (TextView) itemView.findViewById(R.id.law_state);
                law_office = (TextView) itemView.findViewById(R.id.law_office);
            }
        }
    }
}
