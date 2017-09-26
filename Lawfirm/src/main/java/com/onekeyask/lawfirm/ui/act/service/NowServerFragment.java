package com.onekeyask.lawfirm.ui.act.service;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ConversationChatList;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.UserService;
import com.squareup.picasso.Picasso;

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
    private List<ConversationChatList.ServiceListBean> list = new ArrayList<>();

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
        initView(view);
    }

    private void initData() {

        SubscriberOnNextListener getResultOnNextserver = new SubscriberOnNextListener<ConversationChatList>() {
            @Override
            public void onNext(ConversationChatList chatList) {
                ptrFrameLayout.refreshComplete();

                hasMore = chatList.isHasMore();

                if (index == 1) {
                    list.clear();
                    list.addAll(chatList.getServiceList());
                    adapter = new NowServerAdapter();
                    rlv_now_ser.setAdapter(adapter);
                } else {
                    list.addAll(chatList.getServiceList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getConversationChatList(UserService.service(getActivity()).getLawyerId(), index, size, "1", 0, new ProgressSubscriber<ConversationChatList>(getResultOnNextserver, getActivity(), false));


    }

    private void initView(View view) {
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fragment_ptr_service);
        rlv_now_ser = (RecyclerView) view.findViewById(R.id.rlv_now_ser);
        rlv_now_ser.setLayoutManager(new LinearLayoutManager(getActivity()));

        ptrFrameLayout.setDurationToCloseHeader(1500);
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
                L.d("=====getLastServiceTime", list.get(position).getLastServiceTime());
                ((ViewHolder) holder).tv_content_now.setText(list.get(position).getServiceContent());
//                if (list.get(position).getLawyer().getLawyerId().equals("")){
//                    ((ViewHolder) holder).tv_name_now.setTextColor(ContextCompat.getColor(getActivity(), R.color.now_service_no_name));
//                }else {
//                    ((ViewHolder) holder).tv_name_now.setTextColor(ContextCompat.getColor(getActivity(), R.color.now_service_name));
//                }
                ((ViewHolder) holder).tv_name_now.setText(list.get(position).getUser().getPhoneNo());
                ((ViewHolder) holder).tv_time_now.setText(list.get(position).getLastServiceTime());
                switch (list.get(position).getType()) {
                    case "1":
                        ((ViewHolder) holder).tv_tag_now.setText("快速咨询");
                        break;
                    case "2":
                        ((ViewHolder) holder).tv_tag_now.setText("图文咨询");
                        break;
                    case "3":
                        ((ViewHolder) holder).tv_tag_now.setText("电话咨询");
                        break;
                    case "4":
                        ((ViewHolder) holder).tv_tag_now.setText("私人律师");
                        break;
                    default:
                        ((ViewHolder) holder).tv_tag_now.setText(list.get(position).getType());
                        break;
                }
                L.d("=====getHeadURL()", list.get(position).getUser().getHeadURL());
                Picasso.with(getActivity()).load(Uri.parse(list.get(position).getUser().getHeadURL()))
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(((ViewHolder) holder).civ_avatar_now);
                ((ViewHolder) holder).status.setText(list.get(position).getStatus());
                ((ViewHolder) holder).price.setText(list.get(position).getServiceAmount() + "元");
                if (((ViewHolder) holder).status.getText().equals("律师未回复")) {
                    ((ViewHolder) holder).status.setTextColor(ContextCompat.getColor(getActivity(), R.color.blackHint));
                } else {
                    ((ViewHolder) holder).status.setTextColor(ContextCompat.getColor(getActivity(), R.color.baseOrange));
                }
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(TalkingActivity.class, "chatId", String.valueOf(list.get(position).getTargetId()));
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

            private TextView tv_content_now, tv_tag_now, tv_time_now, tv_name_now, status, price;
            private CircleImageView civ_avatar_now;

            private ViewHolder(View itemView) {
                super(itemView);
                tv_content_now = (TextView) itemView.findViewById(R.id.tv_content_now);
                tv_tag_now = (TextView) itemView.findViewById(R.id.tv_tag_now);
                tv_time_now = (TextView) itemView.findViewById(R.id.tv_time_now);
                price = (TextView) itemView.findViewById(R.id.tv_price);
                tv_name_now = (TextView) itemView.findViewById(R.id.tv_name_now);
                status = (TextView) itemView.findViewById(R.id.status);
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
