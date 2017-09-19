package com.onekeyask.lawfirm.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.FoundFragm;
import com.onekeyask.lawfirm.entity.FreeAskOrder;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.ui.act.service.TalkingActivity;
import com.onekeyask.lawfirm.utils.MyDecoration;
import com.onekeyask.lawfirm.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SimpleCardFragment extends BaseFragment {
    private int id;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView discover_list;
    private View view;
    private DiscoverAdapter adapter;
    private int index = 1;
    private RelativeLayout rl_no_content;
    private int size = 10;
    private boolean hasMore = true;
    private List<FoundFragm.DataBean.FreeaskListBean> data = new ArrayList<>();

    public static SimpleCardFragment getInstance(int id) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.id = id;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_card, null);
        initView();

        return view;
    }

    private void initView() {
        index = 1;
        data.clear();
        rl_no_content = (RelativeLayout) view.findViewById(R.id.rl_no_content);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.discover_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
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


        discover_list = (RecyclerView) view.findViewById(R.id.discover_list);
        discover_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        discover_list.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new DiscoverAdapter();
        discover_list.setAdapter(adapter);
        initData();

    }


    private void initData() {

        OkGo.<String>get(Apis.FoundFragList).params("lawyerId", UserService.service(getActivity()).getLawyerId())
                .params("page", index).params("size", size).params("category", id).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                FoundFragm foundFrag = (new Gson()).fromJson(response.body(), FoundFragm.class);
                if (foundFrag.getCode() == 0){
                    hasMore = foundFrag.getData().isHasMore();
                    if (index == 1) {
                        data.clear();
                        data.addAll(foundFrag.getData().getFreeaskList());
                        discover_list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        if (data.size() == 0) {
                            rl_no_content.setVisibility(View.VISIBLE);
                        } else {
                            rl_no_content.setVisibility(View.GONE);
                        }

                    } else {
                        data.addAll(foundFrag.getData().getFreeaskList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

//        SubscriberOnNextListener listener = new SubscriberOnNextListener<FoundFrag>() {
//            @Override
//            public void onNext(FoundFrag foundFrag) {
////                UserDiscoveries discoveries = (new Gson()).fromJson(response.body(), UserDiscoveries.class);
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                showShort(message);
//            }
//        };
//
//        retrofitUtil.getFoundFragList(, index, size, new ProgressSubscriber<FoundFrag>(listener, getActivity(), false));
    }

    private AlertDialog alertDialog;

    private class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.cell_discover, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.dis_type.setText(data.get(position).getCategoryName());
            holder.dis_context.setText(data.get(position).getContent());
            holder.dis_name.setText(data.get(position).getName());
//            holder.dis_office.setText(data.get(position).get());
//            holder.dis_count.setText(String.valueOf(data.get(position).getSupportCount()));
            Glide.with(getActivity()).load(data.get(position).getHeadURL()).into(holder.dis_img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_order, null);

                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setView(dialogView);
                    alertDialog.setCancelable(true);
                    alertDialog.show();

                    TextView tv_msg = (TextView) dialogView.findViewById(R.id.alert_tv_msg);
                    TextView cancel_tv = (TextView) dialogView.findViewById(R.id.dialog_cancel_tv);
                    TextView next_tv = (TextView) dialogView.findViewById(R.id.dialog_next_tv);

                    tv_msg.setText("您是否要确认回答客户：" + data.get(position).getName() + "的问题，如果回答请点击继续，钱自动打到您的账户，如果不是请点击取消！");
                    cancel_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (alertDialog.isShowing()) alertDialog.dismiss();
                        }
                    });
                    next_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (alertDialog.isShowing()) alertDialog.dismiss();
                            grabSingle(data.get(position).getFreeaskId());

                        }
                    });

//                    startActivity(AskDetailActivity.class, "freeAskId", data.get(position).getFreeaskId() + "");
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView dis_type, dis_context, dis_name, dis_office, dis_count;
            private ImageView dis_praise;
            private CircleImageView dis_img;

            public ViewHolder(View itemView) {
                super(itemView);
                dis_type = (TextView) itemView.findViewById(R.id.dis_type);
                dis_context = (TextView) itemView.findViewById(R.id.dis_context);
                dis_name = (TextView) itemView.findViewById(R.id.dis_name);
                dis_office = (TextView) itemView.findViewById(R.id.dis_office);
                dis_count = (TextView) itemView.findViewById(R.id.dis_count);
                dis_praise = (ImageView) itemView.findViewById(R.id.dis_praise);
                dis_img = (CircleImageView) itemView.findViewById(R.id.dis_img);
            }
        }

        private void grabSingle(int freeAskId) {

            OkGo.<String>get(Apis.FreeAskOrder).params("freeaskId", freeAskId)
                    .params("lawyerId", UserService.service(getActivity()).getLawyerId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            FreeAskOrder order = (new Gson()).fromJson(response.body(), FreeAskOrder.class);
                            if (order.getCode() == 0) {
                                index = 1;
                                initData();
                                startActivity(TalkingActivity.class, "chatId", order.getData().getChatId() + "");
                            } else {
                                freeAskOrder();
                            }
                        }
                    });
        }


//            SubscriberOnNextListener listener = new SubscriberOnNextListener<FreeAskOrder>() {
//                @Override
//                public void onNext(FreeAskOrder order) {
//

//                }
//
//                @Override
//                public void onError(int code, String message) {
////                    showShort(message);
//                    freeAskOrder();
//                }
//            };
//
//            retrofitUtil.getFreeAskOrder(freeAskId+"", UserService.service(getActivity()).getLawyerId(),
//                    new ProgressSubscriber<FreeAskOrder>(listener, getActivity(), true));


        private void freeAskOrder() {
            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_order, null);

            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(dialogView);
            alertDialog.setCancelable(true);
            alertDialog.show();

            TextView tv_msg = (TextView) dialogView.findViewById(R.id.alert_tv_msg);
            TextView dialog_tv_title = (TextView) dialogView.findViewById(R.id.dialog_tv_title);
            TextView cancel_tv = (TextView) dialogView.findViewById(R.id.dialog_cancel_tv);
            TextView next_tv = (TextView) dialogView.findViewById(R.id.dialog_next_tv);
            dialog_tv_title.setText("抢单提示");
            tv_msg.setText("不好意思，您来晚了，该订单已被其他律师抢了，您可以看看其他的！");
            cancel_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertDialog.isShowing()) alertDialog.dismiss();
                }
            });
            next_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertDialog.isShowing()) alertDialog.dismiss();

                }
            });
        }


    }


}