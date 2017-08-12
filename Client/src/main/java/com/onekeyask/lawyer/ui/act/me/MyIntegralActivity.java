package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.PointHistory;
import com.onekeyask.lawyer.entity.PointsInfo;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyIntegralActivity extends BaseToolBarActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView rlv_integral;
    private TextView integral;
    private MyIntegralAdapter adapter;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private List<PointHistory.DataBean.PointsHistoriesBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        setToolbarText("我的积分");


        initView();
    }

    private void initView() {
        index = 1;
        data.clear();

        integral = (TextView)findViewById(R.id.integral);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        rlv_integral = (RecyclerView) findViewById(R.id.rlv_integral);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
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

        rlv_integral.setLayoutManager(new LinearLayoutManager(this));
        rlv_integral.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MyIntegralAdapter();
        rlv_integral.setAdapter(adapter);
        initData();

    }

    private void initData() {

        getResultOnNext = new SubscriberOnNextListener<PointsInfo>() {
            @Override
            public void onNext(PointsInfo info) {
                integral.setText(String.valueOf(info.getPoints()));
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getPointsInfo("2", new ProgressSubscriber<PointsInfo>(getResultOnNext, MyIntegralActivity.this, false));



        OkGo.<String>get(Apis.PointsHistory)
                .params("userId", "2")
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        PointHistory history = (new Gson()).fromJson(response.body(), PointHistory.class);
                        hasMore = history.getData().isHasMore();
                        if (history.getCode() == 0) {
                            if (index == 1) {
                                data.clear();
                                data.addAll(history.getData().getPointsHistories());
                                rlv_integral.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                data.addAll(history.getData().getPointsHistories());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(history.getMsg());
                        }
                    }
                });
    }

    private class MyIntegralAdapter extends RecyclerView.Adapter<MyIntegralAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_item_integral, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.title_in.setText(data.get(position).getSummary());
            holder.tv_in_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.get(position).getTranTime()));
            holder.tv_integral.setText("+"+data.get(position).getNumber());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView tv_integral, title_in, tv_in_time;
            public ViewHolder(View itemView) {
                super(itemView);
                tv_integral = (TextView)itemView.findViewById(R.id.tv_integral);
                title_in = (TextView)itemView.findViewById(R.id.title_in);
                tv_in_time = (TextView)itemView.findViewById(R.id.tv_in_time);

            }
        }
    }
}
