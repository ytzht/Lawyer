package com.onekeyask.lawyer.ui.act.service;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.ChatList;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.consulting.TalkingActivity;
import com.onekeyask.lawyer.utils.MyDecoration;
import com.onekeyask.lawyer.utils.UserService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zht on 2017/04/12 17:24
 */

public class CompleteServiceFragment extends BaseFragment {

    private View view;
    private RecyclerView rlv_now_ser;
    private NowServerAdapter adapter;
    private RelativeLayout rl_ser_list;
    private boolean hasMore = true;
    private List<ChatList.ServiceListBean> listBeen = new ArrayList<>();
    private int index = 1;
    private int size = 10;
    private int type = 0;
    private TextView tv_spinner_type, tv_1, tv_2, tv_3, tv_4;
    private PtrClassicFrameLayout ptrFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_server_com, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        initData();
    }


    private void initView(View view) {
        rl_ser_list = (RelativeLayout) view.findViewById(R.id.rl_ser_list);
        rlv_now_ser = (RecyclerView) view.findViewById(R.id.rlv_now_ser);
        tv_spinner_type = (TextView) view.findViewById(R.id.tv_spinner_type);
        rlv_now_ser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlv_now_ser.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        adapter = new NowServerAdapter();
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.service_type, null);
        tv_1 = (TextView) contentView.findViewById(R.id.tv_type_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                index = 1;
                initData();
                popupWindow.dismiss();
                tv_spinner_type.setText(tv_1.getText());
            }
        });
        tv_2 = (TextView)contentView.findViewById(R.id.tv_type_2);
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                index = 1;
                initData();
                popupWindow.dismiss();
                tv_spinner_type.setText(tv_2.getText());
            }
        });
        tv_3 = (TextView) contentView.findViewById(R.id.tv_type_3);
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                index = 1;
                initData();
                popupWindow.dismiss();
                tv_spinner_type.setText(tv_3.getText());
            }
        });
        tv_4 = (TextView) contentView.findViewById(R.id.tv_type_4);
        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                index = 1;
                initData();
                popupWindow.dismiss();
                tv_spinner_type.setText(tv_4.getText());
            }
        });
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

        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_now_ser);
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

        rl_ser_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                popupWindow = getPopwindow(contentView);
                popupWindow.showAsDropDown(rl_ser_list);


            }
        });


    }

    private PopupWindow popupWindow = null;
    private View contentView;
    //跳出选项框
    public PopupWindow getPopwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
//        layoutParams.alpha = 1f;
        getActivity().getWindow().setAttributes(layoutParams);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.showAtLocation(getActivity().findViewById(R.id.rl_ser_list), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(rl_ser_list);
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
//        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.update();
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
//                layoutParams.alpha = 1f;
                getActivity().getWindow().setAttributes(layoutParams);
            }
        });
        return popupWindow;
    }

    private void initData() {

        SubscriberOnNextListener listener = new SubscriberOnNextListener<ChatList>() {
            @Override
            public void onNext(ChatList chatList) {
                ptrFrameLayout.refreshComplete();
                hasMore = chatList.isHasMore();

                if (index == 1) {
                    listBeen.clear();
                    listBeen.addAll(chatList.getServiceList());
                    rlv_now_ser.setAdapter(adapter);
                } else {
                    listBeen.addAll(chatList.getServiceList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getChatList(UserService.service(getActivity()).getUserId(), index, size, "2", type, new ProgressSubscriber<ChatList>(listener, getActivity(), false));


    }


    private class NowServerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == position + 1) {
                return R.layout.cell_load_more;
            } else {
                return R.layout.cell_complete_server;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_complete_server) {
                return new ViewHolder(view);
            } else {
                return new ViewHolderMore(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position + 1 != getItemCount()) {
                ((ViewHolder) holder).tv_content_now.setText(listBeen.get(position).getServiceContent());
                ((ViewHolder) holder).tv_name_now.setText(listBeen.get(position).getLawyer().getName());
                ((ViewHolder) holder).tv_time_now.setText(listBeen.get(position).getLastServiceTime());
                switch (listBeen.get(position).getType()) {
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
                        ((ViewHolder) holder).tv_tag_now.setText("咨询");
                }
                ((ViewHolder) holder).status.setText(listBeen.get(position).getStatus());
                Glide.with(getActivity()).load(Uri.parse(listBeen.get(position).getLawyer().getHeadURL()))
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(((ViewHolder) holder).civ_avatar_now);

                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(TalkingActivity.class, "fid", "0", "cid", String.valueOf(listBeen.get(position).getTargetId()), "sid", listBeen.get(position).getServiceId());
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

            private TextView tv_content_now, tv_tag_now, tv_time_now, tv_name_now, status;
            private CircleImageView civ_avatar_now;

            private ViewHolder(View itemView) {
                super(itemView);
                tv_content_now = (TextView) itemView.findViewById(R.id.tv_content_now);
                tv_tag_now = (TextView) itemView.findViewById(R.id.tv_tag_now);
                tv_time_now = (TextView) itemView.findViewById(R.id.tv_time_now);
                status = (TextView) itemView.findViewById(R.id.status);
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
