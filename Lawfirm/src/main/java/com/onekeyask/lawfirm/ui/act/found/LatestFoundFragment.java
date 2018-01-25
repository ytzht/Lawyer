package com.onekeyask.lawfirm.ui.act.found;


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
import com.onekeyask.lawfirm.entity.ResolvedFound;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.EllipsizingTextView;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zht on 2018/01/23 15:35
 */

public class LatestFoundFragment extends BaseFragment {

    private View view;
    private RecyclerView rlv_resolved;
    private ResolvedAdapter adapter;
    private PtrClassicFrameLayout ptrFrameLayout;
    private boolean hasMore = true;
    private int index = 1;
    private int size = 10;
    private List<ResolvedFound.DataBean.FreeaskListBean> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_resolved, container, false);

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

        SubscriberOnNextListener getResultOnNextserver = new SubscriberOnNextListener<ResolvedFound.DataBean>() {
            @Override
            public void onNext(ResolvedFound.DataBean dataBean) {
                ptrFrameLayout.refreshComplete();

                hasMore = dataBean.isHasMore();

                if (index == 1) {
                    list.clear();
                    list.addAll(dataBean.getFreeaskList());
                    adapter = new ResolvedAdapter();
                    rlv_resolved.setAdapter(adapter);
                } else {
                    list.addAll(dataBean.getFreeaskList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getFreeaskOldlist(UserService.service(getActivity()).getLawyerId(), index, size, new ProgressSubscriber<ResolvedFound.DataBean>(getResultOnNextserver, getActivity(), false));


    }

    private void initView(View view) {
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fragment_ptr_service);
        rlv_resolved = (RecyclerView) view.findViewById(R.id.rlv_resolved);
        rlv_resolved.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        rlv_resolved.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private class ResolvedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == position + 1) {
                return R.layout.cell_load_more;
            } else {
                return R.layout.cell_resolved;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_resolved) {
                return new ResolvedAdapter.ViewHolder(view);
            } else {
                return new ResolvedAdapter.ViewHolderMore(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position + 1 != getItemCount()) {
                ((ResolvedAdapter.ViewHolder) holder).found_name.setText(list.get(position).getName());
//                if (list.get(position).getLawyer().getLawyerId().equals("")){
//                    ((ViewHolder) holder).tv_name_now.setTextColor(ContextCompat.getColor(getActivity(), R.color.now_service_no_name));
//                }else {
//                    ((ViewHolder) holder).tv_name_now.setTextColor(ContextCompat.getColor(getActivity(), R.color.now_service_name));
//                }
                ((ResolvedAdapter.ViewHolder) holder).found_price.setText("￥"+list.get(position).getMoney());
                String state = "";
                switch (list.get(position).getStatus()){
                    case 1:
                        state = "未回复";
                        break;
                    case 2:
                        state = "已回复";
                        break;
                    case 3:
                        state = "已完成";
                        break;
                }
                ((ResolvedAdapter.ViewHolder) holder).found_state.setText(state);
                ((ResolvedAdapter.ViewHolder) holder).found_content.setText(list.get(position).getContent());
                Glide.with(getActivity()).load(Uri.parse(list.get(position).getHeadURL()))
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(((ViewHolder) holder).civ_img);
                ((ResolvedAdapter.ViewHolder) holder).found_tag.setText(list.get(position).getCategoryName());
                ((ResolvedAdapter.ViewHolder) holder).found_time.setText(list.get(position).getCreateTime());
                ((ResolvedAdapter.ViewHolder) holder).found_pv.setText(list.get(position).getReadCount()+"阅读");

                ((ResolvedAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if (list.get(position).getType().equals("3")){
//                            startActivity(CallDetailActivity.class,
//                                    "userServiceId", String.valueOf(list.get(position).getServiceId()),
//                                    "oid", "0");
//                        }else {
//                            startActivity(TalkingActivity.class, "chatId", String.valueOf(list.get(position).getTargetId()));
//                        }
                    }
                });
            } else {
                if (hasMore) {
                    ((ResolvedAdapter.ViewHolderMore) holder).progress_bar_more.setVisibility(View.VISIBLE);
                    ((ResolvedAdapter.ViewHolderMore) holder).tv_progress_more.setText("正在加载...");
                } else {
                    ((ResolvedAdapter.ViewHolderMore) holder).progress_bar_more.setVisibility(View.GONE);
                    ((ResolvedAdapter.ViewHolderMore) holder).tv_progress_more.setText("已加载全部");
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView found_name, found_price, found_state, found_tag, found_time, price, found_pv;
            private EllipsizingTextView found_content;
            private CircleImageView civ_img;

            private ViewHolder(View itemView) {
                super(itemView);
                found_name = (TextView) itemView.findViewById(R.id.found_name);
                found_price = (TextView) itemView.findViewById(R.id.found_price);
                found_state = (TextView) itemView.findViewById(R.id.found_state);
                found_content = (EllipsizingTextView) itemView.findViewById(R.id.found_content);
                found_tag = (TextView) itemView.findViewById(R.id.found_tag);
                found_time = (TextView) itemView.findViewById(R.id.found_time);
                found_pv = (TextView) itemView.findViewById(R.id.found_pv);
                civ_img = (CircleImageView) itemView.findViewById(R.id.civ_img);


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
