package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MyMsg;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.Apis;
import com.onekeyask.lawfirm.utils.MyDecoration;
import com.onekeyask.lawfirm.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopMsgActivity extends BaseToolBarActivity {

    @BindView(R.id.my_msg_list)
    RecyclerView myMsgList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MyMsgAdapter adapter;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private List<MyMsg.DataBean.MessageListBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_msg);
        ButterKnife.bind(this);
        setToolbarText("消息中心");

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();

        index = 1;
        initData();

    }

    private void initView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
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

        myMsgList = (RecyclerView) findViewById(R.id.my_msg_list);
        myMsgList.setLayoutManager(new LinearLayoutManager(this));
        myMsgList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        adapter = new MyMsgAdapter();
        myMsgList.setAdapter(adapter);
        initData();
    }

    private void initData() {
        OkGo.<String>get(Apis.MessageList)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyMsg myMsg = (new Gson()).fromJson(response.body(), MyMsg.class);
                        hasMore = myMsg.getData().isHasMore();
                        if (myMsg.getCode() == 0) {

                            if (index == 1) {
                                data.clear();
                                data.addAll(myMsg.getData().getMessageList());
                                myMsgList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                data.addAll(myMsg.getData().getMessageList());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(myMsg.getMsg());
                        }
                    }
                });

    }


    private class MyMsgAdapter extends RecyclerView.Adapter<MyMsgAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_my_msg, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            if (data.get(position).getStatus() == 0) {
                holder.is_read.setVisibility(View.VISIBLE);
            } else {
                holder.is_read.setVisibility(View.GONE);
            }

            holder.rl_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MsgDetailActivity.class, "id", "" + data.get(position).getMessageId());
                }
            });

            holder.msg_title.setText(data.get(position).getContent());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            holder.type_time.setText(format.format(data.get(position).getCreateTime()));

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView is_read, msg_title, type_time;
            private RelativeLayout rl_msg;

            public ViewHolder(View itemView) {
                super(itemView);

                is_read = (TextView) itemView.findViewById(R.id.is_read);
                msg_title = (TextView) itemView.findViewById(R.id.msg_title);
                type_time = (TextView) itemView.findViewById(R.id.type_time);
                rl_msg = (RelativeLayout) itemView.findViewById(R.id.rl_msg);
            }
        }
    }
}
