package com.onekeyask.lawfirm.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.FoundFrag;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.ui.act.talk.AskDetailActivity;
import com.onekeyask.lawfirm.utils.MyDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeFoundFragment extends BaseFragment {

    private View view;
    private RecyclerView rlv_found_frag;
    private FoundFragAdapter adapter;
    private boolean hasMore = true;
    private List<FoundFrag.FreeaskListBean> listBeen = new ArrayList<>();
    private int index = 1;
    private int size = 6;
    private PtrClassicFrameLayout ptrFrameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_four_fragment, container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        initData();
    }

    private void initView() {
        rlv_found_frag = (RecyclerView) view.findViewById(R.id.rlv_found_frag);
        rlv_found_frag.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlv_found_frag.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new FoundFragAdapter();
        initData();

        rlv_found_frag.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fragment_ptr_found);
        StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("discover");
        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
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
//        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

    }

    private void initData() {


        SubscriberOnNextListener listener = new SubscriberOnNextListener<FoundFrag>() {
            @Override
            public void onNext(FoundFrag foundFrag) {
                ptrFrameLayout.refreshComplete();
                hasMore = foundFrag.isHasMore();

                if (index == 1) {
                    listBeen.clear();
                    listBeen.addAll(foundFrag.getFreeaskList());
                    rlv_found_frag.setAdapter(adapter);
                } else {
                    listBeen.addAll(foundFrag.getFreeaskList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getFoundFragList(3, index, size, new ProgressSubscriber<FoundFrag>(listener, getActivity(), false));
    }

    private class FoundFragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == position + 1) {
                return R.layout.cell_load_more;
            } else {
                return R.layout.cell_found_frag;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_found_frag) {
                return new ViewHolder(view);
            } else {
                return new ViewHolderMore(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position + 1 != getItemCount()) {
                ((ViewHolder) holder).tv_time_found.setText(listBeen.get(position).getCreateTime());
                ((ViewHolder) holder).tv_categoryName_found.setText(listBeen.get(position).getCategoryName());
                ((ViewHolder) holder).tv_content_found.setText(listBeen.get(position).getContent());

                if (listBeen.get(position).getType().equals("1")) {
                    ((ViewHolder) holder).ll_money_found.setVisibility(View.GONE);
                } else if (listBeen.get(position).getType().equals("2")) {
                    ((ViewHolder) holder).ll_money_found.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_money_found.setText(String.valueOf(listBeen.get(position).getMoney() + "￥"));
                }
                ((ViewHolder) holder).tv_name_found.setText(listBeen.get(position).getName());
                Glide.with(getActivity()).load(listBeen.get(position).getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(((ViewHolder) holder).civ_avatar_found);

                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(AskDetailActivity.class, "freeAskId", listBeen.get(position).getFreeaskId() + "");
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
            return listBeen.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_time_found, tv_categoryName_found, tv_content_found, tv_name_found, tv_money_found;
            private CircleImageView civ_avatar_found;
            private LinearLayout ll_money_found;


            ViewHolder(View itemView) {
                super(itemView);
                tv_time_found = (TextView) itemView.findViewById(R.id.tv_time_found);
                tv_categoryName_found = (TextView) itemView.findViewById(R.id.tv_categoryName_found);
                tv_content_found = (TextView) itemView.findViewById(R.id.tv_content_found);
                tv_name_found = (TextView) itemView.findViewById(R.id.tv_name_found);
                tv_money_found = (TextView) itemView.findViewById(R.id.tv_money_found);
                civ_avatar_found = (CircleImageView) itemView.findViewById(R.id.civ_avatar_found);
                ll_money_found = (LinearLayout) itemView.findViewById(R.id.ll_money_found);
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
