package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.BillingDetails;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class BillingDetailsActivity extends BaseToolBarActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView rlv_integral;
    private BillingDetailsAdapter adapter;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private List<BillingDetails.DataBean.WithdrawHistoriesBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        setToolbarText("提现明细");

//        startActivity(WithStateActivity.class);

        initView();
    }


    private void initView() {
        index = 1;
        data.clear();


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
        adapter = new BillingDetailsAdapter();
        rlv_integral.setAdapter(adapter);
        initData();

    }

    private void initData() {
        OkGo.<String>get(Apis.TxHistory)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BillingDetails history = (new Gson()).fromJson(response.body(), BillingDetails.class);
                        hasMore = history.getData().isHasMore();
                        if (history.getCode() == 0) {
                            if (index == 1) {
                                data.clear();
                                if (history.getData().getWithdrawHistories() == null) {
                                    showShort("暂无数据");
                                } else {
                                    data.addAll(history.getData().getWithdrawHistories());
                                }
                                rlv_integral.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                data.addAll(history.getData().getWithdrawHistories());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(history.getMsg());
                        }
                    }
                });

    }


    private class BillingDetailsAdapter extends RecyclerView.Adapter<BillingDetailsAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_item_billing_details, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.detail_time.setText(data.get(position).getCreateTime());
            holder.detail_title.setText("提现");
            holder.detail_score.setText("-" + data.get(position).getMoney());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(WithStateActivity.class, "id", data.get(position).getId() + "");
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView detail_score, detail_title, detail_time;
            private ImageView iv_arrow;

            public ViewHolder(View itemView) {
                super(itemView);
                detail_score = (TextView) itemView.findViewById(R.id.detail_score);
                detail_title = (TextView) itemView.findViewById(R.id.detail_title);
                detail_time = (TextView) itemView.findViewById(R.id.detail_time);
                iv_arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            }
        }
    }
}
