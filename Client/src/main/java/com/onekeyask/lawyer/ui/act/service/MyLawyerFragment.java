package com.onekeyask.lawyer.ui.act.service;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MyLawyer;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.onekeyask.lawyer.global.Apis.MyLawyerList;

/**
 * Created by zht on 2017/04/12 17:24
 */

public class MyLawyerFragment extends BaseFragment {

    RecyclerView myLawyerList;
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
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

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    private void initView(View view) {

        refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
                index = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
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

        initData();
    }

    private void initData() {

        OkGo.<String>get(MyLawyerList)
                .params("userId", "2")
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLawyer myLawyer = (new Gson()).fromJson(response.body(), MyLawyer.class);
                        hasMore = myLawyer.getData().isHasMore();
                        if (myLawyer.getCode() == 0) {

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_lawyer_ll)
    public void onViewClicked() {

    }

    @Override
    public void onResume() {
        super.onResume();

        index = 1;
        initData();

    }

    public class MyLawyerAdapter extends RecyclerView.Adapter<MyLawyerAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cell_my_lawyer, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShort("v");
                }
            });

            Glide.with(getActivity()).load(data.get(position).getHeadURL()).into(holder.law_img);
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
