package com.onekeyask.lawyer.ui.act.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.MyMsg;
import com.onekeyask.lawyer.entity.ResultData;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.onekeyask.lawyer.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopMsgActivity extends BaseToolBarActivity {

    @BindView(R.id.my_msg_list)
    SwipeMenuRecyclerView myMsgList;
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

        myMsgList = (SwipeMenuRecyclerView) findViewById(R.id.my_msg_list);
        myMsgList.setLayoutManager(new LinearLayoutManager(this));
        myMsgList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        adapter = new MyMsgAdapter();
        myMsgList.setAdapter(adapter);

// 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.burro_size_70dp);

                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                SwipeMenuItem closeItem = new SwipeMenuItem(getBaseContext())
                        .setBackground(R.drawable.selector_ext)
                        .setText("删除")
                        .setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

            }
        };
        // 设置监听器。
        myMsgList.setSwipeMenuCreator(mSwipeMenuCreator);
        myMsgList.setSwipeMenuItemClickListener(mMenuItemClickListener);

        initData();
    }

    private void initData() {
        OkGo.<String>get(Apis.MessageList)
                .params("userId", UserService.service(getBaseContext()).getUserId())
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
                                if (myMsg.getData().getMessageList() == null){

                                }else {
                                    data.addAll(myMsg.getData().getMessageList());
                                }
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
            holder.type_time.setText(data.get(position).getCreateTime());

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


    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

//                showShort(list.getData().getCardList().get(adapterPosition).getId() + "");
                new AlertDialog.Builder(TopMsgActivity.this)
                        .setTitle("注意").setMessage("确定要删除此条消息吗？").setCancelable(true).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkGo.<String>get(Apis.MsgDelete).params("userId", UserService.service(getBaseContext()).getUserId())
                                .params("messageId", data.get(adapterPosition).getMessageId()).execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0) {
                                    showShort("删除成功");
                                    initData();
                                } else {
                                    showShort(data.getMsg());
                                }
                            }
                        });
                    }
                }).show();


//                Toast.makeText(getBaseContext(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(getBaseContext(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
