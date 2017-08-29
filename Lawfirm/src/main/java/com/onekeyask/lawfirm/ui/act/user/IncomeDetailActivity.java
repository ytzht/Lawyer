package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.IncomeDetail;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;


public class IncomeDetailActivity extends BaseToolBarActivity {

    private android.support.v7.widget.RecyclerView rlvincome;
    private IncomeAdapter adapter;
    private boolean hasMore = true;
    private int index = 1;
    private int size = 10;
    private List<IncomeDetail.DataBean.IncomeListBean> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        this.rlvincome = (RecyclerView) findViewById(R.id.rlv_income);
        setToolbarText("收入详情");
        index = 1;
        rlvincome.setLayoutManager(new LinearLayoutManager(this));
        rlvincome.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new IncomeAdapter();
        rlvincome.setAdapter(adapter);
        rlvincome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition()
                            == recyclerView.getLayoutManager().getItemCount() - 1) {
                        if (hasMore) {
                            index += 1;
                            initData();
                        }
                    }
                }
            }
        });

        initData();
    }

    private void initData() {

        OkGo.<String>get(Apis.IncomeDetail)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("year", 2017)
                .params("month", 8)
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                IncomeDetail detail = (new Gson()).fromJson(response.body(), IncomeDetail.class);
                if (detail.getCode() == 0){

                }else {
                    showShort(detail.getMsg());
                    finish();
                }
            }
        });



    }

    private class IncomeAdapter extends RecyclerView.Adapter{

        @Override
        public int getItemViewType(int position) {





            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }




    }
}
