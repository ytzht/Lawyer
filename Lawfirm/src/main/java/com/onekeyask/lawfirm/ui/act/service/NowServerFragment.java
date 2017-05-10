package com.onekeyask.lawfirm.ui.act.service;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ConversationChatList;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.MyDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by zht on 2017/04/12 17:23
 */

public class NowServerFragment extends BaseFragment {

    private View view;
    private RecyclerView rlv_now_ser;
    private NowServerAdapter adapter;
    private PtrClassicFrameLayout ptrFrameLayout;
    private boolean hasMore = true;
    private int index = 1;
    private int size = 10;
    private List<ConversationChatList.ChatListBean> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_server_now, container, false);

        initView(view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        initData();
    }

    private void initData() {

        SubscriberOnNextListener getResultOnNextserver = new SubscriberOnNextListener<ConversationChatList>() {
            @Override
            public void onNext(ConversationChatList chatList) {
                ptrFrameLayout.refreshComplete();

                hasMore = chatList.isHasMore();

                if (index == 1) {
                    list.clear();
                    list.addAll(chatList.getChatList());
                    rlv_now_ser.setAdapter(adapter);
                } else {
                    list.addAll(chatList.getChatList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getConversationChatList(3, index, size, "1", 0, new ProgressSubscriber<ConversationChatList>(getResultOnNextserver, getActivity(), false));


    }

    private void initView(View view) {
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fragment_ptr_service);
        rlv_now_ser = (RecyclerView) view.findViewById(R.id.rlv_now_ser);
        rlv_now_ser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlv_now_ser.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new NowServerAdapter();
//        StoreHouseHeader header = new StoreHouseHeader(getActivity());
//        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
//        header.initWithString("discover");
        ptrFrameLayout.setDurationToCloseHeader(1500);
//        ptrFrameLayout.setHeaderView(header);
//        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index = 1;
                        initData();
                    }
                }, 500);
            }
        });

        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        initData();

        rlv_now_ser.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    }

    private class NowServerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == position + 1) {
                return R.layout.cell_load_more;
            } else {
                return R.layout.cell_now_server;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_now_server) {
                return new ViewHolder(view);
            } else {
                return new ViewHolderMore(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position + 1 != getItemCount()) {
                ((ViewHolder) holder).tv_content_now.setText(list.get(position).getLastWord());
                ((ViewHolder) holder).tv_name_now.setText(list.get(position).getUser().getName());
                ((ViewHolder) holder).tv_time_now.setText(list.get(position).getLastWordTime());
                switch (list.get(position).getType()) {
                    case 0:
                        ((ViewHolder) holder).tv_tag_now.setText("0");
                        break;
                    case 1:
                        ((ViewHolder) holder).tv_tag_now.setText("图文订单");
                        break;
                    case 2:
                        ((ViewHolder) holder).tv_tag_now.setText("免费提问");
                        break;
                    case 3:
                        ((ViewHolder) holder).tv_tag_now.setText("打赏咨询");
                        break;
                }
                Glide.with(getActivity()).load(Uri.parse(list.get(position).getUser().getHeadURL()))
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(((ViewHolder) holder).civ_avatar_now);

                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(TalkingActivity.class, "chatId", String.valueOf(list.get(position).getChatId()));
                    }
                });
            } else {
                if (hasMore) {
                    ((ViewHolderMore) holder).progress_bar_more.setVisibility(View.VISIBLE);
                    ((ViewHolderMore) holder).tv_progress_more.setText("正在加载...");
                } else {
                    ((ViewHolderMore) holder).progress_bar_more.setVisibility(View.GONE);
                    ((ViewHolderMore) holder).tv_progress_more.setText("已加载全部");
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_content_now, tv_tag_now, tv_time_now, tv_name_now;
            private CircleImageView civ_avatar_now;

            private ViewHolder(View itemView) {
                super(itemView);
                tv_content_now = (TextView) itemView.findViewById(R.id.tv_content_now);
                tv_tag_now = (TextView) itemView.findViewById(R.id.tv_tag_now);
                tv_time_now = (TextView) itemView.findViewById(R.id.tv_time_now);
                tv_name_now = (TextView) itemView.findViewById(R.id.tv_name_now);
                civ_avatar_now = (CircleImageView) itemView.findViewById(R.id.civ_avatar_now);


            }
        }

        class ViewHolderMore extends RecyclerView.ViewHolder {

            private ProgressBar progress_bar_more;
            private TextView tv_progress_more;

            ViewHolderMore(View itemView) {
                super(itemView);
                tv_progress_more = (TextView) itemView.findViewById(R.id.tv_progress_more);
                progress_bar_more = (ProgressBar) itemView.findViewById(R.id.progress_bar_more);

            }
        }

    }

}
